package com.rongfeng.speedclient.client;

import android.app.ActionBar;
import android.content.Context;
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
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.utils.DensityUtil;
import com.rongfeng.speedclient.utils.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 添加合同
 */
public class ClientAddContractActivity extends BaseActivity {


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
    @Bind(R.id.res_value_tv)
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

    private List<BaseDataModel> dataLabel = new ArrayList<>();

    AddContractTransModel transModel = new AddContractTransModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_contract_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        dataLabel.add(new BaseDataModel("0", "+ 已回款"));
        generationLabels(this, dataLabel, flowLayoutLayout);
    }

    private void invoke() {
        transModel.setCsrId(getIntent().getStringExtra("customerId"));
        transModel.setProductId("");//TODO
        transModel.setConName(contractNameTv.getText().toString());
        transModel.setConRental(contactRebackTv.getText().toString());
        transModel.setRemainingBalance(contractDebtTv.getText().toString());
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTCSRCON, transModel, new TypeToken<BaseDataModel>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.INSERTCSRCON:
                if (status == 1) {
                    AppTools.getToast("添加成功");
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
                            contactRebackLayout.setVisibility(View.VISIBLE);
                            contractDebtLayout.setVisibility(View.VISIBLE);
                            upLabel(m.getDictionaryId());
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
