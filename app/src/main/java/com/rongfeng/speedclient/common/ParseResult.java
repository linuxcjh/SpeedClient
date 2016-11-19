package com.rongfeng.speedclient.common;

import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.common.utils.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * AUTHOR: Alex
 * DATE: 17/11/2015 19:35
 */
public class ParseResult {

    public static ParseResult parseResult;

    public static ParseResult instance() {

        if (parseResult == null) {
            parseResult = new ParseResult();
        }
        return parseResult;
    }

    /**
     * 服务器返回数据解析
     *
     * @param receivedStr
     * @param typeToken
     * @return
     */
    public Object requestServer(String methodName, String receivedStr, TypeToken<?> typeToken) {

        Object result = null;
        try {
            if (!TextUtils.isEmpty(receivedStr)) {
                JSONObject obj = new JSONObject(receivedStr);
                String objStr = obj.get("data").toString();
                result = BasePresenter.gson.fromJson(objStr, typeToken.getType());
//                AppConfig.getAcache().put(methodName,objStr);//缓存接口数据内容
                AppConfig.setStringConfig(methodName, objStr);//缓存接口数据内容
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 成功 or 失败
     *
     * @param receivedStr
     * @return
     */
    public boolean requestServerResult(String receivedStr) {

        boolean result = false;
        if (!TextUtils.isEmpty(receivedStr)) {
            BaseReceivedModel model = commonResult(receivedStr);
            if (model != null) {
                if ("1".equals(model.getResult())) {
                    result = true;
                } else if ("0".equals(model.getResult())) {
                    Toast.makeText(AppConfig.getContext(), model.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        return result;
    }

    /**
     * 公共数据
     *
     * @param result
     * @return
     */
    private BaseReceivedModel commonResult(String result) {
        TypeToken<BaseReceivedModel> type = new TypeToken<BaseReceivedModel>() {
        };
        BaseReceivedModel model = null;
        try {
            model = BasePresenter.gson.fromJson(result, type.getType());

        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return model;

    }

}
