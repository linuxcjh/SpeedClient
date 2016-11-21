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
public class ClientAnalysisAdapter extends QuickAdapter<BaseDataModel> {


    public ClientAnalysisAdapter(Context context, int layoutResId, List<BaseDataModel> data) {
        super(context, layoutResId, data);
    }


    @Override
    protected void convert(BaseAdapterHelper helper, BaseDataModel item, int position) {

        helper.setText(R.id.name_tv,item.getDictionaryId());
        helper.setText(R.id.num_tv,item.getDictionaryName());

    }

}
