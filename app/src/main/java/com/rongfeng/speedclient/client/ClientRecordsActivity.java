package com.rongfeng.speedclient.client;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientRecordAdapter;
import com.rongfeng.speedclient.client.entry.ClientRecordModel;
import com.rongfeng.speedclient.common.BaseActivity;
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

    private ClientRecordAdapter mAdapter;
    private List<ClientRecordModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_record_layout);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        data.add(new ClientRecordModel());
        data.add(new ClientRecordModel());
        data.add(new ClientRecordModel());
        data.add(new ClientRecordModel());
        data.add(new ClientRecordModel());
        data.add(new ClientRecordModel());
        data.add(new ClientRecordModel());
        data.add(new ClientRecordModel());
        data.add(new ClientRecordModel());


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
    }

    @Override
    public void onItemClick(int position, Object object) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void noMoreData() {

    }

    @Override
    public void refreshComplete() {

    }

    @Override
    public void onLoadComplete() {

    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {

    }

    @OnClick(R.id.cancel_tv)
    public void onClick() {
        finish();
    }
}
