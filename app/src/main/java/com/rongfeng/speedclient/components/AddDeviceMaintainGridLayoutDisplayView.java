package com.rongfeng.speedclient.components;

import android.app.Activity;
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
import com.rongfeng.speedclient.common.Constant;
import com.rongfeng.speedclient.common.displayimage.ShowWebImageActivity;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.entity.BaseDataModel;
import com.rongfeng.speedclient.selectpicture.SelectPictureActivity;
import com.rongfeng.speedclient.utils.DensityUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 添加图片
 */
public class AddDeviceMaintainGridLayoutDisplayView extends LinearLayout {
    @Bind(R.id.id_gridLayout)
    GridLayout idGridLayout;
    private OnChangeStatusListener changeListener;
    private Context mContext;
    public List<String> list = new ArrayList<>();
    private int imgColumn = 3;
    private int margin = Utils.dip2px(AppConfig.getContext(), 16);
    private int width;

    public AddDeviceMaintainGridLayoutDisplayView(Context context) {
        super(context);
        this.init(context);
    }

    public AddDeviceMaintainGridLayoutDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public void setOnChangeStatusListener(OnChangeStatusListener changeListener) {
        this.changeListener = changeListener;
    }

    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.add_more_pic_gridlayout_item, this);
        ButterKnife.bind(this, view);
        addDefaultView(0, 0);
    }


    public void addDefaultView(int i, int j) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.add_pic_adapter_item, null);

        final ImageView imageView = (ImageView) view.findViewById(R.id.id_pic);
        final ImageView remindView = (ImageView) view.findViewById(R.id.remind);

        imageView.setImageResource(R.drawable.camera_sign);
        remindView.setVisibility(View.GONE);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppTools.selectPhotoShow((Activity) mContext, mHandler, Constant.SINGLESELECTION);
            }
        });

        GridLayout.Spec rowSpec = GridLayout.spec(i);
        GridLayout.Spec columnSpec = GridLayout.spec(j);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
        params.width = (width - DensityUtil.dip2px(mContext, margin * 2)) / imgColumn;
        idGridLayout.addView(view, params);
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
        int size = paths.size() + 1;
        if (size > 0) {
            final int column = imgColumn; //列数
            int rows = size / column + (size % column == 0 ? 0 : 1);//行数
            idGridLayout.setRowCount(rows);
            idGridLayout.setColumnCount(column);

            int count = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < column; j++) {
                    if (size > count) {

                        count++;

                        View view = LayoutInflater.from(mContext).inflate(R.layout.add_pic_adapter_item, null);
                        final ImageView imageView = (ImageView) view.findViewById(R.id.id_pic);
                        final ImageView remindView = (ImageView) view.findViewById(R.id.remind);


                        if (size != count) {
                            if (isDelete) remindView.setVisibility(View.GONE);
                            String url = paths.get(count-1);
                            if (minUrlPaths != null) url = minUrlPaths.get(count-1);
                            AppTools.setImageViewClub(mContext, url, imageView);

                            imageView.setTag(count-1);
                            imageView.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(mContext, ShowWebImageActivity.class);
                                    intent.putExtra(ShowWebImageActivity.IMAGE_URLS, (Serializable) paths);
                                    intent.putExtra(ShowWebImageActivity.POSITION, (int) v.getTag());
                                    mContext.startActivity(intent);
                                }
                            });
                            remindView.setTag(count-1);
                            remindView.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int position = (int) v.getTag();
                                    paths.remove(position);
                                    list = paths;
                                    setImageLayout(paths, null, false);
                                }
                            });
                        } else if (size == count) {
                            imageView.setImageResource(R.drawable.camera_sign);
                            remindView.setVisibility(View.GONE);

                            view.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (list.size() < 9) {
                                        AppTools.selectPhotoShow((Activity) mContext, mHandler, Constant.SINGLESELECTION);
                                    } else {
                                        AppTools.getToast("最多添加9张照片");
                                    }
                                }
                            });

                        }

                        GridLayout.Spec rowSpec = GridLayout.spec(i);
                        GridLayout.Spec columnSpec = GridLayout.spec(j);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                        params.width = (width - DensityUtil.dip2px(mContext, margin * 2)) / imgColumn;
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
            switch (msg.what) {
                case 0x210:
                    list.add((String) msg.obj);
                    setImageLayout(list, null, false);
                    break;
                case Constant.SINGLESELECTION:
                    BaseDataModel model = (BaseDataModel) msg.obj;
                    if (model.getDictionaryName().equals(((Activity) mContext).getString(R.string.camera_picture))) {//拍照
                        AppTools.getCapturePath((Activity) mContext);

                    } else if (model.getDictionaryName().equals(((Activity) mContext).getString(R.string.photo_picture))) {//相册
                        Intent intent = new Intent((Activity) mContext, SelectPictureActivity.class);
                        intent.putExtra(SelectPictureActivity.IS_MULTI_SELECT, true);
                        intent.putExtra(SelectPictureActivity.HASCOUNT_PICTURE, SelectPictureActivity.MAX_SIZE - list.size());
                        ((Activity) mContext).startActivityForResult(intent, Constant.SELECT_PICTURE);
                    }
                    break;
            }
        }
    };


}