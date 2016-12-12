package com.rongfeng.speedclient.home;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.common.utils.AppTools;

import java.util.List;

/**
 * 上传词表
 * AUTHOR: Alex
 * DATE: 10/12/2016 16:33
 */

public class UpdateClientInfoService extends IntentService {


    // 语音听写对象
    private SpeechRecognizer mIat;

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
        mIat = SpeechRecognizer.createRecognizer(getApplicationContext(), mInitListener);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            List<AddClientTransModel> list = (List<AddClientTransModel>) intent.getSerializableExtra("clientList");
            mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
            if (list != null) {
                String upload = AppTools.getUploadClientNamesWordForm(list);
                if (!TextUtils.isEmpty(upload)) {
                    mIat.updateLexicon("userword", AppTools.getUploadClientNamesWordForm(list), mLexiconListener);
                }
            }
        }
        return START_STICKY;
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                AppTools.getToast("初始化失败，错误码：" + code);
            }
        }
    };
    /**
     * 上传联系人/词表监听器。
     */
    private LexiconListener mLexiconListener = new LexiconListener() {

        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error) {
            if (error != null) {
            } else {

                AppTools.getToast("上传成功");
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
