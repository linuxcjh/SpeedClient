package com.rongfeng.speedclient.common;

import android.os.Build;

import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.DeviceInfoUtil;


/**
 * AUTHOR: Alex
 * DATE: 10/11/2015 17:02
 */
public class BaseTransModel {

    private String userId = AppTools.getUser().getUserId();//当前登录用户的ID
    private String tenementId = AppTools.getUser().getTenementId();//	租户ID【预留】
    private String appVersion = DeviceInfoUtil.instance().getAppInfo();//	app版本
    private String osVersion = DeviceInfoUtil.instance().getDeviceInfo();//	手机操作系统版本
    private String imei = DeviceInfoUtil.instance().getDeviceId();//	手机IMEI号
    private String osType = "0";//	0：android；1：ios
    private String schemaFlag = AppTools.getUser().getSchemaFlag(); //唯一schema标识


    private String phoneModel = Build.MODEL;//手机型号

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getSchemaFlag() {
        return schemaFlag;
    }

    public void setSchemaFlag(String schemaFlag) {
        this.schemaFlag = schemaFlag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTenementId() {
        return tenementId;
    }

    public void setTenementId(String tenementId) {
        this.tenementId = tenementId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }
}
