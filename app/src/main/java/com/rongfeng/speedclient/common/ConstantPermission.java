package com.rongfeng.speedclient.common;

import android.Manifest;

/**
 * Created by 唐磊 on 2015/11/5.
 * 常量类
 */
public interface ConstantPermission {

    /**
     * 权限请求CODE
     */
    int PERMISSION_REQUEST_CODE = 0x31;

    /**
     * 登陆获取手机信息权限
     */
    String[] PERMISSIONS_LOGIN = new String[]{Manifest.permission.READ_PHONE_STATE};

    /**
     * 获取相机权限
     */
    String[] PERMISSIONS_PICTURE = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 获取定位权限
     */
    String[] PERMISSIONS_LOCATION = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE};

    /**
     * 读取手机相册权限
     */
    String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    /**
     * 读取手机联系人权限
     */
    String[] PERMISSIONS_READ_CONTACTS = new String[]{Manifest.permission.READ_CONTACTS
    };

}
