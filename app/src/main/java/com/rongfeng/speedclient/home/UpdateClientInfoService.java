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
import com.iflytek.cloud.util.ContactManager;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.CommonPresenter;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.ICommonAction;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.datanalysis.ClientModel;
import com.rongfeng.speedclient.datanalysis.DBManager;
import com.rongfeng.speedclient.login.TransDataModel;
import com.rongfeng.speedclient.voice.VoiceAnalysisTools;
import com.rongfeng.speedclient.voice.VoicePresenter;
import com.rongfeng.speedclient.voice.model.CsrContactJSONArray;
import com.rongfeng.speedclient.voice.model.LanguageCloudModel;
import com.rongfeng.speedclient.voice.model.SplitWordModel;
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
    private VoicePresenter voicePresenter = new VoicePresenter();
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
        commonPresenter.isShowProgressDialog = false;
        mIat = SpeechRecognizer.createRecognizer(getApplicationContext(), mInitListener);
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");

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
    private void uploadWords(List<SyncClientInfoModel> list) {
        if (list != null) {
            String upload = VoiceAnalysisTools.getUploadClientNamesWordForm(list);
            if (!TextUtils.isEmpty(upload)) {
                mIat.updateLexicon(Constant.UPLOAD_CLIENT_FLAG, upload, mLexiconListener);
            }
        }
    }

    /**
     * 上传客户联系人
     *
     * @param list
     */
    private void uploadClientContact(List<SyncClientInfoModel> list) {
        if (list != null) {
            StringBuilder builder = new StringBuilder();
            for (SyncClientInfoModel m : list) {
                if (m.getCustomerType().equals("2")) {//个人客户名称
                    builder.append(m.getCustomerName()).append("\n"); //客户名称
                }
                for (CsrContactJSONArray contact : m.getCsrContactJSONArray()) { //联系人名称
                    builder.append(contact.getName()).append("\n");
                }
            }
            String upload = builder.toString();
            if (!TextUtils.isEmpty(upload)) {
                mIat.updateLexicon(Constant.UPLOAD_CONTACTS_FLAG, upload, lexiconListener);
                AppConfig.setStringConfig(Constant.UPLOAD_CONTACTS_FLAG, upload);//缓存联系人和个人客户信息用于在新增或修改时增量上传
            }
        }
    }

    /**
     * 上传手机通讯录
     */
    private void upPhoneContact() {

        ContactManager mgr = ContactManager.createManager(this, mContactListener);
        //异步查询联系人接口，通过onContactQueryFinish接口回调
        mgr.asyncQueryAllContactsName();

    }

    /**
     * 上传手机通讯录监听
     */
    private ContactManager.ContactListener mContactListener = new ContactManager.ContactListener() {
        @Override
        public void onContactQueryFinish(String contactInfos, boolean changeFlag) {
            //指定引擎类型
            mIat.updateLexicon(Constant.UPLOAD_CONTACTS_FLAG, contactInfos, lexiconListener);
        }
    };

    //上传联系人监听器。
    private LexiconListener lexiconListener = new LexiconListener() {
        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error) {
            if (error != null) {
//                Log.d(TAG, error.toString());
            } else {
                AppTools.getToast("联系人上传成功");
            }
        }
    };

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
     * 客户名称分词解析
     */
    private void languageCloudParse(SyncClientInfoModel m) {
        LanguageCloudModel model = new LanguageCloudModel();
        model.setText(m.getCustomerName());
        voicePresenter.commonApi("", m, VoiceAnalysisTools.toMap(model), new TypeToken<List<List<List<SplitWordModel>>>>() {
        });
    }


    /**
     * 获取客户
     */
    private void invokeClient() {
        TransDataModel transDataModel = new TransDataModel();
        transDataModel.setClientType("5");
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRDATA, transDataModel, new TypeToken<List<SyncClientInfoModel>>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        switch (methodIndex) {

            case XxbService.SEARCHCSRDATA:
                DBManager dbManager = new DBManager(AppConfig.getContext());

                List<SyncClientInfoModel> list = (List<SyncClientInfoModel>) data;
                if (list != null && list.size() > 0) {

//                    uploadWords(list);
                    uploadClientContact(list);
                    for (int i = 0; i < list.size(); i++) {
                        SyncClientInfoModel model = list.get(i);

                        ClientModel m = dbManager.queryTheClient(model.getCsrId());
                        if (!TextUtils.isEmpty(m.client_id)) {//客户存在
                            String contacts = BasePresenter.gson.toJson(model.getCsrContactJSONArray()).toString();
                            if (!m.getContact_name().equals(contacts)) {//更新已有客户的联系人
                                VoiceAnalysisTools.updateClientContact(model, dbManager);
                            }
                            if (!m.getClient_name().equals(model.getCustomerName())) {//客户名称有变化重新分词并更新数据库
                                languageCloudParse(model);
                            }
                        } else {//客户不存在
                            VoiceAnalysisTools.insertClientDataToDB(model, dbManager);//插入数据库
                            languageCloudParse(model);//客户名称分词并更新数据库
                        }
                    }
                }
                dbManager.closeDB();
                break;
        }
    }

}
