package com.rongfeng.speedclient.client;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.client.entry.ContactPersonModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppConfig;
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
 * 客户注册
 */
public class ClientRegisterActivity extends BaseActivity {

    public static final String CLIENT_CONTACT_INDEX = "client_contact_index";
    public static final String CLIENT_VOCATION_INDEX = "client_vocation_index";
    public static final String CLIENT_LEVEL_INDEX = "client_level_index";
    public static final String CLIENT_SOURCE_INDEX = "client_source_index";
    /**
     * 公司客户
     */
    public static final String COMPANY_CLIENT_TYPE = "1";
    /**
     * 个人客户
     */
    public static final String PERSONAL_CLIENT_TYPE = "2";

    /**
     * 客户级别
     */
    public static final int SELECT_TYPE_CLIENT_LEVEL = 1;

    /**
     * 客户行业
     */
    public static final int SELECT_TYPE_CLIENT_VOCATION = 2;

    /**
     * 客户区域
     */
    public static final int SELECT_TYPE_CLIENT_AREA = 4;

    /**
     * 客户来源
     */
    public static final int SELECT_TYPE_CLIENT_ORIGIN = 5;

    /**
     * 选择位置requestCode
     */
    public static final int SELECT_POSITION_CLIENT_REQUEST_CODE = 0x13;


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
    @Bind(R.id.res_client_vocation_tv)
    TextView resClientVocationTv;
    @Bind(R.id.res_client_vocation_layout)
    LinearLayout resClientVocationLayout;
    @Bind(R.id.res_client_level_tv)
    TextView resClientLevelTv;
    @Bind(R.id.res_client_level_layout)
    LinearLayout resClientLevelLayout;
    @Bind(R.id.res_client_source_tv)
    TextView resClientSourceTv;
    @Bind(R.id.res_client_source_layout)
    LinearLayout resClientSourceLayout;

    private List<ContactPersonModel> linkmanModels = new ArrayList<>();
    private List<BaseDataModel> dataLabel = new ArrayList<>();

    private AddClientTransModel transModel = new AddClientTransModel();

