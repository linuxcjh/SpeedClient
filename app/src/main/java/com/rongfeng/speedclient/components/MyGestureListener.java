package com.rongfeng.speedclient.components;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;


/**
 *
 * @FILE : MyGestureListener.java
 *
 * @AUTHOR: 唐磊 CREAT AT 2014-9-10 上午下午5:26:25
 *
 * @NOTE :[手势监听类]
 *
 */
public class MyGestureListener extends SimpleOnGestureListener {
    private OnFlingListener mOnFlingListener;

    public OnFlingListener getOnFlingListener() {
        return mOnFlingListener;
    }

    public void setOnFlingListener(OnFlingListener mOnFlingListener) {
        this.mOnFlingListener = mOnFlingListener;
    }

    @Override
    public final boolean onFling(final MotionEvent e1, final MotionEvent e2,
                                 final float speedX, final float speedY) {
        if (mOnFlingListener == null) {
            return super.onFling(e1, e2, speedX, speedY);
        }
        float XFrom = e1.getX();
        float XTo = e2.getX();
        float YFrom = e1.getY();
        float YTo = e2.getY();
        // 左右滑动的X轴幅度大于100，并且X轴方向的速度大于100
        if (Math.abs(XFrom - XTo) > 100.0f && Math.abs(speedX) > 100.0f) {
            // X轴幅度大于Y轴的幅度
            if (Math.abs(XFrom - XTo) >= Math.abs(YFrom - YTo)) {
                if (XFrom > XTo) {
                    // 下一个
                    mOnFlingListener.flingToNext();
                } else {
                    // 上一个
                    mOnFlingListener.flingToPrevious();
                }
            }else  return false;

        } else {
            return false;
        }
        return true;
    }

    public interface OnFlingListener {
        void flingToNext();

        void flingToPrevious();
    }
}
