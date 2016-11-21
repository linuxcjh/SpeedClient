package com.rongfeng.speedclient.xrecyclerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.support.v7.widget.RecyclerView;



/**
 * /**
 * 分隔线装饰
 *
 * @author youmingdot
 */
public class DividerLine extends RecyclerView.ItemDecoration {
    /**
     * 水平方向
     */
    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    /**
     * 垂直方向
     */
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    // 画笔
    private Paint paint;

    // 布局方向
    private int orientation;
    // 分割线颜色
    private int color;
    // 分割线尺寸
    private int size;
    // 左右item偏移线
    private int leftRightOffsets;

    public DividerLine() {
        this(VERTICAL);
    }

    public DividerLine(int orientation) {
        this.orientation = orientation;

        paint = new Paint();
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (color != 0) {
            if (orientation == VERTICAL) {
                drawHorizontal(c, parent);
            } else {
                drawVertical(c, parent);
            }
        }
    }

    /**
     * 设置分割线颜色
     *
     * @param color 颜色
     */
    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
    }

    /**
     * 设置分割线尺寸
     *
     * @param size 尺寸
     */
    public void setSize(int size) {
        this.size = size;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view)!= 0) {
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.set(leftRightOffsets, 0, leftRightOffsets, size);
            } else {
                outRect.set(0, 0, size, 0);
            }
        }
    }

    // 绘制垂直分割线
    protected void drawVertical(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + size;

            c.drawRect(left, top, right, bottom, paint);
        }
    }

    // 绘制水平分割线
    protected void drawHorizontal(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + leftRightOffsets;
        final int right = parent.getWidth() - parent.getPaddingRight() - leftRightOffsets;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + size;
            c.drawRect(left, top, right, bottom, paint);
        }
    }


    public void setLeftRightOffsets(int leftRightOffsets) {
        this.leftRightOffsets = leftRightOffsets;
    }
}

