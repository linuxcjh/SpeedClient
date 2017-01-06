package com.rongfeng.speedclient.client.adapter;

import android.content.Context;
import android.text.TextUtils;

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


    String clientType;

    public ClientAdapter(Context context, int layoutResId, List<AddClientTransModel> data) {
        super(context, layoutResId, data);
    }

    public ClientAdapter(Context context, int layoutResId, String clientType) {
        super(context, layoutResId, null);
        this.clientType = clientType;
    }

    public ClientAdapter(Context context, int layoutResId) {
        super(context, layoutResId, null);
    }
    @Override
    protected void convert(ViewHolder holder, AddClientTransModel model, int position) {

        holder.setText(R.id.client_name_tv, model.getCustomerName());
        holder.setText(R.id.client_bus_count_tv, "商机 ￥" + AppTools.getNumKbDot(model.getBusinessMoney()) + "元");
        holder.setText(R.id.client_visited_count_tv, "跟进 " + model.getFollowCount() + " 次");
        if (!TextUtils.isEmpty(clientType)&&(clientType.equals("12") || clientType.equals("13") || clientType.equals("14") || clientType.equals("15") || clientType.equals("16"))) {
            holder.setText(R.id.client_last_tv, "跟进计划 " + model.getRemindTime());
        } else {
            holder.setText(R.id.client_last_tv, "上次跟进 " + model.getLastTime());
        }
        holder.setText(R.id.create_time_tv, "归属人 " + model.getOwnerUserName());
    }

}
