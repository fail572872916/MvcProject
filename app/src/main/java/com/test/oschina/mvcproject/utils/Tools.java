package com.test.oschina.mvcproject.utils;

import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


public class Tools {
    private final String TAG;

    public Tools() {
        this.TAG = getClass().getSimpleName();
    }

    /**
     * @param context 传入上下文
     * @return 返回int[宽, 高], 不含系统状态栏
     */
    public int[] getDisplayByPx(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) (context)).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int[] result = new int[2];
        result[0] = displayMetrics.widthPixels;
        result[1] = displayMetrics.heightPixels;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int height = 0;
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        result[1] = result[1] - height;
        LogSwitch.v(TAG, "getDisplayByPx", "px:" + result[0] + " x " + result[1]);
        return result;
    }

    /**
     * @param context 传入上下文
     * @return 返回int[宽, 高], 包含系统状态栏
     */
    public int[] getDisplayByPxWithoutSystem(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) (context)).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int[] result = new int[2];
        result[0] = displayMetrics.widthPixels;
        result[1] = displayMetrics.heightPixels;
        LogSwitch.v(TAG, "getDisplayByPxWithoutSystem", "px:" + result[0] + " x " + result[1]);
        return result;
    }

    /**
     * @param context 获取屏幕大小
     * @return 返回屏幕大小(W x H)
     */
    public String getScreenSize(Context context) {
        LogSwitch.v(TAG, "getScreenSize", null);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) (context)).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // 屏幕分辨率数组
        String result = displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;
        float scale = context.getResources().getDisplayMetrics().density;
        result = result + "      " + (int) (displayMetrics.widthPixels / scale);
        result = result + "x" + (int) (displayMetrics.heightPixels / scale);
        LogSwitch.v(TAG, "getScreenSize", "屏幕分辨率:" + result);
        return result;
    }

    /**
     * @param address 传入ip地址不包含端口
     * @return 如果表达式通过返回字符串否则返回null
     */
    public String isServerAddress(String address) {
        if (address != null) {
            address = address.replaceAll("[\\。\\,\\，]", ".");
            address = address.replaceAll("[\\：]", ":");
            String ipFormater = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\:\\d{1,5}\\b";
            if (Pattern.compile(ipFormater).matcher(address).matches()) {
                return address;
            } else {
                ipFormater = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])";
                if (Pattern.compile(ipFormater).matcher(address).matches()) {
                    return address;
                }
            }
        }
        return null;
    }

    /**
     * @param address 传入ip地址包含端口
     * @return 如果表达式通过返回字符串否则返回null
     */
    public String isServerAddressFull(String address) {
        if (address != null) {
            address = address.replaceAll("[\\。\\,\\，]", ".");
            address = address.replaceAll("[\\：]", ":");
            String ipFormater = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\:\\d{1,5}\\b";
            if (Pattern.compile(ipFormater).matcher(address).matches()) {
                return address;
            }
        }
        return null;
    }

    /**
     * @param context 传入上下文
     * @return 返回网络类型
     */
    public String[] getNetWorkType(Context context) {
        String[] result = {"", "", ""};
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                @SuppressLint("MissingPermission") WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                result[0] = "WIFI";
                result[1] = wifiInfo.getSSID();
                result[2] = formartInteger(wifiInfo.getIpAddress());
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String typeName = networkInfo.getSubtypeName();
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        result[0] = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        result[0] = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        result[0] = "4G";
                        break;
                    default:
                        if (typeName.equalsIgnoreCase("TD-SCDMA") || typeName.equalsIgnoreCase("WCDMA")
                                || typeName.equalsIgnoreCase("CDMA2000")) {
                            result[0] = "3G";
                        } else {
                            result[0] = typeName;
                        }
                        break;
                }
            }
        }
        return result;
    }

    /**
     * @param listMobiles 传入已有信息List,允许传入null
     * @return 返回手机信息
     */
    @SuppressLint("MissingPermission")
    public List<Map<String, String>> getMobileInfo(Context context, List<Map<String, String>> listMobiles) {
        List<Map<String, String>> result = null;
        if (listMobiles == null) {
            result = new ArrayList<Map<String, String>>();
        } else {
            result = listMobiles;
        }
        Map<String, String> item = new HashMap<String, String>();
        // 手机号码
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        item.put("Name", "手机号码");
        item.put("Content", tm.getLine1Number());
        result.add(item);
        // 手机串号
        item = new HashMap<String, String>();
        item.put("Name", "手机串号");
        item.put("Content", tm.getDeviceId());
        result.add(item);
        // 手机品牌
        item = new HashMap<String, String>();
        item.put("Name", "手机品牌");
        item.put("Content", Build.BRAND);
        result.add(item);
        // 手机品牌
        item = new HashMap<String, String>();
        item.put("Name", "手机型号");
        item.put("Content", Build.MODEL);
        result.add(item);
        // 屏幕大小
        item = new HashMap<String, String>();
        item.put("Name", "屏幕大小");
        int[] screen = getDisplayByPxWithoutSystem(context);
        String size = "Px:" + screen[0] + "x" + screen[1];
        final float scale = context.getResources().getDisplayMetrics().density;
        size = size + " Dp:" + (int) (screen[0] / scale) + "x" + (int) (screen[1] / scale);
        item.put("Content", size);
        result.add(item);
        // 系统版本
        item = new HashMap<String, String>();
        item.put("Name", "系统版本");
        int sdk = Build.VERSION.SDK_INT;
        item.put("Content", "android" + Build.VERSION.RELEASE + "\t[SDK:" + sdk + "]");
        result.add(item);
        // 网络类型
        item = new HashMap<String, String>();
        item.put("Name", "网络类型");
        String[] netType = getNetWorkType(context);
        boolean isConnectNet = netType[0].length() > 0;
        if (!isConnectNet) {
            item.put("Content", "无网络连接");
        } else {
            item.put("Content", netType[0]);
        }
        result.add(item);
        // 外网地址
        WifiManager wifiManager = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE));
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        item = new HashMap<String, String>();
        if (isConnectNet) {
            item.put("Tag", "");
            item.put("Name", "外网地址");
            item.put("Content", "---.---.---.---");
            // WIFI
            if (netType[0].equals("WIFI")) {
                item.put("Tag", "WIFI_IP");
                String localAddress = formartInteger(wifiInfo.getIpAddress());
                item.put("Content", localAddress);
            } else {
                String localAddress = getLocalIpAddress();
                if (localAddress != null) {
                    item.put("Tag", "GPRS_IP");
                    item.put("Content", localAddress);
                }
            }
            result.add(item);
        }
        if (netType[0].equals("WIFI")) {
            // WIFI名称
            item = new HashMap<String, String>();
            item.put("Name", "WIFI名称");
            item.put("Content", netType[1]);
            result.add(item);
            // 内网地址
            item = new HashMap<String, String>();
            item.put("Name", "内网地址");
            item.put("Content", formartInteger(dhcpInfo.ipAddress));
            result.add(item);
            // 内网地址
            item = new HashMap<String, String>();
            item.put("Name", "子网掩码");
            item.put("Content", formartInteger(dhcpInfo.netmask));
            result.add(item);
            // 默认网关
            item = new HashMap<String, String>();
            item.put("Name", "默认网关");
            item.put("Content", formartInteger(dhcpInfo.gateway));
            result.add(item);
            // 域名系统
            item = new HashMap<String, String>();
            item.put("Name", "域名系统");
            item.put("Content", formartInteger(dhcpInfo.dns1));
            result.add(item);
            // 域名系统
            item = new HashMap<String, String>();
            item.put("Name", "域名系统");
            item.put("Content", formartInteger(dhcpInfo.dns2));
            result.add(item);
            // Mac地址
            item = new HashMap<String, String>();
            item.put("Name", "Mac地址");
            item.put("Content", getMacAddress());
            result.add(item);
        }
        return result;
    }


    //获取Mac地址
    private String getMacAddress() {
        String localMacAddress = null;
        //遍历获取设备当前IP对应的Mac地址
        try {
            InetAddress ip = null;
            Enumeration en_netInterface = NetworkInterface.getNetworkInterfaces();//列举
            while (en_netInterface.hasMoreElements()) {//是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();//得到下一个元素
                Enumeration en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = (InetAddress) en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                        break;
                    } else {
                        ip = null;
                    }
                }
                if (ip != null) {
                    break;
                }
            }
            if (ip != null) {
                byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
                if (b != null && b.length > 0) {
                    StringBuilder buffer = new StringBuilder();
                    for (int i = 0; i < b.length; i++) {
                        if (i != 0) {
                            buffer.append(':');
                        }
                        String str = Integer.toHexString(b[i] & 0xFF);
                        buffer.append(str.length() == 1 ? 0 + str : str);
                    }
                    localMacAddress = buffer.toString().toUpperCase();
                }
            }
        } catch (Exception e1) {
        }
        if (localMacAddress != null) {
            localMacAddress = localMacAddress.toUpperCase(Locale.getDefault());
        } else {
            localMacAddress = "02:00:00:00:00:00";
        }
        if (localMacAddress.equals("02:00:00:00:00:00")) {
            try {
                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nif : all) {
                    if (!nif.getName().equalsIgnoreCase("wlan0")) {continue;}
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return localMacAddress;
                    }
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }
                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    localMacAddress = res1.toString();
                }
            } catch (Exception e) {
            }
        }
        return localMacAddress;
    }

    // 获取GPRS IP
    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            LogSwitch.e(TAG, "getLocalIpAddress", "SocketException", e);
        }
        return null;
    }

    // 格式化IP
    private String formartInteger(int param) {
        return (param & 0xFF) + "." + (0xFF & param >> 8) + "." + (0xFF & param >> 16) + "." + (0xFF & param >> 24);
    }

    /**
     * @param context 传入上下文
     * @return 返回本机的IP
     */
    public String getLoalIp(Context context) {
        String result;
        String[] net = getNetWorkType(context);
        if (net[0].equals("WIFI")) {
            result = net[2];
        } else {
            result = getLocalIpAddress();
        }
        return result;
    }

    /**
     * @return 返回当前日期, 格式:yyyy-MM-dd HH:mm:ss
     */
    public String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(new Date());
    }

