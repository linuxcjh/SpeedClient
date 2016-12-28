package com.rongfeng.speedclient.manage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.CommonPaginationPresenter;
import com.rongfeng.speedclient.common.ICommonPaginationAction;
import com.rongfeng.speedclient.login.TransDataModel;
import com.rongfeng.speedclient.manage.adapter.ManageBargainAdapter;
import com.rongfeng.speedclient.manage.model.ManageFollowModel;
import com.rongfeng.speedclient.xrecyclerview.OnItemClickViewListener;
import com.rongfeng.speedclient.xrecyclerview.ProgressStyle;
import com.rongfeng.speedclient.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Client list
 * <p/>
 * Alex
 */
public class ManageBargainActivity extends BaseActivity implements ICommonPaginationAction, OnItemClickViewListener, XRecyclerView.LoadingListener {

    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.client_listView)
    XRecyclerView mRecyclerView;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;
    @Bind(R.id.title_tv)
    TextView titleTv;

    public ManageBargainAdapter mAdapter;

    public TransDataModel transDataModel = new TransDataModel();

    public CommonPaginationPresenter commonPaginationPresenter = new CommonPaginationPresenter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_follow_layout);
        ButterKnife.bind(this);
        initViews();
        onRefresh();
    }


    private void initViews() {

        titleTv.setText("新增成交");

        transDataModel.setManageStartDate(getIntent().getStringExtra("startDate"));
        transDataModel.setManageEndDate(getIntent().getStringExtra("endDate"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.Pacman);
        mRecyclerView.setArrowImageView(R.drawable.refresh_pulldown);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.initListAndStly(this, 0, 10, 0);
        mAdapter = new ManageBargainAdapter(this, R.layout.manage_bargain_item_layout);
        mAdapter.setOnRecyclerViewListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {

        if (data != null) {
            mAdapter.setData((List<ManageFollowModel>) data);
            if (commonPaginationPresenter.data != null && commonPaginationPresenter.data.size() == 0) {
                noDataLayout.setVisibility(View.VISIBLE);

            } else {
                noDataLayout.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onRefresh() {
        commonPaginationPresenter.isShowProgressDialog = false;
        commonPaginationPresenter.isRefresh = true;
        commonPaginationPresenter.page = 0;
        invoke();

    }

    @Override
    public void onLoadMore() {
        commonPaginationPresenter.isRefresh = false;
        commonPaginationPresenter.page++;
        invoke();

    }

    public void invoke() {
        transDataModel.setPage(String.valueOf(commonPaginationPresenter.page));
        commonPaginationPresenter.invokeInterfaceObtainData(XxbService.SEARCHUSERSYSTEMCSRCON, transDataModel, new TypeToken<List<ManageFollowModel>>() {
        });
    }

    @Override
    public void noMoreData() {
        mRecyclerView.noMoreLoading();
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    public void refreshComplete() {
        mRecyclerView.refreshComplete();
    }

    @Override
    public void onLoadComplete() {
        mRecyclerView.loadMoreComplete();
    }


    @Override
    public void onItemClick(int position, Object object) {
//        ManageFollowModel model = (ManageFollowModel) object;
//        Intent intent = new Intent(this, ClientPersonaActivity.class);
//        intent.putExtra("customerId", model.getCsrId());
//        intent.putExtra("customerName", model.getCustomerName());
//        startActivity(intent);
    }

    @OnClick(R.id.cancel_tv)
    public void onClick() {
        finish();
    }
}
