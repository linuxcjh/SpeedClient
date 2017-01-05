package com.rongfeng.speedclient.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.mbiwise.materialintro.prefs.PreferencesManager;

/**
 * 账号与安全
 * 2016/1/12
 */
public class SetActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.account_security_phone_tv)
    TextView accountSecurityPhoneTv;
    @Bind(R.id.account_security_rl1)
    RelativeLayout accountSecurityRl1;
    @Bind(R.id.account_security_rl3)
    RelativeLayout accountSecurityRl3;
    @Bind(R.id.remind_layout)
    RelativeLayout remindLayout;
    @Bind(R.id.reg_confirm_tb)
    ToggleButton regConfirmTb;
    @Bind(R.id.version_num_tv)
    TextView versionNumTv;
    @Bind(R.id.about_layout)
    RelativeLayout aboutLayout;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.net_mac_tv)
    TextView netMacTv;
    @Bind(R.id.net_mac_layout)
    RelativeLayout netMacLayout;
    @Bind(R.id.account_security_exit_bt)
    Button accountSecurityExitBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_security_activity);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        regConfirmTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
//                    model.setIsPolicymaker("1");
                    ;//isPolicymaker;// 是否决策人（0不是决策 人1是决策 人）
                } else {
//                    model.setIsPolicymaker("0");
                }
            }
        });
    }


    @OnClick({R.id.cancel_tv, R.id.account_security_phone_tv, R.id.account_security_exit_bt, R.id.about_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.account_security_phone_tv:
                break;
            case R.id.account_security_exit_bt:

                setResult(RESULT_OK, new Intent());
                finish();

                break;
            case R.id.about_layout:
                new PreferencesManager(getApplicationContext()).resetAll();
//                startActivity(new Intent(this, AboutActivity.class));

                break;
        }
    }
}
