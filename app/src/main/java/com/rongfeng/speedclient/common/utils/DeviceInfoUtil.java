package com.rongfeng.speedclient.common.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

/**
 * 设备信息
 * AUTHOR: Alex
 * DATE: 10/11/2015 18:04
 */
public class DeviceInfoUtil {

    private TelephonyManager tm;

    public DeviceInfoUtil() {
        tm = (TelephonyManager) AppConfig.getContext().getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static DeviceInfoUtil instance() {

        return new DeviceInfoUtil();
    }

    /*
     * 电话状态：
     * 1.tm.CALL_STATE_IDLE=0          无活动
     * 2.tm.CALL_STATE_RINGING=1  响铃
     * 3.tm.CALL_STATE_OFFHOOK=2  摘机
     */
    public int getCallState() {
        return tm.getCallState();//int

    }


    /*
     * 唯一的设备ID：
     * GSM手机的 IMEI 和 CDMA手机的 MEID.
     * Return null if device ID is not available.
     */
    public String getDeviceId() {
        if (ContextCompat.checkSelfPermission(AppConfig.getContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            return tm.getDeviceId();
        }
        return "";//String

    }


    /*
     * 设备的软件版本号：
     * 例如：the IMEI/SV(software version) for GSM phones.
     * Return null if the software version is not available.
     */
    public String getDeviceSoftwareVersion() {
        return tm.getDeviceSoftwareVersion();//String

    }


    /*
     * 手机号：
     * GSM手机的 MSISDN.
     * Return null if it is unavailable.
     */
    public String getLine1Number() {
        return tm.getLine1Number();//String

    }


    /*
     * 当前使用的网络类型：
     * 例如： NETWORK_TYPE_UNKNOWN  网络类型未知  0
       NETWORK_TYPE_GPRS     GPRS网络  1
       NETWORK_TYPE_EDGE     EDGE网络  2
       NETWORK_TYPE_UMTS     UMTS网络  3
       NETWORK_TYPE_HSDPA    HSDPA网络  8
       NETWORK_TYPE_HSUPA    HSUPA网络  9
       NETWORK_TYPE_HSPA     HSPA网络  10
       NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4
       NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5
       NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6
       NETWORK_TYPE_1xRTT    1xRTT网络  7
     */
    public int getNetworkType() {
        return tm.getNetworkType();//int
    }


    /*
     * 手机类型：
     * 例如： PHONE_TYPE_NONE  无信号
       PHONE_TYPE_GSM   GSM信号
       PHONE_TYPE_CDMA  CDMA信号
     */
    public int getPhoneType() {
        return tm.getPhoneType();//int
    }


    /*
     * 服务商名称：
     * 例如：中国移动、联通
     * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
     */
    public String getSimOperatorName() {
        return tm.getSimOperatorName();//String
    }


    /*
     * SIM卡的序列号：
     * 需要权限：READ_PHONE_STATE
     */
    public String getSimSerialNumber() {
        return tm.getSimSerialNumber();//String
    }


    /*
     * SIM的状态信息：
     *  SIM_STATE_UNKNOWN          未知状态 0
     SIM_STATE_ABSENT           没插卡 1
     SIM_STATE_PIN_REQUIRED     锁定状态，需要用户的PIN码解锁 2
     SIM_STATE_PUK_REQUIRED     锁定状态，需要用户的PUK码解锁 3
     SIM_STATE_NETWORK_LOCKED   锁定状态，需要网络的PIN码解锁 4
     SIM_STATE_READY            就绪状态 5
     */
    public int getSimState() {
        return tm.getSimState();//int
    }

    /*
     * 唯一的用户ID：
     * 例如：IMSI(国际移动用户识别码) for a GSM phone.
     * 需要权限：READ_PHONE_STATE
     */
    public String getSubscriberId() {
        return tm.getSubscriberId();//String
    }


    /**
     * 获取手机系统信息
     *
     * @return
     */
    public String getDeviceInfo() {
        StringBuilder sb = new StringBuilder();
        PackageInfo pi;
        try {
            pi = AppConfig.getContext().getPackageManager().getPackageInfo(AppConfig.getContext().getPackageName(), 0);
            sb.append(Build.MODEL);
            sb.append(" , ");
            sb.append(Build.VERSION.SDK);
            sb.append(" , ");
            sb.append(Build.VERSION.RELEASE);
            sb.append(" , ");
            sb.append(pi.versionName);
            sb.append(" , ");
            sb.append(pi.versionCode);
            return sb.toString();
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 本应用版本信息
     *
     * @return
     */
    public String getAppInfo() {
        StringBuilder sb = new StringBuilder();
        PackageInfo pi;
        try {
            pi = AppConfig.getContext().getPackageManager().getPackageInfo(AppConfig.getContext().getPackageName(), 0);
            sb.append(pi.versionName);
            sb.append("-");
            sb.append(pi.versionCode);
            return sb.toString();
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }


}
