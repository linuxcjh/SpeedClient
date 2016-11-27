package com.rongfeng.speedclient.common.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 绘制正弦曲线
 */
public class SinCurveLineCustom extends View {

    Paint paint;
    int x = 0;
    int a = 100;

    public SinCurveLineCustom(Context context) {
        super(context);
        init();
    }

    public SinCurveLineCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SinCurveLineCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);
    }

    public void setValue(int value) {
        this.a = value;
        x = 0;
        invalidate();
    }

    /**
     * 角度转换成弧度
     *
     * @param degree
     * @return
     */
    private double degreeToRad(double degree) {
        return degree * Math.PI / 180;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int width = getWidth();
        int height = getHeight();
        final int centerY = height / 2;
        while (x < width) {
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(5);
            double rad = degreeToRad(x);//角度转换成弧度
            int y = (int) (centerY - a * Math.sin(rad));
            canvas.drawPoint(x, y, paint);
            x++;
        }
    }
}