package com.rongfeng.speedclient.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.rongfeng.speedclient.R;

/**
 * 雷达图
 */
public class RadarChartView extends View {


    private int count = 5;                //数据个数
    private float angle = (float) (Math.PI * 2 / count);    //五边形角度
    private float radius;                   //网格最大半径
    private int centerX;                  //中心X
    private int centerY;                  //中心Y
    private double[] data = {0, 0, 0, 0, 0}; //各维度分值
    private float maxValue = 5;             //数据最大值
    private Paint mainPaint;
    //网格区画笔
    private Paint valuePaint;               //数据区画笔
    private Paint innerCirclePaint;//内圆
    private Paint coverPaint;               //覆盖区域画笔



    private float circleRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.6f, getResources().getDisplayMetrics());

    private float innerCircleRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, getResources().getDisplayMetrics());


    public RadarChartView(Context context) {
        super(context);
        init();
    }

    public RadarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RadarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }


    private void init() {
        mainPaint = new Paint();
        mainPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAssistLight));
        mainPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.8f, getResources().getDisplayMetrics()));
        mainPaint.setAntiAlias(true);
        mainPaint.setStyle(Paint.Style.STROKE);


        valuePaint = new Paint();
        valuePaint.setColor(ContextCompat.getColor(getContext(), R.color.colorBlue));
        valuePaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        valuePaint.setAntiAlias(true);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.WHITE);
        innerCirclePaint.setAntiAlias(true);
        innerCirclePaint.setStyle(Paint.Style.FILL);


        coverPaint = new Paint();
        coverPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorBlue));
        coverPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        coverPaint.setAntiAlias(true);
        coverPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        coverPaint.setAlpha(66);

    }

    /**
     * 添加值
     *
     * @param data
     */
    public void setValue(double[] data) {

        this.data = data;
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawRect();

        radius = Math.min(getHeight(), getWidth()) / 2 * 0.7f;
        centerX = getHeight() / 2;
        centerY = getWidth() / 2;

        drawPolygon(canvas);
        drawLines(canvas);

//      drawText(canvas);
        drawRegion(canvas);
    }


//    2、绘制正五边形

    /**
     * 绘制正多边形
     */
    Path path = new Path();

    private void drawPolygon(Canvas canvas) {
        path.reset();

        float r = radius / (count - 1);//r是蜘蛛丝之间的间距
        for (int i = 1; i <= count; i++) {//中心点不用绘制
            float curR = r * i;//当前半径
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    float x = (float) (centerX + curR * Math.sin(angle));
                    float y = (float) (centerY - curR * Math.cos(angle));
                    path.moveTo(x, y);
                } else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x1 = (float) (centerX + curR * Math.sin(angle / 2));
                    float y1 = (float) (centerY + curR * Math.cos(angle / 2));
                    path.lineTo(x1, y1);
                    float x2 = (float) (centerX - curR * Math.sin(angle / 2));
                    float y2 = (float) (centerY + curR * Math.cos(angle / 2));
                    path.lineTo(x2, y2);
                    float x3 = (float) (centerX - curR * Math.sin(angle));
                    float y3 = (float) (centerY - curR * Math.cos(angle));
                    path.lineTo(x3, y3);
                    float x4 = (float) (centerX);
                    float y4 = (float) (centerY - curR);
                    path.lineTo(x4, y4);
                    float x = (float) (centerX + curR * Math.sin(angle));
                    float y = (float) (centerY - curR * Math.cos(angle));
                    path.lineTo(x, y);
                }
            }
            path.close();//闭合路径
            canvas.drawPath(path, mainPaint);
        }
    }

//    3、绘制从中心到末端的直线
//    同样根据半径，计算出每个末端坐标

    /**
     * 绘制直线
     */
    private void drawLines(Canvas canvas) {
//        Path path = new Path();
        path.reset();
        float r = radius / (count - 1);//r是蜘蛛丝之间的间距
        float curR = r * 5;//当前半径
        float x = (float) (centerX + curR * Math.sin(angle));
        float y = (float) (centerY - curR * Math.cos(angle));
        path.moveTo(x, y);
        path.lineTo(centerX, centerY);
        float x1 = (float) (centerX + curR * Math.sin(angle / 2));
        float y1 = (float) (centerY + curR * Math.cos(angle / 2));
        path.moveTo(x1, y1);
        path.lineTo(centerX, centerY);
        float x2 = (float) (centerX - curR * Math.sin(angle / 2));
        float y2 = (float) (centerY + curR * Math.cos(angle / 2));
        path.moveTo(x2, y2);
        path.lineTo(centerX, centerY);
        float x3 = (float) (centerX - curR * Math.sin(angle));
        float y3 = (float) (centerY - curR * Math.cos(angle));
        path.moveTo(x3, y3);
        path.lineTo(centerX, centerY);
        float x4 = (float) (centerX);
        float y4 = (float) (centerY - curR);
        path.moveTo(x4, y4);
        path.lineTo(centerX, centerY);
        path.close();
        canvas.drawPath(path, mainPaint);
    }
    /**
     * 绘制文字
     */
