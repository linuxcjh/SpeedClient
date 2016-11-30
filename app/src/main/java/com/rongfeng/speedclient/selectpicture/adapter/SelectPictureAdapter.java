package com.rongfeng.speedclient.selectpicture.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.selectpicture.SelectPictureActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 【Client history record list Adapter】
 * AUTHOR: Alex
 * DATE: 22/10/2015 15:23
 */
public class SelectPictureAdapter extends RecyclerView.Adapter<SelectPictureAdapter.ViewHolder> {


    private Context mContext;
    private List<String> mData;
    private boolean isMultiSelect;

    private Map<Integer, String> selectMap;
    private Map<Integer, Map<Integer, String>> whereMap;

    private Handler mHandler;
    private int hasCount;
    private int remainCount;
    private int whereAdapter;//当前是哪一个adapger

    /**
     * @param context
     * @param data
     * @param mHandler
     * @param isMultiSelect 是否多选
     * @param hasCount      现有的数量
     * @param remainCount   剩余可添加的数量
     * @param whereMap      存储已选的map
     * @param whereAdapter  当前目录的位置
     */
    public SelectPictureAdapter(Context context, List<String> data, Handler mHandler, boolean isMultiSelect, int hasCount, int remainCount, Map<Integer, Map<Integer, String>> whereMap, int whereAdapter) {
        this.mData = data;
        this.mContext = context;
        this.mHandler = mHandler;
        this.isMultiSelect = isMultiSelect;
        this.hasCount = hasCount;
        this.whereMap = whereMap;
        this.remainCount = remainCount;
        this.whereAdapter = whereAdapter;

        if (whereMap.containsKey(whereAdapter)) {
            selectMap = whereMap.get(whereAdapter);
        } else {
            selectMap = new HashMap<>();

        }

    }


    public void setData(List<String> data) {

        this.mData = data;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.select_picture_item, null);
        viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
        params.height = AppConfig.getIntConfig("WIDTH", 0) / SelectPictureActivity.SPANCOUNT;
        params.width = params.height;
        holder.imageView.setLayoutParams(params);
        Glide.with(mContext).load(mData.get(position)).into(holder.imageView);
        holder.relativeLayout.setTag(position);

        if (isMultiSelect) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setTag(position);
            if (selectMap.containsKey(holder.checkBox.getTag())) {
                holder.imageView.setColorFilter(Color.parseColor("#77000000"));
                holder.checkBox.setButtonDrawable(R.drawable.segment_select);
            } else {
                holder.checkBox.setButtonDrawable(R.drawable.select_none);
                holder.imageView.setColorFilter(null);
            }
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }


    }


    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout relativeLayout;
        public ImageView imageView;
        public CheckBox checkBox;

        public ViewHolder(View convertView) {
            super(convertView);
            imageView = (ImageView) convertView.findViewById(R.id.imageView);
            checkBox = (CheckBox) convertView.findViewById(R.id.check_cb);
            relativeLayout = (RelativeLayout) convertView.findViewById(R.id.content_layout);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();

                    if (isMultiSelect) {

                    } else {
                        selectMap.put(position, mData.get(position));
                        whereMap.put(whereAdapter,selectMap);
                        mHandler.sendMessage(mHandler.obtainMessage(0x12, whereMap));
                    }
//                 Intent intent = new Intent(mContext, ShowWebImageActivity.class);
//                    intent.putExtra(ShowWebImageActivity.IMAGE_URLS, (Serializable) mData);
//                    intent.putExtra(ShowWebImageActivity.POSITION, (int) v.getTag());
//                    ((Activity) mContext).startActivity(intent);
                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = (int) checkBox.getTag();
                    if (isChecked) {
                        if (selectMap.size() < (hasCount - remainCount)) {
                            selectMap.put(position, mData.get(position));
                            whereMap.put(whereAdapter,selectMap);
                            imageView.setColorFilter(Color.parseColor("#77000000"));
                            checkBox.setButtonDrawable(R.drawable.segment_select);
                            mHandler.sendMessage(mHandler.obtainMessage(0x12, whereMap));
                        } else {
                            AppTools.getToast("最多只能选择" + hasCount + "张照片");
                        }

                    } else {
                        checkBox.setButtonDrawable(R.drawable.select_none);
                        if (selectMap.containsKey(position)) {
                            selectMap.remove(position);
                            whereMap.put(whereAdapter,selectMap);
                            mHandler.sendMessage(mHandler.obtainMessage(0x12, whereMap));
                        }
                        imageView.setColorFilter(null);
                    }
                }
            });
        }
    }


}