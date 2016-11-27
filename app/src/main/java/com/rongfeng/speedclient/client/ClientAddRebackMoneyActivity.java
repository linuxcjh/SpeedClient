package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddContractTransModel;
import com.rongfeng.speedclient.client.entry.AddRebackMoneyModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.entity.BaseDataModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 添加收款
 */
public class ClientAddRebackMoneyActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.contract_name_tv)
    TextView contractNameTv;
    @Bind(R.id.res_value_tv)
    TextView resValueTv;
    @Bind(R.id.contract_back_tv)
    EditText contractBackTv;
    @Bind(R.id.contract_surplus_tv)
    TextView contractSurplusTv;

    private AddRebackMoneyModel transModel = new AddRebackMoneyModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_reback_money_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        AddContractTransModel m = (AddContractTransModel) getIntent().getSerializableExtra("model");
        transModel.setCsrId(getIntent().getStringExtra("customerId"));
        transModel.setConId(m.getConId());
        contractNameTv.setText(m.getConName());
        resValueTv.setText(m.getConRental());
    }


    private void invoke() {

        transModel.setGatheringMoney(contractBackTv.getText().toString());
        transModel.setResidueMoney(contractSurplusTv.getText().toString());
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTCSRGATHERING, transModel, new TypeToken<BaseDataModel>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.INSERTCSRGATHERING:
                if (status == 1) {
                    AppTools.getToast("添加成功");
                    sendBroadcast(new Intent(Constant.CLIENT_REFRESH_PERSONA));
                    finish();
                }
                break;
        }
    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:
                invoke();
                break;
        }
    }

}
