package com.rongfeng.speedclient.voice;

import android.content.Context;

import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.datanalysis.ClientModel;
import com.rongfeng.speedclient.datanalysis.DBManager;
import com.rongfeng.speedclient.voice.model.SyncClientInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;


/**
 * AUTHOR: Alex
 * DATE: 12/11/2015 09:47
 */
public class VoiceAnalysisTools {


    /**
     * Bean Convert Map
     *
     * @param targetBean
     * @return
     */
    public static Map<String, String> toMap(Object targetBean) {

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
    public static void insertClientDataToDB(Context context, List<SyncClientInfoModel> data) {

        List<ClientModel> persons = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            ClientModel m = new ClientModel();
            m.setClient_name(data.get(i).getCustomerName());
            m.setClient_info(data.get(i).getClientNameWordsSplit());
            m.setClient_id(data.get(i).getCsrId());
            persons.add(m);
        }

        DBManager dbManager = new DBManager(context);
        dbManager.truncate();
        if (persons.size() > 0) {
            dbManager.addClient(persons);
        }

        dbManager.closeDB();

    }


    /**
     * 清空表
     *
     * @param context
     */
    public static void clearForm(Context context) {
        DBManager dbManager = new DBManager(context);
        dbManager.truncate();

        dbManager.closeDB();
    }

    /**
     * 添加单个人到数据库
     */
    public static void insertClientDataToDB(SyncClientInfoModel data, DBManager dbManager) {

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
    public static void updateClientNameSplit(Context context, SyncClientInfoModel data) {
        DBManager dbManager = new DBManager(context);

        ClientModel m = new ClientModel();
        m.setClient_info(data.getClientNameWordsSplit());
        m.setClient_id(data.getCsrId());

        dbManager.updateClient(m);

        dbManager.closeDB();
    }

    /**
     * 更新联系人
     *
     * @param data
     * @param dbManager
     */

    public static void updateClientContact(SyncClientInfoModel data, DBManager dbManager) {

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
    public static String getUploadClientNamesWordForm(List<SyncClientInfoModel> list) {

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
    public static void deleteClientDataToDB(Context context) {

        DBManager dbManager = new DBManager(context);
        dbManager.truncate();
        dbManager.closeDB();

    }

    /**
     * 查询数据库数据
     */
    public static List<ClientModel> queryClientDataToDB(Context context) {
        List<ClientModel> persons = new ArrayList<>();
        DBManager dbManager = new DBManager(context);
        persons = dbManager.query();
        dbManager.closeDB();

        return persons;
    }

    /**
     * 判断指定客户是否存在
     *
     * @param context
     * @param clientId
     * @return
     */
    public static boolean queryClientIsExist(Context context, String clientId) {
        DBManager dbManager = new DBManager(context);
        boolean isExist = dbManager.queryTheClientCursor(clientId);
        dbManager.closeDB();

        return isExist;
    }



}
