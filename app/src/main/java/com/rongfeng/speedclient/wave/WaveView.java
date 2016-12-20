package com.rongfeng.speedclient.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.AppConfig;
import com.rongfeng.speedclient.common.utils.Utils;

public class WaveView extends RenderView {

    private static final String TAG = "WaveView";

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*绘图*/

    private final Paint paint = new Paint();

    {
        paint.setDither(true);
        paint.setAntiAlias(true);
    }

    private final Path firstPath = new Path();
    private final Path secondPath = new Path();
    /**
     * 两条正弦波之间的波，振幅比较低的那一条
     */
    private final Path centerPath = new Path();

    /**
     * 采样点的数量，越高越精细，
     * 但高于一定限度后人眼察觉不出。
     */
    private static final int SAMPLING_SIZE = 64;
    /**
     * 采样点的X
     */
    private float[] samplingX;
    /**
     * 采样点位置均匀映射到[-2,2]的X
     */
    private float[] mapX;

    /**
     * 画布宽高
     */
    private int width, height;
    /**
     * 画布中心的高度
     */
    private int centerHeight;
    /**
     * 振幅
     */
    private int amplitude = Utils.dip2px(AppConfig.getContext(), 6);

    /**
     * 控制速度
     */
    private float speedOffset;
    private float speedOffsetValue = 300F;


    private final int backGroundColor = ContextCompat.getColor(AppConfig.getContext(), R.color.colorBlue);
    private final int centerPathColor = Color.argb(64, 255, 255, 255);


    /**
     * 速度值
     *
     * @param speedOffsetValue
     */
    public void setSpeedOffsetValue(float speedOffsetValue) {
        this.speedOffsetValue = speedOffsetValue;
    }

    /**
     * 实际振幅的控制
     *
     * @param amplitude
     */
    public void setAmplitudeValue(int amplitude) {
        this.amplitude = Utils.dip2px(AppConfig.getContext(), 6) + amplitude;
//        Log.d("wave", "amplitude: " + amplitude + "");
//        Log.d("wave","amplitude: "+this.amplitude+"");
//        Log.d("wave","height: "+height+"");


    }


    @Override
    protected void onRender(Canvas canvas, long millisPassed) {
        if (samplingX == null) {//首次初始化
            //赋值基本参数
            width = canvas.getWidth();
            height = canvas.getHeight();
            centerHeight = height >> 1;
//            amplitude = width >> 3;//振幅为宽度的1/8
//            amplitude = height * 1 / 3;//振幅为宽度的1/8

            //初始化采样点和映射
            samplingX = new float[SAMPLING_SIZE + 1];//因为包括起点和终点所以需要+1个位置
            mapX = new float[SAMPLING_SIZE + 1];//同上
            float gap = width / (float) SAMPLING_SIZE;//确定采样点之间的间距
            float x;
            for (int i = 0; i <= SAMPLING_SIZE; i++) {
                x = i * gap;
                samplingX[i] = x;
                mapX[i] = (x / (float) width) * 4 - 2;//将x映射到[-2,2]的区间上
            }
        }

        //绘制背景
        canvas.drawColor(backGroundColor);

        //重置所有path并移动到起点
        firstPath.rewind();
        secondPath.rewind();
        centerPath.rewind();
        firstPath.moveTo(0, centerHeight);
        secondPath.moveTo(0, centerHeight);
        centerPath.moveTo(0, centerHeight);

        //当前时间的偏移量，通过该偏移量使得每次绘图都向右偏移，让画面动起来
        //如果希望速度快一点，可以调小分母
        speedOffset = millisPassed / speedOffsetValue;

        //提前申明各种临时参数
        float x;

        //波形函数的值，包括上一点，当前点和下一点
        float curV = 0, nextV = (float) (amplitude * calcValue(mapX[0], speedOffset));

        //遍历所有采样点
        for (int i = 0; i <= SAMPLING_SIZE; i++) {
            //计算采样点的位置
            x = samplingX[i];
            curV = nextV;
            //提前算出下一采样点的值
            nextV = i < SAMPLING_SIZE ? (float) (amplitude * calcValue(mapX[i + 1], speedOffset)) : 0;

            //连接路径
            firstPath.lineTo(x, centerHeight + curV);
            secondPath.lineTo(x, centerHeight - curV);
            //中间那条路径的振幅是上下的1/5
            centerPath.lineTo(x, centerHeight + curV / 5F);

        }
        //连接所有路径到终点
        firstPath.lineTo(width, centerHeight);
        secondPath.lineTo(width, centerHeight);
        centerPath.lineTo(width, centerHeight);


        //绘制下弦线
        paint.setColor(ContextCompat.getColor(AppConfig.getContext(), R.color.colorVoiceWaveBg));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dip2px(AppConfig.getContext(), 1));

        canvas.drawPath(secondPath, paint);

        //绘制中间线
        paint.setColor(centerPathColor);
        canvas.drawPath(centerPath, paint);
    }

    /**
     * 计算波形函数中x对应的y值
     *
     * @param mapX   换算到[-2,2]之间的x值
     * @param offset 偏移量
     * @return
     */
    private double calcValue(float mapX, float offset) {
        offset %= 2;
        double sinFunc = Math.sin(2 * Math.PI * mapX - offset * Math.PI);
        double recessionFunc = Math.pow(4 / (4 + Math.pow(mapX, 4)), 2.5);
        return sinFunc * recessionFunc;
    }
}
