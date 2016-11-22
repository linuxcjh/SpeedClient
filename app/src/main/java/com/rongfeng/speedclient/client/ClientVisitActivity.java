package com.rongfeng.speedclient.client;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.components.AddVisitGridLayoutDisplayView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 添加跟进
 */
public class ClientVisitActivity extends BaseActivity {


    @Bind(R.id.back_bt)
    ImageView backBt;
    @Bind(R.id.save_bt)
    TextView saveBt;
    @Bind(R.id.client_name_tv)
    TextView clientNameTv;
    @Bind(R.id.arrow_iv)
    ImageView arrowIv;
    @Bind(R.id.time_tv)
    TextView timeTv;
    @Bind(R.id.add_pic_layout)
    AddVisitGridLayoutDisplayView addPicLayout;
    @Bind(R.id.linear_add_pic_layout)
    LinearLayout linearAddPicLayout;
    @Bind(R.id.remark_et)
    EditText remarkEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_vist_customer_activity);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {

    }


    @OnClick({R.id.back_bt, R.id.save_bt, R.id.time_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_bt:
                finish();
                break;
            case R.id.save_bt:
                break;
            case R.id.time_tv:
                break;
        }
    }
}
