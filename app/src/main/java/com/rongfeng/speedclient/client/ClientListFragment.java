package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientAdapter;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.CommonPaginationPresenter;
import com.rongfeng.speedclient.common.ICommonPaginationAction;
import com.rongfeng.speedclient.login.TransDataModel;
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
public class ClientListFragment extends BaseFragment implements ICommonPaginationAction, OnItemClickViewListener, XRecyclerView.LoadingListener {


    @Bind(R.id.client_listView)
    XRecyclerView mRecyclerView;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.add_data_tv)
    TextView addDataTv;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;


    public ClientAdapter mAdapter;

    public TransDataModel transDataModel = new TransDataModel();

    public CommonPaginationPresenter commonPaginationPresenter = new CommonPaginationPresenter(this);

    public static ClientListFragment newInstance(String clientType) {

        Bundle args = new Bundle();
        args.putString("clientType", clientType);
        ClientListFragment fragment = new ClientListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_list_layout, container, false);
        ButterKnife.bind(this, view);
        initViews();
        onRefresh();
        return view;
    }


    private void initViews() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.Pacman);
        mRecyclerView.setArrowImageView(R.drawable.refresh_pulldown);
        mRecyclerView.setLoadingListener(this);
        mAdapter = new ClientAdapter(getActivity(), R.layout.fragment_client_item_other_layout,getArguments().getString("clientType"));
        mAdapter.setOnRecyclerViewListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {

        if (data != null) {
            mAdapter.setData((List<AddClientTransModel>) data);
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
        transDataModel.setClientType(getArguments().getString("clientType"));
        transDataModel.setPage(String.valueOf(commonPaginationPresenter.page));
        commonPaginationPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSR, transDataModel, new TypeToken<List<AddClientTransModel>>() {
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


    @OnClick({R.id.add_data_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_data_tv:
//                Intent intent = new Intent(getActivity(), ClientRegisterActivity.class);
//                getActivity().startActivityForResult(intent, ClientFragment.REGISTER_CLIENT_REQUEST_CODE);

                break;
        }
    }

    /**
     * 显示搜索框
     */
//    public void showPop() {
//        SearchPopupWindow searchPopupWindow = new SearchPopupWindow(getActivity(), Utils.getDeviceHeightPixels(getActivity()), mHandler);
//        searchPopupWindow.actionSearch = true;
//        searchPopupWindow.getPopupWindow().showAsDropDown(view, 0, -Utils.dip2px(getActivity(), 50));
//        searchPopupWindow.intContent("ManageClientListFragment");
//
//    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(int position, Object object) {
        AddClientTransModel model = (AddClientTransModel) object;
        Intent intent = new Intent(getActivity(), ClientPersonaActivity.class);
        intent.putExtra("customerId", model.getCsrId());
        intent.putExtra("customerName", model.getCustomerName());

        switch (getArguments().getString("clientType")) {
            case "2":
            case "9":
            case "14":
                intent.putExtra("flag", ClientPersonaActivity.CLIENT_BARGAIN_INDEX);
                break;
            case "3":
            case "8":
            case "13":
                 intent.putExtra("flag", ClientPersonaActivity.CLIENT_BUSINESS_INDEX);
                break;
            case "4":
            case "10":
            case "15":
                intent.putExtra("flag", ClientPersonaActivity.CLIENT_DEBT_INDEX);
                break;

        }

        startActivity(intent);
    }

}
