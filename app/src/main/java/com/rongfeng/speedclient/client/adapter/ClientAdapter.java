package com.rongfeng.speedclient.client.adapter;

import android.content.Context;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.util.List;

/**
 * Created by Alex on 2016/1/11.
 */
public class ClientAdapter extends BaseRecyclerAdapter<AddClientTransModel> {


    public ClientAdapter(Context context, int layoutResId, List<AddClientTransModel> data) {
        super(context, layoutResId, data);
    }

    public ClientAdapter(Context context, int layoutResId) {
        super(context, layoutResId, null);
    }

    @Override
    protected void convert(ViewHolder holder, AddClientTransModel model, int position) {

        holder.setText(R.id.client_name_tv, model.getCustomerName());
        holder.setText(R.id.client_bus_count_tv, "商机" + model.getBizOppsCount() == null ? model.getBizOppsCount() : 0 + "个");
        holder.setText(R.id.client_visited_count_tv, "拜访过" + model.getVisitCount() == null ? model.getVisitCount() : 0 + "次");
        holder.setText(R.id.client_visit_time_tv, "上次拜访" + model.getLastVisitTime() == null ? model.getLastVisitTime() : "");
        holder.setText(R.id.client_owner_tv, "归属人:" + model.getOwnerName() == null ? model.getOwnerName() : "");
        holder.setText(R.id.client_tel_reback_tv, "创建时间:" + model.getCreateTime() == null ? model.getCreateTime() : "");
    }

}
