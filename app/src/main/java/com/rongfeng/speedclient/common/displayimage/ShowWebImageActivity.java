package com.rongfeng.speedclient.common.displayimage;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.AppTools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络图片展示
 */
public class ShowWebImageActivity extends Activity implements OnClickListener {
    public static final String IMAGE_URLS = "image_urls";
    public static final String POSITION = "position";
    private List<String> imagePaths;
    private int position;
    private GestureImageView[] mImageViews;
    private MyViewPager viewPager;
    private TextView page;
    private TextView saveTv;
    private int count;
    private ImageButton back;
    private String urlPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_image_activity);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
        getIntentValue();
        initViews();
    }

    private void getIntentValue() {
        Intent intent = getIntent();
        imagePaths = (List<String>) intent.getSerializableExtra(IMAGE_URLS);
        position = intent.getIntExtra(POSITION, 0);
        count = imagePaths.size();
    }

    private void initViews() {
        saveTv = (TextView) findViewById(R.id.save_tv);
        page = (TextView) findViewById(R.id.text_page);
        if (count <= 1) {
            page.setVisibility(View.GONE);
        } else {
            page.setVisibility(View.VISIBLE);
            page.setText((position + 1) + "/" + count);
        }

        viewPager = (MyViewPager) findViewById(R.id.web_image_viewpager);
        viewPager.setPageMargin(20);
        viewPager.setAdapter(new ImagePagerAdapter(getWebImageViews()));
        viewPager.setCurrentItem(position);

        viewPager.addOnPageChangeListener(new OnPageChangeListener() {

            public void onPageSelected(int arg0) {
                page.setText((arg0 + 1) + "/" + count);
                mImageViews[arg0].reset();
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    private static int getMemoryCacheSize(Context context) {
        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
            // limit
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }
        return memoryCacheSize;
    }

    private List<View> getWebImageViews() {
        mImageViews = new GestureImageView[count];
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        List<View> views = new ArrayList<View>();
        for (int i = 0; i < count; i++) {
            View view = layoutInflater.inflate(R.layout.web_image_item, null);
            final GestureImageView imageView = (GestureImageView) view
                    .findViewById(R.id.image);
            final ProgressBar progressBar = (ProgressBar) view
                    .findViewById(R.id.loading);
            mImageViews[i] = imageView;
            Glide.with(this).load(imagePaths.get(i)).into(imageView);
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
            views.add(view);
        }
        viewPager.setGestureImages(mImageViews);
        saveTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                urlPath = imagePaths.get(viewPager.getCurrentItem());
                if (!TextUtils.isEmpty(urlPath)) {
                    //开启子线程
                    new Thread() {
                        public void run() {
                            try {
                                URL url = new URL(urlPath);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setConnectTimeout(6 * 1000);  // 注意要设置超时，设置时间不要超过10秒，避免被android系统回收
                                if (conn.getResponseCode() != 200)
                                    throw new RuntimeException("图片下载失败");
                                InputStream inSream = conn.getInputStream();
                                //把图片保存到项目的根目录
                                readAsFile(inSream, new File(AppTools.getFileSavePath(ShowWebImageActivity.this) + "/" + System.currentTimeMillis() + ".jpg"));
                                Message msg = new Message();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        ;
                    }.start();
                }
            }
        });
        return views;
    }

    @Override
    protected void onDestroy() {
        if (mImageViews != null) {
            mImageViews = null;
        }
        super.onDestroy();
    }

    private class ImagePagerAdapter extends PagerAdapter {
        private List<View> views;

        public ImagePagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public Object instantiateItem(View view, int position) {
            ((ViewPager) view).addView(views.get(position), 0);
            return views.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.back:
                finish();
                break;

            default:
                break;
        }
    }

    public static void readAsFile(InputStream inSream, File file) throws Exception {
        FileOutputStream outStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inSream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inSream.close();
    }

    //创建Handler
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                Toast.makeText(AppConfig.getContext(), "图片以保存至" + AppTools.getFileSavePath(ShowWebImageActivity.this) + "文件夹", Toast.LENGTH_SHORT).show();
            }
        }

        ;
    };
}
