package com.rongfeng.speedclient.utils.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.rongfeng.speedclient.common.utils.DateUtil;
import com.rongfeng.speedclient.components.MyViewFlipper;

import java.util.TimeZone;

/**
 * Created by 唐磊 on 2015/11/11.
 * 可以左右滑动的日历控件
 */
public class CalendarView extends MyViewFlipper implements MyViewFlipper.OnViewFlipperListener {
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mWay;
    /**
     * 当月
     */
    private int month;

    /**
     * 当年
     */
    private int year;
    /**
     * 上一个月1号周几
     */
    private int lastMonthFristDay;

    /**
     * 下一个月1号周几
     */
    private int nextMonthFristDay;
    public Calendar calendart;
    protected boolean isFrist = true;
    private Context context;
    private CalendarListener calendarListener;
    private TextView topTextView;


    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData();
    }

    public CalendarView(Context context) {
        super(context);
        this.context = context;
        initData();
    }

    public void initData() {
        setOnViewFlipperListener(this);
        StringData();
        calendart = new Calendar(this);
        calendart.setYMD(mYear, mMonth, mDay, getFristDayOfMoth(mDay,mWay));
        addView(calendart.onCreateView());
    }


    public void StringData() {
        final java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeZone(TimeZone.getDefault());
        year = mYear = c.get(java.util.Calendar.YEAR); // 获取当前年份
        month = mMonth = c.get(java.util.Calendar.MONTH) + 1;// 获取当前月份
        mDay = c.get(java.util.Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        mWay = c.get(java.util.Calendar.DAY_OF_WEEK);// 获取当前日期是周期

    }

    @Override
    public View getNextView() {
        // TODO Auto-generated method stub
        mMonth++;
        if (mMonth > 12) {
            mMonth = 1;
            mYear++;
        }
        calendart.setYMD(mYear, mMonth, (mMonth == month) && (mYear == year) ? mDay : 0, getNextMonthFristDay());
        View view = calendart.onCreateView();
        topTextView.setText(mYear+"年"+mMonth+"月");
        calendarListener.calendarFlipper();
        return view;
    }

    @Override
    public View getPreviousView() {
        // TODO Auto-generated method stub
        mMonth--;
        if (mMonth == 0) {
            mMonth = 12;
            mYear--;
        }
        calendart.setYMD(mYear, mMonth, (mMonth == month) && (mYear == year) ? mDay : 0, getLastMonthFristDay());
        View view = calendart.onCreateView();
        topTextView.setText(mYear + "年" + mMonth + "月");
        calendarListener.calendarFlipper();
        return view;
    }



    /**
     * @return 返回当月1号是星期几，1代表星期一 、2代表星期二、3代表星期三 、4代表星期四、5代表星期五 、6代表星期六、7代表星期天
     */
    private int getFristDayOfMoth(int mDay,int mWay) {
        /** 按 1代表星期一 、2代表星期二、3代表星期三 、4代表星期四、5代表星期五 、6代表星期六、7代表星期日 顺序显示日历 */
        int fd = 0;
        int x = mDay % 7;
        int y = mWay - x;
        if (y > 0) {
            fd = y;
        } else if (y < 0) {
            fd = y + 7;
        } else if (y == 0) {
            fd = 7;
        }
        /** 加以下代码变成按 1代表星期日、 2代表星期一 、3代表星期二、4代表星期三 、5代表星期四、6代表星期五 、7代表星期六  顺序显示日历 */
        if (fd == 7) {
            fd = 1;
        } else fd++;

        return fd;
    }

    public int getLastMonthFristDay() {
        return lastMonthFristDay;
    }

    public void setLastMonthFristDay(int lastMonthFristDay) {
        this.lastMonthFristDay = lastMonthFristDay;
    }

    public int getNextMonthFristDay() {
        return nextMonthFristDay;
    }

    public void setNextMonthFristDay(int nextMonthFristDay) {
        this.nextMonthFristDay = nextMonthFristDay;
    }

    public int getmDay() {
        return mDay;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getmYear() {
        return mYear;
    }

    public int getmMonth() {
        return mMonth;
    }


    public Calendar getCalendart() {
        return calendart;
    }

    public void setCalendarListener(CalendarListener calendarListener) {
        this.calendarListener = calendarListener;
    }

    /**
     * 某年某月 日历
     * @param yearMotn 某年某月  格式为yyyy-MM

     */
    public void setYearMonetCalendart(String yearMotn) {
        this.mYear = Integer.valueOf(yearMotn.split("-")[0]);
        this.mMonth = Integer.valueOf(yearMotn.split("-")[1]);
        removeAllViews();
        setOnViewFlipperListener(this);
        final java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeZone(TimeZone.getDefault());
        c.setTime(DateUtil.strToDate(yearMotn + "-01"));
        int  day = c.get(java.util.Calendar.DAY_OF_MONTH);// 获取日期号码
        int  way = c.get(java.util.Calendar.DAY_OF_WEEK);// 获取周几
        calendart = new Calendar(this);
        calendart.setYMD(mYear, mMonth, (mMonth == month) && (mYear == year) ? mDay : 0, getFristDayOfMoth(day,way));
        addView(calendart.onCreateView());
        topTextView.setText(mYear+"年"+mMonth+"月");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int width = 0;
        View childView = getChildAt(0);
        int cHeight = childView.getMeasuredHeight();
        setMeasuredDimension((widthMode == View.MeasureSpec.EXACTLY) ? sizeWidth : width, (heightMode == View.MeasureSpec.EXACTLY) ? sizeHeight : cHeight);

    }

    public void setTopTextView(TextView topTextView) {
        this.topTextView = topTextView;
        topTextView.setText(mYear+"年"+mMonth+"月");
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        calendarListener.calendarAnimationEnd();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
