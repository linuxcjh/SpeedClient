package com.rongfeng.speedclient.home;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.CommonPresenter;
import com.rongfeng.speedclient.common.ICommonAction;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.login.TransDataModel;
import com.rongfeng.speedclient.voice.VoicePresenter;
import com.rongfeng.speedclient.voice.model.LanguageCloudModel;
import com.rongfeng.speedclient.voice.model.SplitWordModel;

import java.util.List;

/**
 * 上传词表
 * AUTHOR: Alex
 * DATE: 10/12/2016 16:33
 */

public class UpdateClientInfoService extends IntentService implements ICommonAction {

    private CommonPresenter commonPresenter = new CommonPresenter(this);

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

        invokeClient();
        return START_STICKY;
    }

    /**
     * 上传客户词表
     *
     * @param list
     */
    private void uploadWords(List<AddClientTransModel> list) {
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        if (list != null) {
            String upload = AppTools.getUploadClientNamesWordForm(list);
            if (!TextUtils.isEmpty(upload)) {
                mIat.updateLexicon("userword", AppTools.getUploadClientNamesWordForm(list), mLexiconListener);
            }
        }
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

    /**
     * 分词Presenter
     */
    private VoicePresenter voicePresenter = new VoicePresenter() {
        @Override
        public void onResponse(String methodName, AddClientTransModel model, Object object, int status) {

            if (object != null) {
                List<List<List<SplitWordModel>>> models = (List<List<List<SplitWordModel>>>) object;

                if (models != null && models.size() > 0 && models.get(0).size() > 0) {
                    model.setClientNameWordsSplit(BasePresenter.gson.toJson(models.get(0).get(0)));
                    AppTools.insertClientDataToDB(UpdateClientInfoService.this, model);
                }
            }

        }
    };

    /**
     * 客户名称分词解析
     */
    private void languageCloudParse(AddClientTransModel m) {
        LanguageCloudModel model = new LanguageCloudModel();
        model.setText(m.getCustomerName());
        voicePresenter.commonApi("", m, AppTools.toMap(model), new TypeToken<List<List<List<SplitWordModel>>>>() {
        });
    }


    /**
     * 获取客户
     */
    private void invokeClient() {
        TransDataModel transDataModel = new TransDataModel();
        transDataModel.setClientType("5");
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSR, transDataModel, new TypeToken<List<AddClientTransModel>>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        switch (methodIndex) {

            case XxbService.SEARCHCSR:
                List<AddClientTransModel> list = (List<AddClientTransModel>) data;

                if (list != null && list.size() > 0) {
                    uploadWords(list);
                    AppTools.clearForm(this);
                    for (int i = 0; i < list.size(); i++) {
                        languageCloudParse(list.get(i));
                    }
                }
//                AppTools.insertClientDataToDB(this, list);
                break;
        }
    }

}