    private String selectType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register_layout);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {

        if (!TextUtils.isEmpty(getIntent().getStringExtra("voiceConent"))) {
            resCompanyNameTv.setText(getIntent().getStringExtra("voiceConent"));
        }

        transModel.setCustomerType("2");//客户类型【1企业客户；2个人客户】
        resPhoneTv.clearFocus();

        dataLabel.add(new BaseDataModel(CLIENT_CONTACT_INDEX, "+ 联系人"));
        dataLabel.add(new BaseDataModel(CLIENT_VOCATION_INDEX, "+ 客户行业"));
        dataLabel.add(new BaseDataModel(CLIENT_LEVEL_INDEX, "+ 客户级别"));
        dataLabel.add(new BaseDataModel(CLIENT_SOURCE_INDEX, "+ 客户来源"));
        generationLabels(this, dataLabel, flowLayoutLayout);
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
     * 切换动画效果j
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


    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        switch (methodIndex) {
            case XxbService.SEARCHCATEGORYLIST:
                switch (selectType) {
                    case CLIENT_VOCATION_INDEX:
                        AppTools.selectDialog("请选择客户行业", this, (List<BaseDataModel>) data, mHandler, SELECT_TYPE_CLIENT_VOCATION);

                        break;
                    case CLIENT_LEVEL_INDEX:
                        AppTools.selectDialog("请选择客户级别", this, (List<BaseDataModel>) data, mHandler, SELECT_TYPE_CLIENT_LEVEL);

                        break;
                    case CLIENT_SOURCE_INDEX:
                        AppTools.selectDialog("请选择客户来源", this, (List<BaseDataModel>) data, mHandler, SELECT_TYPE_CLIENT_ORIGIN);


                        break;
                }

                break;
            case XxbService.INSERTCSR:

                if (status == 1) {
                    AppTools.getToast("新增成功");
                }
                break;
        }
    }

    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.res_map_iv, R.id.res_business_client_layout, R.id.res_personal_client_layout, R.id.res_company_addr_detail_tv, R.id.res_client_vocation_layout, R.id.res_client_level_layout, R.id.res_client_source_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:
                if (!TextUtils.isEmpty(resCompanyNameTv.getText().toString())) {

                    transModel.setCustomerName(resCompanyNameTv.getText().toString());
                    transModel.setCustomerTel(resPhoneTv.getText().toString());
                    transModel.setCsrContactJsonArray(BasePresenter.gson.toJson(linkmanModels));

                    commonPresenter.invokeInterfaceObtainData(XxbService.INSERTCSR, transModel,
                            new TypeToken<BaseDataModel>() {
                            });
                } else {
                    AppTools.getToast("请填写客户名称");
                }

                break;
            case R.id.res_company_addr_detail_tv:
            case R.id.res_map_iv:
                Intent intentMap = new Intent(this, ClientDotOverlayMapNewActivity.class);
                startActivityForResult(intentMap, Constant.SELECT_POSITION_CLIENT_REQUEST_CODE);
                break;
            case R.id.res_business_client_layout:
                transModel.setCustomerType("1");
                switchClientStatus(resPersonalClientTv, resPersonalClientIv, resBusinessClientTv, resBusinessClientIv);
                fadeInView(resBusinessClientTv, 0.7f, 0.9f, 0.9f, 200);
                fadeInView(resBusinessClientIv, 0.7f, 0.2f, 0.2f, 200);
                break;
            case R.id.res_personal_client_layout:
                transModel.setCustomerType("2");
                switchClientStatus(resBusinessClientTv, resBusinessClientIv, resPersonalClientTv, resPersonalClientIv);
                fadeInView(resPersonalClientTv, 0.7f, 0.9f, 0.9f, 200);
                fadeInView(resPersonalClientIv, 0.7f, 0.2f, 0.2f, 200);
                break;
            case R.id.res_client_vocation_layout:
                selectType = CLIENT_VOCATION_INDEX;
                invoke("2");
                break;
            case R.id.res_client_level_layout:
                selectType = CLIENT_LEVEL_INDEX;
                invoke("1");
                break;
            case R.id.res_client_source_layout:
                selectType = CLIENT_SOURCE_INDEX;
                invoke("4");
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
                        case CLIENT_CONTACT_INDEX:
                            startActivityForResult(new Intent(context, ClientAddContactActivity.class), Constant.ADD_OR_EDIT_CONTRACT);

                            break;
                        case CLIENT_VOCATION_INDEX:
                            resClientVocationLayout.setVisibility(View.VISIBLE);
                            selectType = CLIENT_VOCATION_INDEX;
                            upLabel(CLIENT_VOCATION_INDEX);
                            invoke("2");

                            break;
                        case CLIENT_LEVEL_INDEX:
                            selectType = CLIENT_LEVEL_INDEX;
                            resClientLevelLayout.setVisibility(View.VISIBLE);
                            upLabel(CLIENT_LEVEL_INDEX);
                            invoke("1");

                            break;

                        case CLIENT_SOURCE_INDEX:
                            selectType = CLIENT_SOURCE_INDEX;
                            resClientSourceLayout.setVisibility(View.VISIBLE);
                            upLabel(CLIENT_SOURCE_INDEX);
                            invoke("4");

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
        generationLabels(ClientRegisterActivity.this, dataLabel, flowLayoutLayout);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            switch (requestCode) {


                case SELECT_POSITION_CLIENT_REQUEST_CODE://选择位置

                    PoiItem poiInfo = (PoiItem) data.getParcelableExtra("poiInfo");
                    if (poiInfo != null) {

                        transModel.setCustomerAddress(poiInfo.getSnippet() + " " + poiInfo.getTitle());
                        resCompanyAddrDetailTv.setText(poiInfo.getSnippet() + " " + poiInfo.getTitle());

                        transModel.setLongitude(poiInfo.getLatLonPoint().getLongitude() + "");
                        transModel.setLatitude(poiInfo.getLatLonPoint().getLatitude() + "");

                    }

                    break;

                case Constant.ADD_OR_EDIT_CONTRACT://添加联系人
                    if (data != null) {
                        ContactPersonModel model = (ContactPersonModel) data.getSerializableExtra("model");
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

    public void setApprovalFlowData(final List<ContactPersonModel> rModel) {
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
                        final ContactPersonModel model = rModel.get(count);
                        View view = LayoutInflater.from(this).inflate(R.layout.client_register_contact_item, null);
                        TextView flowNameTv = (TextView) view.findViewById(R.id.contact_info_tv);
                        ImageView delete_iv = (ImageView) view.findViewById(R.id.delete_iv);
                        flowNameTv.setText(model.getName() + "  " + model.getPhone());

                        view.setTag(count);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AppConfig.setIntConfig("position", (Integer) v.getTag());
                                ClientRegisterActivity.this.startActivityForResult(new Intent(ClientRegisterActivity.this, ClientAddContactActivity.class).putExtra("model", model), Constant.ADD_OR_EDIT_CONTRACT);
                            }
                        });

                        delete_iv.setTag(count);
                        delete_iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mHandler.sendMessage(mHandler.obtainMessage(0x22, (Integer) view.getTag()));
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SELECT_TYPE_CLIENT_LEVEL:
                    BaseDataModel modelLevel = (BaseDataModel) msg.obj;

                    resClientLevelTv.setText(modelLevel.getDictionaryName());
                    transModel.setCustomerLevel(modelLevel.getDictionaryId());
                    break;
                case SELECT_TYPE_CLIENT_VOCATION:
                    BaseDataModel modelVocation = (BaseDataModel) msg.obj;

                    resClientVocationTv.setText(modelVocation.getDictionaryName());
                    transModel.setCustomerIndustry(modelVocation.getDictionaryId());
                    break;
                case SELECT_TYPE_CLIENT_ORIGIN:
                    BaseDataModel modelOrigin = (BaseDataModel) msg.obj;

                    resClientSourceTv.setText(modelOrigin.getDictionaryName());
                    transModel.setCustomerSource(modelOrigin.getDictionaryId());
                    break;
                case 0x22:
                    int index = (int) msg.obj;
                    linkmanModels.remove(index);
                    setApprovalFlowData(linkmanModels);
                    break;


            }
        }
    };

    @Override
    protected void onDestroy() {
        AppConfig.setIntConfig("position", -1);
        super.onDestroy();
    }

}


