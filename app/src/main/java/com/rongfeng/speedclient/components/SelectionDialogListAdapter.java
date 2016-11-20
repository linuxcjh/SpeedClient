package com.rongfeng.speedclient.components;

import android.content.Context;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseAdapterHelper;
import com.rongfeng.speedclient.common.QuickAdapter;
import com.rongfeng.speedclient.entity.BaseDataModel;

import java.util.List;

/**
 * Created by 唐磊 on 2015/11/5.
 *   选择对话框ListView 适配器
 */
public class SelectionDialogListAdapter extends QuickAdapter<BaseDataModel> {


    public SelectionDialogListAdapter(Context context, int layoutResId, List<BaseDataModel> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, BaseDataModel item, int position) {
        helper.setText(R.id.text,item.getDictionaryName());
    }
}
