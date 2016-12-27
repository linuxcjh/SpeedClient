package com.rongfeng.speedclient.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.ClientDistributeActivity;
import com.rongfeng.speedclient.client.entry.RecievedClientTransModel;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.CommonPaginationPresenter;
import com.rongfeng.speedclient.common.ICommonPaginationAction;
import com.rongfeng.speedclient.mine.adapter.PositionModel;
import com.rongfeng.speedclient.mine.adapter.PositionRecordAdapter;
import com.rongfeng.speedclient.xrecyclerview.OnItemClickViewListener;
import com.rongfeng.speedclient.xrecyclerview.ProgressStyle;
import com.rongfeng.speedclient.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 位置记录
 */
public class MinePositionRecordActivity extends BaseActivity implements ICommonPaginationAction, XRecyclerView.LoadingListener, OnItemClickViewListener {


    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.client_listView)
    XRecyclerView mRecyclerView;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;
    @Bind(R.id.root_layout)
    RelativeLayout rootLayout;
    @Bind(R.id.title_tv)
    TextView titleTv;

    private PositionRecordAdapter mAdapter;
    private List<PositionModel> data = new ArrayList<>();
    private CommonPaginationPresenter commonPaginationPresenter = new CommonPaginationPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_note_layout);
        ButterKnife.bind(this);
        initViews();
        invoke();

    }

    private void initViews() {

        titleTv.setText("我的位置记录");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.Pacman);
        mRecyclerView.setArrowImageView(R.drawable.refresh_pulldown);
        mRecyclerView.setLoadingListener(this);
        mAdapter = new PositionRecordAdapter(this, R.layout.position_record_item, data);
        mAdapter.setOnRecyclerViewListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void invoke() {
        transDataModel.setRows("20");
        transDataModel.setPage(String.valueOf(commonPaginationPresenter.page));
        commonPaginationPresenter.invokeInterfaceObtainData(XxbService.SEARCHRELATEDPOSITION, transDataModel, new TypeToken<List<PositionModel>>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {

        if (data != null) {
            mAdapter.setData((List<PositionModel>) data);
            if (commonPaginationPresenter.data != null && commonPaginationPresenter.data.size() == 0) {
                noDataLayout.setVisibility(View.VISIBLE);

            } else {
                noDataLayout.setVisibility(View.GONE);
            }
        }

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

        PositionModel model = (PositionModel) object;
        RecievedClientTransModel recievedClientTransModel = new RecievedClientTransModel();
        recievedClientTransModel.setLatitude(model.getLatitude());
        recievedClientTransModel.setLongitude(model.getLongitude());
        recievedClientTransModel.setCustomerAddress(model.getAddress());

        startActivity(new Intent(this, ClientDistributeActivity.class).putExtra("model", recievedClientTransModel));

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

    @OnClick(R.id.cancel_tv)
    public void onClick() {
        finish();
    }


}
