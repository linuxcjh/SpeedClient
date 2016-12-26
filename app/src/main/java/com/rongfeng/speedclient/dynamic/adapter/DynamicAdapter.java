package com.rongfeng.speedclient.dynamic.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.dynamic.model.DynamicModel;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.util.Date;
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
        TextView user_image = holder.getView(R.id.user_image);

        boolean isToday;
        //是不是当天
        if (DateUtil.getDateTime(DateUtil.getDatePattern(), new Date()).equals(model.getCreateTime().split(" ")[0])) {
            isToday = true;
        } else {
            isToday = false;
        }

        if (!TextUtils.isEmpty(AppTools.getUser().getUserName())) {
            holder.setText(R.id.user_image, AppTools.getUser().getUserName().substring(0, 1));
        }
        holder.setText(R.id.user_entry_tv, model.getDynamicTitle());
        holder.setText(R.id.user_entry_time_tv, model.getCreateTime());
        holder.setText(R.id.type_name_tv, model.getDynamicTypeName());
        holder.setText(R.id.time_tv, model.getCreateTime());
        holder.setText(R.id.content_tv, model.getDynamicTitle());

        if (position == 0) {
            currentDateTv.setText("今天 " + DateUtil.getDateTime(DateUtil.getDatePattern(), new Date()) + " " + DateUtil.getWeekByNow());
            currentDateTv.setVisibility(View.VISIBLE);
        } else {
            if (data.get(position - 1).getCreateTime().split(" ")[0].equals(model.getCreateTime().split(" ")[0])) {
                currentDateTv.setVisibility(View.GONE);
            } else {
                currentDateTv.setText(model.getCreateTime().split(" ")[0] + " " + DateUtil.getWeekByNow());
                currentDateTv.setVisibility(View.VISIBLE);
            }
        }

        switch (model.getDynamicType()) {
            case 1:
                userLayout.setVisibility(View.VISIBLE);
                typeLayout.setVisibility(View.GONE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat_h);
                }
                break;
            case 2://跟进客户
                holder.setText(R.id.content_tv, model.getCustomerName());
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_visit);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_visit_h);
                }
                break;
            case 3:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_log);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_log_h);
                }
                break;
            case 4:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat_h);
                }
                break;
            case 5:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);

                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat_h);
                }
                break;
            case 6:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_cust_edit);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_cust_edit_h);
                }
                break;
            case 7:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_opportunity);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_opportunity_h);
                }
                break;
            case 8:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_opportunity_eidt);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_opportunity_eidt_h);
                }
                break;
            case 9:
                holder.setText(R.id.type_name_tv, model.getDynamicTypeName() + " " + AppTools.getNumKbDot(model.getDynamicVal()) + "元");
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_deal);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_deal_h);
                }
                break;
            case 10:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat_h);
                }
                break;
            case 11:
                holder.setText(R.id.type_name_tv, model.getDynamicTypeName() + " " + AppTools.getNumKbDot(model.getDynamicVal()) + "元");

                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_receive);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_receive_h);
                }
                break;
            case 12:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_position);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_position_h);
                }
                break;
        }
    }

}
