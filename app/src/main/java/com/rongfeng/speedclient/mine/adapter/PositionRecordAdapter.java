package com.rongfeng.speedclient.mine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.ImageListModel;
import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.components.AddVisitGridLayoutDisplayView;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 2016/1/11.
 */
public class PositionRecordAdapter extends BaseRecyclerAdapter<PositionModel> {


    public PositionRecordAdapter(Context context, int layoutResId, List<PositionModel> data) {
        super(context, layoutResId, data);
    }

    public PositionRecordAdapter(Context context, int layoutResId) {
        super(context, layoutResId, null);
    }

    @Override
    protected void convert(ViewHolder holder, PositionModel model, final int position) {
        AddVisitGridLayoutDisplayView addPicLayout = holder.getView(R.id.add_pic_layout);
        TextView dataLayout = holder.getView(R.id.current_date_tv);
        if (position == 0) {
            dataLayout.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(model.getCreateTime())) {
            Date date = DateUtil.strToSQLDate(model.getCreateTime().split(" ")[0]);
            if (DateUtils.isToday(date.getTime())) {
                holder.setText(R.id.current_date_tv, "今天");
            } else {
                holder.setText(R.id.current_date_tv, model.getCreateTime().split(" ")[0]);
            }
            holder.setText(R.id.content_tv, model.getAddress());
            holder.setText(R.id.time_tv, model.getCreateTime());

            if (position != 0 && !TextUtils.isEmpty(data.get(position - 1).getCreateTime())) {
                if (data.get(position).getCreateTime().split(" ")[0].equals(data.get(position - 1).getCreateTime().split(" ")[0])) {
                    dataLayout.setVisibility(View.GONE);
                } else {
                    dataLayout.setVisibility(View.VISIBLE);
                }
            }
        }

        if (model.getType().equals("1")) {

            if (model.getJsonArrayPositionImg() != null && model.getJsonArrayPositionImg().size() > 0) {
                addPicLayout.setVisibility(View.VISIBLE);
                List<String> pathsUrl = new ArrayList<>();
                List<String> pathsMinUrl = new ArrayList<>();

                for (ImageListModel picm : model.getJsonArrayPositionImg()) {
                    pathsUrl.add(picm.getFileUrl());
                    pathsMinUrl.add(picm.getMinUrl());
                }
                addPicLayout.setColumn(4);
                addPicLayout.setWidth(addPicLayout.getWidth());
                addPicLayout.setImageLayout(pathsUrl, pathsMinUrl, true);

            }else{
                addPicLayout.setVisibility(View.GONE);
            }
        } else {
            addPicLayout.setVisibility(View.GONE);
        }
    }

}
