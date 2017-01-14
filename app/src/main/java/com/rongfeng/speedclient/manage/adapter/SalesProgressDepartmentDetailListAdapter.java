package com.rongfeng.speedclient.manage.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.manage.model.SalesProgressDepDetailModel;
import com.rongfeng.speedclient.xrecyclerview.OnItemClickViewListener;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 【Client list Adapter】
 * AUTHOR: Alex
 * DATE: 22/10/2015 15:23
 */
public class SalesProgressDepartmentDetailListAdapter extends RecyclerView.Adapter<SalesProgressDepartmentDetailListAdapter.ViewHolder> {

    private DecimalFormat format = new DecimalFormat("######0.00");

    private Context mContext;
    private List<SalesProgressDepDetailModel.SalesProgressDepDetailList> mData;

    private static OnItemClickViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnItemClickViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public SalesProgressDepartmentDetailListAdapter(Context context) {
        this(context, null);
    }

    public SalesProgressDepartmentDetailListAdapter(Context context, List<SalesProgressDepDetailModel.SalesProgressDepDetailList> data) {
        this.mData = data;
        this.mContext = context;
    }

    public void setData(List<SalesProgressDepDetailModel.SalesProgressDepDetailList> data) {

        this.mData = data;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;

        View convertView = LayoutInflater.from(mContext).inflate(R.layout.sales_progress_department_de_item_layout, null);
        LinearLayout.LayoutParams lpEx = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        convertView.setLayoutParams(lpEx);
        viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SalesProgressDepDetailModel.SalesProgressDepDetailList model = mData.get(position);
        holder.model = model;
        holder.position = position;

        holder.nameTv.setText("已完成 " + AppTools.getNumKb(model.getContractMoney()) + "元");
        holder.targetTv.setText("目标 " + AppTools.getNumKb(model.getTargetMoney()) + "元");

        if (!TextUtils.isEmpty(model.getContractMoney()) && !TextUtils.isEmpty(model.getTargetMoney())) {

            if (Float.parseFloat(model.getTargetMoney()) == 0) {
                holder.rateTv.setText("0.0%");
            } else {
                holder.rateTv.setText((format.format(Float.parseFloat(model.getContractMoney()) / Float.parseFloat(model.getTargetMoney()) * 100)) + "%");
            }
        }
        holder.monthTv.setText(model.getMonthNumber());
        holder.monthEnTV.setText(model.getMonthNano());
        holder.root_layout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite));
        holder.nameTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorListName));
        holder.targetTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAssist));
        holder.rateTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAssist));
        holder.monthTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorBlue));
        holder.monthEnTV.setTextColor(ContextCompat.getColor(mContext, R.color.colorAssist));
        holder.divider.setBackgroundResource(R.color.colorDividerLine);
        Drawable leftDrawable = mContext.getResources().getDrawable(R.drawable.crm_prog_month);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        holder.rateTv.setCompoundDrawables(leftDrawable, null, null, null);


    }


    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int position;
        public SalesProgressDepDetailModel.SalesProgressDepDetailList model;

        public RelativeLayout root_layout;
        public TextView nameTv;
        public TextView targetTv;
        public TextView rateTv;
        public TextView monthTv;
        public TextView monthEnTV;
        public ImageView tipIv;
        public ImageView divider;


        public ViewHolder(View convertView) {
            super(convertView);

            root_layout = (RelativeLayout) convertView.findViewById(R.id.root_layout);
            root_layout.setOnClickListener(this);
            nameTv = (TextView) convertView.findViewById(R.id.name_tv);
            targetTv = (TextView) convertView.findViewById(R.id.target_tv);
            rateTv = (TextView) convertView.findViewById(R.id.rate_tv);
            monthTv = (TextView) convertView.findViewById(R.id.month_tv);
            monthEnTV = (TextView) convertView.findViewById(R.id.month_en_tv);
            tipIv = (ImageView) convertView.findViewById(R.id.tip_iv);
            divider = (ImageView) convertView.findViewById(R.id.divider);


        }

        @Override
        public void onClick(View v) {
            onRecyclerViewListener.onItemClick(position, model);
        }
    }

}
