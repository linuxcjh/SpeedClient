package com.rongfeng.speedclient.client.adapter;

import android.content.Context;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseAdapterHelper;
import com.rongfeng.speedclient.common.QuickAdapter;
import com.rongfeng.speedclient.entity.BaseDataModel;

import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class ClientPersonaBargainAdapter extends QuickAdapter<BaseDataModel> {


    public ClientPersonaBargainAdapter(Context context, int layoutResId, List<BaseDataModel> data) {
        super(context, layoutResId, data);
    }


    @Override
    protected void convert(BaseAdapterHelper helper, BaseDataModel item, int position) {

        helper.setText(R.id.title_tv, item.getDictionaryId());
//        helper.setText(R.id.total_num_tv, "总额  " + item.getDictionaryId());
        helper.setText(R.id.reback_tv, "已收  " + item.getDictionaryName());

    }

}
