package com.rongfeng.speedclient.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbAPI;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.DeviceInfoUtil;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.entity.PicInfoModel;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.HashMap;
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
public abstract class BasePresenter {

    public static final int REQUEST_SUCCESS = 1;//请求成功
    public static final int REQUEST_FAILURE = 0;//请求失败
    public boolean isShowProgressDialog = true;
    public ProgressDialog progressDialog;
    private Context context;


    public BasePresenter() {
        if (BaseActivity.activityList.size() > 0) {
            context = BaseActivity.activityList.get(0);
            progressDialog = new ProgressDialog(context);
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
            .baseUrl(AppConfig.getStringConfig("url",XxbAPI.URL))
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
    public void commonApi(final String methodName, Map<String, String> parameterMap, final TypeToken<?> typeToken) {
        isShowFlag = false;
        if (Utils.netWorkJuder(context)) {
            delayDisplayProgress();
            if (parameterMap == null) {//只传公共参数
                parameterMap = AppTools.toMap();
            }

            Call<String> call = service.serviceAPI(methodName, parameterMap);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {
                    dialogDismiss();
                    isShowFlag = true;
                    Object object = null;
                    if (ParseResult.instance().requestServerResult(response.body())) {
                        if (typeToken != null) {
                            object = ParseResult.instance().requestServer(methodName, response.body(), typeToken);
                        }

                        BasePresenter.this.onResponse(methodName, object, REQUEST_SUCCESS);
                    } else {
                        BasePresenter.this.onResponse(methodName, object, REQUEST_FAILURE);
                        BasePresenter.this.onFailure(methodName);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    dialogDismiss();
                    BasePresenter.this.onFailure(methodName);
                    isShowFlag = true;
                    Toast.makeText(AppConfig.getContext(), "数据加载异常！", Toast.LENGTH_SHORT).show();
                }
            });

        } else{
            Toast.makeText(AppConfig.getContext(), "网络不给力，请稍后重试！", Toast.LENGTH_SHORT).show();
            BasePresenter.this.onFailure(methodName);
        }

    }

    /**
     * 只有公共参数的方法
     *
     * @param methodName
     * @param typeToken
     */
    public void commonApi(final String methodName, final TypeToken<?> typeToken) {

        commonApi(methodName, null, typeToken);

    }

    /**
     * 图片上传
     *
     * @param paths
     */
    int i = 0;

    public void uploadFile(final List<String> paths) {
        dialogShow();
        for (String path : paths) {
            i++;
            File file = new File(path);

            Map<String, RequestBody> map = new HashMap<>();
            RequestBody userId = null;
            RequestBody tenementId = null;
            RequestBody appVersion = null;
            RequestBody osVersion = null;
            RequestBody imei = null;
            RequestBody osType = null;
            RequestBody schemaFlag = null;
            try {
                userId = RequestBody.create(MediaType.parse("text/plain"), AppTools.getUser().getUserId());
                tenementId = RequestBody.create(MediaType.parse("text/plain"), AppTools.getUser().getTenementId());
                appVersion = RequestBody.create(MediaType.parse("text/plain"), DeviceInfoUtil.instance().getAppInfo());
                osVersion = RequestBody.create(MediaType.parse("text/plain"), DeviceInfoUtil.instance().getDeviceInfo());
                imei = RequestBody.create(MediaType.parse("text/plain"), DeviceInfoUtil.instance().getDeviceId());
                osType = RequestBody.create(MediaType.parse("text/plain"), "0");
                schemaFlag = RequestBody.create(MediaType.parse("text/plain"), AppTools.getUser().getSchemaFlag());
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);

            String fileName = file.getName();
            if (file.getName().length() >= 10) {
                fileName = file.getName().substring(file.getName().length() - 10, file.getName().length());
            }
            map.put("image\"; filename=\"" + fileName + "", fileBody);
            map.put("userId", userId);
            map.put("tenementId", tenementId);
            map.put("appVersion", appVersion);
            map.put("osVersion", osVersion);
            map.put("imei", imei);
            map.put("osType", osType);
            map.put("schemaFlag", schemaFlag);
            Call<String> call = service.uploadFile(map);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {
                    if (i == paths.size()) {
                        dialogDismiss();
                        i = 0;
                    }
                    Object object = null;
                    if (ParseResult.instance().requestServerResult(response.body())) {
                        object = ParseResult.instance().requestServer("uploadFile", response.body(), new TypeToken<PicInfoModel>() {
                        });
                        BasePresenter.this.onResponse(XxbService.UPLOADFILE, object, REQUEST_SUCCESS);
                    } else {
                        BasePresenter.this.onResponse(XxbService.UPLOADFILE, object, REQUEST_FAILURE);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    if (i == paths.size()) {
                        dialogDismiss();
                        i = 0;
                    }
                }
            });
        }

    }

    protected void dialogShow() {
        if (isShowProgressDialog && progressDialog != null && !progressDialog.isShowing()) {
            try {
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void dialogDismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 无返回值类型
     *
     * @param methodName
     * @param parameterMap
     */
    public void commonApi(final String methodName, Map<String, String> parameterMap) {

        commonApi(methodName, parameterMap, null);
    }


    /**
     * 接口返回数据
     *
     * @param methodName 方法名
     * @param object     返回数据对象
     * @param status     是否成功标识
     */
    public abstract void onResponse(String methodName, Object object, int status) ;
    public  void onFailure(String methodName){}

    private Handler handler = new Handler();
    private boolean isShowFlag; //调用接口成功后dismiss:true /


    /**
     * 延迟显示进度条
     */
    private void delayDisplayProgress() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isShowFlag) {
                    dialogShow();
                }
            }
        }, 500);
    }

}



