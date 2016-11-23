package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientPersonaLabelAdapter;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.entity.BaseDataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rongfeng.speedclient.R.id.shortcut_layout;


/**
 * 客户画像
 */
public class ClientPersonaActivity extends BaseActivity  {

    public static final int CLIENT_LABEL_INDEX = 0;
    public static final int CLIENT_BUSINESS_INDEX = 1;
    public static final int CLIENT_BARGAIN_INDEX = 2;
    public static final int CLIENT_DEBT_INDEX = 3;
    public static final int CLIENT_SLIDE_ALL_COUNT = 4;//Total page count

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
    @Bind(shortcut_layout)
    RelativeLayout shortcutLayout;
    @Bind(R.id.plus_ib)
    ImageButton plusIb;
    @Bind(R.id.client_record_layout)
    LinearLayout clientRecordLayout;
    @Bind(R.id.add_client_tv)
    TextView addClientTv;
    @Bind(R.id.client_image_view)
    ImageView clientImageView;
    @Bind(R.id.client_record_num_tv)
    TextView clientRecordNumTv;
    @Bind(R.id.label_layout_text_tv)
    TextView labelLayoutTextTv;
    @Bind(R.id.label_layout_value_tv)
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

    private ClientPersonaLabelAdapter adapter;
    private List<BaseDataModel> models = new ArrayList<>();

    private ClientPersonaLabelFragment labelFragment = new ClientPersonaLabelFragment();
    private ClientPersonaBusinessFragment businessFragment = new ClientPersonaBusinessFragment();
    private ClientPersonaBargainFragment bargainFragment = new ClientPersonaBargainFragment();
    private ClientPersonaDebtFragment debtFragment = new ClientPersonaDebtFragment();

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;
    //当前页
    public int changeStatus = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_persona_layout);
        ButterKnife.bind(this);
        initViews();
        initViewPage();
    }

    private void initViews() {
    }

    private void initViewPage() {

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


    @OnClick({R.id.cancel_tv, R.id.contact_layout, R.id.shortcut_contract_tv, R.id.shortcut_bus_tv, R.id.shortcut_record_tv, shortcut_layout, R.id.plus_ib, R.id.client_record_layout, R.id.label_layout, R.id.bus_layout, R.id.bargain_layout, R.id.debt_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.contact_layout:
                startActivity(new Intent(this, ClientContactsActivity.class));
                break;
            case R.id.shortcut_contract_tv:
                startActivity(new Intent(this, ClientAddContractActivity.class));

                break;
            case R.id.shortcut_bus_tv:
                startActivity(new Intent(this, ClientAddBusinessActivity.class));

                break;
            case R.id.shortcut_record_tv:
                startActivity(new Intent(this, ClientVisitActivity.class));

                break;
            case shortcut_layout:
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
                startActivity(new Intent(this, ClientRecordsActivity.class));
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
}
