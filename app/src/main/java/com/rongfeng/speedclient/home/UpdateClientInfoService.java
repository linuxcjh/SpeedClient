package com.rongfeng.speedclient.home;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.common.CommonPresenter;
import com.rongfeng.speedclient.common.ICommonAction;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.voice.VoiceAnalysisTools;
import com.rongfeng.speedclient.voice.model.SyncClientInfoModel;

import java.util.List;

/**
 * 上传词表
 * AUTHOR: Alex
 * DATE: 10/12/2016 16:33
 */

public class UpdateClientInfoService extends IntentService implements ICommonAction {

    private CommonPresenter commonPresenter = new CommonPresenter(this);

    /**
     * 分词Presenter
     */
    public UpdateClientInfoService() {
        super("com.rongfeng.speedclient.home.UpdateClientInfoService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        //异步任务

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        commonPresenter.isShowProgressDialog = false;


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        invokeClient();
        return START_STICKY;
    }


    /**
     * 获取客户
     */
    private void invokeClient() {
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRDATA, AppTools.toMap(), new TypeToken<List<SyncClientInfoModel>>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        switch (methodIndex) {

            case XxbService.SEARCHCSRDATA:
                VoiceAnalysisTools.getInstance().analysisData((List<SyncClientInfoModel>) data);
                break;
        }
    }

}
