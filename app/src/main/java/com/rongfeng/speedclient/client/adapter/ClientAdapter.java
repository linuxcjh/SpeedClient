package com.rongfeng.speedclient.client.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.TextView;

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
        if (!TextUtils.isEmpty(clientType)
                && (clientType.equals("12") || clientType.equals("13") || clientType.equals("14") || clientType.equals("16"))) {//未来7天
            holder.setText(R.id.client_last_tv, "计划跟进 " + model.getRemindTime());
            holder.setText(R.id.client_visited_count_tv, "上次跟进 " + model.getLastTime());

        } else if (!TextUtils.isEmpty(clientType)
                && (clientType.equals("4") || clientType.equals("10") || clientType.equals("15"))) {//欠款列表
            holder.setText(R.id.client_visited_count_tv, "欠款 " + AppTools.getNumKbDot(model.getDebtMoney()) + "元");
            Drawable drawable = context.getResources().getDrawable(R.drawable.cust_arrears);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) holder.getView(R.id.client_visited_count_tv)).setCompoundDrawables(drawable, null, null, null);

        } else {
            holder.setText(R.id.client_last_tv, "上次跟进 " + model.getLastTime());
            holder.setText(R.id.client_visited_count_tv, "跟进 " + model.getFollowCount() + " 次");

        }
        holder.setText(R.id.create_time_tv, "归属人 " + model.getOwnerUserName());
    }

}
