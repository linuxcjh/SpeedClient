package com.rongfeng.speedclient.voice;

import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbAPI;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.LoggingInterceptor;
import com.rongfeng.speedclient.common.ToStringConverterFactory;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.voice.model.SplitWordModel;
import com.rongfeng.speedclient.voice.model.SyncClientInfoModel;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * AUTHOR: Alex
 * DATE: 21/10/2015 19:09
 */
public class VoicePresenter {


    public static final int REQUEST_SUCCESS = 1;//请求成功
    public static final int REQUEST_FAILURE = 0;//请求失败


    public VoicePresenter() {

    }


    /**
     * String Convert
     */
    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://ltpapi.voicecloud.cn/analysis/")
            .client(client())
            .addConverterFactory(new ToStringConverterFactory())
            .build();

    public XxbAPI service = retrofit.create(XxbAPI.class);


    public OkHttpClient client() {

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new LoggingInterceptor());

        return client;
    }


    /**
     * 接口调用
     *
     * @param methodName   方法名
     * @param parameterMap 参数
     * @param typeToken    返回值类型
     */
    public void commonApi(final String methodName, final SyncClientInfoModel model, Map<String, String> parameterMap, final TypeToken<?> typeToken) {

        if (Utils.netWorkJuder(AppConfig.getContext())) {

            Call<String> call = service.serviceAPI(methodName, parameterMap);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {

                    Object object = null;
                    if (response.body() != null) {
                        object = BasePresenter.gson.fromJson(response.body(), typeToken.getType());
                    }
                    VoicePresenter.this.onResponse(methodName, model, object, REQUEST_SUCCESS);
                }

                @Override
                public void onFailure(Throwable t) {

                    Toast.makeText(AppConfig.getContext(), "数据加载异常！", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(AppConfig.getContext(), "网络不给力，请稍后重试！", Toast.LENGTH_SHORT).show();

        }

    }


    /**
     * 接口返回数据
     *
     * @param methodName 方法名
     * @param object     返回数据对象
     * @param status     是否成功标识
     */
    public void onResponse(String methodName, SyncClientInfoModel model, Object object, int status) {

        if (object != null) {
            List<List<List<SplitWordModel>>> models = (List<List<List<SplitWordModel>>>) object;

            if (models != null && models.size() > 0 && models.get(0).size() > 0) {
                List<SplitWordModel> results = models.get(0).get(0);
                for (int i = 0; i < results.size(); i++) {
                    if (results.get(i).getCont().length() == 1) {//当前为单个字
                        if ((i + 1) < results.size()) {
                            results.get(i).setCont(results.get(i).getCont() + results.get(i + 1).getCont());
                        }
                    }
                }
                model.setClientNameWordsSplit(BasePresenter.gson.toJson(results));
                VoiceAnalysisTools.getInstance().updateClientNameSplit(AppConfig.getContext(), model);//更新分词结果
            }
        }
    }


}



