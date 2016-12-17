package com.rongfeng.speedclient.voice;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.util.ContactManager;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.datanalysis.ClientModel;
import com.rongfeng.speedclient.datanalysis.DBManager;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.voice.model.AreaModel;
import com.rongfeng.speedclient.voice.model.CsrContactJSONArray;
import com.rongfeng.speedclient.voice.model.LanguageCloudModel;
import com.rongfeng.speedclient.voice.model.SplitWordModel;
import com.rongfeng.speedclient.voice.model.SyncClientInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;


/**
 * AUTHOR: Alex
 * DATE: 12/11/2015 09:47
 */
public class VoiceAnalysisTools {

    private VoicePresenter voicePresenter;
    private SpeechRecognizer mIat;
    private static VoiceAnalysisTools ourInstance = new VoiceAnalysisTools();

    public static VoiceAnalysisTools getInstance() {

        return ourInstance;
    }

    private VoiceAnalysisTools() {
        voicePresenter = new VoicePresenter();
        mIat = SpeechRecognizer.createRecognizer(AppConfig.getContext(), mInitListener);
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
    }


    /**
     * Bean Convert Map
     *
     * @param targetBean
     * @return
     */
    public Map<String, String> toMap(Object targetBean) {

        Map<String, String> result = new IdentityHashMap<String, String>();
        Method[] methods = targetBean.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(targetBean, (Object[]) null);
                    result.put(field, null == value ? "" : value.toString());
                }
            } catch (Exception e) {
            }
        }


        return result;
    }


    /**
     * 添加数据到数据库
     */
    public void insertClientDataToDB(List<SyncClientInfoModel> data) {

        List<ClientModel> persons = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            ClientModel m = new ClientModel();
            m.setClient_name(data.get(i).getCustomerName());
            m.setClient_info(data.get(i).getClientNameWordsSplit());
            m.setClient_id(data.get(i).getCsrId());
            persons.add(m);
        }

        DBManager dbManager = new DBManager(AppConfig.getContext());
        dbManager.truncate();
        if (persons.size() > 0) {
            dbManager.addClient(persons);
        }

        dbManager.closeDB();

    }


    /**
     * 清空表
     */
    public void clearForm() {
        DBManager dbManager = new DBManager(AppConfig.getContext());
        dbManager.truncate();

        dbManager.closeDB();
    }

    /**
     * 添加单个人到数据库
     */
    public void insertClientDataToDB(SyncClientInfoModel data, DBManager dbManager) {

        ClientModel m = new ClientModel();
        m.setClient_name(data.getCustomerName());
        m.setClient_id(data.getCsrId());
        m.setClient_update_time(data.getUpdateTime());
        m.setContact_name(BasePresenter.gson.toJson(data.getCsrContactJSONArray()));

        dbManager.addClient(m);
    }

    /**
     * 更新客户名称分词结果
     */
    public void updateClientNameSplit(SyncClientInfoModel data) {

        DBManager dbManager = new DBManager(AppConfig.getContext());

        ClientModel m = new ClientModel();
        m.setClient_info(data.getClientNameWordsSplit());
        m.setClient_id(data.getCsrId());
        m.setClient_update_time(data.getUpdateTime());
        m.setClient_name(data.getCustomerName());

        dbManager.updateClient(m);

        dbManager.closeDB();
    }

    /**
     * 更新联系人
     *
     * @param data
     * @param dbManager
     */

    public void updateClientContact(SyncClientInfoModel data, DBManager dbManager) {

        ClientModel m = new ClientModel();
        m.setClient_name(data.getCustomerName());
        m.setClient_id(data.getCsrId());
        m.setClient_update_time(data.getUpdateTime());
        m.setContact_name(BasePresenter.gson.toJson(data.getCsrContactJSONArray()));

        dbManager.updateClientContact(m);

    }

    /**
     * 上传用户词表
     */
    public String getUploadClientNamesWordForm(List<SyncClientInfoModel> list) {

//        {"userword":[{"name":"我的常用词","words":["佳晨实业","蜀南庭苑"]}
//                ,{"name":"我的好友","words":["李馨琪","鹿晓雷"]}]}
        JSONObject wordsOjb = new JSONObject();
        JSONArray wordFormList = new JSONArray();
        JSONObject inner = new JSONObject();
        JSONArray innerNames = new JSONArray();

//        JSONObject inner2 = new JSONObject();
//        JSONArray innerNames2 = new JSONArray();
        try {
            inner.put("name", "客户名称词表");
            for (int i = 0; i < list.size(); i++) {
                innerNames.put(i, list.get(i).getCustomerName());
            }
            inner.put("words", innerNames);
            wordFormList.put(0, inner);

//            inner2.put("name", "我的好友");
//            innerNames2.put(0, "张范孚");
//            inner2.put("words", innerNames2);
//            wordFormList.put(1, inner2);

            wordsOjb.put("userword", wordFormList);
            return wordsOjb.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";

    }

    /**
     * 删除数据库数据
     */
    public void deleteClientDataToDB() {

        DBManager dbManager = new DBManager(AppConfig.getContext());
        dbManager.truncate();
        dbManager.closeDB();

    }

    /**
     * 查询数据库数据
     */
    public List<ClientModel> queryClientDataToDB() {
        List<ClientModel> persons = new ArrayList<>();
        DBManager dbManager = new DBManager(AppConfig.getContext());
        persons = dbManager.query();
        dbManager.closeDB();

        return persons;
    }

    /**
     * 查询数据库数据中联系人
     */
    public ClientModel queryClientDataToDB(String contactPhone) {
        DBManager dbManager = new DBManager(AppConfig.getContext());
        ClientModel model = dbManager.queryContact(contactPhone);
        dbManager.closeDB();
        return model;
    }

    /**
     * 判断指定客户是否存在
     *
     * @param clientId
     * @return
     */
    public boolean queryClientIsExist(String clientId) {
        DBManager dbManager = new DBManager(AppConfig.getContext());
        boolean isExist = dbManager.queryTheClientCursor(clientId);
        dbManager.closeDB();

        return isExist;
    }


    public void analysisData(List<SyncClientInfoModel> list) {

        String contactStr = AppConfig.getStringConfig(Constant.SAVE_UPLOAD_CONTACTS_FLAG, "");
        StringBuilder builder = new StringBuilder(contactStr);

        DBManager dbManager = new DBManager(AppConfig.getContext());

        if (list != null && list.size() > 0) {
//            uploadWords(list);
//            uploadClientContact(list);
            for (int i = 0; i < list.size(); i++) {
                SyncClientInfoModel model = list.get(i);
                ClientModel m = dbManager.queryTheClient(model.getCsrId());
                if (!TextUtils.isEmpty(m.client_id)) {//客户存在
                    String contacts = BasePresenter.gson.toJson(model.getCsrContactJSONArray()).toString();
                    if (!m.getContact_name().equals(contacts)) {//联系人变化更新已有客户的联系人
                        updateClientContact(model, dbManager);
                        uploadClientContactInfo(model, contactStr, builder);
                    }
                    if (!m.getClient_name().equals(model.getCustomerName())) {//客户名称有变化重新分词并更新数据库
                        languageCloudParse(model);
                        uploadClientContactInfo(model, contactStr, builder);
                    }
                } else {//客户不存在
                    insertClientDataToDB(model, dbManager);//插入数据库
                    languageCloudParse(model);//客户名称分词并更新数据库
                    uploadClientContactInfo(model, contactStr, builder);//上传联系人
                }
            }
        }

        if (builder.toString().startsWith("\n")) {
            builder.deleteCharAt(0);
        }
        if (!contactStr.equals(builder.toString())) {
            mIat.updateLexicon(Constant.UPLOAD_CONTACTS_FLAG, builder.toString(), lexiconListener);
            AppConfig.setStringConfig(Constant.SAVE_UPLOAD_CONTACTS_FLAG, builder.toString());//缓存联系人和个人客户信息用于在新增或修改时增量上传
            builder.delete(0, builder.length());
        }
        dbManager.closeDB();

    }


    /**
     * 客户名称分词解析
     */
    public void languageCloudParse(SyncClientInfoModel m) {
        LanguageCloudModel model = new LanguageCloudModel();
        model.setText(m.getCustomerName());
        voicePresenter.commonApi("", m, toMap(model), new TypeToken<List<List<List<SplitWordModel>>>>() {
        });
    }

    /**
     * 全量上传客户联系人
     *
     * @param list
     */
    public void uploadClientContact(List<SyncClientInfoModel> list) {
        if (list != null) {
            StringBuilder builder = new StringBuilder();
            for (SyncClientInfoModel m : list) {
                builder.append(m.getCustomerName()).append("\n"); //客户名称
                for (CsrContactJSONArray contact : m.getCsrContactJSONArray()) { //联系人名称
                    builder.append(contact.getName()).append("\n");
                }
            }
            String upload = builder.toString();
            if (!TextUtils.isEmpty(upload)) {
                mIat.updateLexicon(Constant.UPLOAD_CONTACTS_FLAG, upload, lexiconListener);
                AppConfig.setStringConfig(Constant.SAVE_UPLOAD_CONTACTS_FLAG, upload);//缓存联系人和个人客户信息用于在新增或修改时增量上传
            }
        }
    }


    /**
     * 增量上传
     *
     * @param m
     */
    public void uploadClientContactInfo(SyncClientInfoModel m, String contactStr, StringBuilder builder) {
        if (!contactStr.contains(m.getCustomerName())) {
            builder.append("\n").append(m.getCustomerName()); //客户名称
        }
        for (CsrContactJSONArray contact : m.getCsrContactJSONArray()) { //联系人名称
            if (!contactStr.contains(contact.getName())) {
                builder.append("\n").append(contact.getName());
            }
        }

    }

    /**
     * 上传客户词表
     *
     * @param list
     */
    private void uploadWords(List<SyncClientInfoModel> list, SpeechRecognizer mIat) {
        if (list != null) {
            String upload = VoiceAnalysisTools.getInstance().getUploadClientNamesWordForm(list);
            if (!TextUtils.isEmpty(upload)) {
                mIat.updateLexicon(Constant.UPLOAD_CLIENT_FLAG, upload, mLexiconListener);
            }
        }
    }


    /**
     * 上传手机通讯录
     */
    private void upPhoneContact() {

        ContactManager mgr = ContactManager.createManager(AppConfig.getContext(), new ContactManager.ContactListener() {
            @Override
            public void onContactQueryFinish(String contactInfos, boolean changeFlag) {
                //指定引擎类型
                mIat.updateLexicon(Constant.UPLOAD_CONTACTS_FLAG, contactInfos, lexiconListener);
            }
        });
        //异步查询联系人接口，通过onContactQueryFinish接口回调
        mgr.asyncQueryAllContactsName();

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
     * 解析数据
     */
    public List<BaseDataModel> analysisData(@Nullable EditText editText, String resultStr) {


        if (TextUtils.isEmpty(resultStr)) {
            return null;
        }

        List<String> resultStrs = new ArrayList<>();

        String pinYinStr = AppTools.convertPinYin(resultStr);

        List<ClientModel> clientModels = VoiceAnalysisTools.getInstance().queryClientDataToDB();

        List<String> clientData = new ArrayList<>();

        if (clientModels.size() != 0) {
            for (int i = 0; i < clientModels.size(); i++) {

                ClientModel resultModel = clientModels.get(i);
                String name = resultModel.getClient_name();
                String namePY = AppTools.convertPinYin(name);
                //客户名称分词结果
                List<SplitWordModel> splitWordModels = BasePresenter.gson.fromJson(resultModel.getClient_info(), new TypeToken<List<SplitWordModel>>() {
                }.getType());
                ArrayList<CsrContactJSONArray> contactModels = BasePresenter.gson.fromJson(resultModel.getContact_name(), new TypeToken<List<CsrContactJSONArray>>() {
                }.getType());
                if (resultStr.indexOf(name) != -1 || pinYinStr.indexOf(namePY) != -1) {//客户全名匹配
                    clientData.add(resultModel.getClient_id() + "," + name + "," + name);
                    resultStrs.add(name);
                }

                if (splitWordModels != null && splitWordModels.size() > 0) {//客户名称分词匹配

                    for (int j = 0; j < splitWordModels.size(); j++) {

                        if (j == 0 && !TextUtils.isEmpty(splitWordModels.get(j).getCont()) && dupArea(splitWordModels.get(j).getCont())) {

                        } else {
                            if (!word.contains(splitWordModels.get(j).getCont()) && resultStr.indexOf(splitWordModels.get(j).getCont()) != -1) {
                                clientData.add(resultModel.getClient_id() + "," + name + "," + splitWordModels.get(j).getCont());
                                resultStrs.add(splitWordModels.get(j).getCont());
                            }
                        }

                    }
                }

                if (contactModels != null && contactModels.size() > 0) {//联系人匹配
                    for (int k = 0; k < contactModels.size(); k++) {
                        if (resultStr.indexOf(contactModels.get(k).getName()) != -1) {
                            clientData.add(resultModel.getClient_id() + "," + name + "  " + contactModels.get(k).getName() + "," + contactModels.get(k).getName());
                            resultStrs.add(contactModels.get(k).getName());
                        }
                    }
                }
            }
        }

        if (editText != null) {
            setContentColor(editText, resultStrs);
        }

        return obtainWithoutDup(clientData);
    }


    private void setContentColor(EditText editText, List<String> resultStrs) {
        SpannableString ss = new SpannableString(editText.getText().toString());

        resultStrs = new ArrayList<>(new HashSet<>(resultStrs));

        for (String str : resultStrs) {
            int pos = editText.getText().toString().indexOf(str);
            if (pos != -1) {
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AppConfig.getContext(), R.color.colorBlue)), pos, pos + str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        editText.setText(ss);
    }

    /**
     * 判断客户名称第一个分词是不是地名
     *
     * @param content
     * @return
     */
    private boolean dupArea(String content) {
        List<AreaModel> areaModels = AppTools.getAreaData(AppConfig.getContext(), "", content);
        areaModels.size();

        if (areaModels.size() > 0) {
            return true;
        }
        return false;

    }

    /**
     * 去掉重复
     *
     * @param clientData
     * @param
     * @return
     */
    private List<BaseDataModel> obtainWithoutDup(List<String> clientData) {

        List<String> listWithoutDup = new ArrayList<>(new HashSet<>(clientData));
        List<BaseDataModel> temp = new ArrayList<>();
        List<BaseDataModel> result = new ArrayList<>();

        for (int i = 0; i < listWithoutDup.size(); i++) {
            temp.add(new BaseDataModel(listWithoutDup.get(i).split(",")[0], listWithoutDup.get(i).split(",")[1], listWithoutDup.get(i).split(",")[2]));
        }

        for (int i = 0; i < temp.size(); i++) { //去掉客户名称重复
            if (i != 0) {
                if (!temp.get(i).getDictionaryName().equals(temp.get(i - 1).getDictionaryName())) {
                    result.add(temp.get(i));
                }
            } else {
                result.add(temp.get(i));
            }
        }

        return result;
    }


    //过滤公司名 需要排除的词
    private String word = "公司 股份 证券 有限 责任 咨询 设备 信息 科技 实业 中国 国际 地产 房地产 客户 " +
            "0 1 2 3 4 5 6 7 8 9";


}
