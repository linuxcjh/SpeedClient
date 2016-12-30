package com.rongfeng.speedclient.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.RecievedClientTransModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.login.TransDataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rongfeng.speedclient.R.id.client_image_view;
import static com.rongfeng.speedclient.R.id.label_layout_value_tv;
import static com.rongfeng.speedclient.R.id.shortcut_layout;


/**
 * 客户画像
 */
public class ClientPersonaActivity extends BaseActivity {

    public static final int CLIENT_LABEL_INDEX = 0;
    public static final int CLIENT_BUSINESS_INDEX = 1;
    public static final int CLIENT_BARGAIN_INDEX = 2;
    public static final int CLIENT_DEBT_INDEX = 3;
    public static final int CLIENT_SLIDE_ALL_COUNT = 4;//Total page count


    private RefreshBroadCastReceiver refreshBroadCastReceiver;

    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.contact_num_tv)
    TextView contactNumTv;
    @Bind(R.id.contact_layout)
    LinearLayout contactLayout;
    @Bind(R.id.shortcut_contract_tv)
    TextView shortcutContractTv;
    @Bind(R.id.shortcut_bus_tv)
    TextView shortcutBusTv;
    @Bind(R.id.shortcut_record_tv)
    TextView shortcutRecordTv;
    @Bind(R.id.shortcut_layout)
    RelativeLayout shortcutLayout;
    @Bind(R.id.plus_ib)
    ImageButton plusIb;
    @Bind(R.id.client_record_layout)
    LinearLayout clientRecordLayout;
    @Bind(R.id.add_client_tv)
    ImageView addClientTv;
    @Bind(client_image_view)
    ImageView clientImageView;
    @Bind(R.id.client_record_num_tv)
    TextView clientRecordNumTv;
    @Bind(R.id.label_layout_text_tv)
    TextView labelLayoutTextTv;
    @Bind(label_layout_value_tv)
    TextView labelLayoutValueTv;
    @Bind(R.id.label_layout)
    LinearLayout labelLayout;
    @Bind(R.id.bus_layout_text_tv)
    TextView busLayoutTextTv;
    @Bind(R.id.bus_layout_value_tv)
    TextView busLayoutValueTv;
    @Bind(R.id.bus_layout)
    LinearLayout busLayout;
    @Bind(R.id.bargain_layout_text_tv)
    TextView bargainLayoutTextTv;
    @Bind(R.id.bargain_layout_value_tv)
    TextView bargainLayoutValueTv;
    @Bind(R.id.bargain_layout)
    LinearLayout bargainLayout;
    @Bind(R.id.debt_layout_text_tv)
    TextView debtLayoutTextTv;
    @Bind(R.id.debt_layout_value_tv)
    TextView debtLayoutValueTv;
    @Bind(R.id.debt_layout)
    LinearLayout debtLayout;
    @Bind(R.id.label_iv)
    ImageView labelIv;
    @Bind(R.id.bus_iv)
    ImageView busIv;
    @Bind(R.id.bargain_iv)
    ImageView bargainIv;
    @Bind(R.id.debt_iv)
    ImageView debtIv;
    @Bind(R.id.content_viewPager)
    ViewPager contentViewPager;
    @Bind(R.id.client_name_tv)
    TextView clientNameTv;
    @Bind(R.id.focus_bt)
    ImageView focusBt;
    @Bind(R.id.shortcut_connect_tv)
    TextView shortcutConnectTv;


    private ClientPersonaLabelFragment labelFragment;
    private ClientPersonaBusinessFragment businessFragment;
    private ClientPersonaBargainFragment bargainFragment;
    private ClientPersonaDebtFragment debtFragment;

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;
    //当前页
    public int changeStatus = -1;

    private RecievedClientTransModel recievedClientTransModel = new RecievedClientTransModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_persona_layout);
        ButterKnife.bind(this);
        initViewPage();
        initViews();
        invoke();
    }

    private void initViews() {
        clientNameTv.setText(getIntent().getStringExtra("customerName"));
        refreshBroadCastReceiver = new RefreshBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.CLIENT_REFRESH_PERSONA);
        filter.addAction(Constant.CLIENT_REFRESH_PERSONA_LABEL);
        registerReceiver(refreshBroadCastReceiver, filter);

        //语音界面跳转过来
        int flag = getIntent().getIntExtra("flag", 0);
        contentViewPager.setCurrentItem(flag, true);
    }


    private void invoke() {
        commonPresenter.isShowProgressDialog = false;
        transDataModel.setCsrId(getIntent().getStringExtra("customerId"));
        commonPresenter.invokeInterfaceObtainData(XxbService.GETCSRBYID, transDataModel, new TypeToken<RecievedClientTransModel>() {
        });
    }

    /**
     * 关注
     */
    private void focusInvoke(TransDataModel transDataModel) {
        transDataModel.setCsrId(getIntent().getStringExtra("customerId"));
        commonPresenter.invokeInterfaceObtainData(XxbService.UPDATECSRATTENTION, transDataModel, new TypeToken<BaseDataModel>() {
        });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);

        switch (methodIndex) {
            case XxbService.GETCSRBYID:
                if (status == 1) {
                    recievedClientTransModel = (RecievedClientTransModel) data;

                    labelFragment.setClientInfo(recievedClientTransModel);

                    if (recievedClientTransModel.getCustomerType().equals("1")) {
                        clientImageView.setImageResource(R.drawable.custshow_company);
                    } else {
                        clientImageView.setImageResource(R.drawable.custshow_personal);
                    }

                    clientNameTv.setText(recievedClientTransModel.getCustomerName());
                    contactNumTv.setText("联系人(" + recievedClientTransModel.getContactCount() + ")");
                    clientRecordNumTv.setText("跟进(" + recievedClientTransModel.getFollowUpCount() + ")");
                    labelFragment.setData(recievedClientTransModel.getFixationJsonArray());

                    labelLayoutValueTv.setText(recievedClientTransModel.getTagCount() + " 个");
                    busLayoutValueTv.setText("￥ " + AppTools.getNumKbDot(recievedClientTransModel.getBusinessMoney()));
                    bargainLayoutValueTv.setText("￥ " + AppTools.getNumKbDot(recievedClientTransModel.getTurnoverMoney()));
                    debtLayoutValueTv.setText("￥ " + AppTools.getNumKbDot(recievedClientTransModel.getArrearsMoney()));

                    if (recievedClientTransModel != null && recievedClientTransModel.getAttention().equals("1")) {
                        focusBt.setImageResource(R.drawable.cust_focused);
                    } else {
                        focusBt.setImageResource(R.drawable.cust_focus);
                    }
                    focusBt.setVisibility(View.VISIBLE);

                }
                break;
            case XxbService.UPDATECSRATTENTION:
                if (status == 1) {
                    if (recievedClientTransModel != null && recievedClientTransModel.getAttention().equals("1")) {
                        recievedClientTransModel.setAttention("0");
//                        focusBt.setText("未关注");
                        focusBt.setImageResource(R.drawable.cust_focus);
                        AppTools.getToast("已取消关注");
                    } else {
                        recievedClientTransModel.setAttention("1");
//                        focusBt.setText("已关注");
                        AppTools.getToast("已关注");
                        focusBt.setImageResource(R.drawable.cust_focused);

                    }

                }
                break;
        }
    }

    private void initViewPage() {
        String customerId = getIntent().getStringExtra("customerId");
        labelFragment = ClientPersonaLabelFragment.newInstance(customerId);
        businessFragment = ClientPersonaBusinessFragment.newInstance(customerId);
        bargainFragment = ClientPersonaBargainFragment.newInstance(customerId);
        debtFragment = ClientPersonaDebtFragment.newInstance(customerId);

        fragments.add(labelFragment);
        fragments.add(businessFragment);
        fragments.add(bargainFragment);
        fragments.add(debtFragment);
        contentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case CLIENT_LABEL_INDEX:
                        changeStatus = CLIENT_LABEL_INDEX;
                        setLayoutStatus(labelIv, labelLayoutTextTv, labelLayoutValueTv);

                        break;
                    case CLIENT_BUSINESS_INDEX:
                        changeStatus = CLIENT_BUSINESS_INDEX;
                        setLayoutStatus(busIv, busLayoutTextTv, busLayoutValueTv);

                        break;
                    case CLIENT_BARGAIN_INDEX:
                        changeStatus = CLIENT_BARGAIN_INDEX;
                        setLayoutStatus(bargainIv, bargainLayoutTextTv, bargainLayoutValueTv);

                        break;
                    case CLIENT_DEBT_INDEX:
                        changeStatus = CLIENT_DEBT_INDEX;
                        setLayoutStatus(debtIv, debtLayoutTextTv, debtLayoutValueTv);

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                return super.instantiateItem(container, position);
            }

            @Override
            public Fragment getItem(int position) {

                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return CLIENT_SLIDE_ALL_COUNT;
            }
        };

        contentViewPager.setOffscreenPageLimit(3);
        contentViewPager.setAdapter(mAdapter);
    }


    @OnClick({R.id.cancel_tv, R.id.add_client_tv, R.id.contact_layout, R.id.shortcut_contract_tv, R.id.shortcut_bus_tv, R.id.shortcut_record_tv, shortcut_layout, R.id.plus_ib, R.id.client_record_layout, R.id.label_layout, R.id.bus_layout, R.id.bargain_layout, R.id.debt_layout, R.id.focus_bt, R.id.shortcut_connect_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.add_client_tv:
                startActivity(new Intent(this, ClientEditActivity.class).putExtra("model", recievedClientTransModel));

                break;
            case R.id.contact_layout:
                if (!TextUtils.isEmpty(recievedClientTransModel.getCustomerType())) {
                    startActivity(new Intent(this, ClientContactsActivity.class).putExtra("clientType", recievedClientTransModel.getCustomerType()).putExtra("customerId", transDataModel.getCsrId()).putExtra("customerName", clientNameTv.getText().toString()));

                } else {
                    startActivity(new Intent(this, ClientContactsActivity.class).putExtra("customerId", transDataModel.getCsrId()).putExtra("customerName", clientNameTv.getText().toString()));

                }

                break;
            case R.id.shortcut_contract_tv:
                startActivity(new Intent(this, ClientAddContractActivity.class).putExtra("customerId", transDataModel.getCsrId()).putExtra("customerName", clientNameTv.getText().toString()));
                endAnimation();

                break;
            case R.id.shortcut_bus_tv:
                startActivity(new Intent(this, ClientAddBusinessActivity.class).putExtra("customerId", transDataModel.getCsrId()).putExtra("customerName", clientNameTv.getText().toString()));
                endAnimation();

                break;
            case R.id.shortcut_record_tv:
                startActivity(new Intent(this, ClientVisitActivity.class).putExtra("customerId", transDataModel.getCsrId()).putExtra("customerName", clientNameTv.getText().toString()));
                endAnimation();

                break;
            case R.id.shortcut_connect_tv:
                Intent intent = new Intent(this, ClientAddContactUpLoadActivity.class);
                intent.putExtra("customerId", getIntent().getStringExtra("customerId"));
                startActivityForResult(intent, Constant.ADD_CONTACT_REQUEST_CODE);
                endAnimation();

                break;
            case shortcut_layout:
                shortcutLayout.setVisibility(View.GONE);
                endAnimation();
                break;
            case R.id.plus_ib:
                if (shortcutLayout.getVisibility() == View.GONE) {
                    shortcutLayout.setVisibility(View.VISIBLE);
                    startAnimation();
                } else {
                    shortcutLayout.setVisibility(View.GONE);
                    endAnimation();
                }
                break;
            case R.id.client_record_layout:
                startActivity(new Intent(this, ClientRecordsActivity.class).putExtra("customerId", transDataModel.getCsrId()).putExtra("customerName", clientNameTv.getText().toString()));
                break;
            case R.id.label_layout:
                contentViewPager.setCurrentItem(CLIENT_LABEL_INDEX, true);
                break;
            case R.id.bus_layout:
                contentViewPager.setCurrentItem(CLIENT_BUSINESS_INDEX, true);

                break;
            case R.id.bargain_layout:
                contentViewPager.setCurrentItem(CLIENT_BARGAIN_INDEX, true);

                break;
            case R.id.debt_layout:
                contentViewPager.setCurrentItem(CLIENT_DEBT_INDEX, true);

                break;
            case R.id.focus_bt:
                TransDataModel transDataModel = new TransDataModel();
                if (recievedClientTransModel != null && recievedClientTransModel.getAttention().equals("1")) {
                    transDataModel.setAttention("0");
                } else {
                    transDataModel.setAttention("1");
                }
                focusInvoke(transDataModel);
                break;
        }
    }


    /**
     * 设置View状态
     *
     * @param currentView
     * @param textView
     * @param valueView
     */
    private void setLayoutStatus(ImageView currentView, TextView textView, TextView valueView) {
        resetLayout();
        currentView.setVisibility(View.VISIBLE);
        textView.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
        valueView.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
    }

    private void resetLayout() {

        labelIv.setVisibility(View.INVISIBLE);
        busIv.setVisibility(View.INVISIBLE);
        bargainIv.setVisibility(View.INVISIBLE);
        debtIv.setVisibility(View.INVISIBLE);

        labelLayoutTextTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        busLayoutTextTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        bargainLayoutTextTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        debtLayoutTextTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));

        labelLayoutValueTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        busLayoutValueTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        bargainLayoutValueTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));
        debtLayoutValueTv.setTextColor(ContextCompat.getColor(this, R.color.colorAssist));

    }

    /**
     * 中间按钮开始动画
     */
    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.imageview_rotate);
        animation.setFillAfter(true);
        LinearInterpolator interpolator = new LinearInterpolator();
        animation.setInterpolator(interpolator);
        plusIb.startAnimation(animation);

    }

    /**
     * 中间按钮结束动画
     */
    private void endAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.imageview_reback_rotate);
        animation.setFillAfter(true);
        LinearInterpolator interpolator = new LinearInterpolator();
        animation.setInterpolator(interpolator);
        plusIb.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shortcutLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (shortcutLayout.getVisibility() == View.VISIBLE) {
            shortcutLayout.setVisibility(View.GONE);
            endAnimation();
        } else {
            finish();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(refreshBroadCastReceiver);
    }

    class RefreshBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Constant.CLIENT_REFRESH_PERSONA)) {
                invoke();
                businessFragment.invoke();
                bargainFragment.invoke();
                debtFragment.invoke();
            } else if (intent.getAction().equals(Constant.CLIENT_REFRESH_PERSONA_LABEL)) {
                invoke();
            }

        }
    }
}
