package com.rongfeng.speedclient.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.adapter.ClientPersonaLabelAdapter;
import com.rongfeng.speedclient.client.entry.RecievedClientTransModel;
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
    private RecievedClientTransModel recievedClientTransModel = new RecievedClientTransModel();

    public static ClientPersonaLabelFragment newInstance(String customerId) {

        Bundle args = new Bundle();
        args.putString("customerId", customerId);
        ClientPersonaLabelFragment fragment = new ClientPersonaLabelFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_persona_label_fragment_layout, null);
        ButterKnife.bind(this, view);

        init();
        return view;
    }


    private void init() {
        adapter = new ClientPersonaLabelAdapter(getActivity(), R.layout.client_persona_item, models);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }


    public void setClientInfo(RecievedClientTransModel recievedClientTransModel) {
        this.recievedClientTransModel = recievedClientTransModel;
    }


    public void setData(List<BaseDataModel> model) {

        List<BaseDataModel> result = new ArrayList<>();

        for (int i = 0; i < model.size(); i++) {

            if (!TextUtils.isEmpty(model.get(i).getDictionaryName())) {
                BaseDataModel m = new BaseDataModel();
                m.setDictionaryId(model.get(i).getDictionaryId());
                m.setDictionaryName(model.get(i).getDictionaryName());
                result.add(m);
            }
        }

        this.models = result;

        adapter.setData(models);

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
                startActivity(new Intent(getActivity(), ClientDistributeActivity.class).putExtra("model",recievedClientTransModel));
                break;
        }
    }
}
