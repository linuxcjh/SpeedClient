package com.rongfeng.speedclient.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.components.ClientSalesPerView;
import com.rongfeng.speedclient.components.ClientSalesProgressTextSurface;
import com.rongfeng.speedclient.manage.model.ReceivedManageModel;

import java.text.DecimalFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 管理
 * 2016/1/13
 */
public class ManageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


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
    @Bind(R.id.sales_progress_check_text)
    TextView salesProgressCheckText;
    @Bind(R.id.progress_person_num_tv)
    TextView progressPersonNumTv;
    @Bind(R.id.sales_progress_check_arrow_iv)
    ImageView salesProgressCheckArrowIv;
    @Bind(R.id.sales_progress_check_layout)
    LinearLayout salesProgressCheckLayout;
    @Bind(R.id.complete_tv)
    TextView completeTv;
    @Bind(R.id.plan_tv)
    TextView planTv;
    @Bind(R.id.sales_progress_view)
    ClientSalesProgressTextSurface salesProgressView;
    @Bind(R.id.today_bt)
    Button todayBt;
    @Bind(R.id.month_bt)
    Button monthBt;
    @Bind(R.id.quarter_bt)
    Button quarterBt;
    @Bind(R.id.year_bt)
    Button yearBt;
    @Bind(R.id.custome_bt)
    Button customeBt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crm_layout, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_blue_light, android.R.color.holo_red_light);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        setStartEndDate(1);
        onRefresh();

    }

    @Override
    public void onRefresh() {
        commonPresenter.isShowProgressDialog = false;
        transDataModel.setQueryId(AppTools.getUser().getUserId());
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHUSERSYSTEM, transDataModel, new TypeToken<ReceivedManageModel>() {
        });


    }

    private void setStartEndDate(int index) {
        switch (index) {
            case 0://今日
                transDataModel.setStartDate(DateUtil.convertDate2String(new Date(), DateUtil.getDatePattern()));
                transDataModel.setEndDate(DateUtil.convertDate2String(new Date(), DateUtil.getDatePattern()));
                progressPersonNumTv.setText(transDataModel.getStartDate());
                salesBulletinPersonNumTv.setText(transDataModel.getStartDate());
                salesPerPersonNumTv.setText(transDataModel.getStartDate());

                break;
            case 1://本月
                transDataModel.setStartDate(DateUtil.convertDate2String(DateUtil.getFirstDayOfMonth(new Date()), DateUtil.getDatePattern()));
                transDataModel.setEndDate(DateUtil.convertDate2String(new Date(), DateUtil.getDatePattern()));
                break;
            case 2://本季
                transDataModel.setStartDate(DateUtil.convertDate2String(DateUtil.getCurrentQuarterStartTime(), DateUtil.getDatePattern()));
                transDataModel.setEndDate(DateUtil.convertDate2String(new Date(), DateUtil.getDatePattern()));
                break;
            case 3://本年
                transDataModel.setStartDate(DateUtil.convertDate2String(DateUtil.getYearFirst(Integer.parseInt(DateUtil.getYear(DateUtil.convertDateToString(new Date())))), DateUtil.getDatePattern()));
                transDataModel.setEndDate(DateUtil.convertDate2String(new Date(), DateUtil.getDatePattern()));
                break;
        }


        if (index != 0) {
            progressPersonNumTv.setText(transDataModel.getStartDate() + " - " + transDataModel.getEndDate());
            salesBulletinPersonNumTv.setText(transDataModel.getStartDate() + " - " + transDataModel.getEndDate());
            salesPerPersonNumTv.setText(transDataModel.getStartDate() + " - " + transDataModel.getEndDate());
        }


    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);

        swipeRefreshLayout.setRefreshing(false);

        if (data != null) {
            ReceivedManageModel model = (ReceivedManageModel) data;
            try {

                //销售进展
                completeTv.setText(AppTools.getNumKb(model.getContractMoney()) + "元");
                planTv.setText("/" + AppTools.getNumKb(model.getTargetMoney()) + "元");

                if (!TextUtils.isEmpty(model.getContractMoney()) && !TextUtils.isEmpty(model.getTargetMoney())) {
                    float temp = 1;
                    if (Float.parseFloat(model.getTargetMoney()) != 0) {
                        temp = Float.parseFloat(model.getContractMoney()) / Float.parseFloat(model.getTargetMoney());
                    } else if (Float.parseFloat(model.getTargetMoney()) == 0 && Float.parseFloat(model.getContractMoney()) == 0) {
                        temp = 0;
                    }
                    DecimalFormat format = new DecimalFormat("#####.000");
                    salesProgressView.setValue((Float.parseFloat(format.format(temp))));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            //销售业绩
            salesTargetTv.setText(AppTools.getNumKb(model.getTargetMoney()));
            salesForecastTv.setText(AppTools.getNumKb(model.getForecastMoney()));
            salesContractTv.setText(AppTools.getNumKb(model.getContractMoney()));
            salesRebackTv.setText(AppTools.getNumKb(model.getReturnedMoney()));
            if (!TextUtils.isEmpty(model.getTargetMoney()) && !TextUtils.isEmpty(model.getForecastMoney()) && !TextUtils.isEmpty(model.getContractMoney()) && !TextUtils.isEmpty(model.getReturnedMoney())) {
                clientSalesPerview.setValue(new float[]{Float.valueOf(model.getTargetMoney()), Float.valueOf(model.getForecastMoney()), Float.valueOf(model.getContractMoney()), Float.valueOf(model.getReturnedMoney())});
            }

            //销售简报
            bulletinNewOppTv.setText(model.getBusinessCount());
            bulletinNewClientTv.setText(model.getAddCount());
            bulletinNewVisitTv.setText(model.getFollowCount());
            bulletinOppStatusChangeTv.setText(model.getContractCount());
        }
    }


    @OnClick({R.id.today_bt, R.id.month_bt, R.id.quarter_bt, R.id.year_bt, R.id.custome_bt, R.id.bulletin_new_client_layout, R.id.bulletin_new_opp_layout, R.id.bulletin_new_visit_layout, R.id.bulletin_opp_status_change_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.today_bt:
                resetButtons(todayBt);
                setStartEndDate(0);
                onRefresh();
                break;
            case R.id.month_bt:
                resetButtons(monthBt);
                setStartEndDate(1);
                onRefresh();
                break;
            case R.id.quarter_bt:
                resetButtons(quarterBt);
                setStartEndDate(2);
                onRefresh();
                break;
            case R.id.year_bt:
                resetButtons(yearBt);
                setStartEndDate(3);
                onRefresh();
                break;
            case R.id.custome_bt:
                resetButtons(customeBt);
                startActivityForResult(new Intent(getActivity(), ManageSearchDateActivity.class), 0x11);

                break;
            case R.id.bulletin_new_client_layout:

                startActivity(new Intent(getActivity(), ManageClientListActivity.class)
                        .putExtra("title", "新增客户")
                        .putExtra("startDate", transDataModel.getStartDate())
                        .putExtra("endDate", transDataModel.getEndDate()));

                break;
            case R.id.bulletin_new_opp_layout:
                startActivity(new Intent(getActivity(), ManageBusinessActivity.class)
                        .putExtra("startDate", transDataModel.getStartDate())
                        .putExtra("endDate", transDataModel.getEndDate()));
                break;
            case R.id.bulletin_new_visit_layout:
                startActivity(new Intent(getActivity(), ManageFollowActivity.class)
                        .putExtra("startDate", transDataModel.getStartDate())
                        .putExtra("endDate", transDataModel.getEndDate()));
                break;
            case R.id.bulletin_opp_status_change_layout:
                startActivity(new Intent(getActivity(), ManageBargainActivity.class)
                        .putExtra("startDate", transDataModel.getStartDate())
                        .putExtra("endDate", transDataModel.getEndDate()));
                break;
        }
    }


    private void resetButtons(Button targetBt) {

        todayBt.setBackgroundResource(R.drawable.stats_tab_normal);
        monthBt.setBackgroundResource(R.drawable.stats_tab_normal);
        quarterBt.setBackgroundResource(R.drawable.stats_tab_normal);
        yearBt.setBackgroundResource(R.drawable.stats_tab_normal);
        customeBt.setBackgroundResource(R.drawable.stats_tab_normal);

        todayBt.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAssist));
        monthBt.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAssist));
        quarterBt.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAssist));
        yearBt.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAssist));
        customeBt.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAssist));

        targetBt.setBackgroundResource(R.drawable.stats_tab_active);
        targetBt.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            transDataModel.setStartDate(data.getStringExtra("startTime"));
            transDataModel.setEndDate(data.getStringExtra("endTime"));

            progressPersonNumTv.setText(transDataModel.getStartDate() + " - " + transDataModel.getEndDate());
            salesBulletinPersonNumTv.setText(transDataModel.getStartDate() + " - " + transDataModel.getEndDate());
            salesPerPersonNumTv.setText(transDataModel.getStartDate() + " - " + transDataModel.getEndDate());

            onRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
