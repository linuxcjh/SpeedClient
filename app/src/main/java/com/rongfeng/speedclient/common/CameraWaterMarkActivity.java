package com.rongfeng.speedclient.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.permisson.PermissionsActivity;
import com.rongfeng.speedclient.permisson.PermissionsChecker;
import com.rongfeng.speedclient.utils.DensityUtil;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import static com.rongfeng.speedclient.push.PushUtils.TAG;

/**
 * @FILE : CameraWaterMarkActivity.java
 * @AUTHOR: 唐磊 CREAT AT 2015-1-8 上午下午2:18:31
 * @NOTE :[水印相机]
 */
public class CameraWaterMarkActivity extends Activity implements
        OnClickListener, Callback, AutoFocusCallback, AMapLocationListener {
    {

        if (PermissionsChecker.getPermissionsChecker().lacksPermissions(ConstantPermission.PERMISSIONS_LOCATION)) {
            PermissionsChecker.getPermissionsChecker().startPermissionsActivity(this, ConstantPermission.PERMISSIONS_LOCATION);
        } else {
            AppTools.startLbsLocation(this, true);
        }

    }

    private SurfaceView surfaceView;
    private ImageButton button;
    private SurfaceHolder holder;
    private Camera camera;
    private TextView addr_tv, time_tv;
    private String timeTitle;
    private boolean isPhotograph;
    private int width, height;
    private float imageH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_water_camera_layout);
        init();

    }

    private void init() {

        button = (ImageButton) findViewById(R.id.button1);
        button.setOnClickListener(this);
        addr_tv = (TextView) findViewById(R.id.camer_addr);
        time_tv = (TextView) findViewById(R.id.camer_time);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 设置类型
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        width = mDisplayMetrics.widthPixels;
        height = mDisplayMetrics.heightPixels;
        handler.sendEmptyMessage(1);

    }


    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        try {
            if (camera != null) {

                camera.setParameters(setCameraParameters());
                camera.setDisplayOrientation(90);
                camera.startPreview();
            } else {
            }
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
    }


    /**
     * 相机参数
     */
    private Camera.Parameters setCameraParameters() {
        Camera.Parameters parameters = camera.getParameters();

        parameters.setPictureFormat(PixelFormat.JPEG);
        List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
        for (Camera.Size size : pictureSizeList) {
            Log.i(TAG, "pictureSizeList size.width=" + size.width + "  size.height=" + size.height);
        }
        /**从列表中选取合适的分辨率*/
        Camera.Size picSize = getProperSize(pictureSizeList, ((float) height / width));
        if (null == picSize) {
            Log.i(TAG, "null == picSize");
            picSize = parameters.getPictureSize();
        }
        Log.i(TAG, "picSize.width=" + picSize.width + "  picSize.height=" + picSize.height);
        // 根据选出的PictureSize重新设置SurfaceView大小
        float w = picSize.width;
        float h = picSize.height;
        imageH = 4 / 3f * width;
        parameters.setPictureSize(picSize.width, picSize.height);
        surfaceView.setLayoutParams(new RelativeLayout.LayoutParams((int) (height * (h / w)), height));

        // 获取摄像头支持的PreviewSize列表
        List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();

        for (Camera.Size size : previewSizeList) {
            Log.i(TAG, "previewSizeList size.width=" + size.width + "  size.height=" + size.height);
        }
        Camera.Size preSize = getProperSize(previewSizeList, ((float) height) / width);
        if (null != preSize) {
            Log.i(TAG, "preSize.width=" + preSize.width + "  preSize.height=" + preSize.height);
            parameters.setPreviewSize(preSize.width, preSize.height);
        }

        parameters.setJpegQuality(80);

        return parameters;
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        try {
            camera = Camera.open();//这个0或者1 是camera 驱动注册时候 系统赋予 前置后置的
            camera.setPreviewDisplay(holder);
        } catch (Exception e) {
            CameraWaterMarkActivity.this.finish();

        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        try {
            camera.stopPreview();
            camera.release();
            camera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ShutterCallback shutter = new ShutterCallback() {

        @Override
        public void onShutter() {

        }

    };

    private PictureCallback jpeg = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),
                    matrix, true);
            String pathName = getIntent().getStringExtra("path");
            isPhotograph = true;
            addWaterMarkBitmap(bm, pathName);
            setResult(RESULT_OK, new Intent());
            finish();
        }

    };

    public void addWaterMarkBitmap(Bitmap bm, String pathName) {
        int h = bm.getHeight();
        int w = bm.getWidth();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        String familyName = "宋体";
        Typeface font = Typeface.create(familyName, Typeface.NORMAL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(font);
        float bi = (float) h / imageH;
//        paint.setTextSize(addr_tv.getTextSize() * bi);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 11, getResources().getDisplayMetrics()) * bi);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG));
        canvas.drawBitmap(bm, 0, 0, paint);


//        if (!TextUtils.isEmpty(addr_tv.getText().toString())) {
//            if (addr_tv.getLineCount() == 2) {
//                Layout layout = addr_tv.getLayout();
//                int aLineOfWords = layout.getLineEnd(0);// 0第一行
//                // 返回指定行中最后一个字在整个字符串中的位置
//                String str1 = addr_tv.getText().toString()
//                        .substring(0, aLineOfWords);
//                String str2 = addr_tv.getText().toString()
//                        .substring(aLineOfWords);
//                canvas.drawText(str1, Utils.dip2px(this, 10), h - Utils.dip2px(this, 60), paint);
//                canvas.drawText(str2, Utils.dip2px(this, 10), h - Utils.dip2px(this, 40), paint);
//
//            } else {
//                canvas.drawText(addr_tv.getText().toString(), Utils.dip2px(this, 20), h - Utils.dip2px(this, 40), paint);
//
//            }
////
//        }
//        if (!TextUtils.isEmpty(time_tv.getText().toString())) {
//            canvas.drawText(time_tv.getText().toString(), Utils.dip2px(this, 20), h - Utils.dip2px(this, 20), paint);
//        }


        if (!TextUtils.isEmpty(addr_tv.getText())) {
            if (addr_tv.getLineCount() == 2) {
                Layout layout = addr_tv.getLayout();
                int aLineOfWords = layout.getLineEnd(0);// 0第一行
                // 返回指定行中最后一个字在整个字符串中的位置
                String str1 = addr_tv.getText().toString()
                        .substring(0, aLineOfWords);
                String str2 = addr_tv.getText().toString()
                        .substring(aLineOfWords);
                canvas.drawText(str1, addr_tv.getLeft() * bi,
                        h - Utils.dip2px(this, 50) - (addr_tv.getTop() + addr_tv.getTextSize()) * bi, paint);
                canvas.drawText(
                        str2,
                        addr_tv.getLeft() * bi,
                        h - Utils.dip2px(this, 36) -  (addr_tv.getTop() + 2 * addr_tv.getTextSize() + addr_tv
                                .getTextSize() / 5) * bi, paint);
            } else {
                canvas.drawText(addr_tv.getText().toString(), addr_tv.getLeft()
                                * bi, h - Utils.dip2px(this, 36) - (addr_tv.getTop() + addr_tv.getTextSize()) * bi,
                        paint);
            }

        }
        if (!TextUtils.isEmpty(time_tv.getText())) {
            canvas.drawText(time_tv.getText().toString(), time_tv.getLeft()
                    * bi, h - Utils.dip2px(this, 24) - (time_tv.getTextSize()) * bi, paint);
        }
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(pathName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                stream.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    private PictureCallback raw = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
        }
    };

    @Override
    public void onClick(View arg0) {
        arg0.setEnabled(false);
        camera.autoFocus(this);
    }

    @Override
    public void onAutoFocus(boolean arg0, Camera arg1) {
        camera.takePicture(shutter, raw, jpeg);

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                handler.sendMessage(handler.obtainMessage(0, aMapLocation.getAddress()));
            }
        }
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0:
                    addr_tv.setTextColor(Color.WHITE);
                    if (msg.obj != null) {
                        addr_tv.setText((String) msg.obj);
                        setPaintByMeasureText();
                    }
                    break;
                case 1:
                    time_tv.setTextColor(Color.WHITE);
                    time_tv.setText(DateUtil.getStringByDate(new Date(), DateUtil.yyyy_MM_dd_HH_mm_ss));
                    if (!isPhotograph) {
                        handler.sendMessageDelayed(handler.obtainMessage(1), 1000);
                    }
                    break;
            }
        }

    };

    /**
     * 动态计算字体大小
     */
    private void setPaintByMeasureText() {
        TextPaint mTextPaint = addr_tv.getPaint();
        String text = addr_tv.getText().toString();
        float textWidth = mTextPaint.measureText(text);
        int maxTextWidth = width - 2 * DensityUtil.dip2px(this, 10);
        if (textWidth > maxTextWidth) {
            int length = text.length();
            int wordWidth = 2 * maxTextWidth / length;
            int sp = DensityUtil.px2sp(CameraWaterMarkActivity.this, wordWidth);
            sp = sp > 15 ? 15 : sp;
            addr_tv.setTextSize(sp);
            time_tv.setTextSize(sp);
        }
    }

    @Override
    protected void onDestroy() {
        AppTools.stopLbsLocation();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantPermission.PERMISSION_REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else if (requestCode == ConstantPermission.PERMISSION_REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            AppTools.startLbsLocation(this, true);
        }
    }

    /**
     * 从列表中选取合适的分辨率
     * 默认w:h = 4:3
     * <p>注意：这里的w对应屏幕的height
     * h对应屏幕的width<p/>
     */
    private Camera.Size getProperSize(List<Camera.Size> pictureSizeList, float screenRatio) {
        Log.i(TAG, "screenRatio=" + screenRatio);
        Camera.Size result = null;
        for (Camera.Size size : pictureSizeList) {

            if (size.width > width || size.height > height) {//如果大于屏幕尺寸直接抛弃
                continue;
            }
            float currentRatio = ((float) size.width) / size.height;
            if (currentRatio - screenRatio == 0) {
                result = size;
                break;
            }
        }

        if (null == result) {
            for (Camera.Size size : pictureSizeList) {
                float curRatio = ((float) size.width) / size.height;
                if (curRatio == 4f / 3) {// 默认w:h = 4:3
                    result = size;
                    break;
                }
            }
        }

        return result;
    }

}