package com.rongfeng.speedclient.client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientPersonaAdapter;
import com.rongfeng.speedclient.common.BaseFragment;
import com.rongfeng.speedclient.components.MyGridView;
import com.rongfeng.speedclient.entity.BaseDataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Client标签
 * 2016/1/13
 */
public class ClientPersonaBusinessFragment extends BaseFragment  {

    @Bind(R.id.grid_view)
    MyGridView gridView;

    private ClientPersonaAdapter adapter;
    List<BaseDataModel> models = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_persona_business_fragment_layout, null);
        ButterKnife.bind(this, view);

        init();
        return view;
    }


    private void init() {
        models.add(new BaseDataModel("新客户", "300个"));
        models.add(new BaseDataModel("老客户", "300个"));
        models.add(new BaseDataModel("商机客户", "300个"));
        models.add(new BaseDataModel("欠款客户", "300个"));
        models.add(new BaseDataModel("客户总数", "300个"));
        models.add(new BaseDataModel("关注客户", "300个"));
        adapter = new ClientPersonaAdapter(getActivity(), R.layout.client_persona_item, models);
        gridView.setAdapter(adapter);

    }

}
