package com.rongfeng.speedclient.organization;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.CommonPresenter;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.ICommonAction;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.login.TransDataModel;
import com.rongfeng.speedclient.organization.adapter.OrganizationInfoAdapter;
import com.rongfeng.speedclient.organization.model.OrganizationInfoModel;
import com.rongfeng.speedclient.organization.model.OrganizationReceivedModel;
import com.rongfeng.speedclient.xrecyclerview.OnItemClickViewListener;
import com.rongfeng.speedclient.xrecyclerview.ProgressStyle;
import com.rongfeng.speedclient.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrganizationFragment extends BackHandledFragment implements ICommonAction, XRecyclerView.LoadingListener, OnItemClickViewListener {

    @Bind(R.id.search_layout)
    RelativeLayout searchLayout;
    @Bind(R.id.client_listView)
    XRecyclerView mRecyclerView;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;
    private boolean hadIntercept;
    private Handler mHandler;

    private CommonPresenter commonPresenter = new CommonPresenter(this);

    private TransDataModel transDataModel = new TransDataModel();

    private OrganizationReceivedModel receivedModel = new OrganizationReceivedModel();

    private OrganizationInfoAdapter mAdapter;

    private List<OrganizationInfoModel> models = new ArrayList<>();

    public static OrganizationFragment newInstance(String departmentId) {
        Bundle args = new Bundle();
        args.putString("departmentId", departmentId);
        OrganizationFragment fragment = new OrganizationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_organization_layout, null);
        ButterKnife.bind(this, view);
        initView();
        invoke();
        return view;
    }


    private void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.Pacman);
        mRecyclerView.setArrowImageView(R.drawable.refresh_pulldown);
        mRecyclerView.setLoadingListener(this);
        mAdapter = new OrganizationInfoAdapter(getActivity(), models);
        mAdapter.setOnRecyclerViewListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void invoke() {
        transDataModel.setDepartmentId(AppTools.getUser().getDepartmentId());
        transDataModel.setSearchDepartmentId(getArguments().getString("departmentId",""));
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHDEPARTMENTBYALLSK, transDataModel, new TypeToken<OrganizationReceivedModel>() {
        });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status) {

        switch (methodIndex) {
            case XxbService.SEARCHDEPARTMENTBYALLSK:
                if (data != null) {
                    receivedModel = (OrganizationReceivedModel) data;
                    mBackHandledInterface.editDepartment(receivedModel);
                    models.clear();
                    models.addAll(receivedModel.getJsonArrayDep());
                    models.addAll(receivedModel.getJsonArrayUser());

                    mAdapter.setData(models);

                }
                break;
        }

    }

    @OnClick(R.id.search_layout)
    public void onClick() {
        mHandler.sendEmptyMessage(Constant.SHOW_SEARCH_VIEW_INDEX);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 返回true直接退出当前Activity
     *
     * @return
     */
    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public void onItemClick(int position, Object object) {
        OrganizationInfoModel m = (OrganizationInfoModel) object;

        if(TextUtils.isEmpty(m.getUserId())){
            mHandler.sendMessage(mHandler.obtainMessage(Constant.ADD_FRAGMENT_REPEAT_INDEX, m.getDepartmentId()));
        }

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
