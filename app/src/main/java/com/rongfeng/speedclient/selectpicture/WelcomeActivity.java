package com.rongfeng.speedclient.selectpicture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.home.MainTableActivity;
import com.rongfeng.speedclient.login.LoginActivity;


/**
 * 启动页
 */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        // 闪屏的核心代码
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ("1".equals(AppConfig.getStringConfig("login", "0"))) {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    WelcomeActivity.this.finish(); // 结束启动动画界面
                } else {
                    Intent intent = new Intent(WelcomeActivity.this,
                            MainTableActivity.class); // 从启动动画ui跳转到主ui
                    startActivity(intent);
                    WelcomeActivity.this.finish(); // 结束启动动画界面
                }
            }
        }, 400); // 启动动画持续3秒钟
    }

}