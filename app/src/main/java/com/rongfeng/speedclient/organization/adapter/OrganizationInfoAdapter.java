package com.rongfeng.speedclient.organization.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.rongfeng.speedclient.components.AvatarImageView;
import com.rongfeng.speedclient.organization.OrganizationSetPersonActivity;
import com.rongfeng.speedclient.organization.model.OrganizationInfoModel;
import com.rongfeng.speedclient.xrecyclerview.OnItemClickViewListener;

import java.util.List;

/**
 * 【Client list Adapter】
 * AUTHOR: Alex
 * DATE: 22/10/2015 15:23
 */
public class OrganizationInfoAdapter extends RecyclerView.Adapter<OrganizationInfoAdapter.ViewHolder> {

    static final int TYPE_OTHER = 0;
    static final int TYPE_EXECUTE = 1;

    public Context mContext;
    private List<OrganizationInfoModel> mData;

    private static OnItemClickViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnItemClickViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public OrganizationInfoAdapter(Context context) {
        this(context, null);
    }


    public OrganizationInfoAdapter(Context context, List<OrganizationInfoModel> data) {
        this.mData = data;
        this.mContext = context;
    }

    public void setData(List<OrganizationInfoModel> data) {

        this.mData = data;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (mData != null && mData.size() > 0) {
            return TextUtils.isEmpty(mData.get(position).getUserId()) ? TYPE_OTHER : TYPE_EXECUTE;
        }
        return super.getItemViewType(position);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        View convertView = null;

        switch (viewType) {
            case TYPE_OTHER:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.choose_department_item, null);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                convertView.setLayoutParams(lp);
                viewHolder = new ViewHolder(convertView, TYPE_OTHER);
                viewHolder.context = mContext;

                break;
            case TYPE_EXECUTE:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.address_book_fragment_item, null);
                LinearLayout.LayoutParams lpEx = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                convertView.setLayoutParams(lpEx);
                viewHolder = new ViewHolder(convertView, TYPE_EXECUTE);
                viewHolder.context = mContext;

                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrganizationInfoModel model = mData.get(position);
        holder.position = position;
        holder.model = model;

        switch (getItemViewType(position)) {
            case TYPE_OTHER:
                if (position == 0) {
                    holder.image_divider.setVisibility(View.GONE);
                } else {
                    holder.image_divider.setVisibility(View.VISIBLE);

                }
                if (position == 0) {
                    holder.dep_title_tv.setVisibility(View.VISIBLE);

                } else if (mData.size() > position && position > 0) {
                    if (TextUtils.isEmpty(mData.get(position - 1).getUserId()) && TextUtils.isEmpty(model.getUserId())) {
                        holder.dep_title_tv.setVisibility(View.GONE);
                    } else {
                        holder.dep_title_tv.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.dep_title_tv.setVisibility(View.GONE);
                }
                holder.department_name_tv.setText(model.getDepartmentName());
                break;
            case TYPE_EXECUTE:
                if (position == 0) {

                    holder.image_divider.setVisibility(View.GONE);
                } else {
                    holder.image_divider.setVisibility(View.VISIBLE);
                }

                if (position == 0) {
                    holder.person_title_tv.setVisibility(View.VISIBLE);

                } else if (mData.size() > position && position > 0) {
                    if (!TextUtils.isEmpty(mData.get(position - 1).getUserId()) && !TextUtils.isEmpty(model.getUserId())) {
                        holder.person_title_tv.setVisibility(View.GONE);
                    } else {
                        holder.person_title_tv.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.person_title_tv.setVisibility(View.GONE);
                }
                String str = null;
                if (!TextUtils.isEmpty(model.getUserName())) {
                    str = model.getUserName().trim().substring(0, 1);
                }
                holder.avatar_iv.setContentText(str);
                AppTools.setImageViewPicture(mContext, model.getDepartmentId(), holder.avatar_iv);

                holder.name_tv.setText(model.getUserName());
                holder.department_tv.setText(model.getDepartmentName());
                holder.position_tv.setText(model.getUserPosition());

                if (TextUtils.isEmpty(model.getUserPosition())) {
                    holder.image_view.setVisibility(View.GONE);
                } else {
                    holder.image_view.setVisibility(View.VISIBLE);
                }

                if (model.getIsForbidden().equals("2")) {
                    holder.activate_tv.setVisibility(View.VISIBLE);
                } else {
                    holder.activate_tv.setVisibility(View.GONE);
                }

                if (model.getIsPermissions().equals("0")) {
                    holder.set_iv.setVisibility(View.GONE);
                } else {
                    holder.set_iv.setVisibility(View.VISIBLE);
                }
                break;
        }

    }


    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public int position;
        public OrganizationInfoModel model;

        public RelativeLayout root_layout;
        public AvatarImageView avatar_iv;
        public TextView name_tv;
        public TextView department_tv;
        public TextView position_tv;
        public TextView activate_tv;


        public ImageView set_iv;
        public ImageView msg_iv;
        public ImageView call_iv;


        public TextView department_name_tv;

        public ImageView image_view;
        public ImageView image_divider;


        public TextView person_title_tv;
        public TextView dep_title_tv;

        public ViewHolder(View convertView, int viewType) {
            super(convertView);


            switch (viewType) {
                case TYPE_OTHER:
                    root_layout = (RelativeLayout) convertView.findViewById(R.id.root_layout);
                    root_layout.setOnClickListener(this);
                    department_name_tv = (TextView) convertView.findViewById(R.id.department_name_tv);
                    image_divider = (ImageView) convertView.findViewById(R.id.divider);
                    dep_title_tv = (TextView) convertView.findViewById(R.id.dep_title_tv);


                    break;

                case TYPE_EXECUTE:
                    root_layout = (RelativeLayout) convertView.findViewById(R.id.root_layout);
                    root_layout.setOnClickListener(this);
                    avatar_iv = (AvatarImageView) convertView.findViewById(R.id.avatar_iv);
                    name_tv = (TextView) convertView.findViewById(R.id.name_tv);
                    department_tv = (TextView) convertView.findViewById(R.id.department_tv);
                    position_tv = (TextView) convertView.findViewById(R.id.position_tv);

                    set_iv = (ImageView) convertView.findViewById(R.id.set_iv);
                    msg_iv = (ImageView) convertView.findViewById(R.id.msg_iv);
                    call_iv = (ImageView) convertView.findViewById(R.id.call_iv);

                    msg_iv.setOnClickListener(this);
                    call_iv.setOnClickListener(this);
                    set_iv.setOnClickListener(this);
                    image_view = (ImageView) convertView.findViewById(R.id.image_view);
                    image_divider = (ImageView) convertView.findViewById(R.id.divider);
                    activate_tv = (TextView) convertView.findViewById(R.id.activate_tv);
                    person_title_tv = (TextView) convertView.findViewById(R.id.person_title_tv);

                    break;
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_layout:
                    onRecyclerViewListener.onItemClick(position, model);

                    break;
                case R.id.set_iv:
                    context.startActivity(new Intent(context, OrganizationSetPersonActivity.class).putExtra("model", model));
                    break;
                case R.id.msg_iv:

                    if (!TextUtils.isEmpty(model.getPhone())) {
                        AppTools.sendSMS(context, model.getPhone());
                    } else {
                        AppTools.getToast("暂无电话号码");
                    }
                    break;
                case R.id.call_iv:
                    if (!TextUtils.isEmpty(model.getPhone())) {
                        AppTools.callPhone(context, model.getPhone());
                    } else {
                        AppTools.getToast("暂无电话号码");
                    }
                    break;

            }
        }
    }

}
