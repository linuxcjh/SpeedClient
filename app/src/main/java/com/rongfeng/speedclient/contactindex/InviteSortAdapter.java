package com.rongfeng.speedclient.contactindex;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class InviteSortAdapter extends BaseAdapter implements SectionIndexer {
    private List<SortModel> mList;
    private Context mContext;
    LayoutInflater mInflater;


    public InviteSortAdapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;
        if (list == null) {
            this.mList = new ArrayList<>();
        } else {
            this.mList = list;
        }
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<SortModel> list) {
        if (list == null) {
            this.mList = new ArrayList<>();
        } else {
            this.mList = list;
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final SortModel mContent = mList.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_invite_layout, null);
            viewHolder.tvUser = (TextView) view.findViewById(R.id.user_tv);
            viewHolder.dividerIv = (ImageView) view.findViewById(R.id.divider_iv);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvNumber = (TextView) view.findViewById(R.id.number);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.msg_iv = (ImageView) view.findViewById(R.id.msg_iv);
            viewHolder.call_iv = (ImageView) view.findViewById(R.id.call_iv);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        //根据position获取分类的首字母的Char ascii值
        final int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.sortLetters);
            viewHolder.dividerIv.setVisibility(View.GONE);
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
            viewHolder.dividerIv.setVisibility(View.VISIBLE);
        }



        if (!TextUtils.isEmpty(this.mList.get(position).name)) {
            viewHolder.tvUser.setText(this.mList.get(position).name.substring(0, 1));
        }
        viewHolder.tvTitle.setText(this.mList.get(position).name);
        viewHolder.tvNumber.setText(this.mList.get(position).number);
        viewHolder.msg_iv.setTag(mList.get(position));
        viewHolder.call_iv.setTag(mList.get(position));

        viewHolder.msg_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortModel model = (SortModel) v.getTag();
                if (!TextUtils.isEmpty(model.number)) {
                    AppTools.sendSMS(mContext, model.number);
                } else {
                    AppTools.getToast("暂无电话号码");
                }
            }
        });

        viewHolder.call_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortModel model = (SortModel) v.getTag();

                if (!TextUtils.isEmpty(model.number)) {
                    AppTools.callPhone(mContext, model.number);
                } else {
                    AppTools.getToast("暂无电话号码");
                }
            }
        });


        return view;

    }

    public static class ViewHolder {

        public TextView tvUser;
        public ImageView dividerIv;
        public TextView tvLetter;
        public TextView tvTitle;
        public TextView tvNumber;
        public ImageView msg_iv;
        public ImageView call_iv;

    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mList.get(position).sortLetters.charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mList.get(i).sortLetters;
            char firstChar = sortStr.toUpperCase(Locale.CHINESE).charAt(0);
            if (firstChar == section) {
                return i;

            }
        }

        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }


}