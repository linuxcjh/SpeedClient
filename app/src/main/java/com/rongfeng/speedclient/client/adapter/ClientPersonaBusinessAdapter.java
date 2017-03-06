package com.rongfeng.speedclient.client.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.entry.AddBusinessTransModel;
import com.rongfeng.speedclient.common.BaseAdapterHelper;
import com.rongfeng.speedclient.common.QuickAdapter;
import com.rongfeng.speedclient.common.utils.AppTools;

import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class ClientPersonaBusinessAdapter extends QuickAdapter<AddBusinessTransModel> {


    public ClientPersonaBusinessAdapter(Context context, int layoutResId, List<AddBusinessTransModel> data) {
        super(context, layoutResId, data);
    }


    @Override
    protected void convert(BaseAdapterHelper helper, AddBusinessTransModel item, int position) {


        helper.setText(R.id.title_tv, item.getBusinessName());

        helper.setText(R.id.num_tv, "￥" + AppTools.getNumKbDot(item.getPredictMoney()) + " 元");


        switch (item.getBusinessStageName()) {

            case "意向"://TODO
                setReset(helper);
                ((TextView) helper.getView(R.id.stage_one_tv)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                helper.setTextColor(R.id.stage_one_tv, ContextCompat.getColor(context, R.color.colorBlue));
                helper.setImageResource(R.id.stage_one_image, R.drawable.addcustomer_type_select);
                break;
            case "洽谈":
                setReset(helper);
                ((TextView) helper.getView(R.id.stage_two_tv)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                helper.setTextColor(R.id.stage_two_tv, ContextCompat.getColor(context, R.color.colorBlue));
                helper.setImageResource(R.id.stage_two_image, R.drawable.addcustomer_type_select);
                break;
            case "商务":
                setReset(helper);
                ((TextView) helper.getView(R.id.stage_three_tv)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

                helper.setTextColor(R.id.stage_three_tv, ContextCompat.getColor(context, R.color.colorBlue));
                helper.setImageResource(R.id.stage_three_image, R.drawable.addcustomer_type_select);
                break;
            case "成交":
                setReset(helper);
                ((TextView) helper.getView(R.id.stage_four_tv)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                helper.setTextColor(R.id.stage_four_tv, ContextCompat.getColor(context, R.color.colorBlue));
                helper.setImageResource(R.id.stage_four_image, R.drawable.addcustomer_type_select);
                break;
        }


    }

    public void setReset(BaseAdapterHelper helper) {

        ((TextView) helper.getView(R.id.stage_one_tv)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        ((TextView) helper.getView(R.id.stage_two_tv)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        ((TextView) helper.getView(R.id.stage_three_tv)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        ((TextView) helper.getView(R.id.stage_four_tv)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        helper.setTextColor(R.id.stage_one_tv, ContextCompat.getColor(context, R.color.colorAssist));
        helper.setTextColor(R.id.stage_two_tv, ContextCompat.getColor(context, R.color.colorAssist));
        helper.setTextColor(R.id.stage_three_tv, ContextCompat.getColor(context, R.color.colorAssist));
        helper.setTextColor(R.id.stage_four_tv, ContextCompat.getColor(context, R.color.colorAssist));

        helper.setImageResource(R.id.stage_one_image, R.drawable.business_item_progress_bg);
        helper.setImageResource(R.id.stage_two_image, R.drawable.business_item_progress_bg);
        helper.setImageResource(R.id.stage_three_image, R.drawable.business_item_progress_bg);
        helper.setImageResource(R.id.stage_four_image, R.drawable.business_item_progress_bg);

    }

}
