package com.rongfeng.speedclient.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.client.ClientListFragment;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;

import java.util.List;

/**
 * Client
 * 2016/1/13
 */
public class ClientSelectFragment extends ClientListFragment {


    public static ClientSelectFragment newInstance() {

        Bundle args = new Bundle();

        ClientSelectFragment fragment = new ClientSelectFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void invoke() {
        transDataModel.setClientType("5");//全部客户
        transDataModel.setPage(String.valueOf(commonPaginationPresenter.page));
        commonPaginationPresenter.invokeInterfaceObtainData(XxbService.SEARCHCSR, transDataModel, new TypeToken<List<AddClientTransModel>>() {
        });
    }

    @Override
    public void onItemClick(int position, Object object) {

        AddClientTransModel model = (AddClientTransModel) object;
        getActivity().setResult(Activity.RESULT_OK, new Intent().putExtra("model", model));
        getActivity().finish();

    }
}
