package com.rongfeng.speedclient;

import android.app.Application;
import android.content.Intent;

import com.facebook.stetho.Stetho;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.home.UpdateClientInfoService;

/**
 * AUTHOR: Alex
 * DATE: 30/10/2016 11:01
 */

public class MineApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.setContext(this);
        startService(new Intent(this, UpdateClientInfoService.class));//启动数据服务
        Stetho.initializeWithDefaults(this);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5814610b");
    }
}
