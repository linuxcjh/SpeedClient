package com.rongfeng.speedclient.dynamic.adapter;

import android.content.Context;

import com.rongfeng.speedclient.dynamic.model.NoticeModel;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

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
    protected void convert(ViewHolder holder, NoticeModel model, int position) {


    }

}
