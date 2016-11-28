package com.rongfeng.speedclient.client;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientRecordAdapter;
import com.rongfeng.speedclient.client.entry.ClientRecordAllModel;
import com.rongfeng.speedclient.client.entry.ClientRecordModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.CommonPaginationPresenter;
import com.rongfeng.speedclient.common.ICommonPaginationAction;
import com.rongfeng.speedclient.xrecyclerview.OnItemClickViewListener;
import com.rongfeng.speedclient.xrecyclerview.ProgressStyle;
import com.rongfeng.speedclient.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 客户跟进记录
 */
public class ClientRecordsActivity extends BaseActivity implements ICommonPaginationAction, XRecyclerView.LoadingListener, OnItemClickViewListener {
    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.client_image_view)
    ImageView clientImageView;
    @Bind(R.id.company_name_tv)
    TextView companyNameTv;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;
    @Bind(R.id.client_listView)
    XRecyclerView mRecyclerView;
    @Bind(R.id.total_tv)
    TextView totalTv;

    private ClientRecordAdapter mAdapter;
    private ClientRecordAllModel allModel = new ClientRecordAllModel();
    private List<ClientRecordModel> data = new ArrayList<>();
    private CommonPaginationPresenter commonPaginationPresenter = new CommonPaginationPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_record_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.Pacman);
        mRecyclerView.setArrowImageView(R.drawable.refresh_pulldown);
        mRecyclerView.setLoadingListener(this);
        mAdapter = new ClientRecordAdapter(this, R.layout.client_record_item, data);
        mAdapter.setOnRecyclerViewListener(this);
        mRecyclerView.setAdapter(mAdapter);
        invoke();
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status) {

        if (data != null) {
            allModel = (ClientRecordAllModel) commonPaginationPresenter.resultModel;
            totalTv.setText("共 "+allModel.getFollowUpCount()+" 条");
            mAdapter.setData((List<ClientRecordModel>) data);
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

    private void invoke() {
        transDataModel.setCsrId(getIntent().getStringExtra("customerId"));
        transDataModel.setPage(String.valueOf(commonPaginationPresenter.page));
        commonPaginationPresenter.invokeInterfaceObtainData(XxbService.SEARCHFOLLOWUP, transDataModel, new TypeToken<ClientRecordAllModel>() {
        });
    }

    @Override
    public void noMoreData() {
        mRecyclerView.noMoreLoading();
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

    }

    @OnClick(R.id.cancel_tv)
    public void onClick() {
        finish();
    }
}
