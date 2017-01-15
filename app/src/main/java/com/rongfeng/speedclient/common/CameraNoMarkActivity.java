package com.rongfeng.speedclient.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.rongfeng.speedclient.R;

import java.io.FileOutputStream;
import java.util.List;

import static com.rongfeng.speedclient.push.PushUtils.TAG;

/**
 * @FILE : CameraWaterMarkActivity.java
 * @AUTHOR: 唐磊 CREAT AT 2015-1-8 上午下午2:18:31
 * @NOTE :[水印相机]
 */
public class CameraNoMarkActivity extends Activity implements
        OnClickListener, Callback, AutoFocusCallback {

    private SurfaceView surfaceView;
    private ImageButton button;
    private SurfaceHolder holder;
    private Camera camera;
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
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 设置类型
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        width = mDisplayMetrics.widthPixels;
        height = mDisplayMetrics.heightPixels;

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
            CameraNoMarkActivity.this.finish();

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
            addWaterMarkBitmap(bm, pathName);
            setResult(RESULT_OK, new Intent());
            finish();
        }

    };

    public void addWaterMarkBitmap(Bitmap bitmap, String pathName) {

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
    protected void onDestroy() {
        super.onDestroy();
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