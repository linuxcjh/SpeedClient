package com.rongfeng.speedclient.client;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddBusinessTransModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.Constant;
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
 * 添加商机
 */
public class ClientAddBusinessActivity extends BaseActivity {

    public static final int SELECT_PRODUCT_INDEX = 1;


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
    @Bind(R.id.flowLayout_layout)
    FlowLayout flowLayoutLayout;
    @Bind(R.id.contract_product_tv)
    TextView contractProductTv;
    @Bind(R.id.product_layout)
    LinearLayout productLayout;
    @Bind(R.id.stage_one_image)
    ImageView stageOneImage;
    @Bind(R.id.stage_two_image)
    ImageView stageTwoImage;
    @Bind(R.id.stage_three_image)
    ImageView stageThreeImage;
    @Bind(R.id.stage_four_image)
    ImageView stageFourImage;
    @Bind(R.id.circle_layout)
    LinearLayout circleLayout;
    @Bind(R.id.middle_layout)
    RelativeLayout middleLayout;
    @Bind(R.id.stage_one_tv)
    TextView stageOneTv;
    @Bind(R.id.stage_two_tv)
    TextView stageTwoTv;
    @Bind(R.id.stage_three_tv)
    TextView stageThreeTv;
    @Bind(R.id.stage_four_tv)
    TextView stageFourTv;
    private List<BaseDataModel> dataLabel = new ArrayList<>();

    private List<BaseDataModel> stageModels = new ArrayList<>();

    private AddBusinessTransModel transModel = new AddBusinessTransModel();

    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add_business_layout);
        ButterKnife.bind(this);
        initViews();
        invoke("5");
    }

    private void initViews() {

        dataLabel.add(new BaseDataModel("0", "+ 产品"));
        generationLabels(this, dataLabel, flowLayoutLayout);

    }

    private void invoke() {
        transModel.setCsrId(getIntent().getStringExtra("customerId"));
        transModel.setBusinessName(resBusNameTv.getText().toString());
        transModel.setPredictMoney(resValueTv.getText().toString());
        transModel.setPredictTime(resBargainTimeTv.getText().toString());
        commonPresenter.invokeInterfaceObtainData(XxbService.INSERTCSRBUSINESS, transModel, new TypeToken<BaseDataModel>() {
        });
    }

    /**
     * 获取类型
     *
     * @param flag
     */
    private void invoke(String flag) {
        transDataModel.setFlg(flag);
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCATEGORYLIST, transDataModel,
                new TypeToken<List<BaseDataModel>>() {
                });

    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.INSERTCSRBUSINESS:
                if (status == 1) {
                    AppTools.getToast("添加成功");
                    sendBroadcast(new Intent(Constant.CLIENT_REFRESH_PERSONA));
                    finish();
                }
                break;
            case XxbService.SEARCHCATEGORYLIST:

                if (flag) {
                    AppTools.selectDialog("请选择产品", this, (List<BaseDataModel>) data, mHandler, SELECT_PRODUCT_INDEX);
                } else {
                    stageModels = (List<BaseDataModel>) data;
                    if (stageModels != null && stageModels.size() > 0) {
                        transModel.setBusinessStage(stageModels.get(0).getDictionaryId());
                    }
                }
                break;
        }

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SELECT_PRODUCT_INDEX:
                    BaseDataModel modelLevel = (BaseDataModel) msg.obj;
                    contractProductTv.setText(modelLevel.getDictionaryName());
                    transModel.setProductId(modelLevel.getDictionaryId());
                    break;


            }
        }
    };

    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.res_bargain_time_tv, R.id.product_layout, R.id.stage_one_image, R.id.stage_two_image, R.id.stage_three_image, R.id.stage_four_image, R.id.stage_one_tv, R.id.stage_two_tv, R.id.stage_three_tv, R.id.stage_four_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:
                invoke();
                break;
            case R.id.res_bargain_time_tv:
                AppTools.obtainData(this, resBargainTimeTv);
                break;
            case R.id.product_layout:
                flag = true;
                invoke("6");
                break;
            case R.id.stage_one_image:
            case R.id.stage_one_tv:
                setFocusStatus(stageOneTv, stageOneImage);
                transModel.setBusinessStage(stageModels.get(0).getDictionaryId());

                break;
            case R.id.stage_two_image:
            case R.id.stage_two_tv:
                setFocusStatus(stageTwoTv, stageTwoImage);
                transModel.setBusinessStage(stageModels.get(1).getDictionaryId());

                break;
            case R.id.stage_three_image:
            case R.id.stage_three_tv:
                setFocusStatus(stageThreeTv, stageThreeImage);
                transModel.setBusinessStage(stageModels.get(2).getDictionaryId());

                break;
            case R.id.stage_four_image:
            case R.id.stage_four_tv:

                setFocusStatus(stageFourTv, stageFourImage);
                transModel.setBusinessStage(stageModels.get(3).getDictionaryId());

                break;
        }
    }

    public void setFocusStatus(TextView textView, ImageView imageView) {
        setReset();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
        imageView.setImageResource(R.drawable.addcustomer_type_select);

    }

    public void setReset() {

        stageOneTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        stageTwoTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        stageThreeTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        stageFourTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);


        stageOneTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        stageTwoTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        stageThreeTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        stageFourTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));

        stageOneImage.setImageResource(R.drawable.business_item_progress_bg);
        stageTwoImage.setImageResource(R.drawable.business_item_progress_bg);
        stageThreeImage.setImageResource(R.drawable.business_item_progress_bg);
        stageFourImage.setImageResource(R.drawable.business_item_progress_bg);


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
                            productLayout.setVisibility(View.VISIBLE);
                            upLabel(m.getDictionaryId());
                            flag = true;
                            invoke("6");
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
