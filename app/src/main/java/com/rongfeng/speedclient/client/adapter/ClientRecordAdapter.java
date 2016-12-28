package com.rongfeng.speedclient.client.adapter;

import android.content.Context;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.ClientRecordModel;
import com.rongfeng.speedclient.client.entry.ImageListModel;
import com.rongfeng.speedclient.components.AddVisitGridLayoutDisplayView;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 2016/1/11.
 */
public class ClientRecordAdapter extends BaseRecyclerAdapter<ClientRecordModel> {

    List<String> pathsUrl = new ArrayList<>();
    List<String> pathsMinUrl = new ArrayList<>();

    public ClientRecordAdapter(Context context, int layoutResId, List<ClientRecordModel> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(ViewHolder helper, ClientRecordModel item, int position) {

        AddVisitGridLayoutDisplayView addPicLayout = helper.getView(R.id.add_pic_layout);

        helper.setText(R.id.time_tv, item.getFollowUpTime());
        helper.setText(R.id.content_tv, item.getContent());


        if (item.getFollowUpInImageJSONArray() != null && item.getFollowUpInImageJSONArray().size() > 0) {

            pathsUrl.clear();
            pathsMinUrl.clear();

            for (ImageListModel picm : item.getFollowUpInImageJSONArray()) {
                pathsUrl.add(picm.getFileUrl());
                pathsMinUrl.add(picm.getMinUrl());
            }
            addPicLayout.setColumn(4);
            addPicLayout.setWidth(addPicLayout.getWidth());
            addPicLayout.setImageLayout(pathsUrl, pathsMinUrl, true);

        }
    }


}
