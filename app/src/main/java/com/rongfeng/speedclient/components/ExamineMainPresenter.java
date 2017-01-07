package com.rongfeng.speedclient.components;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.version.UpdateVersion;
import com.rongfeng.speedclient.version.VersionModel;
import com.rongfeng.speedclient.version.VersionTools;

/**
 * Created by 陈建辉 on 2015/11/25
 */
public class ExamineMainPresenter extends BasePresenter {

    private Activity mActivity;

    public ExamineMainPresenter(Activity activity) {
        this.mActivity = activity;
    }


    /**
     * 版本升级
     */
    public void getPhoneVersion() {
        commonApi(XxbService.GETPHONEVERSION, new TypeToken<VersionModel>() {
        });
    }

    @Override
    public void onResponse(String methodName, Object object, int status) {

        switch (methodName) {
            case XxbService.GETPHONEVERSION:
                if (object != null) {
                    UpdateVersion updateVersion = new UpdateVersion(mActivity,
                            (VersionModel) object, 0);
                    updateVersion.showIsUpdate();
                }
                break;
        }

    }

    private DownloadCompleteReceiver receiver;

    /**
     * 静态注册广播
     */
    public void initRegisterBroadCast() {
        receiver = new DownloadCompleteReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_DISAPPEAR_SIGN_DOWNLOAD_APP);
        mActivity.registerReceiver(receiver, filter);
    }

    /**
     * 销毁广播
     */
    public void unRegisterBroadCast() {
        mActivity.unregisterReceiver(receiver);

    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(
                    Constant.BROADCAST_DISAPPEAR_SIGN_DOWNLOAD_APP)) { // apk下载
                VersionTools.installAPKFile(context);
            }
        }
    }

}
