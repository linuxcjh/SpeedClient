package com.rongfeng.speedclient.schedule;

import android.os.Bundle;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by 唐磊 on 2015/12/16.
 */
public class ScheduleDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_details_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
    }

}
