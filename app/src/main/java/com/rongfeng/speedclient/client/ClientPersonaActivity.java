package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientPersonaAdapter;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.components.MyGridView;
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
public class ClientPersonaActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.contact_num_tv)
    TextView contactNumTv;
    @Bind(R.id.contact_layout)
    LinearLayout contactLayout;
    @Bind(R.id.grid_view)
    MyGridView gridView;
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
    private ClientPersonaAdapter adapter;
    List<BaseDataModel> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_persona_layout);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        models.add(new BaseDataModel("新客户", "300个"));
        models.add(new BaseDataModel("老客户", "300个"));
        models.add(new BaseDataModel("商机客户", "300个"));
        models.add(new BaseDataModel("欠款客户", "300个"));
        models.add(new BaseDataModel("客户总数", "300个"));
        models.add(new BaseDataModel("关注客户", "300个"));
        adapter = new ClientPersonaAdapter(this, R.layout.client_persona_item, models);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

                break;
            case R.id.shortcut_bus_tv:
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

                setLayoutStatus(labelIv, labelLayoutTextTv, labelLayoutValueTv);
                break;
            case R.id.bus_layout:
                setLayoutStatus(busIv, busLayoutTextTv, busLayoutValueTv);

                break;
            case R.id.bargain_layout:
                setLayoutStatus(bargainIv, bargainLayoutTextTv, bargainLayoutValueTv);

                break;
            case R.id.debt_layout:
                setLayoutStatus(debtIv, debtLayoutTextTv, debtLayoutValueTv);

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
