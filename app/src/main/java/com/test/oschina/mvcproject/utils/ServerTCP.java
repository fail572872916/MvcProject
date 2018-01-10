package com.test.oschina.mvcproject.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;

public class ServerTCP {
	private final String TAG;
	// 变量
	private ServerSocket serverSocket;
	private Socket socket;
	private ExecutorService cachedThreadPool;
	private int runnableID;
	// 常量
	public final int Handler_CreateSucs = 0x00;
	public final int Handler_CreateFail = 0x01;
	public final int Handler_NewConn = 0x02;
	public final int Handler_ConnClose = 0x03;
	public final int Handler_ServerClose = 0x04;
	public final int Handler_RcvMsg = 0x05;
	public final int Handler_SendMsg = 0x06;
	public final int Msg_ParamsByte = 0xF0;
	public final int Msg_ParamsString = 0xF1;
	public final int Msg_ParamsTogether = 0xF2;
	private final Map<String, SocketRunnable> mapSockets;
	private final Map<String, String> mapAddress;
	// 回调
	private ServerReceive serverReceive;
	@SuppressLint("ObsoleteSdkInt")
	public ServerTCP(Context context) {
		this.TAG = getClass().getSimpleName();
		this.mapSockets = new HashMap<String, SocketRunnable>();
		this.mapAddress = new HashMap<String, String>();
		this.cachedThreadPool = Executors.newCachedThreadPool();
		this.runnableID = 1;

	}
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (serverReceive != null) {
				if (msg.obj == null) {
					serverReceive.result(msg.what, null, null);
				} else if (msg.obj instanceof byte[]) {
					String address = null;
					if (msg.arg1 > 0) {
						address = mapAddress.get(msg.arg1 + "");
					}
					serverReceive.result(msg.what, address, (byte[]) msg.obj);
				} else if (msg.obj instanceof String) {
					serverReceive.result(msg.what, msg.obj.toString(), null);
				}
				if (msg.what == Handler_ServerClose) {
					serverReceive = null;
				}
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * @param port
	 *              需要打开的端口
	 * @param serverReceive
	 *              数据回调接口
	 */
	public void initServer(final int port, ServerReceive serverReceive) {
		this.serverReceive = serverReceive;
		// 线程池里创建服服务器
		this.cachedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					serverSocket = new ServerSocket(port);
					handler.sendEmptyMessage(Handler_CreateSucs);
					while ((socket = serverSocket.accept()) != null) {
						String ip = socket.getInetAddress().getHostAddress();
						int port = socket.getPort();
						String address = ip + ":" + port;
						LogSwitch.i(TAG, "run", "New connection have came=" + address);
						Message msg = new Message();
						msg.what = Handler_NewConn;
						msg.obj = address;
						handler.sendMessage(msg);
						// 新的连接
						SocketRunnable runnableNew = new SocketRunnable(socket, runnableID);
						runnableID++;
						mapSockets.put(address, runnableNew);
						cachedThreadPool.execute(runnableNew);
					}
				} catch (IOException e) {
					clsoeServer();
					LogSwitch.e(TAG, "initServer", "IOException", e);
				}
			}
		});
		int amount = ((ThreadPoolExecutor) cachedThreadPool).getActiveCount();
		LogSwitch.d(TAG, "initConnection", "Now the thread amount = " + amount);
	}

	// 内部类创建线程
	public class SocketRunnable implements Runnable {
		private final int id;
		private String TAG;
		private Socket socket;
		private String address;
		private InputStream inputStream;
		public OutputStream outputStream;

		public SocketRunnable(Socket socket, int id) {
			this.id = id;
			this.TAG = getClass().getSimpleName();
			this.socket = socket;
			String ip = socket.getInetAddress().getHostAddress();
			int port = socket.getPort();
			this.address = ip + ":" + port;
			// 关联id和address
			ServerTCP.this.mapAddress.put(id + "", address);
			try {
				this.inputStream = socket.getInputStream();
				this.outputStream = socket.getOutputStream();
			} catch (IOException e) {
				LogSwitch.e(TAG, "RunnableSocket", "IOException", e);
			}
		}

		@Override
		public void run() {
			try {
				byte[] buffer = new byte[8192];
				byte[] buff;
				while (inputStream != null) {
					int length = inputStream.read(buffer);
					if (length <= -1) {
						// 关闭连接
						colse();
						break;
					}
					buff = new byte[length];
					System.arraycopy(buffer, 0, buff, 0, length);
					// Handler发送消息
					Message msg = new Message();
					msg.what = Handler_RcvMsg;
					msg.obj = buff;
					msg.arg1 = id;
					handler.sendMessage(msg);
				}
			} catch (IOException e) {
				LogSwitch.e(TAG, "run", "IOException", e);
			}
		}

		// 关闭连接
		public void colse() {
			Message msg = new Message();
			msg.what = Handler_ConnClose;
			msg.obj = address;
			handler.sendMessage(msg);
			try {
				if (socket != null) {
					socket.close();
					socket = null;
				}
			} catch (IOException e) {
				LogSwitch.e(TAG, "colse", "IOException", e);
			}
			try {
				if (inputStream != null) {
					inputStream.close();
					inputStream = null;
				}
			} catch (IOException e) {
				LogSwitch.e(TAG, "colse", "IOException", e);
			}
			try {
				if (outputStream != null) {
					outputStream.close();
					outputStream = null;
				}
			} catch (IOException e) {
				LogSwitch.e(TAG, "colse", "IOException", e);
			}
		}
	}

	/**
	 * @param address
	 *              连接地址.
	 * @param buffer  需要发送的数据包
	 *
	 */
	public boolean sendByte(String address, byte[] buffer) {
		SocketRunnable runnable = mapSockets.get(address);
		if (runnable != null) {
			try {
				runnable.outputStream.write(buffer);
				runnable.outputStream.flush();
				return true;
			} catch (IOException e) {
				LogSwitch.e(TAG, "sendByte", "IOException", e);
			}
		}
		return false;
	}

	/**
	 * @param address
	 *              连接地址
	 */
	public void clsoeConnection(String address) {
		mapSockets.get(address).colse();
		mapSockets.remove(address);
	}

	/**
	 * 关闭服务器
	 */
	public void clsoeServer() {
		handler.sendEmptyMessage(Handler_ServerClose);
		Iterator<String> keys = mapSockets.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			SocketRunnable runnable = mapSockets.get(key);
			runnable.colse();
		}
		mapSockets.clear();
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			LogSwitch.e(TAG, "clsoeServer", "serverSocket", e);
		}
	}
}
