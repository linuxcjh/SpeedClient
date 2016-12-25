package com.rongfeng.speedclient.mine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.DeviceInfoUtil;
import com.rongfeng.speedclient.components.MyDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于
 * 2016/1/12
 */
public class AboutActivity extends BaseActivity {

    TextView titleMidTv;
    @Bind(R.id.version_tv)
    TextView versionTv;
    @Bind(R.id.about_bj_layout)
    RelativeLayout aboutBjLayout;
    @Bind(R.id.about_xa_layout)
    RelativeLayout aboutXaLayout;
    @Bind(R.id.about_service_layout)
    RelativeLayout aboutServiceLayout;
    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.total_tv)
    TextView totalTv;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.mine_company_tv)
    TextView mineCompanyTv;

    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

        titleMidTv.setText("关于行销宝");
        versionTv.setText("行销宝V" + DeviceInfoUtil.instance().getAppInfo());

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.CONFIRMDIALOG:
                    AppTools.callPhone(AboutActivity.this, number);
                    break;
            }
        }
    };

    @OnClick({R.id.about_bj_layout, R.id.about_xa_layout, R.id.about_service_layout, R.id.cancel_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.about_bj_layout:
                number = "010-61272420";
                initDialog();
                break;
            case R.id.about_xa_layout:
                number = "029-68629366";
                initDialog();
                break;
            case R.id.about_service_layout:
                number = "400-052-1169";
                initDialog();
                break;
        }

    }

    private void initDialog() {
        MyDialog dialog = new MyDialog(this, mHandler);
        dialog.buildDialog().setTitle("拨打电话").setMessage(number);

    }


}
