package com.rongfeng.speedclient.dynamic.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.ClientDistributeActivity;
import com.rongfeng.speedclient.client.ClientPersonaActivity;
import com.rongfeng.speedclient.client.ClientRecordsActivity;
import com.rongfeng.speedclient.client.entry.ImageListModel;
import com.rongfeng.speedclient.client.entry.RecievedClientTransModel;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.components.AddVisitGridLayoutDisplayView;
import com.rongfeng.speedclient.dynamic.model.DynamicModel;
import com.rongfeng.speedclient.voice.VoiceNoteActivity;
import com.rongfeng.speedclient.xrecyclerview.BaseRecyclerAdapter;
import com.rongfeng.speedclient.xrecyclerview.ViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 2016/1/11.
 */
public class DynamicAdapter extends BaseRecyclerAdapter<DynamicModel> {


    public DynamicAdapter(Context context, int layoutResId, List<DynamicModel> data) {
        super(context, layoutResId, data);
    }

    public DynamicAdapter(Context context, int layoutResId) {
        super(context, layoutResId, null);
    }

    @Override
    protected void convert(final ViewHolder holder, DynamicModel model, int position) {
        AddVisitGridLayoutDisplayView addPicLayout = holder.getView(R.id.add_pic_layout);
        addPicLayout.setVisibility(View.GONE);
        holder.setVisible(R.id.content_tv, true);
        LinearLayout userLayout = holder.getView(R.id.user_layout);
        RelativeLayout typeLayout = holder.getView(R.id.type_layout);
        ImageView type_image = holder.getView(R.id.type_image);
        TextView user_image = holder.getView(R.id.user_image);

        LinearLayout top_layout = holder.getView(R.id.top_layout);
        LinearLayout middle_layout = holder.getView(R.id.middle_layout);
        ImageView top_iv = holder.getView(R.id.top_iv);
        ImageView type_user_image = holder.getView(R.id.type_user_image);


        holder.setText(R.id.time_process_tv, model.getCreateTime().split(" ")[1]); //时间轴时间

        if (position == 0) { //第一条布局
            Drawable drawable = context.getResources().getDrawable(R.drawable.main_dynamic_left_item);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) holder.getView(R.id.data_tv)).setCompoundDrawables(drawable, null, null, null);
            holder.setText(R.id.data_tv, "今天 " + model.getCreateTime().split(" ")[1]);
            top_layout.setVisibility(View.VISIBLE);
            middle_layout.setVisibility(View.GONE);
            top_iv.setVisibility(View.VISIBLE);
            holder.setTextColor(R.id.data_tv, ContextCompat.getColor(context, R.color.colorBlue));
            holder.setBackgroundRes(R.id.top_content_layout, R.drawable.main_notice_white_item);


        } else {

            if (data.get(position - 1).getCreateTime().split(" ")[0].equals(model.getCreateTime().split(" ")[0])) {//同一天
                top_layout.setVisibility(View.GONE);
                middle_layout.setVisibility(View.VISIBLE);
            } else {
                Drawable drawable = context.getResources().getDrawable(R.drawable.main_notice_left_item);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                ((TextView) holder.getView(R.id.data_tv)).setCompoundDrawables(drawable, null, null, null);
                holder.setText(R.id.data_tv, model.getCreateTime().split(" ")[0] + " " + DateUtil.getWeekByNow());
                holder.setTextColor(R.id.data_tv, ContextCompat.getColor(context, R.color.colorWhite));
                holder.setBackgroundRes(R.id.top_content_layout, R.drawable.main_notice_gray_item);
                top_layout.setVisibility(View.VISIBLE);
                middle_layout.setVisibility(View.VISIBLE);
            }
            top_iv.setVisibility(View.GONE); //隐藏第一条布局中的竖线


        }

        //初始化user布局 单一布局
        type_user_image.setVisibility(View.GONE);
        user_image.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(AppTools.getUser().getUserName())) {
            holder.setText(R.id.user_image, AppTools.getUser().getUserName().substring(0, 1));
        }

        holder.setText(R.id.user_entry_tv, model.getDynamicTitle());
        holder.setText(R.id.user_entry_time_tv, model.getCreateTime());
        holder.setText(R.id.type_name_tv, model.getDynamicTypeName());
        holder.setText(R.id.time_tv, model.getCreateTime());
        holder.setText(R.id.content_tv, model.getDynamicTitle());

        boolean isToday;
        //是不是当天
        if (DateUtil.getDateTime(DateUtil.getDatePattern(), new Date()).equals(model.getCreateTime().split(" ")[0])) {
            isToday = true;
        } else {
            isToday = false;
        }


        switch (model.getDynamicType()) {

            case 1:
                userLayout.setVisibility(View.VISIBLE);
                typeLayout.setVisibility(View.GONE);
                if (isToday) {
                    user_image.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));
                    user_image.setBackgroundResource(R.drawable.dynamic_item_bg);
                } else {
                    user_image.setTextColor(ContextCompat.getColor(context, R.color.colorAssist));
                    user_image.setBackgroundResource(R.drawable.dynamic_item_gray_bg);
                }
                break;
            case 2://跟进客户
                holder.setText(R.id.content_tv, model.getCustomerName());
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_visit);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_visit_h);
                }
                break;
            case 3:
                type_user_image.setVisibility(View.VISIBLE);
                user_image.setVisibility(View.GONE);
                userLayout.setVisibility(View.VISIBLE);
                typeLayout.setVisibility(View.GONE);
                holder.setText(R.id.user_entry_tv, model.getDynamicTypeName());
                if (isToday) {

                    type_user_image.setImageResource(R.drawable.dynamic_log);
                } else {
                    type_user_image.setImageResource(R.drawable.dynamic_log_h);
                }
                break;
            case 4:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat_h);
                }
                break;
            case 5:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);

                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat_h);
                }
                break;
            case 6:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_cust_edit);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_cust_edit_h);
                }
                break;
            case 7:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_opportunity);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_opportunity_h);
                }
                break;
            case 8:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_opportunity_eidt);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_opportunity_eidt_h);
                }
                break;
            case 9:
                holder.setText(R.id.type_name_tv, model.getDynamicTypeName() + " " + AppTools.getNumKbDot(model.getDynamicVal()) + "元");
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_deal);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_deal_h);
                }
                break;
            case 10:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_cust_creat_h);
                }
                break;
            case 11:
                holder.setText(R.id.type_name_tv, model.getDynamicTypeName() + " " + AppTools.getNumKbDot(model.getDynamicVal()) + "元");

                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_receive);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_receive_h);
                }
                break;
            case 12:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);
                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_position);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_position_h);
                }
                break;
            case 13:
                userLayout.setVisibility(View.GONE);
                typeLayout.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(model.getAddress())) {
                    holder.setVisible(R.id.content_tv, false);
                } else {
                    holder.setVisible(R.id.content_tv, true);
                    holder.setText(R.id.content_tv, model.getAddress());

                }

                if (isToday) {
                    type_image.setImageResource(R.drawable.dynamic_position);
                } else {
                    type_image.setImageResource(R.drawable.dynamic_position_h);
                }
                if (model.getJsonArrayPositionImg() != null && model.getJsonArrayPositionImg().size() > 0) {
                    addPicLayout.setVisibility(View.VISIBLE);
                    List<String> pathsUrl = new ArrayList<>();
                    List<String> pathsMinUrl = new ArrayList<>();

                    for (ImageListModel picm : model.getJsonArrayPositionImg()) {
                        pathsUrl.add(picm.getFileUrl());
                        pathsMinUrl.add(picm.getMinUrl());
                    }
                    addPicLayout.setColumn(4);
                    addPicLayout.setWidth(addPicLayout.getWidth());
                    addPicLayout.setImageLayout(pathsUrl, pathsMinUrl, true);

                } else {
                    addPicLayout.setVisibility(View.GONE);
                }
                break;
        }


        holder.getView().setTag(model);
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DynamicModel model = (DynamicModel) holder.getView().getTag();

                switch (model.getDynamicType()) {

                    case 1:

                        break;
                    case 2://跟进客户
                        context.startActivity(new Intent(context, ClientRecordsActivity.class).putExtra("customerId", model.getCsrId()).putExtra("customerName", model.getCustomerName()));
                        break;
                    case 3:
                        context.startActivity(new Intent(context, VoiceNoteActivity.class));

                        break;
                    case 5:
                        context.startActivity(new Intent(context, ClientPersonaActivity.class).putExtra("customerId", model.getCsrId()).putExtra("customerName", model.getCustomerName()));

                        break;
                    case 6:
                        context.startActivity(new Intent(context, ClientPersonaActivity.class).putExtra("customerId", model.getCsrId()).putExtra("customerName", model.getCustomerName()));

                        break;
                    case 7:
                        context.startActivity(new Intent(context, ClientPersonaActivity.class)
                                .putExtra("customerId", model.getCsrId())
                                .putExtra("customerName", model.getCustomerName())
                                .putExtra("flag", 1)
                        );

                        break;
                    case 8:
                        context.startActivity(new Intent(context, ClientPersonaActivity.class)
                                .putExtra("customerId", model.getCsrId())
                                .putExtra("customerName", model.getCustomerName())
                                .putExtra("flag", 1)
                        );
                        break;
                    case 9:
                        context.startActivity(new Intent(context, ClientPersonaActivity.class)
                                .putExtra("customerId", model.getCsrId())
                                .putExtra("customerName", model.getCustomerName())
                                .putExtra("flag", 2)
                        );
                        break;
                    case 10:
                        context.startActivity(new Intent(context, ClientPersonaActivity.class)
                                .putExtra("customerId", model.getCsrId())
                                .putExtra("customerName", model.getCustomerName())
                                .putExtra("flag", 2)
                        );
                        break;
                    case 11:

                        context.startActivity(new Intent(context, ClientPersonaActivity.class).putExtra("customerId", model.getCsrId()).putExtra("customerName", model.getCustomerName()));

                        break;
                    case 12:
                    case 13:


                        RecievedClientTransModel recievedClientTransModel = new RecievedClientTransModel();
                        recievedClientTransModel.setLatitude(model.getLatitude());
                        recievedClientTransModel.setLongitude(model.getLongitude());
                        recievedClientTransModel.setCustomerAddress(model.getAddress());
                        context.startActivity(new Intent(context, ClientDistributeActivity.class).putExtra("model", recievedClientTransModel));


                        break;
                }
            }
        });
    }

}
