package com.rongfeng.speedclient.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;


/**
 * Created by 唐磊 on 2015/11/2.
 * 弹簧控件
 */

public class SpringView extends LinearLayout implements AbsListView.OnScrollListener {

    private Context context;
    private int mHeaderHeight;
    protected boolean isTop = true;
    protected boolean isBottom;
    protected boolean isIntercept = false;
    protected boolean isFrist = true;
    private ListView listView;
    private View view;
    int by;

    public SpringView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);
        // 获取屏幕高度
        int mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
        // header 的高度为屏幕高度的
        mHeaderHeight = mScreenHeight / 3;
    }

    @SuppressLint("NewApi")
    private void initContentView() {
        view = getChildAt(0);
        if (view instanceof ListView) {
            this.listView = (ListView) view;
            listView.setOnScrollListener(this);
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && isFrist) {
            initContentView();
            isFrist = false;
        }
    }


    float sY, sX;
    boolean isStrat = true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sY = event.getRawY();
                sX = event.getRawX();
            case MotionEvent.ACTION_MOVE:
                float mY = event.getRawY();
                float mX = event.getRawX();
                if (isTop && isStrat) {
                    sY = mY;
                    sX = mX;
                    isStrat = false;
                }

                if (isBottom && isStrat) {
                    sY = mY;
                    sX = mX;
                    isStrat = false;
                }

                float msY = mY - sY;
                float msX = mX - sX;
                if (Math.abs(msY) > Math.abs(msX) && Math.abs(msY) > 10) {
                    //下拉
                    if (msY > 0 && isTop) {
                        pulling(msY);
                        isIntercept = true;
                    }
                    //上拉
                    if (msY < 0 && isBottom) {
                        pulling(msY);
                        isIntercept = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isIntercept) {
                    int scrollY = SpringView.this.getScrollY();
                    /* 求等差数列末项 ， 首相1 末项为by 公差为1 等差数列和 scrollY*/
                    by= (int) ((Math.sqrt(8*Math.abs(scrollY)+1)-1)/2);
                    handler.sendEmptyMessage(0);
                    isStrat = true;
                    isIntercept = false;
                    return true;
                }
                break;
        }
        if (!isIntercept) {
            super.dispatchTouchEvent(event);
        }
        return true;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isIntercept;
    }

    /**
     * 正在上下拉
     */
    private void pulling(float moveY) {
        this.scrollTo(0, (int) -moveY / 2);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            // 当不滚动时
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                // 判断滚动到底部
                if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
                    View vBottom = view.getChildAt(listView.getChildCount() - 1);
                    int h = vBottom.getBottom();
                    int h1 = view.getBottom();
                    if (h <= h1) {
                        isBottom = true;
                    } else {
                        isBottom = false;
                    }
                }
                break;
        }
    }

    int t, vt;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        try {
            View vtop = view.getChildAt(0);
            t = vtop.getTop();
            vt = view.getPaddingTop();

            if (isIntercept) {
                return;
            }
            // 判断滚动到顶部
            if (listView.getFirstVisiblePosition() == 0 && t == vt) {
                isTop = true;
                isStrat = true;
            } else
                isTop = false;

            if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                View vBottom = view.getChildAt(visibleItemCount - 1);
                int h = vBottom.getBottom();
                int h1 = view.getBottom();
                if (h <= h1) {
                    isBottom = true;
                    isStrat = true;
                } else {
                    isBottom = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int scrollY = SpringView.this.getScrollY();
                    if( by<=1){
                       by=1;
                    }
                    if (scrollY > 0) {
                        SpringView.this.scrollBy(0, -by);
                    } else if (scrollY < 0) {
                        SpringView.this.scrollBy(0, by);
                    } else if (scrollY == 0) {
                        break;
                    }
                    by--;
                    sendEmptyMessageDelayed(0,5);
                    break;

            }
        }
    };
}

