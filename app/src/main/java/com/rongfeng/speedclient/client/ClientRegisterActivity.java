package com.rongfeng.speedclient.client;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.SingleClickBt;
import com.rongfeng.speedclient.utils.FlowLayout;

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
    LinearLayout contactsLayout;
    @Bind(R.id.flowLayout_layout)
    FlowLayout flowLayoutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register_layout);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {

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


    @OnClick({R.id.cancel_tv, R.id.commit_tv, R.id.res_map_iv,R.id.res_business_client_layout, R.id.res_personal_client_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:
                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            switch (requestCode) {


                case Constant.SELECT_POSITION_CLIENT_REQUEST_CODE://选择位置

                    PoiItem poiInfo = (PoiItem) data.getParcelableExtra("poiInfo");
                    if (poiInfo != null) {

//                        if (poiInfo.getPoiId().equals("1001")) {
//                            clientAddPresenter.model.setCustomerAddress(poiInfo.getSnippet()+" "+poiInfo.getTitle());
//                            resCompanyAddrDetailTv.setText(poiInfo.getSnippet()+" "+poiInfo.getTitle());
//
//                        } else {
//                            clientAddPresenter.model.setCustomerAddress(poiInfo.getProvinceName() + poiInfo.getCityName() + poiInfo.getAdName() + poiInfo.getSnippet()+" "+poiInfo.getTitle());
//                            resCompanyAddrDetailTv.setText(poiInfo.getProvinceName() + poiInfo.getCityName() + poiInfo.getAdName() + poiInfo.getSnippet()+" "+poiInfo.getTitle());
//
//                        }
//                        clientAddPresenter.model.setLongitude(poiInfo.getLatLonPoint().getLongitude() + "");
//                        clientAddPresenter.model.setLatitude(poiInfo.getLatLonPoint().getLatitude() + "");
//
//                        resCompanyAddrTv.setText(poiInfo.getProvinceName() + " " + poiInfo.getCityName() + " " + poiInfo.getAdName());
//                        clientAddPresenter.model.setAreaName(poiInfo.getProvinceName() + poiInfo.getCityName() + poiInfo.getAdName());
//                        clientAddPresenter.model.setProvince(poiInfo.getProvinceCode());
//                        clientAddPresenter.model.setCity(poiInfo.getCityCode());
//                        clientAddPresenter.model.setDistrict(poiInfo.getAdCode());
                    }

                    break;
                case Constant.CONTACT_SELECT_RESULT://从通讯录选择
//                    new GetCustomerContactNum(ClientRegisterActivity.this,
//                            mHandler, Constant.CONTACT_SELECT_RESULT)
//                            .getPhone(data);
                    break;


            }
        }


    }


}
