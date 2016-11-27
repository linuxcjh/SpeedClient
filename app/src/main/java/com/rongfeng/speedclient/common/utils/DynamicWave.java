
package com.rongfeng.speedclient.common.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

public class DynamicWave extends View {


    private static final int WAVE_PAINT_COLOR = 0x88C6E2FF;

    private static final float STRETCH_FACTOR_A = 0;
    private static final int OFFSET_Y = -200;

    private static final int TRANSLATE_X_SPEED_ONE = 25;

    private static final int TRANSLATE_X_SPEED_TWO = 8;
    private float mCycleFactorW;

    private int a = (int) STRETCH_FACTOR_A;
    
    private int mTotalWidth, mTotalHeight;
    private float[] mYPositions;
    private float[] mResetOneYPositions;
    private float[] mResetTwoYPositions;
    private int mXOffsetSpeedOne;
    private int mXOffsetSpeedTwo;
    private int mXOneOffset;
    private int mXTwoOffset;

    private Paint mWavePaint;
    private DrawFilter mDrawFilter;

    public DynamicWave(Context context, AttributeSet attrs) {
        super(context, attrs);

        mXOffsetSpeedOne = Utils.dipToPx(context, TRANSLATE_X_SPEED_ONE);
        mXOffsetSpeedTwo = Utils.dipToPx(context, TRANSLATE_X_SPEED_TWO);


        mWavePaint = new Paint();

        mWavePaint.setAntiAlias(true);

        mWavePaint.setStyle(Style.FILL);

        mWavePaint.setColor(WAVE_PAINT_COLOR);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    public void setFactor(int a){
    	this.a = a;
    	this.invalidate();
    }
    public int getFactor(){
    	return a;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.setDrawFilter(mDrawFilter);
        resetPositonY();
        for (int i = 0; i < mTotalWidth; i++) {
            canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] - 200, i,
                    mTotalHeight,
                    mWavePaint);
            canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - 200, i,
                    mTotalHeight,
                    mWavePaint);
        }

        mXOneOffset += mXOffsetSpeedOne;
        mXTwoOffset += mXOffsetSpeedTwo;

        if (mXOneOffset >= mTotalWidth) {
            mXOneOffset = 0;
        }
        if (mXTwoOffset > mTotalWidth) {
            mXTwoOffset = 0;
        }
        for (int i = 0; i < mTotalWidth; i++) {
            mYPositions[i] = (float) (a * Math.sin(mCycleFactorW * i) + OFFSET_Y);
        }

        postInvalidate();
    }

    private void resetPositonY() {
        int yOneInterval = mYPositions.length - mXOneOffset;
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0,
                yTwoInterval);
        System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mYPositions = new float[mTotalWidth];
        mResetOneYPositions = new float[mTotalWidth];
        mResetTwoYPositions = new float[mTotalWidth];
        mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);
        for (int i = 0; i < mTotalWidth; i++) {
            mYPositions[i] = (float) (a * Math.sin(mCycleFactorW * i) + OFFSET_Y);
        }
    }

}
