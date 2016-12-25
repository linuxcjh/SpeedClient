package com.rongfeng.speedclient.components;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.text.DecimalFormat;

/**
 * AUTHOR: Alex
 * DATE: 5/1/2016 17:01
 */
public class ClientSalesProgressTextSurface extends View {


    private PaintFlagsDrawFilter pfd;
    private int height;//高
    private int heightDr;//高

    private int progress;
    private float valueProgress;
    private int outCircleRadius = dip2px(getContext(), 7);
    private int insideCircleRadius = dip2px(getContext(), 4);

    DecimalFormat format = new DecimalFormat("#####0.0");

    public ClientSalesProgressTextSurface(Context context) {
        this(context, null);
    }

    public ClientSalesProgressTextSurface(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClientSalesProgressTextSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        height = dip2px(context, 20);
        heightDr = dip2px(context, 40);
    }


    public void setValue(float valueProgress) {
        this.valueProgress = valueProgress;
        startAnimator();
        invalidate();
    }

    float ratio;
    float valueRatio;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        progress = (int) ((getWidth() - 4 * outCircleRadius) * valueProgress);
        if (progress > (getWidth() - 4 * outCircleRadius)) {
            progress = (getWidth() - 4 * outCircleRadius);
        }
        if (animator != null) {
            ratio = progress * (float) animator.getAnimatedValue();
            valueRatio = valueProgress * 100 * (float) animator.getAnimatedValue();

        }
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#EBEBEB"));
        canvas.setDrawFilter(pfd);
        canvas.drawRoundRect(new RectF(0, getHeight() / 2 - dip2px(getContext(), 4), getWidth() - 2 * outCircleRadius, getHeight() / 2 + dip2px(getContext(), 4)), dip2px(getContext(), 4), dip2px(getContext(), 4), paint);


        paint.setColor(Color.parseColor("#1ABEAC"));
        canvas.drawRoundRect(new RectF(0, getHeight() / 2 - dip2px(getContext(), 4), ratio, getHeight() / 2 + dip2px(getContext(), 4)), dip2px(getContext(), 4), dip2px(getContext(), 4), paint);

        canvas.drawCircle(ratio + outCircleRadius, getHeight() / 2, outCircleRadius, paint);

        Path mPath = new Path();
        paint.setColor(Color.parseColor("#1ABEAC"));
        paint.setStyle(Paint.Style.FILL);

        if (ratio > 0) {

            mPath.moveTo(ratio + outCircleRadius, getHeight() / 2 - outCircleRadius);
            mPath.lineTo(ratio + outCircleRadius, getHeight() / 2 + outCircleRadius);
            mPath.quadTo(ratio + outCircleRadius - dip2px(getContext(), 10), getHeight() / 2 + insideCircleRadius, ratio + outCircleRadius - dip2px(getContext(), 15), getHeight() / 2 + insideCircleRadius);
            mPath.lineTo(ratio + outCircleRadius - dip2px(getContext(), 15), getHeight() / 2 - insideCircleRadius);
            mPath.quadTo(ratio + outCircleRadius - dip2px(getContext(), 10), getHeight() / 2 - insideCircleRadius, ratio + outCircleRadius, getHeight() / 2 - outCircleRadius);
            canvas.drawPath(mPath, paint);
        }

        paint.setColor(Color.WHITE);
        canvas.drawCircle(ratio + outCircleRadius, getHeight() / 2, dip2px(getContext(), 4), paint);

        //绘制百分比
        if (valueRatio > 0) {

            paint.setColor(Color.parseColor("#1ABEAC"));
            paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
            if (valueRatio >= 100 && valueRatio < 999) {

                canvas.drawText(format.format(valueRatio) + "%", ratio + outCircleRadius - paint.measureText(format.format(valueRatio) + "%") / 2, getHeight() / 2 + dip2px(getContext(), 20), paint);

            } else if (valueRatio >= 999) {
                canvas.drawText("999.9+" + "%", ratio + outCircleRadius - paint.measureText("999.9+" + "%") / 2, getHeight() / 2 + dip2px(getContext(), 20), paint);

            } else {
                canvas.drawText(format.format(valueRatio) + "%", ratio + outCircleRadius - paint.measureText(format.format(valueRatio) + "%") / 3, getHeight() / 2 + dip2px(getContext(), 20), paint);

            }


        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec, heightDr);
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    ValueAnimator animator;

    /**
     * 动画效果
     */
    private void startAnimator() {
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ratio = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();

    }
}
