package com.rongfeng.speedclient.client.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseAdapterHelper;
import com.rongfeng.speedclient.common.QuickAdapter;
import com.rongfeng.speedclient.entity.BaseDataModel;

import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class ClientPersonaLabelAdapter extends QuickAdapter<BaseDataModel> {


    public ClientPersonaLabelAdapter(Context context, int layoutResId, List<BaseDataModel> data) {
        super(context, layoutResId, data);
    }


    @Override
    protected void convert(BaseAdapterHelper helper, BaseDataModel item, int position) {

        helper.setText(R.id.name_tv, item.getDictionaryId());
        helper.setText(R.id.num_tv, item.getDictionaryName());
        LinearLayout layout = helper.getView(R.id.base_layout);
        int type = position % 4;
        switch (type) {
            case 0:
                layout.setBackgroundResource(R.drawable.custshow_1_blue);
                break;
            case 1:
                layout.setBackgroundResource(R.drawable.custshow_2_orange);
                break;
            case 2:
                layout.setBackgroundResource(R.drawable.custshow_3_green);
                break;
            case 3:
                layout.setBackgroundResource(R.drawable.custshow_4_red);
                break;

        }

    }

}
