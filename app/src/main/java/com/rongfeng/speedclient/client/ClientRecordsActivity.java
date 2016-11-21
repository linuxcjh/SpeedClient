package com.rongfeng.speedclient.client;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 客户跟进记录
 */
public class ClientRecordsActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.client_image_view)
    ImageView clientImageView;
    @Bind(R.id.company_name_tv)
    TextView companyNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_record_layout);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @OnClick(R.id.cancel_tv)
    public void onClick() {
        finish();
    }
}
