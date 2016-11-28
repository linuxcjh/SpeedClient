package com.rongfeng.speedclient.client;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddVisitRecordModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.components.AddVisitGridLayoutDisplayView;
import com.rongfeng.speedclient.entity.BaseDataModel;

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
    SingleClickBt saveBt;
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
    @Bind(R.id.time_layout)
    LinearLayout timeLayout;


    private AddVisitRecordModel visitRecordModel = new AddVisitRecordModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_vist_customer_activity);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        clientNameTv.setText(getIntent().getStringExtra("customerName"));
        timeTv.setText(DateUtil.getTime(DateUtil.yyyy_MM_dd_HH_mm));
    }


    private void invoke() {
        visitRecordModel.setCsrId(getIntent().getStringExtra("customerId"));
        visitRecordModel.setContent(remarkEt.getText().toString());
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTFOLLOWUP, visitRecordModel, new TypeToken<BaseDataModel>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);

        switch (methodIndex) {
            case XxbService.INSERTFOLLOWUP:
                if (status == 1) {
                    AppTools.getToast("添加成功");
                    finish();
                }

                break;
        }
    }

    @OnClick({R.id.back_bt, R.id.save_bt,R.id.time_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_bt:
                finish();
                break;
            case R.id.save_bt:

                invoke();

                break;
            case R.id.time_layout:

                AppTools.obtainDataAndTime(this,timeTv);
                break;
        }
    }
}
