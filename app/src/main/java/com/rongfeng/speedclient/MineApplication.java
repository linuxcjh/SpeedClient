package com.rongfeng.speedclient;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * AUTHOR: Alex
 * DATE: 30/10/2016 11:01
 */

public class MineApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5814610b");

    }
}