//    private void drawText(Canvas canvas) {
//        float r = radius / (count - 1);//r是蜘蛛丝之间的间距
//        float curR = r * 5;//当前半径
//        float x = (float) (centerX + curR * Math.sin(angle));
//        float y = (float) (centerY - curR * Math.cos(angle));
//
//        canvas.drawText(titles[0], x, y, textPaint);
//
//        float x1 = (float) (centerX + curR * Math.sin(angle / 2));
//        float y1 = (float) (centerY + curR * Math.cos(angle / 2));
//        canvas.drawText(titles[1], x1, y1, textPaint);
//
//        float x2 = (float) (centerX - curR * Math.sin(angle / 2));
//        float y2 = (float) (centerY + curR * Math.cos(angle / 2));
//        canvas.drawText(titles[2], x2, y2, textPaint);
//
//        float x3 = (float) (centerX - curR * Math.sin(angle));
//        float y3 = (float) (centerY - curR * Math.cos(angle));
//
//        canvas.drawText(titles[3], x3, y3, textPaint);
//
//
//        float x4 = (float) (centerX);
//        float y4 = (float) (centerY - curR);
//
//        canvas.drawText(titles[4], x4, y4, textPaint);
//
//
//    }


    /**
     * 绘制区域
     *
     * @param canvas
     */
    private void drawRegion(Canvas canvas) {
//        Path path = new Path();
        path.reset();

        valuePaint.setAlpha(230);
        float r = radius / (count - 1);//r是蜘蛛丝之间的间距
        double percent1;
        if (data[0] != maxValue) {
            percent1 = data[0] % maxValue;
        } else {
            percent1 = maxValue;
        }
        float x1 = (float) (centerX + r * percent1 * Math.sin(angle));
        float y1 = (float) (centerY - r * percent1 * Math.cos(angle));
//绘制小圆点
        path.moveTo(x1, y1);


        double percent2;
        if (data[1] != maxValue) {
            percent2 = data[1] % maxValue;
        } else {
            percent2 = maxValue;
        }
        float x2 = (float) (centerX + r * percent2 * Math.sin(angle / 2));
        float y2 = (float) (centerY + r * percent2 * Math.cos(angle / 2));
//绘制小圆点
        path.lineTo(x2, y2);


        double percent3;
        if (data[2] != maxValue) {
            percent3 = data[2] % maxValue;
        } else {
            percent3 = maxValue;
        }
        float x3 = (float) (centerX - r * percent3 * Math.sin(angle / 2));
        float y3 = (float) (centerY + r * percent3 * Math.cos(angle / 2));
//绘制小圆点
        path.lineTo(x3, y3);

        double percent4;
        if (data[3] != maxValue) {
            percent4 = data[3] % maxValue;
        } else {
            percent4 = maxValue;
        }
        float x4 = (float) (centerX - r * percent4 * Math.sin(angle));
        float y4 = (float) (centerY - r * percent4 * Math.cos(angle));
//绘制小圆点
        path.lineTo(x4, y4);

        double percent5;
        if (data[4] != maxValue) {
            percent5 = data[4] % maxValue;
        } else {
            percent5 = maxValue;
        }
        float x5 = (float) (centerX);
        float y5 = (float) (centerY - r * percent5);
//绘制小圆点
        path.lineTo(x5, y5);
        path.lineTo(x1, y1);
        path.close();

        //绘制填充区域
        canvas.drawPath(path, coverPaint);

        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);


        canvas.drawCircle(x1, y1, circleRadius, valuePaint);
        canvas.drawCircle(x1, y1, innerCircleRadius, innerCirclePaint);

        canvas.drawCircle(x2, y2, circleRadius, valuePaint);
        canvas.drawCircle(x2, y2, innerCircleRadius, innerCirclePaint);

        canvas.drawCircle(x3, y3, circleRadius, valuePaint);
        canvas.drawCircle(x3, y3, innerCircleRadius, innerCirclePaint);

        canvas.drawCircle(x4, y4, circleRadius, valuePaint);
        canvas.drawCircle(x4, y4, innerCircleRadius, innerCirclePaint);

        canvas.drawCircle(x5, y5, circleRadius, valuePaint);
        canvas.drawCircle(x5, y5, innerCircleRadius, innerCirclePaint);


    }
}

