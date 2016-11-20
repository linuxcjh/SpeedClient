package com.rongfeng.speedclient.components;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 滑动删除
 */
public class SwipeView extends FrameLayout {


    private boolean isEnable = true;//控制打开、关闭是否可用

    public SwipeView(Context context) {
        super(context);
        init();
    }

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private View menuView, contentView;
    int menuViewWidth;

    private void init() {

        mViewDragHelper = ViewDragHelper.create(this, callback);
    }

    /**
     * ViewDragHelper类封装了对触摸位置、速度、距离的检测，以及Scroller.
     * 需要我们制定什么时候滑动，以及滑动多少。
     * 需要把ViewGroup中受到的触摸事件传给ViewDragHelper实例
     */
    private ViewDragHelper mViewDragHelper;
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        /**
         * @return 返回true表示获得view的控制权
         */
        @Override
        public boolean tryCaptureView(View view, int i) {
            if (isEnable) {
                return view == contentView;
            }
            return false;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        /**
         * 控制view在水平方向上实际滑动了多少
         * @param child 当前触摸的view
         * @param left view的左边坐标，负数表示view的左边超出父view边界的长度
         * @param dx
         * @return 返回多少，代表想让child的left=多少
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        //        /**
//         * 水平方向拖拽的范围
//         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return menuViewWidth;
        }

        /**
         * view滑动后的回调
         * @param changedView
         * @param left
         * @param top
         * @param dx   x轴方向的改编值
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            //状态发生改变
            if (contentView.getLeft() == 0 && mStatus != SwipeStatus.Close) {
                mStatus = SwipeStatus.Close;
                if (mOnSwipeStatusChangeListener != null) {
                    mOnSwipeStatusChangeListener.onClose(SwipeView.this);
                }
            } else if (contentView.getLeft() == -menuViewWidth && mStatus != SwipeStatus.Open) {
                mStatus = SwipeStatus.Open;
                if (mOnSwipeStatusChangeListener != null) {
                    mOnSwipeStatusChangeListener.onOpen(SwipeView.this);
                }
            } else if (mStatus != SwipeStatus.Swiping) {
                mStatus = SwipeStatus.Swiping;
                if (mOnSwipeStatusChangeListener != null) {
                    mOnSwipeStatusChangeListener.onSwiping(SwipeView.this);
                }
            }


        }

        /**
         * TouchUp的回调
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);


            if (contentView.getLeft() < -menuViewWidth / 2) {   //滑动条打开
                open();
            } else {          //滑动条关闭
                close();
            }

        }
    };


    /**
     * 是否禁止打开、关闭
     * @param enable
     */
    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    /**
     * 判断是否打开状态
     *
     * @return
     */
    public boolean isOpen() {

        return mStatus == SwipeStatus.Open ? true : false;

    }

    /**
     * 打开滑动条
     */
    public void open() {
        mViewDragHelper.smoothSlideViewTo(contentView, -menuViewWidth, 0);
        ViewCompat.postInvalidateOnAnimation(SwipeView.this);  //动画刷新
    }

    /**
     * 关闭滑动条
     */
    public void close() {
        mViewDragHelper.smoothSlideViewTo(contentView, 0, 0);
        ViewCompat.postInvalidateOnAnimation(SwipeView.this);  //动画刷新
    }


    /**
     * Scroller帮助计算好view在某个时间点会处于某个位置，达到动画的效果
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {     //内部有Scroller计算位置和移动
            ViewCompat.postInvalidateOnAnimation(SwipeView.this);       //刷新当前view
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    private int lastX, lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - lastX;
                int dy = y - lastY;
                if (Math.abs(dx) > Math.abs(dy)) {          //想要横向滑
                    requestDisallowInterceptTouchEvent(true); //拦截该事件，不让listview处理
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        lastX = x;
        lastY = y;

        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 从xml中加载完布局，只知道有几个子view，并没有进行测量
     * 一般可以初始化子view的引用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);
        contentView = getChildAt(1);


    }

    /**
     * 测量完子view后调用，在这里可以直接获取子view的高度
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        menuViewWidth = menuView.getMeasuredWidth();
    }


    //当前的状态
    private SwipeStatus mStatus;

    enum SwipeStatus {
        Open, Close, Swiping;
    }

    public void removeSwipeStatusChangeListener() {
        mOnSwipeStatusChangeListener = null;
    }

    private OnSwipeStatusChangeListener mOnSwipeStatusChangeListener;

    public interface OnSwipeStatusChangeListener {
        void onClose(SwipeView swipeView);

        void onOpen(SwipeView swipeView);

        void onSwiping(SwipeView swipeView);
    }

    //暴露给外部：状态发生改变
    public void setOnSwipeStatusChangeListener(OnSwipeStatusChangeListener onSwipeStatusChangeListener) {
        mOnSwipeStatusChangeListener = onSwipeStatusChangeListener;
    }
}
