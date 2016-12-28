package com.rongfeng.speedclient.manage.adapter;

import android.content.Context;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.manage.model.ManageFollowModel;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.util.List;

/**
 * Created by Alex on 2016/1/11.
 */
public class ManageFollowAdapter extends BaseRecyclerAdapter<ManageFollowModel> {


    public ManageFollowAdapter(Context context, int layoutResId, List<ManageFollowModel> data) {
        super(context, layoutResId, data);
    }

    public ManageFollowAdapter(Context context, int layoutResId) {
        super(context, layoutResId, null);
    }

    @Override
    protected void convert(ViewHolder holder, ManageFollowModel model, int position) {

        holder.setText(R.id.client_name_tv, model.getCustomerName());
        holder.setText(R.id.create_time_tv, model.getFollowUpTime());

    }

}
