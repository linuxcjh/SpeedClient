package com.rongfeng.speedclient.manage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 时间
 */
public class ManageSearchDateActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.commit_tv)
    TextView commitTv;
    @Bind(R.id.start_time_tv)
    TextView startTimeTv;
    @Bind(R.id.start_time_layout)
    RelativeLayout startTimeLayout;
    @Bind(R.id.end_time_tv)
    TextView endTimeTv;
    @Bind(R.id.end_time_layout)
    RelativeLayout endTimeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_analysis_search_data_layout);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.start_time_layout, R.id.end_time_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:
                if (TextUtils.isEmpty(startTimeTv.getText().toString())) {
                    AppTools.getToast("请先选择起始日期");
                    break;
                }
                if (TextUtils.isEmpty(endTimeTv.getText().toString())) {
                    AppTools.getToast("请先选择截止日期");
                    break;
                }
                Intent intent = getIntent();
                intent.putExtra("startTime", startTimeTv.getText().toString());
                intent.putExtra("endTime", endTimeTv.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.start_time_layout:
                AppTools.obtainDataCompareNowSlip(this, startTimeTv);
                break;
            case R.id.end_time_layout:
                AppTools.obtainDataCompareSlip(this, startTimeTv, endTimeTv);
                break;
        }
    }


}
