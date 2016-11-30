package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
    @Bind(R.id.contract_date_tv)
    TextView contractDateTv;

    private AddRebackMoneyModel transModel = new AddRebackMoneyModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_reback_money_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        final AddContractTransModel m = (AddContractTransModel) getIntent().getSerializableExtra("model");
        transModel.setCsrId(getIntent().getStringExtra("customerId"));
        transModel.setConId(m.getConId());
        contractNameTv.setText(m.getConName());
        resValueTv.setText(AppTools.getNumKbDot(m.getRemainingBalance()));

        contractBackTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(m.getRemainingBalance()) && !TextUtils.isEmpty(editable.toString())) {
                    contractSurplusTv.setText(AppTools.getNumKbDot((Float.parseFloat(m.getRemainingBalance()) - Float.parseFloat(editable.toString())) + ""));
                } else {
                    contractSurplusTv.setText(AppTools.getNumKbDot(m.getRemainingBalance()));
                }
            }
        });
    }


    private void invoke() {

        transModel.setGatheringMoney(contractBackTv.getText().toString());
        transModel.setResidueMoney(contractSurplusTv.getText().toString());
        transModel.setCollectionDate(contractDateTv.getText().toString());
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
                    setResult(RESULT_OK, new Intent());
                    finish();
                }
                break;
        }
    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.contract_date_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:
                if (TextUtils.isEmpty(contractBackTv.getText().toString())) {
                    AppTools.getToast("请输入收款金额");
                    return;
                }
                if (TextUtils.isEmpty(contractDateTv.getText().toString())) {
                    AppTools.getToast("请选择收款日期");
                    return;
                }
                invoke();
                break;
            case R.id.contract_date_tv:
                AppTools.obtainData(this, contractDateTv);
                break;
        }
    }

}
