package com.example.videoplayer;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity
{
    private EditText nameText;
    private String path;//定义变量path存放路径
    private MediaPlayer mediaPlayer;//播放视频文件要用到的类
    private SurfaceView surfaceView;
    private boolean pause;
    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }

        mediaPlayer=new MediaPlayer();
        nameText=(EditText)findViewById(R.id.filename);
        surfaceView=(SurfaceView)findViewById(R.id.surfaceView);
        // surfaceView.getHolder()控件
        //把输送给surfaceView的视频画面直接显示在屏幕上，不要维持它自身的缓冲区
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(176,144);//设置视频的分辨率
        surfaceView.getHolder().setKeepScreenOn(true);//保持屏幕不锁屏
        // 权限支持
        checkWritePermission();

    }

    protected void onDestroy()//销毁视频文件
    {
        mediaPlayer.release();
        mediaPlayer=null;
        super.onDestroy();
    }

    public void mediaplay(View v)
    {
        switch (v.getId())
        {
            case R.id.playbutton:
                //获得文本框输入的文件名称
                String filename=nameText.getText().toString();
                //查看文件是否存在
                File file=new File(Environment.getExternalStorageDirectory(),filename);
                if(file.exists())//如果文件存在
                {
                    path= file.getAbsolutePath();//取得文件的绝对路径
                    play();//播放视频
                }
                else
                {
                    path=null;
                    Toast.makeText(this,R.string.filenoexsit,Toast.LENGTH_SHORT);
                }
                break;

            case R.id.pausebutton:
                //是否处于播放装填
                if(mediaPlayer.isPlaying())
                {

                    mediaPlayer.pause();
                    pause=true;
                }
                else
                {
                    if(pause)
                    {
                        mediaPlayer.start();
                        pause=false;
                    }
                }
                break;

            case R.id.resetbutton:
                //是否处于播放装填
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.seekTo(0);

                }
                else
                {
                    if(path!=null)
                    {
                        play();
                    }
                }
                break;

            case R.id.stopbutton:
                //是否处于播放装填
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                break;

            default:
                break;
        }
    }

    private void play()
    {
        try
        {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);//播放器恢复到最初始状态
            mediaPlayer.setDisplay(surfaceView.getHolder());//输送已经设置好的视频画面
            mediaPlayer.prepare();//缓冲视频
            //监听缓冲是否完成
            mediaPlayer.setOnPreparedListener(new PreparedListener());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private final class PreparedListener implements MediaPlayer.OnPreparedListener
    {
        public void onPrepared(MediaPlayer mp)
        {
            mediaPlayer.start();//播放视频
        }
    }


    /**
     * 重写onRequestPermissionsResult，用于接受请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 请求权限成功
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "用户授权成功", Toast.LENGTH_SHORT).show();

    }

    /**
     * 请求权限失败
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "用户授权失败", Toast.LENGTH_SHORT).show();
        /**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         */
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    /**
     * 检查读写权限权限
     */
    private void checkWritePermission() {
        boolean result = PermissionManager.checkPermission(this, Constants.PERMS_WRITE);
        if (!result) {
            PermissionManager.requestPermission(this, Constants.WRITE_PERMISSION_TIP, Constants.WRITE_PERMISSION_CODE, Constants.PERMS_WRITE);
        }
    }
}