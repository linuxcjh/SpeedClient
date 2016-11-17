package com.rongfeng.speedclient.client.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.services.core.PoiItem;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseAdapterHelper;
import com.rongfeng.speedclient.common.QuickAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class ClientAddrDetailsAdapter extends QuickAdapter<PoiItem> {

    private int showPoi = -1;

    public ClientAddrDetailsAdapter(Context context, int layoutResId, List<PoiItem> data) {
        super(context, layoutResId, data);
    }


    public void setShow(int showPoi) {
        this.showPoi = showPoi;
    }

    @Override
    protected void convert(BaseAdapterHelper helper, PoiItem item, int position) {

        ImageView imageView = helper.getView(R.id.selected);
        if (showPoi == position) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        helper.setText(R.id.name, item.getTitle());
        helper.setText(R.id.addr, item.getSnippet());

    }

}