//    /**
//     * @param context 上下文
//     * @return VersionName
//     */
//    public String getVersionName(Context context) {
//        try {
//            String pkName = context.getPackageName();
//            String version = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
//            version = version + "(" + new UserKey().getAPPTag() + ")";
//            return version;
//        } catch (Exception e) {
//            LogSwitch.e(TAG, "getVersionName", "Exception", e);
//        }
//        return null;
//    }

    /**
     * @param context 传入上下文
     * @return 软件名称
     */
    public String getSoftName(Context context) {
        String applicationName = null;
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (NameNotFoundException e) {
            LogSwitch.e(TAG, "getSoftName", "NameNotFoundException", e);
        }
        return applicationName;
    }

    /**
     * @param context
     * @param serviceName
     * @return 运行状态
     */
    public boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> listServices = manager.getRunningServices(50);
        if (listServices.size() <= 0) {
            return false;
        }
        for (int i = 0; i < listServices.size(); i++) {
            if (listServices.get(i).service.getClassName().toString().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param context 传入上下文
     * @return 获取系统唯一值
     */
    public String getSystemSymbol(Context context) {
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String key = field.getName().toUpperCase(Locale.getDefault());
                if (key.equals("FINGERPRINT")) {
                    return field.get(null).toString();
                }
            }
        } catch (IllegalAccessException e) {
            LogSwitch.e(TAG, "initData", "IllegalAccessException", e);
        } catch (IllegalArgumentException e) {
            LogSwitch.e(TAG, "initData", "IllegalArgumentException", e);
        }
        return null;
    }
}
