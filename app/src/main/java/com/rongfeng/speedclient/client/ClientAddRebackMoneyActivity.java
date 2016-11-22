package com.rongfeng.speedclient.client;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.SingleClickBt;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 添加收款
 */
public class ClientAddRebackMoneyActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.contract_name_tv)
    TextView contractNameTv;
    @Bind(R.id.res_value_tv)
    TextView resValueTv;
    @Bind(R.id.contract_back_tv)
    EditText contractBackTv;
    @Bind(R.id.contract_surplus_tv)
    TextView contractSurplusTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_reback_money_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:
                break;
        }
    }

}
