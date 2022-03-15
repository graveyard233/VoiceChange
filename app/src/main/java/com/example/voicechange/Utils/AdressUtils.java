package com.example.voicechange.Utils;

import android.util.Log;

import com.example.voicechange.BuildConfig;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * author
 * 获取本地IP、MAC地址以及版本号
 * date:2021/12/14
 */
public class AdressUtils {
    private static final String TAG = "AdressUtils";

    public static String getLocalip() {
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i(TAG, "SocketException");
            e.printStackTrace();
        }
        return hostIp;
    }


    public static String getMac(boolean isHasPoint) {//boolean 对象选择是否有点
        String mac = "00:00:00:00:00:00";
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if ((!nif.getName().equalsIgnoreCase("wlan0")) && !nif.getName().equalsIgnoreCase("eth0")) {
                    continue;
                }

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return mac;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    if (isHasPoint) {
                        res1.append(String.format("%02X:", b));
                    } else {
                        res1.append(String.format("%02X", b));
                    }
                }

                if (res1.length() > 0) {
                    if (isHasPoint) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                }
                mac = res1.toString();
                if (nif.getName().equalsIgnoreCase("eth0")){
                    return mac;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mac;
    }

    public static String getVersion(){
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

}
