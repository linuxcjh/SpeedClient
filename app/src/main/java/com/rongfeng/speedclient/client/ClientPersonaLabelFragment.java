package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientPersonaLabelAdapter;
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
public class ClientPersonaLabelFragment extends BaseFragment implements AdapterView.OnItemClickListener {


    @Bind(R.id.grid_view)
    MyGridView gridView;

    private ClientPersonaLabelAdapter adapter;
    List<BaseDataModel> models = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_persona_label_fragment_layout, null);
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
        adapter = new ClientPersonaLabelAdapter(getActivity(), R.layout.client_persona_item, models);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), ClientPersonaActivity.class));
                break;
        }
    }
}
