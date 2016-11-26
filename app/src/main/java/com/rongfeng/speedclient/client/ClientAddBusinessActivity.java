package com.rongfeng.speedclient.client;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddBusinessTransModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.utils.FlowLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 添加商机
 */
public class ClientAddBusinessActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.res_bus_name_tv)
    EditText resBusNameTv;
    @Bind(R.id.res_value_tv)
    EditText resValueTv;
    @Bind(R.id.res_bargain_time_tv)
    TextView resBargainTimeTv;
    @Bind(R.id.res_map_iv)
    ImageView resMapIv;
    @Bind(R.id.flowLayout_layout)
    FlowLayout flowLayoutLayout;

    private AddBusinessTransModel transModel =new AddBusinessTransModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_business_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

    }

    private void invoke() {
        transModel.setCsrId(getIntent().getStringExtra("customerId"));
        transModel.setBusinessName(resBusNameTv.getText().toString());
        transModel.setBusinessStage("");//TODO
        transModel.setPredictMoney(resValueTv.getText().toString());
        transModel.setPredictTime(resBargainTimeTv.getText().toString());
        transModel.setProductId("");//TODO
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTCSRBUSINESS, transModel, new TypeToken<BaseDataModel>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.INSERTCSRBUSINESS:
                if (status == 1) {
                    AppTools.getToast("添加成功");
                    finish();
                }
                break;
        }
    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.res_bargain_time_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:
                invoke();
                break;
            case R.id.res_bargain_time_tv:
                break;
        }
    }
}
