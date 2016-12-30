package com.rongfeng.speedclient.manage.adapter;

import android.content.Context;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddBusinessTransModel;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.util.List;

/**
 * Created by Alex on 2016/1/11.
 */
public class ManageBusinessAdapter extends BaseRecyclerAdapter<AddBusinessTransModel> {


    public ManageBusinessAdapter(Context context, int layoutResId, List<AddBusinessTransModel> data) {
        super(context, layoutResId, data);
    }

    public ManageBusinessAdapter(Context context, int layoutResId) {
        super(context, layoutResId, null);
    }

    @Override
    protected void convert(ViewHolder holder, AddBusinessTransModel model, int position) {

        holder.setText(R.id.client_name_tv, model.getBusinessName());
        holder.setText(R.id.create_time_tv, model.getCustomerName());
        holder.setText(R.id.money_tv, "￥" + AppTools.getNumKbDot(model.getPredictMoney()) + " 元");


    }

}
