package com.rongfeng.speedclient.utils.calendar;


import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Created by 唐磊 on 2015/11/2.
 * 弹簧控件
 */

public class JudgeChildScrollTop implements AbsListView.OnScrollListener, View.OnTouchListener {

    private ListView listView;
    private ScrollView scrollView;
    private Handler handler;

    public JudgeChildScrollTop(View view, Handler handler) {
        this.handler = handler;
        init(view);
    }

    private void init(View view) {

        if (view instanceof ListView) {
            this.listView = (ListView) view;
            listView.setOnScrollListener(this);
        }

        if (view instanceof ScrollView) {
            this.scrollView = (ScrollView) view;
            scrollView.setOnTouchListener(this);
        }
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            // 当不滚动时
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
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
            // 判断滚动到顶部
            if (listView.getFirstVisiblePosition() == 0 && t == vt) {
                handler.sendMessage(handler.obtainMessage(0, true));
            } else
                handler.sendMessage(handler.obtainMessage(0, false));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int scrollY = scrollView.getScrollY();
                if (scrollY == 0) {
                    handler.sendMessage(handler.obtainMessage(0, true));
                } else
                    handler.sendMessage(handler.obtainMessage(0, false));
                break;
        }
        return false;
    }

}

