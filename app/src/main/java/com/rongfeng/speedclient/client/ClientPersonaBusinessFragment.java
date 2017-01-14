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
import com.rongfeng.speedclient.client.adapter.ClientPersonaBusinessAdapter;
import com.rongfeng.speedclient.client.entry.AddBusinessTransModel;
import com.rongfeng.speedclient.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Client标签
 * 2016/1/13
 */
public class ClientPersonaBusinessFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.grid_view)
    ListView listView;

    private ClientPersonaBusinessAdapter adapter;
    List<AddBusinessTransModel> models = new ArrayList<>();

    public static ClientPersonaBusinessFragment newInstance(String customerId) {

        Bundle args = new Bundle();
        args.putString("customerId", customerId);
        ClientPersonaBusinessFragment fragment = new ClientPersonaBusinessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_persona_business_fragment_layout, null);
        ButterKnife.bind(this, view);
        init();
        invoke();
        return view;
    }


    private void init() {
        addHeadView();
        listView.setOnItemClickListener(this);
        adapter = new ClientPersonaBusinessAdapter(getActivity(), R.layout.client_persona_business_item, models);
        listView.setAdapter(adapter);

    }


    private void addHeadView() {
        View headView = getActivity().getLayoutInflater().inflate(R.layout.persona_head_view, null);
        TextView contentTv = (TextView) headView.findViewById(R.id.content_tv);
        contentTv.setText("+ 添加商机");
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ClientAddBusinessActivity.class).putExtra("customerId", getArguments().getString("customerId", "")));
            }
        });
        listView.addHeaderView(headView);

    }

    public void invoke() {
        transDataModel.setCsrId(getArguments().getString("customerId", ""));
        commonPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSRBUSINESS, transDataModel, new TypeToken<List<AddBusinessTransModel>>() {
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(new Intent(getActivity(), ClientDetaisBusinessActivity.class).putExtra("model", models.get(i-1)).putExtra("customerId", getArguments().getString("customerId", "")));
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status) {
        super.obtainData(data, methodIndex, status);
        switch (methodIndex) {
            case XxbService.SEARCHCSRBUSINESS:
                if (data != null) {
                    models = (List<AddBusinessTransModel>) data;
                    adapter.setData(models);
                }

                break;
        }
    }
}
