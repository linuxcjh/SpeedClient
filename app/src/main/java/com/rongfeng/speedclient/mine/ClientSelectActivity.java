package com.rongfeng.speedclient.mine;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 添加商机
 */
public class ClientSelectActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.total_tv)
    TextView totalTv;
    @Bind(R.id.container_layout)
    FrameLayout containerLayout;
    @Bind(R.id.title_tv)
    TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        titleTv.setText("选择客户");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container_layout, ClientSelectFragment.newInstance());
        transaction.commit();
    }

    @OnClick(R.id.cancel_tv)
    public void onClick() {
        finish();
    }
}
