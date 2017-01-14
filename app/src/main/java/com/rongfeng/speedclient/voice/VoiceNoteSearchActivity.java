package com.rongfeng.speedclient.voice;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.CommonPaginationPresenter;
import com.rongfeng.speedclient.common.ICommonPaginationAction;
import com.rongfeng.speedclient.common.utils.AppTools;
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
public class VoiceNoteSearchActivity extends BaseActivity implements ICommonPaginationAction, XRecyclerView.LoadingListener, OnItemClickViewListener {


    @Bind(R.id.left_icon)
    ImageView leftIcon;
    @Bind(R.id.search_et)
    EditText searchEt;
    @Bind(R.id.clear_bt)
    ImageView clearBt;
    @Bind(R.id.cancel_bt)
    ImageView cancelBt;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.client_listView)
    XRecyclerView mRecyclerView;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;
    private VoiceNoteAdapter mAdapter;
    private List<VoiceNoteModel> data = new ArrayList<>();
    private CommonPaginationPresenter commonPaginationPresenter = new CommonPaginationPresenter(this);

    private String searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_note_search_layout);
        ButterKnife.bind(this);
        initViews();
        initViewsAndData();

    }

    private void initViews() {

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


        if (!TextUtils.isEmpty(getIntent().getStringExtra("content"))) {
            searchResult = getIntent().getStringExtra("content");
            onRefresh();
        } else {
            openKeyboard(new Handler(), 200);
        }

    }


    private void initViewsAndData() {
        searchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {//搜索调接口
                    invoke(searchResult);
                }
                return false;
            }

        });

        searchEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    clearBt.setVisibility(View.VISIBLE);
                    mAdapter.setKeyWords(s.toString());
                    searchResult = s.toString();
                    onRefresh();
                } else {
                    clearBt.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * 弹出键盘
     *
     * @param mHandler
     * @param s
     */
    private void openKeyboard(Handler mHandler, int s) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, s);
    }

    private void invoke(String searchResult) {
        if (!TextUtils.isEmpty(searchResult)) {
            transDataModel.setRows("20");
            transDataModel.setKeyword(searchResult);
            transDataModel.setPage(String.valueOf(commonPaginationPresenter.page));
            commonPaginationPresenter.invokeInterfaceObtainData(XxbService.SEARCHNOTE, transDataModel, new TypeToken<List<VoiceNoteModel>>() {
            });
        } else {
            AppTools.getToast("请输入内容");
        }

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

    }

    @Override
    public void onRefresh() {
        commonPaginationPresenter.isShowProgressDialog = false;
        commonPaginationPresenter.isRefresh = true;
        commonPaginationPresenter.page = 0;
        invoke(searchResult);

    }

    @Override
    public void onLoadMore() {
        commonPaginationPresenter.isRefresh = false;
        commonPaginationPresenter.page++;
        invoke(searchResult);

    }

    @OnClick({R.id.left_icon, R.id.clear_bt, R.id.cancel_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_icon:
                mAdapter.setKeyWords(searchEt.getText().toString());
                onRefresh();

                break;
            case R.id.clear_bt:
                searchEt.setText("");
                break;
            case R.id.cancel_bt:
                AppTools.hideKeyboard(searchEt);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppTools.hideKeyboard(searchEt);
    }
}
