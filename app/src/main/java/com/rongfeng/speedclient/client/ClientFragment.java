package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientAnalysisAdapter;
import com.rongfeng.speedclient.client.entry.AnalysisClientModel;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.components.MyGridView;
import com.rongfeng.speedclient.components.RadarChartView;
import com.rongfeng.speedclient.entity.BaseDataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Client
 * 2016/1/13
 */
public class ClientFragment extends BaseFragment implements AdapterView.OnItemClickListener {


    @Bind(R.id.add_client_tv)
    ImageView addClientTv;
    @Bind(R.id.grid_view)
    MyGridView gridView;
    @Bind(R.id.old_client_tv)
    TextView oldClientTv;
    @Bind(R.id.radar_view)
    RadarChartView radarView;
    @Bind(R.id.bus_client_tv)
    TextView busClientTv;
    @Bind(R.id.focus_client_tv)
    TextView focusClientTv;
    @Bind(R.id.new_client_tv)
    TextView newClientTv;
    @Bind(R.id.debt_client_tv)
    TextView debtClientTv;
    @Bind(R.id.top_layout)
    RadioGroup topLayout;
    @Bind(R.id.pass_record)
    RadioButton passRecord;
    @Bind(R.id.future_record)
    RadioButton futureRecord;
    @Bind(R.id.bus_client_layout)
    LinearLayout busClientLayout;
    @Bind(R.id.focus_client_layout)
    LinearLayout focusClientLayout;
    @Bind(R.id.new_client_layout)
    LinearLayout newClientLayout;
    @Bind(R.id.debt_client_layout)
    LinearLayout debtClientLayout;
    @Bind(R.id.old_client_layout)
    LinearLayout oldClientLayout;

