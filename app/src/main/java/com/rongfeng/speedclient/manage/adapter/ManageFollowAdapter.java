package com.rongfeng.speedclient.manage.adapter;

import android.content.Context;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.ImageListModel;
import com.rongfeng.speedclient.components.AddVisitGridLayoutDisplayView;
import com.rongfeng.speedclient.manage.model.ManageFollowModel;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 2016/1/11.
 */
public class ManageFollowAdapter extends BaseRecyclerAdapter<ManageFollowModel> {
    List<String> pathsUrl = new ArrayList<>();
    List<String> pathsMinUrl = new ArrayList<>();

    public ManageFollowAdapter(Context context, int layoutResId, List<ManageFollowModel> data) {
        super(context, layoutResId, data);
    }

    public ManageFollowAdapter(Context context, int layoutResId) {
        super(context, layoutResId, null);
    }

    @Override
    protected void convert(ViewHolder helper, ManageFollowModel item, int position) {

        AddVisitGridLayoutDisplayView addPicLayout = helper.getView(R.id.add_pic_layout);

        helper.setText(R.id.client_name_tv, item.getCustomerName());
        helper.setText(R.id.create_time_tv, item.getFollowUpTime());

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
