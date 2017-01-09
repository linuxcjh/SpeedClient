package com.rongfeng.speedclient.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.CommonPresenter;
import com.rongfeng.speedclient.common.ICommonAction;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.home.MainTableActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alex on 2015/11/25.
 * 登录
 */
public class LoginActivity extends BaseActivity implements ICommonAction {


    @Bind(R.id.input_phone_et)
    EditText inputPhoneEt;
    @Bind(R.id.login_bt)
    Button loginBt;
    private CommonPresenter commonPresenter = new CommonPresenter(this);
    private LoginModel transModel = new LoginModel();

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        isLogin();
    }

    /**
     * 判断是否登录
     */
    public void isLogin() {
        inputPhoneEt.setText(AppConfig.getStringConfig("userName", ""));
        inputPhoneEt.setSelection(inputPhoneEt.getText().toString().length());
        if (!TextUtils.isEmpty(AppTools.getUser().getUserId())) {
            gotoActivity();
//            loginBt.performClick();
        }

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
        progressDialog.dismiss();
        if (status == 1) {
            AppConfig.setStringConfig("userName", inputPhoneEt.getText().toString());

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
            if (AppTools.getUser().getIsForbidden().equals("2") || TextUtils.isEmpty(AppTools.getUser().getUserName())) {
                startActivity(new Intent(this, PersonSetActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, MainTableActivity.class));
                finish();
            }


        }

    }

    @OnClick({R.id.login_bt})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt:
                if (verificationLogin()) {
                    progressDialog.show();
                    transModel.setUserAccount(inputPhoneEt.getText().toString());
//                    transModel.setPassword(new MD5().GetMD5Code(userPwd.getText().toString()));
                    commonPresenter.invokeInterfaceObtainData(XxbService.LOGINPHONE, transModel, new TypeToken<Enterprise>() {
                    });
                }
                break;

        }

    }

    private boolean verificationLogin() {
        if (TextUtils.isEmpty(inputPhoneEt.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入邀请码！", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
