package com.rongfeng.speedclient.components;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.rongfeng.speedclient.R;


/**
 * CRM 销售业绩柱状图
 * AUTHOR: Alex
 * DATE: 5/1/2016 17:01
 */
public class ClientSalesPerView extends View {


    private PaintFlagsDrawFilter pfd;

    private int width;//宽、高
    private int pWidth;
    private int rectWith;
    Context context;

    private int offSet;

    String[] str = {"目标", "预测", "合同", "回款"};
    float[] value = {0, 0, 0, 0};


    public ClientSalesPerView(Context context) {
        this(context, null);
    }

    public ClientSalesPerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClientSalesPerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        width = dip2px(context, 164);
        pWidth = dip2px(context, 136);
        offSet = dip2px(context, 9);
        rectWith = dip2px(context, 23);
        for (int i = 0; i < value.length; i++) {
            value[i] = pWidth;
        }
    }


    public void setValue(float[] values) {


        float max = 0f;
        int j = 0;
        for (int i = 0; i < values.length; i++) { //找出最大的值

            if (values[i] > max) {
                max = values[i];
                j = i;
            }
        }

        if (max != 0) {
            for (int i = 0; i < values.length; i++) { //计算各个柱状图所占的比例

                if (i == j) {

                    value[i] = pWidth - 1 * pWidth;//设置最大的值为满格
                } else {
                    value[i] = pWidth - (values[i] / max) * pWidth;

                }

            }
        } else {
            for (int i = 0; i < value.length; i++) {
                value[i] = pWidth;
            }
        }

        startAnimator();
//        invalidate();
    }

    float ratio;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        canvas.setDrawFilter(pfd);

        paint.setColor(ContextCompat.getColor(context, R.color.colorDividerLine));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(dip2px(context, 1));
        canvas.drawLine(0, pWidth, width, pWidth, paint);


        canvas.drawRoundRect(new RectF(offSet, 0, offSet + rectWith, pWidth), dip2px(getContext(), 1), dip2px(getContext(), 1), paint);

        canvas.drawRoundRect(new RectF(3 * offSet + rectWith, 0, 3 * offSet + 2 * rectWith, pWidth), dip2px(getContext(), 1), dip2px(getContext(), 1), paint);

        canvas.drawRoundRect(new RectF(5 * offSet + 2 * rectWith, 0, 5 * offSet + 3 * rectWith, pWidth), dip2px(getContext(), 1), dip2px(getContext(), 1), paint);

        canvas.drawRoundRect(new RectF(7 * offSet + 3 * rectWith, 0, 7 * offSet + 4 * rectWith, pWidth), dip2px(getContext(), 1), dip2px(getContext(), 1), paint);


        if (animator != null) {
            ratio = (pWidth - value[0]) * (float) animator.getAnimatedValue();
            paint.setColor(ContextCompat.getColor(context, R.color.colorGreen));
            canvas.drawRoundRect(new RectF(offSet, pWidth - ratio, offSet + rectWith, pWidth), dip2px(getContext(), 1), dip2px(getContext(), 1), paint);

            ratio = (pWidth - value[1]) * (float) animator.getAnimatedValue();
            paint.setColor(ContextCompat.getColor(context, R.color.colorBlueLight));
            canvas.drawRoundRect(new RectF(3 * offSet + rectWith, pWidth - ratio, 3 * offSet + 2 * rectWith, pWidth), dip2px(getContext(), 1), dip2px(getContext(), 1), paint);

            ratio = (pWidth - value[2]) * (float) animator.getAnimatedValue();
            paint.setColor(ContextCompat.getColor(context, R.color.colorIntroduce));
            canvas.drawRoundRect(new RectF(5 * offSet + 2 * rectWith, pWidth - ratio, 5 * offSet + 3 * rectWith, pWidth), dip2px(getContext(), 1), dip2px(getContext(), 1), paint);

            ratio = (pWidth - value[3]) * (float) animator.getAnimatedValue();
            paint.setColor(ContextCompat.getColor(context, R.color.colorOrange));
            canvas.drawRoundRect(new RectF(7 * offSet + 3 * rectWith, pWidth - ratio, 7 * offSet + 4 * rectWith, pWidth), dip2px(getContext(), 1), dip2px(getContext(), 1), paint);

        }
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, context.getResources().getDisplayMetrics()));
        paint.setColor(ContextCompat.getColor(context, R.color.colorAssistLight));
        canvas.drawText(str[0], offSet + (rectWith - paint.measureText(str[0])) / 2, pWidth + dip2px(context, 14), paint);
        canvas.drawText(str[1], 3 * offSet + rectWith + (rectWith - paint.measureText(str[1])) / 2, pWidth + dip2px(context, 14), paint);
        canvas.drawText(str[2], 5 * offSet + 2 * rectWith + (rectWith - paint.measureText(str[2])) / 2, pWidth + dip2px(context, 14), paint);
        canvas.drawText(str[3], 7 * offSet + 3 * rectWith + (rectWith - paint.measureText(str[3])) / 2, pWidth + dip2px(context, 14), paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, width);
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
