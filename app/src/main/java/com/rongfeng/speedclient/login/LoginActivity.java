package com.rongfeng.speedclient.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbAPI;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.CommonPresenter;
import com.rongfeng.speedclient.common.ICommonAction;
import com.rongfeng.speedclient.common.MD5;
import com.rongfeng.speedclient.common.ToStringConverterFactory;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.home.MainTableActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Retrofit;

/**
 * Created by Alex on 2015/11/25.
 * 登录
 */
public class LoginActivity extends BaseActivity implements ICommonAction {


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
    @Bind(R.id.set)
    TextView set;
    @Bind(R.id.mnty)
    TextView mnty;
    @Bind(R.id.register)
    TextView register;
    @Bind(R.id.experience)
    TextView experience;
    @Bind(R.id.login_user)
    RelativeLayout loginUser;

    private CommonPresenter commonPresenter = new CommonPresenter(this);
    private LoginModel transModel = new LoginModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        isLogin();
    }

    /**
     * 判断是否登录
     */
    public void isLogin() {
        if (!TextUtils.isEmpty(AppTools.getUser().getUserId())) {
            gotoActivity();
        }
        userName.setText(AppConfig.getStringConfig("userName", ""));
        userName.setSelection(userName.getText().toString().length());

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (TextUtils.isEmpty(AppConfig.getStringConfig("url", ""))) {
            AppConfig.setStringConfig("url", XxbAPI.URL);
        }
        /**
         * String Convert
         */
        commonPresenter.retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.getStringConfig("url",XxbAPI.URL))
                .client(commonPresenter.client())
                .addConverterFactory(new ToStringConverterFactory())
                .build();

        commonPresenter.service = commonPresenter.retrofit.create(XxbAPI.class);
    }

    /**
     */
    public void gotoActivity() {
        Intent intent = new Intent(LoginActivity.this, MainTableActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        if (status == 1) {
            AppConfig.setStringConfig("userName", userName.getText().toString());
            AppConfig.setStringConfig("userPwd", userPwd.getText().toString());

            Enterprise model = (Enterprise) data;
            AppTools.saveEnterpriseModel(model);
            List<User> arr = model.getArr();

            if (arr != null) {
                int len = arr.size();
                if (len > 1) {
//                    DialogUtil.showDialog(this, this, arr).show(getSupportFragmentManager(), "");
                } else if (len == 1) {
                    if (!AppTools.verifyLoginInfo(model.getArr().get(0).getIsLose(), false)) {
                    } else if (!AppTools.verifyLoginNoEffect(model.getArr().get(0).getIsEffect(), false)) {
                    } else {
                        AppTools.saveUserModel(model.getArr().get(0));
                    }

                }
            }
            startActivity(new Intent(this, MainTableActivity.class));
            finish();
        }

    }

    @OnClick({R.id.login,R.id.set})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                if (verificationLogin()) {
                    transModel.setUserAccount(userName.getText().toString());
                    transModel.setPassword(new MD5().GetMD5Code(userPwd.getText().toString()));
                    commonPresenter.invokeInterfaceObtainData(XxbService.LOGINPHONE, transModel, new TypeToken<Enterprise>() {
                    });
                }
                break;
            case R.id.set:
                startActivity(new Intent(this, SetActivity.class));
                break;


        }
//        startActivity(new Intent(this, MainTableActivity.class));
//        finish();


    }

    private boolean verificationLogin() {
        if (TextUtils.isEmpty(userName.getText().toString())) {
            Toast.makeText(getApplicationContext(), "账号不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(userPwd.getText().toString())) {
            Toast.makeText(getApplicationContext(), "密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
