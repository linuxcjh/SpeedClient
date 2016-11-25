package com.rongfeng.speedclient.call;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 添加商机
 */
public class AlertActivity extends Activity {


    @Bind(R.id.content_et)
    EditText contentEt;
    @Bind(R.id.cancel_bt)
    Button cancelBt;
    @Bind(R.id.confirm_bt)
    Button confirmBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏，一定要在setContentView之前
        setContentView(R.layout.activity_alert_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

    }

    @OnClick({R.id.cancel_bt, R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_bt:
                finish();
                break;
            case R.id.confirm_bt:
                AppTools.getToast("以保存为笔记");
                finish();

                break;
        }
    }
}
