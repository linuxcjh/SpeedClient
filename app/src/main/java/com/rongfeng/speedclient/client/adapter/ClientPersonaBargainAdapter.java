package com.rongfeng.speedclient.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.ClientAddRebackMoneyActivity;
import com.rongfeng.speedclient.client.entry.AddContractTransModel;
import com.rongfeng.speedclient.common.BaseAdapterHelper;
import com.rongfeng.speedclient.common.QuickAdapter;
import com.rongfeng.speedclient.common.utils.AppTools;

import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class ClientPersonaBargainAdapter extends QuickAdapter<AddContractTransModel> {


    private String clientId;

    public ClientPersonaBargainAdapter(Context context, int layoutResId, List<AddContractTransModel> data, String clientId) {
        super(context, layoutResId, data);
        this.clientId = clientId;
    }


    @Override
    protected void convert(BaseAdapterHelper helper, AddContractTransModel item, int position) {

        helper.setText(R.id.title_tv, item.getConName());
        helper.setText(R.id.total_num_tv, "总额  " + AppTools.getNumKbDot(item.getConRental()) + " 元");
        helper.setText(R.id.reback_tv, "已收  " + AppTools.getNumKbDot(item.getMoneyReceipt()) + " 元");

        Button backBt = helper.getView(R.id.back_bt);
        backBt.setTag(item);
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddContractTransModel m = (AddContractTransModel) view.getTag();
                context.startActivity(new Intent(context, ClientAddRebackMoneyActivity.class).putExtra("model", m).putExtra("customerId", clientId));
            }
        });

    }

}
