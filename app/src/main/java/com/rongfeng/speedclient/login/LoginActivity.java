package com.rongfeng.speedclient.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.home.MainTableActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alex on 2015/11/25.
 * 登录
 */
public class LoginActivity extends BaseActivity {


    @Bind(R.id.user_name)
    EditText userName;
    @Bind(R.id.user_pwd)
    EditText userPwd;
    @Bind(R.id.forget_password)
    TextView forgetPassword;
    @Bind(R.id.user_pwd_layout)
    RelativeLayout userPwdLayout;
    @Bind(R.id.forget_pwd)
    TextView forgetPwd;
    @Bind(R.id.login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.login)
    public void onClick() {

        startActivity(new Intent(this, MainTableActivity.class));
        finish();
    }
}
