package com.rongfeng.speedclient.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.CommonPaginationPresenter;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.ICommonPaginationAction;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.dynamic.adapter.DynamicAdapter;
import com.rongfeng.speedclient.dynamic.model.DynamicModel;
import com.rongfeng.speedclient.login.TransDataModel;
import com.rongfeng.speedclient.xrecyclerview.OnItemClickViewListener;
import com.rongfeng.speedclient.xrecyclerview.ProgressStyle;
import com.rongfeng.speedclient.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 动态
 * 2016/1/13
 */
public class DynamicFragment extends BaseFragment implements ICommonPaginationAction, OnItemClickViewListener, XRecyclerView.LoadingListener {


    @Bind(R.id.client_listView)
    XRecyclerView mRecyclerView;
    @Bind(R.id.shortcut_camera_tv)
    TextView shortcutCameraTv;
    @Bind(R.id.shortcut_position_tv)
    TextView shortcutPositionTv;
    @Bind(R.id.shortcut_layout)
    RelativeLayout shortcutLayout;
    @Bind(R.id.plus_ib)
    ImageButton plusIb;

    private DynamicAdapter mAdapter;

    public TransDataModel transDataModel = new TransDataModel();

    private CommonPaginationPresenter commonPaginationPresenter = new CommonPaginationPresenter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic_layout, null);
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
        mAdapter = new DynamicAdapter(getActivity(), R.layout.fragment_dynamic_item_layout);
        mAdapter.setOnRecyclerViewListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {

        if (data != null) {
            mAdapter.setData((List<DynamicModel>) data);
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
        transDataModel.setPage(String.valueOf(commonPaginationPresenter.page));
        commonPaginationPresenter.invokeInterfaceObtainData(XxbService.SEARCHHOMEPAGEDYNAMIC, transDataModel, new TypeToken<List<DynamicModel>>() {
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


    }



    @OnClick({R.id.shortcut_camera_tv, R.id.shortcut_position_tv, R.id.plus_ib})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shortcut_camera_tv:
                AppTools.getCapturePath(getActivity(), this);
                endAnimation();


                break;
            case R.id.shortcut_position_tv:
                endAnimation();

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
        }
    }

    /**
     * 中间按钮开始动画
     */
    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.imageview_rotate);
        animation.setFillAfter(true);
        LinearInterpolator interpolator = new LinearInterpolator();
        animation.setInterpolator(interpolator);
        plusIb.startAnimation(animation);

    }

    /**
     * 中间按钮结束动画
     */
    private void endAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.imageview_reback_rotate);
        animation.setFillAfter(true);
        LinearInterpolator interpolator = new LinearInterpolator();
        animation.setInterpolator(interpolator);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shortcutLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        plusIb.startAnimation(animation);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case Constant.CAMERA_REQUEST_CODE: //拍照
//                    addPicLayout.setData(AppConfig.getStringConfig("cameraPath", ""));
                    break;

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
