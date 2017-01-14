package com.rongfeng.speedclient.manage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.manage.adapter.SalesProgressDepartmentDetailListAdapter;
import com.rongfeng.speedclient.manage.model.SalesProgressDepDetailModel;
import com.rongfeng.speedclient.xrecyclerview.OnItemClickViewListener;
import com.rongfeng.speedclient.xrecyclerview.ProgressStyle;
import com.rongfeng.speedclient.xrecyclerview.XRecyclerView;

import java.text.DecimalFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 销售进展个人
 * <p/>
 * Alex
 */
public class ManageSalesProgressDetailActivity extends BaseActivity implements XRecyclerView.LoadingListener, OnItemClickViewListener {


    @Bind(R.id.client_listView)
    XRecyclerView mRecyclerView;
    @Bind(R.id.cancel_ib)
    ImageButton cancelIb;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;


    ImageView contactAvatarIv;
    TextView contactNameTv;
    TextView yearTv;
    TextView targetTv;
    TextView rateTv;
    TextView completeTv;
    TextView listTitleTv;


    SalesProgressDepartmentDetailListAdapter mAdapter;
    @Bind(R.id.title_tv)
    TextView titleTv;
    private DecimalFormat format = new DecimalFormat("######0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm_sales_progress_person_de_layout);
        ButterKnife.bind(this);
        initViews();
        onRefresh();
    }

    private void initViews() {



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.Pacman);
        mRecyclerView.setArrowImageView(R.drawable.refresh_pulldown);
        mRecyclerView.setLoadingListener(this);

        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setPullRefreshEnabled(false);


        View view = LayoutInflater.from(this).inflate(R.layout.crm_sales_progress_person_de_top_layout, null);
        contactAvatarIv = ButterKnife.findById(view, R.id.contact_avatar_iv);
        contactNameTv = ButterKnife.findById(view, R.id.contact_name_tv);
        yearTv = ButterKnife.findById(view, R.id.year_tv);
        rateTv = ButterKnife.findById(view, R.id.rate_tv);
        targetTv = ButterKnife.findById(view, R.id.target_tv);
        completeTv = ButterKnife.findById(view, R.id.complete_tv);
        listTitleTv = ButterKnife.findById(view, R.id.list_title_tv);


        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mRecyclerView.addHeaderView(view);
        AppTools.setImageViewPicture(this, AppTools.getUser().getUserImageUrl(), contactAvatarIv);
        contactNameTv.setText(AppTools.getUser().getUserName());
        yearTv.setText(Calendar.getInstance().get(Calendar.YEAR) + "年 销售进展");

        listTitleTv.setText(Calendar.getInstance().get(Calendar.YEAR) + "年12个月销售进展");

        mAdapter = new SalesProgressDepartmentDetailListAdapter(this);
        mAdapter.setOnRecyclerViewListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        transDataModel.setTargetYear(Calendar.getInstance().get(Calendar.YEAR) + "");
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHSELLINGSTAGE, transDataModel, new TypeToken<SalesProgressDepDetailModel>() {
        });
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {

        SalesProgressDepDetailModel resultModel = (SalesProgressDepDetailModel) data;
        targetTv.setText(AppTools.getNumKb(resultModel.getTargetMoney()));
        completeTv.setText(AppTools.getNumKb(resultModel.getCompleteMoney()));

        if (!TextUtils.isEmpty(resultModel.getTargetMoney()) && !TextUtils.isEmpty(resultModel.getCompleteMoney())) {

            if (Float.parseFloat(resultModel.getTargetMoney()) == 0) {
                rateTv.setText("0.0%");
            } else {
                rateTv.setText((format.format(Float.parseFloat(resultModel.getCompleteMoney()) / Float.parseFloat(resultModel.getTargetMoney()) * 100)) + "%");
            }
        }

        String[] month = getResources().getStringArray(R.array.progress_month_en_);
        for (int i = 0; i < resultModel.getCompleteTargetJSONArray().size(); i++) {
            resultModel.getCompleteTargetJSONArray().get(i).setMonthNumber((i + 1) + "");
            resultModel.getCompleteTargetJSONArray().get(i).setMonthNano(month[i]);
        }

        mAdapter.setData(resultModel.getCompleteTargetJSONArray());
    }

    @Override
    public void onItemClick(int position, Object object) {


    }

    @OnClick({R.id.cancel_ib})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_ib:
                finish();
                break;

        }
    }
}
