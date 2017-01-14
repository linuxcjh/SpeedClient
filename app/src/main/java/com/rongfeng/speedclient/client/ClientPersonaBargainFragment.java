package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientPersonaBargainAdapter;
import com.rongfeng.speedclient.client.entry.AddContractTransModel;
import com.rongfeng.speedclient.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Client标签
 * 2016/1/13
 */
public class ClientPersonaBargainFragment extends BaseFragment implements AdapterView.OnItemClickListener {


    @Bind(R.id.grid_view)
    ListView listView;

    private ClientPersonaBargainAdapter adapter;
    List<AddContractTransModel> models = new ArrayList<>();

    public static ClientPersonaBargainFragment newInstance(String customerId) {

        Bundle args = new Bundle();
        args.putString("customerId", customerId);
        ClientPersonaBargainFragment fragment = new ClientPersonaBargainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_persona_bargain_fragment_layout, null);
        ButterKnife.bind(this, view);

        init();
        invoke();

        return view;
    }


    private void init() {
        addHeadView();
        listView.setOnItemClickListener(this);
        adapter = new ClientPersonaBargainAdapter(getActivity(), R.layout.client_persona_bargain_item, models, getArguments().getString("customerId", ""));
        listView.setAdapter(adapter);

    }

    private void addHeadView() {
        View headView = getActivity().getLayoutInflater().inflate(R.layout.persona_head_view, null);
        TextView contentTv = (TextView) headView.findViewById(R.id.content_tv);
        contentTv.setText("+ 添加成交");
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ClientAddContractActivity.class).putExtra("customerId", getArguments().getString("customerId", "")));
            }
        });
        listView.addHeaderView(headView);

    }


    public void invoke() {
        transDataModel.setCsrId(getArguments().getString("customerId", ""));
        transDataModel.setIsArrears("0");//1查询有欠款0查询所有
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRCON, transDataModel, new TypeToken<List<AddContractTransModel>>() {
        });
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.SEARCHCSRCON:
                if (data != null) {
                    models = (List<AddContractTransModel>) data;
                    adapter.setData(models);
                }

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(getActivity(), ClientDetailsContractActivity.class).putExtra("model", models.get(i-1)).putExtra("customerId", getArguments().getString("customerId", "")));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
