package com.rongfeng.speedclient.call;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.voice.CallFragment;

import butterknife.ButterKnife;


/**
 * 添加商机
 */
public class AlertActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏，一定要在setContentView之前

        setContentView(R.layout.activity_alert_new_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container_layout, CallFragment.newInstance(getIntent()
                .getStringExtra("clientName"),
                getIntent().getStringExtra("contactName"),getIntent().getStringExtra("clientId")));
        transaction.commit();
    }


}
