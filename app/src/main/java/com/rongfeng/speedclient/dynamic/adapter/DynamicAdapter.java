package com.rongfeng.speedclient.dynamic.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.dynamic.model.DynamicModel;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.util.List;

/**
 * Created by Alex on 2016/1/11.
 */
public class DynamicAdapter extends BaseRecyclerAdapter<DynamicModel> {


    public DynamicAdapter(Context context, int layoutResId, List<DynamicModel> data) {
        super(context, layoutResId, data);
    }

    public DynamicAdapter(Context context, int layoutResId) {
        super(context, layoutResId, null);
    }

    @Override
    protected void convert(ViewHolder holder, DynamicModel model, int position) {

        LinearLayout userLayout = holder.getView(R.id.user_layout);
        TextView currentDateTv = holder.getView(R.id.current_date_tv);
        RelativeLayout typeLayout = holder.getView(R.id.type_layout);
        ImageView type_image = holder.getView(R.id.type_image);
        ImageView user_image = holder.getView(R.id.user_image);


        holder.setText(R.id.user_entry_tv, "");
        holder.setText(R.id.user_entry_time_tv, "");
        holder.setText(R.id.type_name_tv, "");
        holder.setText(R.id.time_tv, "");
        holder.setText(R.id.content_tv, "");

        if (position == 0) {
            currentDateTv.setVisibility(View.VISIBLE);
        } else {
            currentDateTv.setVisibility(View.GONE);
        }


        switch (model.getDynamicType()) {
            case 1:
                userLayout.setVisibility(View.VISIBLE);
                typeLayout.setVisibility(View.GONE);
                user_image.setImageResource(R.drawable.cust_plus_visit);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
        }
    }

}
