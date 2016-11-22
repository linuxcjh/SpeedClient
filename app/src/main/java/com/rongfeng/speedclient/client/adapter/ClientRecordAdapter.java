package com.rongfeng.speedclient.client.adapter;

import android.content.Context;
import android.view.View;

import com.rongfeng.speedclient.client.entry.ClientRecordModel;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.util.List;

/**
 * Created by Alex on 2016/1/11.
 */
public class ClientRecordAdapter extends BaseRecyclerAdapter<ClientRecordModel> implements View.OnClickListener {

    public ClientRecordAdapter(Context context, int layoutResId, List<ClientRecordModel> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(ViewHolder helper, ClientRecordModel item, int position) {

    }

    @Override
    public void onClick(View v) {
        ClientRecordModel item = (ClientRecordModel) v.getTag();
//        switch (v.getId()) {
//            case R.id.history_tv:
//                Intent intent=new Intent(context, StatementHistroyActivity.class);
//                intent.putExtra("item",item);;
//                context.startActivity(intent);
//                break;
//            case R.id.rapportStatus:
//
//                switch (item.getRapportStatus()) {
//                    case "0":
//                        Intent intent1=new Intent(context,ReportAddActivity.class);
//                        intent1.putExtra("item",item);
//                        intent1.putExtra("position",(int)v.getTag(R.id.rapportStatus));
//                        fragment.startActivityForResult(intent1,1001);
//                        break;
//                    case "1":
//                        Intent intent2=new Intent(context,ReportEditActivity.class);
//                        intent2.putExtra("item",item);
//                        context.startActivity(intent2);
//                        break;
//                }
//                break;
//        }
    }
}