    private ClientAnalysisAdapter adapter;
    private List<BaseDataModel> models = new ArrayList<>();
    private AnalysisClientModel model = new AnalysisClientModel();
    private List<Double> radarData = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_layout, null);
        ButterKnife.bind(this, view);
        init();

        return view;
    }


    private void init() {

        transDataModel.setRadarType("0");
        models.add(new BaseDataModel("新客户", "0 个"));
        models.add(new BaseDataModel("老客户", "0 个"));
        models.add(new BaseDataModel("商机客户", "0 个"));
        models.add(new BaseDataModel("欠款客户", "0 个"));
        models.add(new BaseDataModel("重点关注", "0 个"));
        models.add(new BaseDataModel("0次跟进", "0 个"));
        adapter = new ClientAnalysisAdapter(getActivity(), R.layout.client_first_item, models);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        topLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.pass_record) {
                    passRecord.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                    passRecord.setBackgroundResource(R.drawable.client_top_left_fouces);
                    futureRecord.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
                    futureRecord.setBackgroundResource(R.drawable.client_top_right_normal);
                    transDataModel.setRadarType("0");
                    invokeStatistics();
                } else {
                    futureRecord.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                    futureRecord.setBackgroundResource(R.drawable.client_top_right_fouces);
                    passRecord.setBackgroundResource(R.drawable.client_top_left_normal);
                    passRecord.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
                    transDataModel.setRadarType("1");
                    invokeStatistics();
                }
            }
        });


    }


    public void invoke() {
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRCOUNTSTATISTICSDOWN, new TypeToken<AnalysisClientModel>() {
        });
    }


    public void invokeStatistics() {
        commonPresenter.isShowProgressDialog = false;
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRCOUNTSTATISTICSTOP, transDataModel, new TypeToken<AnalysisClientModel>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {

            case XxbService.SEARCHCSRCOUNTSTATISTICSDOWN:
                if (data != null) {
                    model = (AnalysisClientModel) data;
                    models.get(0).setDictionaryName(model.getStatisticNewClient() + " 个");
                    models.get(1).setDictionaryName(model.getStatisticBargainClient() + " 个");
                    models.get(2).setDictionaryName(model.getStatisticBusinessClient() + " 个");
                    models.get(3).setDictionaryName(model.getStatisticDebtClient() + " 个");
                    models.get(4).setDictionaryName(model.getStatisticTotalClient() + " 个");
                    models.get(5).setDictionaryName(model.getStatisticFocusClient() + " 个");

                    adapter.setData(models);
                }

                break;
            case XxbService.SEARCHCSRCOUNTSTATISTICSTOP:
                if (data != null) {

                    model = (AnalysisClientModel) data;

                    newClientTv.setText(model.getAnalysisNewClient());
                    focusClientTv.setText(model.getAnalysisFocusClient());
                    debtClientTv.setText(model.getAnalysisDebtClient());
                    oldClientTv.setText(model.getAnalysisBargainClient());
                    busClientTv.setText(model.getAnalysisBusinessClient());

                    setRadarViewData(model);
                }

                break;
        }
    }

    private void setRadarViewData(AnalysisClientModel model) {
        radarData.clear();
        getRaderViewData(model.getAnalysisBusinessClient());
        getRaderViewData(model.getAnalysisBargainClient());
        getRaderViewData(model.getAnalysisDebtClient());
        getRaderViewData(model.getAnalysisFocusClient());
        getRaderViewData(model.getAnalysisNewClient());

        double max = 0;
        double[] d = new double[5];
        for (int i = 0; i < radarData.size(); i++) {
            d[i] = radarData.get(i);
            if (radarData.get(i) > max) {
                max = radarData.get(i);
            }
        }

        if (max == 0) {
            max = 5;
        }
        for (int i = 0; i < d.length; i++) {
            d[i] = 5d / max * d[i];
        }

        radarView.setValue(d);
    }


    private void getRaderViewData(String source) {
        if (!TextUtils.isEmpty(source)) {
            radarData.add(Double.parseDouble(source));
        } else {
            radarData.add(0d);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        invoke();
        invokeStatistics();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.add_client_tv, R.id.old_client_tv, R.id.bus_client_layout, R.id.focus_client_layout, R.id.new_client_layout, R.id.debt_client_layout, R.id.old_client_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_client_tv:
                startActivity(new Intent(getActivity(), ClientRegisterActivity.class));
                break;
            case R.id.bus_client_layout:
                if (transDataModel.getRadarType().equals("0")) {
                    initStartActivity("8", "过去7日跟进商机客户");
                } else {
                    initStartActivity("13", "未来7日待跟进商机客户");

                }
                break;
            case R.id.focus_client_layout:
                if (transDataModel.getRadarType().equals("0")) {
                    initStartActivity("11", "过去7日跟进关注客户");
                } else {
                    initStartActivity("16", "未来7日待跟进关注客户");

                }
                break;
            case R.id.new_client_layout:
                if (transDataModel.getRadarType().equals("0")) {
                    initStartActivity("7", "过去7日跟进新客户");
                } else {
                    initStartActivity("12", "未来7日待跟进新客户");

                }
                break;
            case R.id.debt_client_layout:
                if (transDataModel.getRadarType().equals("0")) {
                    initStartActivity("10", "过去7日跟进欠款客户");
                } else {
                    initStartActivity("15", "未来7日待跟进欠款客户");

                }
                break;
            case R.id.old_client_layout:
                if (transDataModel.getRadarType().equals("0")) {
                    initStartActivity("9", "过去7日跟进老客户");
                } else {
                    initStartActivity("14", "未来7日待跟进老客户");
                }
                break;
        }
    }


    private void initStartActivity(String type, String typeName) {

        startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", type).putExtra("title", typeName));


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", "1").putExtra("title", "新客户"));
                break;
            case 1:
                startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", "2").putExtra("title", "老客户"));
                break;
            case 2:
                startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", "3").putExtra("title", "商机客户"));
                break;
            case 3:
                startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", "4").putExtra("title", "欠款客户"));
                break;
            case 4:
                startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", "6").putExtra("title", "重点关注客户"));
                break;
            case 5:
                startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", "6").putExtra("title", "0次跟进客户"));//TODO
                break;

        }
    }


}
