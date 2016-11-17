package com.rongfeng.speedclient.common.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * 防止多次点击
 * AUTHOR: Alex
 * DATE: 12/10/2016 11:34
 */

public class SingleClickBt extends Button {

    private int TIME_GAP = 3000;
    private long last_time = 0L;


    public SingleClickBt(Context context) {
        super(context);
    }

    public SingleClickBt(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleClickBt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                long current_time = System.currentTimeMillis();
                long d_time = current_time - last_time;
                if (d_time < TIME_GAP) {
                    last_time = current_time;
                    return true;
                } else {
                    last_time = current_time;

                }
                break;
        }
        return super.onTouchEvent(event);
    }


}
