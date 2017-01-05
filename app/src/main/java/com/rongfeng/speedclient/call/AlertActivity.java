package com.rongfeng.speedclient.call;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.rongfeng.speedclient.R;

import butterknife.ButterKnife;


/**
 * 添加商机
 */
public class AlertActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏，一定要在setContentView之前

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alert_new_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container_layout, CallFragment.newInstance(getIntent()
                        .getStringExtra("clientName"),
                getIntent().getStringExtra("contactName"), getIntent().getStringExtra("clientId")));
        transaction.commit();
    }


    public void convertFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_layout, CallAddRemindFragment.newInstance(getIntent()
                        .getStringExtra("clientName"),
                getIntent().getStringExtra("contactName"), getIntent().getStringExtra("clientId")));
        transaction.commit();
    }


}
