package com.rongfeng.speedclient.dynamic.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.dynamic.model.NoticeModel;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.sql.Date;
import java.util.List;

/**
 * Created by Alex on 2016/1/11.
 */
public class NoticeAdapter extends BaseRecyclerAdapter<NoticeModel> {


    public NoticeAdapter(Context context, int layoutResId, List<NoticeModel> data) {
        super(context, layoutResId, data);
    }

    public NoticeAdapter(Context context, int layoutResId) {
        super(context, layoutResId, null);
    }

    @Override
    protected void convert(ViewHolder holder, NoticeModel model, final int position) {
        TextView dataLayout = holder.getView(R.id.current_date_tv);
        if (position == 0) {
            dataLayout.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(model.getCreateTime())) {
            Date date = DateUtil.strToSQLDate(model.getCreateTime().split(" ")[0]);
            if (DateUtils.isToday(date.getTime())) {
                holder.setText(R.id.current_date_tv, "今天 " + model.getCreateTime().split(" ")[1]);
            } else {
                holder.setText(R.id.current_date_tv, model.getCreateTime());
            }

            if (position != 0 && !TextUtils.isEmpty(data.get(position - 1).getCreateTime())) {
                if (data.get(position).getCreateTime().split(" ")[0].equals(data.get(position - 1).getCreateTime().split(" ")[0])) {
                    dataLayout.setVisibility(View.GONE);
                } else {
                    dataLayout.setVisibility(View.VISIBLE);
                }
            }

            if (!TextUtils.isEmpty(model.getCsrName())) {
                holder.setVisible(R.id.client_name_tv, true);
                holder.setText(R.id.client_name_tv,"客户："+ model.getCsrName());
            } else {
                holder.setVisible(R.id.client_name_tv, false);
            }
            if (!TextUtils.isEmpty(model.getContent())) {
                holder.setVisible(R.id.content_tv, true);
                holder.setText(R.id.content_tv, "内容："+model.getContent());
            } else {
                holder.setVisible(R.id.content_tv, false);
            }


        }

    }
}
