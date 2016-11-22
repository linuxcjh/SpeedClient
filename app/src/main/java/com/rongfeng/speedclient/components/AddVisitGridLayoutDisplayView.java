package com.rongfeng.speedclient.components;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.displayimage.ShowWebImageActivity;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.utils.DensityUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 拜访添加图片
 */
public class AddVisitGridLayoutDisplayView extends LinearLayout {
    @Bind(R.id.id_gridLayout)
    GridLayout idGridLayout;
    private Context mContext;
    public List<String> list = new ArrayList<>();
    private int imgColumn;
    private int margin = 0;
    private int width;

    public AddVisitGridLayoutDisplayView(Context context) {
        super(context);
        this.init(context);
    }

    public AddVisitGridLayoutDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }


    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.add_more_pic_gridlayout_item, this);
        ButterKnife.bind(this, view);

    }

    /**
     * 动态添加图片
     *
     * @param paths       原图
     * @param minUrlPaths 缩略图
     * @param isDelete
     */
    public void setImageLayout(final List<String> paths, final List<String> minUrlPaths, boolean isDelete) {

        idGridLayout.removeAllViews();
        int size = paths.size();
        if (size > 0) {
            if (imgColumn == 0) imgColumn = 4;
            final int column = imgColumn; //列数
            int rows = size / column + (size % column == 0 ? 0 : 1);//行数
            idGridLayout.setRowCount(rows);
            idGridLayout.setColumnCount(column);

            int count = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < column; j++) {
                    if (size > count) {
                        View view = LayoutInflater.from(mContext).inflate(R.layout.add_visit_pic_adapter_item, null);
                        final ImageView imageView = (ImageView) view.findViewById(R.id.id_pic);
                        final ImageView remindView = (ImageView) view.findViewById(R.id.remind);
                        if (isDelete) remindView.setVisibility(View.GONE);
                        String url = paths.get(count);
                        if (minUrlPaths != null) url = minUrlPaths.get(count);
                        AppTools.setImageViewClub(mContext, url, imageView);

                        imageView.setTag(count);
                        imageView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(mContext, ShowWebImageActivity.class);
                                intent.putExtra(ShowWebImageActivity.IMAGE_URLS, (Serializable) paths);
                                intent.putExtra(ShowWebImageActivity.POSITION, (int) v.getTag());
                                mContext.startActivity(intent);
                            }
                        });
                        remindView.setTag(count);
                        remindView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int position = (int) v.getTag();
                                paths.remove(position);
                                list = paths;
                                setImageLayout(paths, null, false);
                            }
                        });
                        count++;

                        GridLayout.Spec rowSpec = GridLayout.spec(i);
                        GridLayout.Spec columnSpec = GridLayout.spec(j);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                        params.width = (width - DensityUtil.dip2px(mContext, margin * 2)) / imgColumn - DensityUtil.dip2px(mContext, 4);
                        idGridLayout.addView(view, params);
                    }
                }
            }
        }
    }

    public void setData(final String path) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (path != null && !path.contains("http")) {
                    String s = AppTools.getCompressImage(path);
                    if (!TextUtils.isEmpty(s)) {
                        mHandler.sendMessage(mHandler.obtainMessage(0x210, s));
                    }
                } else if (path != null) {
                    mHandler.sendMessage(mHandler.obtainMessage(0x210, path));
                }
            }
        }).start();
    }

    public void setData(final List<String> path) {
        for (final String str : path) {
            setData(str);
        }
    }

    public List<String> getData() {

        return list;
    }

    public void setColumn(int column) {
        this.imgColumn = column;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list.add((String) msg.obj);
            setImageLayout(list, null, false);
        }
    };


}
