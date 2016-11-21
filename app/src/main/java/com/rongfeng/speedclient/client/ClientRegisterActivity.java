package com.rongfeng.speedclient.client;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.PoiItem;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.schedule.model.LinkmanModel;
import com.rongfeng.speedclient.utils.DensityUtil;
import com.rongfeng.speedclient.utils.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 客户注册
 */
public class ClientRegisterActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.commit_tv)
    SingleClickBt commitTv;
    @Bind(R.id.res_business_client_tv)
    TextView resBusinessClientTv;
    @Bind(R.id.res_business_client_iv)
    ImageView resBusinessClientIv;
    @Bind(R.id.res_business_client_layout)
    RelativeLayout resBusinessClientLayout;
    @Bind(R.id.res_personal_client_tv)
    TextView resPersonalClientTv;
    @Bind(R.id.res_personal_client_iv)
    ImageView resPersonalClientIv;
    @Bind(R.id.res_personal_client_layout)
    RelativeLayout resPersonalClientLayout;
    @Bind(R.id.res_company_name_tv)
    EditText resCompanyNameTv;
    @Bind(R.id.res_phone_tv)
    EditText resPhoneTv;
    @Bind(R.id.res_company_addr_detail_tv)
    TextView resCompanyAddrDetailTv;
    @Bind(R.id.res_map_iv)
    ImageView resMapIv;
    @Bind(R.id.contacts_layout)
    GridLayout contactsLayout;
    @Bind(R.id.flowLayout_layout)
    FlowLayout flowLayoutLayout;

    private List<LinkmanModel> linkmanModels = new ArrayList<>();


    private List<BaseDataModel> dataLabel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register_layout);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {


        dataLabel.add(new BaseDataModel("0", "+ 联系人"));
        dataLabel.add(new BaseDataModel("1", "+ 区域"));
        dataLabel.add(new BaseDataModel("2", "+ 客户级别"));

        generationLabels(this, dataLabel, flowLayoutLayout);
    }


    /**
     * 切换客户类型
     *
     * @param currentTv
     * @param currentIv
     * @param targetTv
     * @param targetIv
     */

    private void switchClientStatus(TextView currentTv, ImageView currentIv, TextView targetTv, ImageView targetIv) {
        targetTv.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
        targetTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        targetIv.setImageResource(R.drawable.addcustomer_type_select);

        currentIv.setImageResource(R.drawable.addcustomer_type);
        currentTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssistLight));
        currentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    }

    /**
     * 切换动画效果
     *
     * @param view
     * @param alpha
     * @param startScaleX
     * @param startScaleY
     * @param duration
     */
    private void fadeInView(View view, float alpha, float startScaleX, float startScaleY, int duration) {
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", alpha, 1f);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", startScaleX, 1f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", startScaleY, 1f);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorAlpha).with(animatorScaleX).with(animatorScaleY);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }


    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.res_map_iv, R.id.res_business_client_layout, R.id.res_personal_client_layout, R.id.res_company_addr_detail_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:
                break;
            case R.id.res_company_addr_detail_tv:
            case R.id.res_map_iv:
                Intent intentMap = new Intent(this, ClientDotOverlayMapNewActivity.class);
                startActivityForResult(intentMap, Constant.SELECT_POSITION_CLIENT_REQUEST_CODE);
                break;
            case R.id.res_business_client_layout:
                switchClientStatus(resPersonalClientTv, resPersonalClientIv, resBusinessClientTv, resBusinessClientIv);
                fadeInView(resBusinessClientTv, 0.7f, 0.9f, 0.9f, 200);
                fadeInView(resBusinessClientIv, 0.7f, 0.2f, 0.2f, 200);
                break;
            case R.id.res_personal_client_layout:
                switchClientStatus(resBusinessClientTv, resBusinessClientIv, resPersonalClientTv, resPersonalClientIv);
                fadeInView(resPersonalClientTv, 0.7f, 0.9f, 0.9f, 200);
                fadeInView(resPersonalClientIv, 0.7f, 0.2f, 0.2f, 200);
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
                            startActivityForResult(new Intent(context, ClientAddContactActivity.class), Constant.ADD_OR_EDIT_CONTRACT);

                            break;
                        case "1":
                            Toast.makeText(AppConfig.getContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();

                            break;

                    }
                }
            });

            view.setLayoutParams(lp);
            flowLayout.addView(view);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            switch (requestCode) {


                case Constant.SELECT_POSITION_CLIENT_REQUEST_CODE://选择位置

                    PoiItem poiInfo = (PoiItem) data.getParcelableExtra("poiInfo");
                    if (poiInfo != null) {

//                            clientAddPresenter.model.setCustomerAddress(poiInfo.getProvinceName() + poiInfo.getCityName() + poiInfo.getAdName() + poiInfo.getSnippet()+" "+poiInfo.getTitle());
                        resCompanyAddrDetailTv.setText(poiInfo.getProvinceName() + poiInfo.getCityName() + poiInfo.getAdName() + poiInfo.getSnippet() + " " + poiInfo.getTitle());

//                        clientAddPresenter.model.setLongitude(poiInfo.getLatLonPoint().getLongitude() + "");
//                        clientAddPresenter.model.setLatitude(poiInfo.getLatLonPoint().getLatitude() + "");

                    }

                    break;
                case Constant.ADD_OR_EDIT_CONTRACT://添加联系人
                    if (data != null) {
                        LinkmanModel model = (LinkmanModel) data.getSerializableExtra("model");
                        if (AppConfig.getIntConfig("position", -1) != -1) {
                            linkmanModels.set(AppConfig.getIntConfig("position", -1), model);
                            AppConfig.setIntConfig("position", -1);
                        } else {
                            linkmanModels.add(model);
                        }
                        setApprovalFlowData(linkmanModels);
                    }


                    break;


            }
        }


    }

    /**
     * 联系人列表
     *
     * @param rModel
     */
    public void setApprovalFlowData(final List<LinkmanModel> rModel) {
        contactsLayout.removeAllViews();
        int size = rModel.size();

        if (size > 0) {
            final int column = 1; //列数
            int rows = size;//行数

            contactsLayout.setRowCount(rows);
            contactsLayout.setColumnCount(column);

            int count = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < column; j++) {
                    if (size > count) {
                        final LinkmanModel model = rModel.get(count);
                        View view = LayoutInflater.from(this).inflate(R.layout.client_register_contact_item, null);
                        TextView flowNameTv = (TextView) view.findViewById(R.id.contact_info_tv);
                        flowNameTv.setText(model.getName() + "  " + model.getPhone());

                        view.setTag(count);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConfig.setIntConfig("position", (Integer) v.getTag());
                                ClientRegisterActivity.this.startActivityForResult(new Intent(ClientRegisterActivity.this, ClientAddContactActivity.class).putExtra("model", model), Constant.ADD_OR_EDIT_CONTRACT);
                            }
                        });
//
                        count++;

                        GridLayout.Spec rowSpec = GridLayout.spec(i);
                        GridLayout.Spec columnSpec = GridLayout.spec(j);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                        params.setGravity(Gravity.FILL);
                        contactsLayout.addView(view, params);
                    }
                }
            }
        }
    }
}



