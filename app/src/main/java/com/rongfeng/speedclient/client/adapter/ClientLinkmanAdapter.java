package com.rongfeng.speedclient.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.client.ClientAddContactDetailsActivity;
import com.rongfeng.speedclient.client.entry.ContactPersonModel;
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.components.AvatarImageView;

import java.util.List;

/**
 * 联系人Adapter
 * Created by Administrator on 2015/10/27.
 */
public class ClientLinkmanAdapter extends BaseAdapter implements View.OnClickListener {
    private boolean isShow = false;
    private Context context;
    private Handler handler;
    private List<ContactPersonModel> data;

    public ClientLinkmanAdapter(Context context, int layoutResId, Handler handler) {
        this(context, null, handler);
    }

    public ClientLinkmanAdapter(Context context, List<ContactPersonModel> data, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.data = data;
    }

    public void setData(List<ContactPersonModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data != null ? data.size() + 2 : 2;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.client_linkman_item, null);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.tavatar);
            viewHolder.name = (TextView) convertView.findViewById(R.id.link_name);
            viewHolder.remind = (ImageView) convertView.findViewById(R.id.remind);
            viewHolder.clue_avatar_no_url_iv = (AvatarImageView) convertView.findViewById(R.id.clue_avatar_no_url_iv);
            viewHolder.grid_item_layout=(LinearLayout)convertView.findViewById(R.id.grid_item_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (data != null && position < data.size()) {
            final ContactPersonModel item = data.get(position);
            viewHolder.avatar.setClickable(false);
            viewHolder.grid_item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context.startActivity(new Intent(context, ClientAddContactDetailsActivity.class).putExtra("contactId", item.getCsrContactId()));

                }
            });
            viewHolder.name.setText(item.getName());
            viewHolder.name.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(item.getFileImg()) && !TextUtils.isEmpty(item.getName())) {
                viewHolder.avatar.setVisibility(View.GONE);
                viewHolder.clue_avatar_no_url_iv.setVisibility(View.VISIBLE);
                viewHolder.clue_avatar_no_url_iv.setContentText(item.getName().substring(0, 1));
            } else {
                viewHolder.clue_avatar_no_url_iv.setVisibility(View.GONE);
                viewHolder.avatar.setVisibility(View.VISIBLE);
                AppTools.setImageViewPicture(context, item.getFileImg(), viewHolder.avatar);
            }
            viewHolder.remind.setOnClickListener(this);
            viewHolder.remind.setTag(R.id.remind, position);
            if (isShow) {

                viewHolder.remind.setVisibility(View.VISIBLE);
            } else {
                viewHolder.remind.setVisibility(View.INVISIBLE);
            }


        } else if ((data == null && position == 0) || (data != null && position == data.size())) {
            viewHolder.grid_item_layout.setClickable(false);

            viewHolder.clue_avatar_no_url_iv.setVisibility(View.GONE);
            viewHolder.avatar.setVisibility(View.VISIBLE);

            Glide.with(context).load(R.drawable.clientcontacts_add_button).into(viewHolder.avatar);
            viewHolder.avatar.setTag(R.id.remind, position);
            viewHolder.avatar.setOnClickListener(this);
            viewHolder.name.setText("添加");
            viewHolder.remind.setVisibility(View.GONE);
        } else if ((data == null && position == 1) || (data != null && position == data.size() + 1)) {
            viewHolder.grid_item_layout.setClickable(false);

            viewHolder.clue_avatar_no_url_iv.setVisibility(View.GONE);
            viewHolder.avatar.setVisibility(View.VISIBLE);

            Glide.with(context).load(R.drawable.clientcontacts_delete_button).into(viewHolder.avatar);
            viewHolder.avatar.setTag(R.id.remind, position);
            viewHolder.avatar.setOnClickListener(this);
            viewHolder.remind.setVisibility(View.GONE);
            viewHolder.name.setText("删除");
        }


        return convertView;
    }


    static class ViewHolder {

        public ImageView avatar;
        public LinearLayout grid_item_layout;
        public AvatarImageView clue_avatar_no_url_iv;
        public TextView name;
        public ImageView remind;

    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.remind);
        switch (v.getId()) {
            case R.id.remind:
                handler.sendMessage(handler.obtainMessage(Constant.UNBIND_CONTACT_SIGN, position));
                break;
            case R.id.tavatar:
                if ((data != null && data.size() == position) || data == null && position == 0) {//删除
                    handler.sendMessage(handler.obtainMessage(Constant.BIND_CONTACT_SIGN, position));

//                    AppTools.selectDialog("添加联系人", (Activity) context, AppTools.generationDictionary(context.getResources().getStringArray(R.array.select_contact)), handler, Constant.BIND_CONTACT_SIGN);
                } else if ((data != null && data.size() + 1 == position) || data == null && position == 1) {//添加
                    isShow = isShow ? false : true;
                    notifyDataSetChanged();
                }
                break;
        }
    }
}
