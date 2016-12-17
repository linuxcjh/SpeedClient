package com.rongfeng.speedclient.components;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseAdapterHelper;
import com.rongfeng.speedclient.common.QuickAdapter;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.entity.BaseDataModel;

import java.util.List;

/**
 * Created by 唐磊 on 2015/11/5.
 * 选择对话框ListView 适配器
 */
public class SelectionDialogVoiceListAdapter extends QuickAdapter<BaseDataModel> {


    public SelectionDialogVoiceListAdapter(Context context, int layoutResId, List<BaseDataModel> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseAdapterHelper helper, BaseDataModel item, int position) {


        TextView textView = helper.getView(R.id.text);
        if (!TextUtils.isEmpty(item.getIndexStr())) {
            SpannableString ss = new SpannableString(item.getDictionaryName());
            int pos = item.getDictionaryName().indexOf(item.getIndexStr());
            if (pos != -1) {
                ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AppConfig.getContext(), R.color.colorBlue)), pos, pos + item.getIndexStr().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            textView.setText(ss);

        } else {
            textView.setText(item.getDictionaryName());
        }

    }
}
