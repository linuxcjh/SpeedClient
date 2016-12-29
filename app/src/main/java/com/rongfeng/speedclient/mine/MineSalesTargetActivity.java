package com.rongfeng.speedclient.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.mine.adapter.MineTargetAdapter;
import com.rongfeng.speedclient.mine.adapter.MineTargetModel;
import com.rongfeng.speedclient.mine.adapter.MineTargetTransAllModel;
import com.rongfeng.speedclient.mine.adapter.MineTargetTransModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Client list
 * <p/>
 * Alex
 */
public class MineSalesTargetActivity extends BaseActivity {


    @Bind(R.id.cancel_tv)
    ImageView cancelTv;
    @Bind(R.id.client_listView)
    RecyclerView mRecyclerView;
    @Bind(R.id.already_count_tv)
    TextView alreadyCountTv;

    public MineTargetAdapter mAdapter;
    private List<MineTargetModel> models = new ArrayList<>();
    private List<MineTargetModel> results = new ArrayList<>();

    private MineTargetTransAllModel transModel = new MineTargetTransAllModel();

    String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_person_target_layout);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews() {
        year = DateUtil.getYear(DateUtil.getStringByDate(new Date(), DateUtil.getDatePattern()));

        String[] str = getResources().getStringArray(R.array.target_person);
        for (int i = 0; i < str.length; i++) {
            MineTargetModel m = new MineTargetModel();
            m.setTargetMonth(str[i]);
            models.add(m);
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MineTargetAdapter(this, R.layout.target_item_layout, models, false);
        mRecyclerView.setAdapter(mAdapter);
        invoke();

    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {


        switch (methodIndex) {
            case XxbService.SEARCHPERFORMANCEOBJECTIVES:
                if (data != null) {

                    float sum = 0f;
                    results = (List<MineTargetModel>) data;
                    for (int i = 0; i < results.size(); i++) {
                        models.get(i).setMonthTarget(results.get(i).getMonthTarget());
                        sum += Float.parseFloat(results.get(i).getMonthTarget());
                    }
                    alreadyCountTv.setText(AppTools.getNumKbDot(sum + "") + " 元");
                    mAdapter.setData(models);
                }
                break;
            case XxbService.SETPERFORMANCEOBJECTIVES:

                if (status == 1) {
                    AppTools.getToast("设置成功");
                    finish();
                }
                break;

        }
    }


    public void invoke() {
        transModel.setTargetUserId(AppTools.getUser().getUserId());
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHPERFORMANCEOBJECTIVES, transModel, new TypeToken<List<MineTargetModel>>() {
        });
    }


    public void addInvoke() {
        commonPresenter.invokeInterfaceObtainData(XxbService.SETPERFORMANCEOBJECTIVES, transModel, new TypeToken<Object>() {
        });
    }


    @OnClick({R.id.cancel_tv, R.id.commit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.commit_tv:

                List<MineTargetTransModel> transList = new ArrayList<>();

                for (int i = 0; i < models.size(); i++) {

                    if (!TextUtils.isEmpty(models.get(i).getMonthTarget())) {
                        MineTargetTransModel m = new MineTargetTransModel();
                        m.setMonthTarget(models.get(i).getMonthTarget());

                        if (i < 9) {
                            m.setTargetMonth(year + "-0" + (i + 1));
                        } else {
                            m.setTargetMonth(year + "-" + (i + 1));
                        }

                        transList.add(m);
                    }
                }

                if (transList.size() > 0) {
                    transModel.setTargetUserId(AppTools.getUser().getUserId());
                    transModel.setTargetJSONArray(BasePresenter.gson.toJson(transList));
                    addInvoke();

                }
                break;
        }
    }
}
