package com.rongfeng.speedclient.components;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ViewFlipper;

import com.rongfeng.speedclient.R;


/**
 * @FILE : MyViewFlipper.java
 * @AUTHOR: 唐磊 CREAT AT 2014-9-10 上午下午5:25:31
 * @NOTE :[自定义ViewFlipper]
 */
public class MyViewFlipper extends ViewFlipper implements MyGestureListener.OnFlingListener {
    public MyViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private GestureDetector mGestureDetector = null;
    private OnViewFlipperListener mOnViewFlipperListener = null;
    private float sX, sY, eX, eY;

    public MyViewFlipper(Context context) {
        super(context);
    }

    public void setOnViewFlipperListener(
            OnViewFlipperListener mOnViewFlipperListener) {
        this.mOnViewFlipperListener = mOnViewFlipperListener;
        MyGestureListener myGestureListener = new MyGestureListener();
        myGestureListener.setOnFlingListener(this);
        mGestureDetector = new GestureDetector(myGestureListener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sX = event.getX();
                sY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                eX = event.getX();
                eY = event.getY();
                if (Math.abs(eX - sX) > Math.abs(eY - sY) && Math.abs(eX - sX) > 10) { // 判断是否左右滑动 是就拦截事件
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(event);
    }

    // 返回true 说明所有事件都要调onInterceptTouchEvent方法
    // 返回false 只有Down事件调onInterceptTouchEvent方法 后续事件不会调用onInterceptTouchEvent方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void flingToNext() {
        if (null != mOnViewFlipperListener) {
            final int childCnt = getChildCount();
            if (childCnt == 2) {
                removeViewAt(1);
            }
            final View view = mOnViewFlipperListener.getNextView();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (view != null) {
                        addView(view, 0);
                        if (0 != childCnt) {
                            setInAnimation(getContext(), R.anim.right_in);
                            setOutAnimation(getContext(), R.anim.right_out);
                            getInAnimation().setAnimationListener(mOnViewFlipperListener);
                            setDisplayedChild(0);
                        }
                    }
                }
            },50);


        }
    }

    @Override
    public void flingToPrevious() {
        if (null != mOnViewFlipperListener) {
            final int childCnt = getChildCount();

            if (childCnt == 2) {
                removeViewAt(1);
            }
            final View view = mOnViewFlipperListener.getPreviousView();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (view != null) {
                        addView(view, 0);
                        if (0 != childCnt) {
                            setInAnimation(getContext(), R.anim.left_in);
                            setOutAnimation(getContext(), R.anim.left_out);
                            getInAnimation().setAnimationListener(mOnViewFlipperListener);
                            setDisplayedChild(0);
                        }
                    }
                }
            },50);
        }
    }


    public GestureDetector getmGestureDetector() {
        return mGestureDetector;
    }

    public void setmGestureDetector(GestureDetector mGestureDetector) {
        this.mGestureDetector = mGestureDetector;
    }


    public interface OnViewFlipperListener extends Animation.AnimationListener{
        View getNextView();

        View getPreviousView();
    }
}
