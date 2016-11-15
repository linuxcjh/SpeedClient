package com.rongfeng.speedclient.common;

import android.util.Log;

import com.rongfeng.speedclient.BuildConfig;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import okio.Buffer;

/**https://packages-gitlab-com.s3.amazonaws.com/7/8/el/6/package_files/666.rpm?AWSAccessKeyId=AKIAJ74R7IHMTQVGFCEA&Signature=ySPwdqUxd1Tg7M2LafxEbazc0WA%3D&Expires=1445655058
 * Print log in Logcat
 */
public  class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        String requestLog = String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers());
      if(BuildConfig.DEBUG){
          Log.d("-- Retrofit --",  requestLog);
          Log.d("-- Retrofit --",  bodyToString(request));
      }
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        String responseLog = String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers());

        String bodyString = response.body().string();
        if(BuildConfig.DEBUG){
            Log.d("-- Retrofit --",  responseLog);
            Log.d("-- Retrofit --", bodyString);
        }

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
    }

    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8().replaceAll("&","\n");
        } catch (final IOException e) {
            return "did not work";
        }
    }
}

