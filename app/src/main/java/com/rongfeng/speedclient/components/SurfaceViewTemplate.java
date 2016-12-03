package com.rongfeng.speedclient.components;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * AUTHOR: Alex
 * DATE: 11/11/2015 00:22
 */
public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Thread t;
    private boolean isRunning;


    public SurfaceViewTemplate(Context context) {
        super(context, null);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);


        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        t = new Thread(this);
        t.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        isRunning = false;
    }

    @Override
    public void run() {

        while (isRunning) {
            draw();
        }

    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {

            }
        } catch (Exception e) {

        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }
}
