package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddContractTransModel;
import com.rongfeng.speedclient.client.entry.RebackMoneyModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.utils.DensityUtil;
import com.rongfeng.speedclient.utils.FlowLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rongfeng.speedclient.R.id.contact_first_pay_tv;
import static com.rongfeng.speedclient.R.id.contact_reback_layout;
import static com.rongfeng.speedclient.R.id.res_value_tv;


/**
 * 合同详情
 */
public class ClientDetailsContractActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.commit_tv)
    TextView commitTv;
    @Bind(R.id.contract_name_tv)
    EditText contractNameTv;
    @Bind(R.id.contract_product_tv)
    TextView contractProductTv;
    @Bind(R.id.res_map_iv)
    ImageView resMapIv;
    @Bind(res_value_tv)
    EditText resValueTv;
    @Bind(R.id.contact_reback_tv)
    EditText contactRebackTv;
    @Bind(R.id.contract_debt_tv)
    TextView contractDebtTv;
    @Bind(R.id.contact_bargain_time_tv)
    TextView contactBargainTimeTv;
    @Bind(contact_reback_layout)
    LinearLayout contactRebackLayout;
    @Bind(R.id.contract_debt_layout)
    LinearLayout contractDebtLayout;
    @Bind(R.id.product_layout)
    LinearLayout productLayout;
    @Bind(R.id.contact_bargain_time_layout)
    LinearLayout contactBargainTimeLayout;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.arrow_iv)
    ImageView arrowIv;
    @Bind(contact_first_pay_tv)
    EditText contactFirstPayTv;


    AddContractTransModel transModel = new AddContractTransModel();
    @Bind(R.id.flowLayout_layout)
    FlowLayout flowLayoutLayout;
    @Bind(R.id.divide_title_tv)
    TextView divideTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details_contract_layout);
        ButterKnife.bind(this);
        initViews();
        invoke();
        invokeRebackDetails();
    }

    private void initViews() {

        titleTv.setText("成交详情");
        contactFirstPayTv.setEnabled(false);
        contractNameTv.setEnabled(false);
        resValueTv.setEnabled(false);
        contactRebackTv.setEnabled(false);
    }

    private void invoke() {
        transModel = (AddContractTransModel) getIntent().getSerializableExtra("model");
//        if (transModel.getRemainingBalance().equals("0")) {
//            commitTv.setVisibility(View.GONE);
//        }
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRCONBYID, transModel, new TypeToken<AddContractTransModel>() {
        });
    }

    /**
     * 回款详情
     */
    private void invokeRebackDetails() {
        transModel.setCsrId(getIntent().getStringExtra("customerId"));
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRGATHERING, transModel, new TypeToken<List<RebackMoneyModel>>() {
        });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.SEARCHCSRCONBYID:
                if (status == 1) {
                    transModel = (AddContractTransModel) data;

                    contractNameTv.setText(transModel.getConName());
                    contractProductTv.setText(transModel.getProductName());
                    resValueTv.setText(AppTools.getNumKbDot(transModel.getConRental()));
                    contactBargainTimeTv.setText(transModel.getTransactionDate());
                    contactRebackTv.setText(AppTools.getNumKbDot(transModel.getMoneyReceipt()));
                    contractDebtTv.setText(AppTools.getNumKbDot(transModel.getRemainingBalance()));
                    contactFirstPayTv.setText(AppTools.getNumKbDot(transModel.getReturnedMoney()));
                    if (transModel.getRemainingBalance().equals("0")) {
                        commitTv.setVisibility(View.GONE);
                    }
                }
                break;
            case XxbService.SEARCHCSRGATHERING:
                if (status == 1) {
                    List<RebackMoneyModel> models = (List<RebackMoneyModel>) data;
                    if (models != null && models.size() > 0) {
                        generationLabels(models);
                        divideTitleTv.setVisibility(View.VISIBLE);
                    } else {
                        divideTitleTv.setVisibility(View.GONE);
                    }

                }
                break;
        }
    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.product_layout, R.id.contact_bargain_time_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:
                startActivity(new Intent(this, ClientAddRebackMoneyActivity.class).putExtra("model", transModel).putExtra("customerId", getIntent().getStringExtra("customerId")));
                break;
        }
    }

    /**
     * label
     *
     * @param datas
     */
    public void generationLabels(final List<RebackMoneyModel> datas) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp.height = DensityUtil.dip2px(this, 60);
        flowLayoutLayout.removeAllViews();

        for (int i = 0; i < datas.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.reback_money_item, null);
            TextView nameTv = (TextView) view.findViewById(R.id.name_tv);
            TextView numTv = (TextView) view.findViewById(R.id.num_tv);

            nameTv.setText("回款金额: " + AppTools.getNumKbDot(datas.get(i).getGatheringMoney()) + "元");
            numTv.setText("回款日期: " + datas.get(i).getGatheringDate());
            view.setLayoutParams(lp);
            flowLayoutLayout.addView(view);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            invoke();
        }
    }
}
