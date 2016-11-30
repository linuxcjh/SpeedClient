package com.rongfeng.speedclient.client.adapter;

import android.content.Context;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddClientTransModel;
import com.rongfeng.speedclient.common.utils.AppTools;
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
        holder.setText(R.id.client_bus_count_tv, "商机 ￥" + AppTools.getNumKbDot(model.getBusinessMoney())+"元");
        holder.setText(R.id.client_visited_count_tv, "跟进 " + model.getFollowCount() + " 次");
        holder.setText(R.id.client_last_tv, "上次跟进 " + model.getLastTime() );
        holder.setText(R.id.create_time_tv, "归属人 " + model.getOwnerUserName());
    }

}
