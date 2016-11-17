package com.rongfeng.speedclient.client.adapter;

import android.content.Context;

import com.amap.api.services.core.PoiItem;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseAdapterHelper;
import com.rongfeng.speedclient.common.QuickAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class AttendanceAddrDetailsAdapter extends QuickAdapter<PoiItem> {
    private boolean isShow=false;
    public AttendanceAddrDetailsAdapter(Context context, int layoutResId, List<PoiItem> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, PoiItem item, int position) {
        helper.setText(R.id.name,item.getTitle());
        helper.setText(R.id.addr,item.getSnippet());

    }

}
