package com.rongfeng.speedclient.API;

import com.squareup.okhttp.RequestBody;

import java.util.Map;

import retrofit.Call;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;

/**
 * Total API
 * AUTHOR: Alex
 * DATE: 21/10/2015 18:44
 */
public interface XxbAPI {

//    String URL = "http://crm.xxb001.com/restful/xxbService/";//Schema外网服务地址
    String URL = "http://xxb5pro.xxb001.com/restful/xxbService/";//外网测试
//   String URL = "http://xxb5pro.3swin.net/restful/xxbService/";//测试
//   String URL = "http://192.168.2.118:85/restful/xxbService/";
//   String URL = "http://192.168.2.93:8489/restful/xxbService/";
//   String URL = "http://192.168.2.95:8082/restful/xxbService/";
//   String URL = "http://192.168.2.68:8489/restful/xxbService/";

    /**
     * 通用接口调用
     *
     * @param methodName //     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("{methodName}")
    Call<String> serviceAPI(@Path("methodName") String methodName, @FieldMap() Map<String, String> map);

    //图片上传
    @Multipart
    @POST("uploadFile")
    Call<String> uploadFile(@Part("myfile\"; filename=\"image.png") RequestBody file);

    @Multipart
    @POST("uploadFile")
    Call<String> uploadFile(@PartMap Map<String, RequestBody> params);

}


