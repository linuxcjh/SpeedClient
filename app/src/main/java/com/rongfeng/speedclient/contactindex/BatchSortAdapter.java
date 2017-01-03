package com.rongfeng.speedclient.contactindex;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class BatchSortAdapter extends BaseAdapter implements SectionIndexer {
    private List<SortModel> mList=new ArrayList<>();
    private List<SortModel> mSelectedList= new ArrayList<>();
    private Context mContext;


    public BatchSortAdapter(Context mContext) {
        this.mContext = mContext;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_batch_contact, null);
            viewHolder.invite_bt = (Button) view.findViewById(R.id.invite_bt);
            viewHolder.tvUser = (TextView) view.findViewById(R.id.user_tv);
            viewHolder.dividerIv = (ImageView) view.findViewById(R.id.divider_iv);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvNumber = (TextView) view.findViewById(R.id.number);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.cbChecked = (CheckBox) view.findViewById(R.id.cbChecked);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        mList.get(position).position = position;//记录位置

        if (mList.get(position).isExist) {//已存在
            viewHolder.invite_bt.setBackgroundResource(R.drawable.adbook_already);
            viewHolder.invite_bt.setVisibility(View.VISIBLE);
            viewHolder.cbChecked.setVisibility(View.GONE);

        } else {
//            viewHolder.invite_bt.setBackgroundResource(R.drawable.adbook_invite);
            viewHolder.invite_bt.setVisibility(View.GONE);
            viewHolder.cbChecked.setVisibility(View.VISIBLE);
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
        viewHolder.cbChecked.setChecked(isSelected(mContent));

        viewHolder.invite_bt.setTag(this.mList.get(position));
        viewHolder.invite_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SortModel selectModel = (SortModel) v.getTag();
                if (selectModel.isExist) {
                    AppTools.getToast("客户已导入");
                    return;
                }


            }
        });

        return view;

    }

    public static class ViewHolder {

        public Button invite_bt;
        public TextView tvUser;
        public ImageView dividerIv;
        public TextView tvLetter;
        public TextView tvTitle;
        public TextView tvNumber;
        public CheckBox cbChecked;
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

    private boolean isSelected(SortModel model) {
        return mSelectedList.contains(model);
        //return true;
    }

    public void toggleChecked(int position) {
        if (isSelected(mList.get(position))) {
            removeSelected(position);
        } else {
            setSelected(position);
        }

    }

    private void setSelected(int position) {
        if (!mSelectedList.contains(mList.get(position))) {
            mSelectedList.add(mList.get(position));
        }
    }

    private void removeSelected(int position) {
        if (mSelectedList.contains(mList.get(position))) {
            mSelectedList.remove(mList.get(position));
        }
    }

    public List<SortModel> getSelectedList() {
        return mSelectedList;
    }


}