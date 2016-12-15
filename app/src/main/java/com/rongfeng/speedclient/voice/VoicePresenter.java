package com.rongfeng.speedclient.voice;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbAPI;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.LoggingInterceptor;
import com.rongfeng.speedclient.common.ToStringConverterFactory;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.voice.model.SyncClientInfoModel;
import com.squareup.okhttp.OkHttpClient;

import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * AUTHOR: Alex
 * DATE: 21/10/2015 19:09
 */
public abstract class VoicePresenter {

    public static final int REQUEST_SUCCESS = 1;//请求成功
    public static final int REQUEST_FAILURE = 0;//请求失败

    private Context context;


    public VoicePresenter() {
        if (BaseActivity.activityList.size() > 0) {
            context = BaseActivity.activityList.get(0);

        }
    }


    public static Gson gson = new GsonBuilder().serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

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

        if (Utils.netWorkJuder(context)) {

            if (parameterMap == null) {//只传公共参数
                parameterMap = AppTools.toMap();
            }

            Call<String> call = service.serviceAPI(methodName, parameterMap);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {

                    Object object = null;
                    if(response.body()!=null){
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
    public abstract void onResponse(String methodName, SyncClientInfoModel model, Object object, int status);


}



