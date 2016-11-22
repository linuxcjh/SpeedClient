package com.rongfeng.speedclient.client;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.utils.FlowLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 添加商机
 */
public class ClientAddBusinessActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.res_bus_name_tv)
    EditText resBusNameTv;
    @Bind(R.id.res_value_tv)
    EditText resValueTv;
    @Bind(R.id.res_bargain_time_tv)
    TextView resBargainTimeTv;
    @Bind(R.id.flowLayout_layout)
    FlowLayout flowLayoutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_business_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.res_bargain_time_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:
                break;
            case R.id.res_bargain_time_tv:
                break;
        }
    }
}