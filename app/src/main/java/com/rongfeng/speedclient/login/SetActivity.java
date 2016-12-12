package com.rongfeng.speedclient.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rongfeng.speedclient.API.XxbAPI;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.ICommonAction;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.SingleClickBt;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alex on 2015/11/25.
 * 登录
 */
public class SetActivity extends BaseActivity implements ICommonAction {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.contract_name_tv)
    EditText contractNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_layout);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        if (TextUtils.isEmpty(AppConfig.getStringConfig("url", ""))) {
            contractNameTv.setText(XxbAPI.URL);
        }else{
            contractNameTv.setText(AppConfig.getStringConfig("url", ""));
        }
    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:

                if(!TextUtils.isEmpty(contractNameTv.getText().toString())){
                    AppConfig.setStringConfig("url",contractNameTv.getText().toString());
                    AppTools.getToast("地址已设为："+contractNameTv.getText().toString());
                    finish();

                }else{
                    AppTools.getToast("请填写服务地址");
                }
                break;
        }
    }
}
