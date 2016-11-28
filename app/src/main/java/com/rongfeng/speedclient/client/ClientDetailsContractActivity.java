package com.rongfeng.speedclient.client;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.utils.DensityUtil;
import com.rongfeng.speedclient.utils.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rongfeng.speedclient.R.id.res_value_tv;


/**
 * 合同详情
 */
public class ClientDetailsContractActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
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
    @Bind(R.id.contact_reback_layout)
    LinearLayout contactRebackLayout;
    @Bind(R.id.contract_debt_layout)
    LinearLayout contractDebtLayout;
    @Bind(R.id.flowLayout_layout)
    FlowLayout flowLayoutLayout;
    @Bind(R.id.product_layout)
    LinearLayout productLayout;
    @Bind(R.id.contact_bargain_time_layout)
    LinearLayout contactBargainTimeLayout;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.arrow_iv)
    ImageView arrowIv;

    private List<BaseDataModel> dataLabel = new ArrayList<>();

    AddContractTransModel transModel = new AddContractTransModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details_contract_layout);
        ButterKnife.bind(this);
        initViews();
        invoke();
    }

    private void initViews() {

        titleTv.setText("合同详情");
        contractNameTv.setEnabled(false);
        resValueTv.setEnabled(false);
        contactRebackTv.setEnabled(false);
        dataLabel.add(new BaseDataModel("0", "+ 收款"));
        generationLabels(this, dataLabel, flowLayoutLayout);
    }

    private void invoke() {
        transModel = (AddContractTransModel) getIntent().getSerializableExtra("model");
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRCONBYID, transModel, new TypeToken<AddContractTransModel>() {
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
                    resValueTv.setText(transModel.getReturnedMoney());
                    contactBargainTimeTv.setText(transModel.getTransactionDate());
                    contactRebackTv.setText(transModel.getReturnedMoney());
                    contractDebtTv.setText(transModel.getRemainingBalance());


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
                invoke();
                break;
        }
    }


    /**
     * label
     *
     * @param context
     * @param datas
     * @param flowLayout
     */
    public void generationLabels(final Context context, final List<BaseDataModel> datas, final FlowLayout flowLayout) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        lp.height = DensityUtil.dip2px(context, 40);
        flowLayout.removeAllViews();

        for (int i = 0; i < datas.size(); i++) {
            final View view = LayoutInflater.from(context).inflate(R.layout.main_lable_edit_view, null);

            final TextView textView = (TextView) view.findViewById(R.id.label_tv);
            textView.setText(datas.get(i).getDictionaryName());
            view.setTag(datas.get(i));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseDataModel m = (BaseDataModel) v.getTag();
                    switch (m.getDictionaryId()) {
                        case "0":
                            context.startActivity(new Intent(context, ClientAddRebackMoneyActivity.class).putExtra("model", transModel).putExtra("customerId", getIntent().getStringExtra("customerId")));

                            break;

                    }
                }
            });

            view.setLayoutParams(lp);
            flowLayout.addView(view);
        }

    }

    /**
     * 更新label
     *
     * @param id
     */
    private void upLabel(String id) {

        for (int i = 0; i < dataLabel.size(); i++) {
            if (dataLabel.get(i).getDictionaryId().equals(id)) {
                dataLabel.remove(i);
                break;
            }
        }
        generationLabels(this, dataLabel, flowLayoutLayout);
    }


}
