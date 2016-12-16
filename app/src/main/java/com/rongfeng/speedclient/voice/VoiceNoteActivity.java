package com.rongfeng.speedclient.voice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.CommonPaginationPresenter;
import com.rongfeng.speedclient.common.ICommonPaginationAction;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.components.SearchPopupWindow;
import com.rongfeng.speedclient.datanalysis.ClientModel;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.voice.adapter.VoiceNoteAdapter;
import com.rongfeng.speedclient.voice.model.VoiceNoteModel;
import com.rongfeng.speedclient.xrecyclerview.OnItemClickViewListener;
import com.rongfeng.speedclient.xrecyclerview.ProgressStyle;
import com.rongfeng.speedclient.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 笔记
 */
public class VoiceNoteActivity extends BaseActivity implements ICommonPaginationAction, XRecyclerView.LoadingListener, OnItemClickViewListener {


    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.client_listView)
    XRecyclerView mRecyclerView;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;
    @Bind(R.id.root_layout)
    RelativeLayout rootLayout;
    private SearchPopupWindow searchPopupWindow;

    private VoiceNoteAdapter mAdapter;
    private List<VoiceNoteModel> data = new ArrayList<>();
    private CommonPaginationPresenter commonPaginationPresenter = new CommonPaginationPresenter(this);
    private VoiceNoteModel receivedModel = new VoiceNoteModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_note_layout);
        ButterKnife.bind(this);
        initViews();
        invoke();

    }

    private void initViews() {
        searchPopupWindow = new SearchPopupWindow(this, Utils.getDeviceHeightPixels(this), mHandler);
        searchPopupWindow.getPopupWindow();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.Pacman);
        mRecyclerView.setArrowImageView(R.drawable.refresh_pulldown);
        mRecyclerView.setLoadingListener(this);
        mAdapter = new VoiceNoteAdapter(this, R.layout.voice_note_item, data);
        mAdapter.setOnRecyclerViewListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void invoke() {
        transDataModel.setRows("20");
        transDataModel.setPage(String.valueOf(commonPaginationPresenter.page));
        commonPaginationPresenter.invokeInterfaceObtainData(XxbService.SEARCHNOTE, transDataModel, new TypeToken<List<VoiceNoteModel>>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {

        if (data != null) {
            mAdapter.setData((List<VoiceNoteModel>) data);
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

        receivedModel = (VoiceNoteModel) object;
        analysisData(receivedModel.getNoteContent());
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

    /**
     * 解析数据
     */
    private void analysisData(String resultStr) {
        String pinYinStr = AppTools.convertPinYin(resultStr);
        if (!TextUtils.isEmpty(resultStr)) {

            List<ClientModel> clientModels = VoiceAnalysisTools.getInstance().queryClientDataToDB();

            List<BaseDataModel> clientData = new ArrayList<>();

            if (clientModels.size() != 0) {
                for (int i = 0; i < clientModels.size(); i++) {

                    String name = clientModels.get(i).client_name;
                    String namePY = AppTools.convertPinYin(name);

                    if (resultStr.indexOf(name) != -1 || pinYinStr.indexOf(namePY) != -1) {//全名匹配
                        clientData.add(new BaseDataModel(clientModels.get(i).client_id, name));
                    } else if (name.length() > 2 && (resultStr.contains(name.substring(0, 2)) || pinYinStr.contains(AppTools.convertPinYin(name.substring(0, 2))))) {//模糊匹配，开始2个字
                        clientData.add(new BaseDataModel(clientModels.get(i).client_id, name));
                    }

                }
            }
            if (clientData.size() > 1) {
                AppTools.selectDialog("识别到的客户", this, clientData, mHandler, 2);
            } else if (clientData.size() == 1) {
                showPop(clientData.get(0));
            } else if (clientData.size() == 0) {
                showPop(null);
            }


        } else {
            AppTools.getToast("请输入内容");
        }


    }

    /**
     * 显示搜索框
     */
    public void showPop(BaseDataModel model) {
        if (!searchPopupWindow.mPopupWindow.isShowing()) {
            searchPopupWindow.mPopupWindow.showAtLocation(rootLayout, Gravity.TOP, 0, 0);
            searchPopupWindow.setIsFromNoteRecord(true);
            searchPopupWindow.setContent(receivedModel.getNoteContent());
            searchPopupWindow.setSelectClient(model);


        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    BaseDataModel m = (BaseDataModel) msg.obj;
                    showPop(m);
                    break;

            }

        }
    };


}
