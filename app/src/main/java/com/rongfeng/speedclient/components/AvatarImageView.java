package com.rongfeng.speedclient.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.rongfeng.speedclient.R;


/**
 * AUTHOR: Alex
 * DATE: 19/12/2015 10:24
 */
public class AvatarImageView extends ImageView {

    private PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    /**
     * 半径
     */
    private float radius;
    private String contentText;
    private float textSize;
    private int textColor;
    private int contentBackColor;

    public AvatarImageView(Context context) {
        this(context, null);
    }

    public AvatarImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public AvatarImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageView);
        radius = typedArray.getDimension(R.styleable.AvatarImageView_radius, 0);
        textSize = typedArray.getDimension(R.styleable.AvatarImageView_textSize, 0);
        contentText = typedArray.getString(R.styleable.AvatarImageView_text);
        textColor = typedArray.getColor(R.styleable.AvatarImageView_textColor, Color.WHITE);
        contentBackColor = typedArray.getColor(R.styleable.AvatarImageView_contentBackColor, Color.GRAY);
        typedArray.recycle();
    }

    /**
     * 显示内容
     *
     * @param contentText
     */
    public AvatarImageView setContentText(String contentText) {
        this.contentText = contentText;
        invalidate();
        return this;
    }

    /**
     * 字体大小
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    /**
     * 设置半径
     *
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setContentBackColor(int contentBackColor) {
        this.contentBackColor = contentBackColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        canvas.setDrawFilter(pdf);
        if (!TextUtils.isEmpty(contentText)) {
            Paint paint = new Paint();
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setColor(contentBackColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(radius / 2, radius / 2, radius / 2, paint);

            Paint tvPaint = new Paint(paint);
            tvPaint.setColor(textColor);
            tvPaint.setTextSize(textSize);
            canvas.drawText(contentText, (radius - getStringWidth(contentText, tvPaint)) / 2, (radius - getStringHeight(tvPaint)) / 2, tvPaint);
        }
        super.onDraw(canvas);
    }

    /**
     * 位子宽度
     *
     * @param str
     * @param paint
     * @return
     */
    private int getStringWidth(String str, Paint paint) {
        return (int) paint.measureText(str);
    }

    /**
     * 文字高度
     *
     * @param paint
     * @return
     */
    private int getStringHeight(Paint paint) {
        return (int) (paint.ascent() + paint.descent());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension((int) radius, (int) radius);
    }
}
