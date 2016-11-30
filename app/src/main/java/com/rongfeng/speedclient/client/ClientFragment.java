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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientAnalysisAdapter;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.client.entry.AnalysisClientModel;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.common.utils.AppTools;
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
    @Bind(R.id.pass_record)
    Button passRecord;
    @Bind(R.id.future_record)
    Button futureRecord;
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
        models.add(new BaseDataModel("客户总数", "0 个"));
        models.add(new BaseDataModel("关注客户", "0 个"));
        adapter = new ClientAnalysisAdapter(getActivity(), R.layout.client_first_item, models);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    public void invoke() {
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRCOUNTSTATISTICSDOWN, new TypeToken<AnalysisClientModel>() {
        });
    }



    public void invokeStatistics() {
        commonPresenter.isShowProgressDialog = false;
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRCOUNTSTATISTICSTOP, transDataModel,new TypeToken<AnalysisClientModel>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {

            case XxbService.SEARCHCSR:
                AppTools.insertClientDataToDB(getActivity(), (List<AddClientTransModel>) data);

                break;
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
                    radarData.clear();
                    newClientTv.setText(model.getAnalysisNewClient());
                    focusClientTv.setText(model.getAnalysisFocusClient());
                    debtClientTv.setText(model.getAnalysisDebtClient());
                    oldClientTv.setText(model.getAnalysisBargainClient());
                    busClientTv.setText(model.getAnalysisBusinessClient());

                    setRadarViewData(model.getAnalysisNewClient());
                    setRadarViewData(model.getAnalysisFocusClient());
                    setRadarViewData(model.getAnalysisDebtClient());
                    setRadarViewData(model.getAnalysisBargainClient());
                    setRadarViewData(model.getAnalysisBusinessClient());


                    double max = 0;
                    double[] d = new double[5];
                    for (int i = 0; i < radarData.size(); i++) {
                        d[i] = radarData.get(i);
                        if (radarData.get(i) > max) {
                            max = radarData.get(i);
                        }
                    }

                    if (max == 1) {
                        max += 1;
                    }

                    radarView.setValue(d, (float) max);

                }

                break;
        }
    }


    private void setRadarViewData(String source) {
        if (!TextUtils.isEmpty(source) && Integer.parseInt(source) != 0) {
            radarData.add(Double.parseDouble(source));
        } else {
            radarData.add(1d);
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


    @OnClick({R.id.add_client_tv, R.id.old_client_tv, R.id.pass_record, R.id.future_record})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_client_tv:
                startActivity(new Intent(getActivity(), ClientRegisterActivity.class));
                break;
            case R.id.old_client_tv:
                startActivity(new Intent(getActivity(), ClientPersonaActivity.class));
                break;
            case R.id.pass_record:
                setLayoutStatus(passRecord, false);
                transDataModel.setRadarType("0");
                invokeStatistics();
                break;
            case R.id.future_record:
                setLayoutStatus(futureRecord, true);
                transDataModel.setRadarType("1");
                invokeStatistics();
                break;
        }
    }

    /**
     * 设置View状态
     *
     * @param button
     */
    private void setLayoutStatus(Button button, boolean isRight) {
        resetLayout();
        button.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        if (isRight) {
            button.setBackgroundResource(R.drawable.client_top_right_fouces);
        } else {
            button.setBackgroundResource(R.drawable.client_top_left_fouces);

        }
    }

    private void resetLayout() {


        passRecord.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));
        futureRecord.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlue));

        passRecord.setBackgroundResource(R.drawable.client_top_left_normal);
        futureRecord.setBackgroundResource(R.drawable.client_top_right_normal);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", "1"));
                break;
            case 1:
                startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", "2"));
                break;
            case 2:
                startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", "3"));
                break;
            case 3:
                startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", "4"));
                break;
            case 4:
                startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", "5"));
                break;
            case 5:
                startActivity(new Intent(getActivity(), ClientListActivity.class).putExtra("clientType", "6"));
                break;

        }
    }

}
