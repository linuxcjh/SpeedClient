package com.rongfeng.speedclient;

import android.app.Application;

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
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5814610b");

    }
}
