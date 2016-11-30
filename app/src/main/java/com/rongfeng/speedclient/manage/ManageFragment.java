package com.rongfeng.speedclient.manage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.components.ClientSalesPerView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 管理
 * 2016/1/13
 */
public class ManageFragment extends BaseFragment {


    @Bind(R.id.client_tv)
    TextView clientTv;
    @Bind(R.id.check_tv)
    TextView checkTv;
    @Bind(R.id.top_layout)
    RelativeLayout topLayout;
    @Bind(R.id.date_layout)
    LinearLayout dateLayout;
    @Bind(R.id.sales_per_check_text)
    TextView salesPerCheckText;
    @Bind(R.id.sales_per_person_num_tv)
    TextView salesPerPersonNumTv;
    @Bind(R.id.sales_per_check_arrow_iv)
    ImageView salesPerCheckArrowIv;
    @Bind(R.id.sales_per_check_layout)
    LinearLayout salesPerCheckLayout;
    @Bind(R.id.image1)
    ImageView image1;
    @Bind(R.id.text1)
    TextView text1;
    @Bind(R.id.sales_target_tv)
    TextView salesTargetTv;
    @Bind(R.id.sales_target_layout)
    RelativeLayout salesTargetLayout;
    @Bind(R.id.sales_forecast_tv)
    TextView salesForecastTv;
    @Bind(R.id.sales_forecast_layout)
    RelativeLayout salesForecastLayout;
    @Bind(R.id.sales_contract_tv)
    TextView salesContractTv;
    @Bind(R.id.sales_contract_layout)
    RelativeLayout salesContractLayout;
    @Bind(R.id.sales_reback_tv)
    TextView salesRebackTv;
    @Bind(R.id.sales_reback_layout)
    RelativeLayout salesRebackLayout;
    @Bind(R.id.client_sales_perview)
    ClientSalesPerView clientSalesPerview;
    @Bind(R.id.sales_per_layout)
    LinearLayout salesPerLayout;
    @Bind(R.id.sales_bulletin_check_text)
    TextView salesBulletinCheckText;
    @Bind(R.id.sales_bulletin_person_num_tv)
    TextView salesBulletinPersonNumTv;
    @Bind(R.id.bulletin_check_arrow_iv)
    ImageView bulletinCheckArrowIv;
    @Bind(R.id.sales_bulletin_check_layout)
    LinearLayout salesBulletinCheckLayout;
    @Bind(R.id.bulletin_new_client_tv)
    TextView bulletinNewClientTv;
    @Bind(R.id.bulletin_new_client_text)
    TextView bulletinNewClientText;
    @Bind(R.id.bulletin_new_client_layout)
    RelativeLayout bulletinNewClientLayout;
    @Bind(R.id.bulletin_new_opp_tv)
    TextView bulletinNewOppTv;
    @Bind(R.id.bulletin_new_opp_text)
    TextView bulletinNewOppText;
    @Bind(R.id.bulletin_new_opp_layout)
    RelativeLayout bulletinNewOppLayout;
    @Bind(R.id.bulletin_new_visit_tv)
    TextView bulletinNewVisitTv;
    @Bind(R.id.bulletin_new_visit_text)
    TextView bulletinNewVisitText;
    @Bind(R.id.bulletin_new_visit_layout)
    RelativeLayout bulletinNewVisitLayout;
    @Bind(R.id.bulletin_opp_status_change_tv)
    TextView bulletinOppStatusChangeTv;
    @Bind(R.id.bulletin_opp_status_change_text)
    TextView bulletinOppStatusChangeText;
    @Bind(R.id.bulletin_opp_status_change_layout)
    RelativeLayout bulletinOppStatusChangeLayout;
    @Bind(R.id.root_layout)
    LinearLayout rootLayout;
    @Bind(R.id.scroll_sv)
    ScrollView scrollSv;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crm_layout, null);
        ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        clientSalesPerview.setValue(new float[]{40000, 60000, 80000, 50000});

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        clientSalesPerview.setValue(new float[]{40000, 60000, 80000, 50000});

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
