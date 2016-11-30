package com.rongfeng.speedclient.selectpicture;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.selectpicture.adapter.SelectPictureAdapter;
import com.rongfeng.speedclient.selectpicture.model.ImageFloder;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * AUTHOR: Alex
 * DATE: 18/4/2016 11:42
 */
public class SelectPictureActivity extends BaseActivity implements ListImageDirPopupWindow.OnImageDirSelected {
    /**
     * 选择结果标识
     */
    public static final String SELECTED_RESULT = "selected_result";
    /**
     * 剩余可选照片标识
     */
    public static final String HASCOUNT_PICTURE = "has_count_picture";
    /**
     * 是否多选标识
     */
    public static final String IS_MULTI_SELECT = "isMultiSelect";
    /**
     * 最多可选照片数
     */
    public static final int MAX_SIZE = 9;
    /**
     * Grid 列数
     */
    public static final int SPANCOUNT = 3;


    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.cancel_ib)
    ImageButton cancelIb;
    @Bind(R.id.filter_tv)
    TextView filterTv;
    @Bind(R.id.select_dir_bt)
    Button selectDirBt;
    private ProgressDialog mProgressDialog;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();
    /**
     * 总共照片数
     */
    int totalCount = 0;

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

    /**
     * 选择结果
     */
    private List<String> selectResult = new ArrayList<>();

    /**
     * 个目录选择照片map
     */
    private Map<Integer, Map<Integer, String>> selectMap = new HashMap<>();

    /**
     * 已存在的图片数量
     */
    private int hasCount;

    /**
     * 是否多选
     */
    private boolean isMultiSelect;
    private ListImageDirPopupWindow mListImageDirPopupWindow;
    private SelectPictureAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture_layout);
        ButterKnife.bind(this);
        initData();
        iniView();
        getImages();
    }


    private void initData() {
        hasCount = getIntent().getIntExtra(HASCOUNT_PICTURE, MAX_SIZE);
        if (hasCount != MAX_SIZE) {
            filterTv.setText("完成(" + selectResult.size() + "/" + hasCount + ")");

        }
        isMultiSelect = getIntent().getBooleanExtra(IS_MULTI_SELECT, false);
        if (isMultiSelect) {
            filterTv.setVisibility(View.VISIBLE);
        } else {
            filterTv.setVisibility(View.GONE);
        }
    }

    private void iniView() {

        GridLayoutManager layoutManager = new GridLayoutManager(this, SPANCOUNT);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        recyclerView.setLayoutManager(layoutManager);
    }

    @OnClick({R.id.cancel_ib, R.id.filter_tv, R.id.select_dir_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_ib:
                finish();
                break;
            case R.id.filter_tv:
                setResultData();
                break;
            case R.id.select_dir_bt:
                mListImageDirPopupWindow.showAsDropDown(selectDirBt, 0, 0);
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
//                lp.alpha = 0.8f;
                getWindow().setAttributes(lp);
                break;
        }
    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) (AppConfig.getIntConfig("HEIGHT", 0) * 0.7),
                mImageFloders, LayoutInflater.from(SelectPictureActivity.this)
                .inflate(R.layout.list_dir, null));

        mListImageDirPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    /**
     * 为View绑定数据
     */
    private void data2View() {
        if (mImgDir == null) {
            AppTools.getToast("暂无图片");
            return;
        }
        selectDirBt.setText(mImgDir.getName());
        mImgs = Arrays.asList(mImgDir.list());
        String path = mImgDir.getAbsolutePath();
        for (int i = 0; i < mImgs.size(); i++) {
            mImgs.set(i, path + "/" + mImgs.get(i));
        }
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new SelectPictureAdapter(SelectPictureActivity.this, mImgs, mHandler, isMultiSelect, hasCount, selectResult.size(), selectMap, 0);
        recyclerView.setAdapter(mAdapter);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x11:
                    mProgressDialog.dismiss();
                    data2View();
                    initListDirPopupWindw();
                    break;
                case 0x12:
                    selectMap = (Map<Integer, Map<Integer, String>>) msg.obj;
                    selectResult.clear();
                    for (Map.Entry<Integer, Map<Integer, String>> entry : selectMap.entrySet()) {
                        entry.getValue().entrySet();
                        for (Map.Entry<Integer, String> e : entry.getValue().entrySet()) {
                            selectResult.add(e.getValue());
                        }
                    }
                    if (isMultiSelect) {
                        filterTv.setText("完成(" + selectResult.size() + "/" + hasCount + ")");

                    } else {
                        setResultData();
                    }
                    break;
            }

        }
    };


    /**
     * 结果返回
     */
    private void setResultData() {

        Intent intent = new Intent();
        if (isMultiSelect) {
            intent.putExtra(SELECTED_RESULT, (Serializable) selectResult);
        } else {
            if (selectResult.size() > 0) {
                intent.putExtra(SELECTED_RESULT, selectResult.get(0));
            }
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void selected(ImageFloder floder, int posCurrentDir) {

        mImgDir = new File(floder.getDir());
        mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg"))
                    return true;
                return false;
            }
        }));
        String path = mImgDir.getAbsolutePath();
        for (int i = 0; i < mImgs.size(); i++) {
            mImgs.set(i, path + "/" + mImgs.get(i));
        }
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new SelectPictureAdapter(SelectPictureActivity.this, mImgs, mHandler, isMultiSelect, hasCount, selectResult.size(), selectMap, posCurrentDir);
        recyclerView.setAdapter(mAdapter);

        selectDirBt.setText(floder.getName());
        mListImageDirPopupWindow.dismiss();

    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = SelectPictureActivity.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;
                    totalCount += picSize;

                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();
                mDirPaths = null;
                mHandler.sendEmptyMessage(0x11);
            }
        }).start();

    }
}
