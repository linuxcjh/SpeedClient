package com.rongfeng.speedclient.utils.calendar;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 唐磊 on 2015/11/12.
 * 动画特效日历
 * 最多三个子View
 */
public class EffectsCalendarOtherView extends FrameLayout implements View.OnClickListener {

    private int calendarHeight;//日历控件高
    private int cH;
    private ViewGroup childView1;
    private CalendarView calendarView;
    private Context context;
    private int lineHeight;//日历行高
    private int calendarViewTopHeight;//日历表头高
    private boolean isIntercept = false;//父控件是否拦截事件
    private boolean isTop;
    private boolean isBottom;
    private boolean isShrink;//是否收缩
    private List<Integer> list;//日历数据缓存
    private int postion; //日历已选位置
    private HashMap<Integer, String> map = new HashMap<>();//阳历 农历键值对map
    private int checkedLineTB[] = new int[2];//存放已选行的 getTop  getBottom;
    private int lineNumber = 0; //日历 行数
    private int lineCount;//日历 行总数
    private int y;//缓存滑动到的位置
    private int vh = 0;//如果有三个子View 缓存第二子View高
    private boolean isScrollTop = true;
    private WeekView weekView;
    private ListView listView;
    private boolean isScroll = true;//listView是否正滚动
    private boolean isPullUp;//是否正在上拉
    private LinearLayout linearLayout;
    private int by;

    public EffectsCalendarOtherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        calendarViewTopHeight = Utils.dip2px(context, 60);
        LinearLayout layoutCalendar = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.calender_layout, null);
        calendarView = (CalendarView) layoutCalendar.findViewById(R.id.calendarView);
        TextView topTextView = (TextView) layoutCalendar.findViewById(R.id.toptext);
        ImageButton uptMouth = (ImageButton) layoutCalendar.findViewById(R.id.upmouth);
        ImageButton nextMouth = (ImageButton) layoutCalendar.findViewById(R.id.nextmouth);
        uptMouth.setOnClickListener(this);
        nextMouth.setOnClickListener(this);
        calendarView.setTopTextView(topTextView);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutCalendar.setLayoutParams(lp);
        addView(layoutCalendar, 0);

        childView1 = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.effects_calendar_chlid_other_layout, null);
        LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        childView1.setLayoutParams(lp1);
        addView(childView1);
        weekView = (WeekView) childView1.findViewById(R.id.weekView);
        weekView.setCalendarView(calendarView);
    }


    int widthMeasureSpec, heightMeasureSpec;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        View childView0 = getChildAt(0);
        calendarHeight = childView0.getMeasuredHeight();
        int lineNumber = calendarView.calendart.size / 7;
        lineHeight = (calendarHeight - calendarViewTopHeight) / lineNumber;
        int childCount = getChildCount();
        if (childCount == 3) {
            ViewGroup v = (ViewGroup) getChildAt(1);
            vh = v.getChildAt(0).getMeasuredHeight();
            v.scrollTo(0, -calendarHeight);
        }

        if (calendarHeight != cH) {
            if (isShrink) {
                childView1.scrollTo(0, -calendarViewTopHeight);
            } else {
                childView1.scrollTo(0, -calendarHeight - vh + lineHeight);
                weekView.setVisibility(INVISIBLE);
            }
        }

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, sizeHeight - calendarViewTopHeight - lineHeight);
        linearLayout.setLayoutParams(lp);
        cH = calendarHeight;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        measure(widthMeasureSpec, heightMeasureSpec);
        super.onLayout(changed, left, top, right, bottom);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    isScrollTop = (boolean) msg.obj;
                    if (!isScrollTop) {
                        isScroll = true;
                    }
                    break;
                case 1:
                    if( by<=1){
                        by=1;
                    }
                    if (isTop) {
                        if(childView1.getScrollY()==-calendarViewTopHeight) break;
                        childView1.scrollBy(0, by);
                    } else {
                        if(childView1.getScrollY()==-calendarHeight - vh + lineHeight){
                            final String str= calendarView.getCalendart().getAdapter().getDateString();
                            if(!TextUtils.isEmpty(str)){
                                weekView.setDateString(str,false);
                            }
                            break;
                        }
                        childView1.scrollBy(0, -by);
                    }
                    by--;
                    sendEmptyMessageDelayed(1,5);
                    break;
            }
        }
    };

    float sY, sX;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sY = event.getRawY();
                sX = event.getRawX();
                fetch();
                break;
            case MotionEvent.ACTION_MOVE:
                float mY = event.getRawY();
                float mX = event.getRawX();

                if (isScrollTop && isScroll) {
                    sY = event.getRawY();
                    sX = event.getRawX();
                    isScroll = false;
                }
                int msY = (int) (mY - sY);
                int msX = (int) (mX - sX);

                /* 上下滑动距离大于左右滑动的距离， 并且最小大于10 */
                if (Math.abs(msY) > Math.abs(msX) && Math.abs(msY) > 10/*&&((ViewGroup)childView1.getChildAt(1)).getChildCount()>0*/) {
                    //上拉
                    if (msY < 0 && isBottom) {
                        isIntercept = true;
                        pullUping(msY);
                        break;
                    }

                    if (msY > 0 && isBottom) {
                        break;
                    }

                    if (msY > 0 && !isScrollTop) {
                        break;
                    }

                    //下拉
                    if (msY > 0 && isTop) {
                        isIntercept = true;
                        pullDowning(msY);
                    }

                } else {
                    super.dispatchTouchEvent(event);
                    if (isTop) return true;
                    isIntercept = false;

                }
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                final String str= calendarView.getCalendart().getAdapter().getDateString();
                if (y > 0) {
                    isPullUp=false;
                    if ((y >= checkedLineTB[0])) {//移动位置超过已选日历行的底边缘
//                        childView1.scrollTo(0, -calendarHeight - vh + lineHeight);
                        int scrollY = childView1.getScrollY();
                        int sn=scrollY+calendarHeight + vh - lineHeight;
                         /* 求等差数列末项 ， 首相1 末项为by 公差为1 等差数列和 sn*/
                        by= (int) ((Math.sqrt(8* Math.abs(sn)+1)-1)/2);
                        handler.sendEmptyMessage(1);
                        weekView.setVisibility(INVISIBLE);
                        isBottom = true;
                        isTop = false;
                        y = 0;
                        if(!TextUtils.isEmpty(str)){
                            weekView.setDateString(str,false);
                        }
                        return true;
                    } else if (y < checkedLineTB[0]) {//移动位置未越过已选日历行的底边缘
//                        childView1.scrollTo(0, -calendarViewTopHeight);
                        int scrollY = childView1.getScrollY();
                        int sn=-calendarViewTopHeight-scrollY;
                         /* 求等差数列末项 ， 首相1 末项为by 公差为1 等差数列和 sn*/
                        by= (int) ((Math.sqrt(8* Math.abs(sn)+1)-1)/2);
                        handler.sendEmptyMessage(1);
                        weekView.setVisibility(VISIBLE);
                        isBottom = false;
                        isTop = true;
                        y = 0;
                        return true;
                    }
                }
                if (weekView.getVisibility() == VISIBLE) {
                    isShrink = true;
                } else if (weekView.getVisibility() == INVISIBLE) {
                    isShrink = false;
                }
                if (y == 0&&isPullUp) {
                    isPullUp=false;
                    return true;
                }

                break;
        }
        if (!isIntercept) super.dispatchTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return isIntercept;
    }

    private void fetch() {
        map.clear();
        list = null;
        list = calendarView.calendart.getList();
        postion = calendarView.calendart.getAdapter().getPostion();
        lineCount = list.size() / 7;
        lineNumber = 1;
        if (postion >= 0 && postion <= 6) {
            lineNumber = 1;
        } else if (postion >= 7 && postion <= 13) {
            lineNumber = 2;
        } else if (postion >= 14 && postion <= 20) {
            lineNumber = 3;
        } else if (postion >= 21 && postion <= 27) {
            lineNumber = 4;
        } else if (postion >= 28 && postion <= 34) {
            lineNumber = 5;
        } else if (postion >= 35 && postion <= 41) {
            lineNumber = 6;
        }
        /*已选行的上边缘的位置*/
        checkedLineTB[0] = calendarViewTopHeight + (lineNumber - 1) * lineHeight;
        /*已选行的下边缘的位置*/
        checkedLineTB[1] = calendarViewTopHeight + lineNumber * lineHeight;
    }


    /* 上拉   极限位置为最顶端日历的第一行下边缘 */
    private void pullUping(int moveY) {
        if (Math.abs(calendarHeight + vh + moveY) > calendarViewTopHeight + lineHeight) {
            childView1.scrollTo(0, -calendarHeight + lineHeight - vh - moveY);
            y = Math.abs(-calendarHeight + lineHeight - vh - moveY);
            isPullUp=true;
            if (-childView1.getScrollY() <= checkedLineTB[0]) {
                weekView.setVisibility(VISIBLE);
            }else  weekView.setVisibility(INVISIBLE);
        } else {
            childView1.scrollTo(0, -calendarViewTopHeight);
            weekView.setVisibility(VISIBLE);
            isTop = true;
            isBottom = false;
            y = 0;
        }
    }

    /*下拉  极限位置为日历底边缘*/
    private void pullDowning(int moveY) {
        String str= calendarView.getCalendart().getAdapter().getDateString();
        if (calendarHeight + vh - lineHeight + 100 >= (calendarViewTopHeight + moveY)) {
            y = calendarViewTopHeight + moveY;
            if (y > calendarHeight + vh - lineHeight) y = calendarHeight + vh - lineHeight;
            childView1.scrollTo(0, -y);
            if (-childView1.getScrollY() >= checkedLineTB[0]) {
                weekView.setVisibility(INVISIBLE);
            }

        } else {
            childView1.scrollTo(0, -calendarHeight - vh + lineHeight);
            if(weekView.getVisibility()==VISIBLE){
                weekView.setVisibility(INVISIBLE);
            }
            isBottom = true;
            isTop = false;
        }
    }


    public CalendarView getCalendarView() {
        return calendarView;
    }



    public WeekView getWeekView() {
        return weekView;
    }

    public ViewGroup getChildView1() {return childView1;}

    public void setIsShrink(boolean isShrink) {
        this.isShrink = isShrink;
        if (isShrink) {
            isTop = true;
        } else {
            isBottom = true;
            weekView.setVisibility(INVISIBLE);
        }
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void weekFlipper(List<Integer> list, String dateString) {
        List<String> listaString = new ArrayList<>();
        String str = calendarView.getmYear() + "-" + (calendarView.getmMonth() < 10 ? "0" + calendarView.getmMonth() : calendarView.getmMonth());
        for (int i = 0; i < list.size(); i++) {
            String day = null;
            try {
                day = list.get(i) < 10 ? "0" + String.valueOf(list.get(i)) : String.valueOf(list.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
            listaString.add(str + "-" + day);
        }
        if (listaString.contains(dateString)) {
            weekView.getWeek().getAdapter().setIsflag(Integer.valueOf(dateString.split("-")[2]));
        }
    }


    public void calendarFlipper(String month, boolean isContainsKey, List<CalendarModel> list, String dateString) {
        if (isContainsKey) {
            calendarView.getCalendart().getAdapter().setDaySelected(true, 0);
            calendarView.getCalendart().getAdapter().setData(list);
            weekView.setData(list);
            if (dateString.startsWith(month)) {
                calendarView.getCalendart().getAdapter().setDaySelected(true, Integer.valueOf(dateString.split("-")[2]));
                if (!isShrink()) {
                    weekView.setDateString(dateString, false);
                    return;
                }
            }

        } else {
            weekView.setData(null);
        }

        if (!isShrink()) {
            weekView.setDateString(month + "-01", true);
            calendarView.calendart.getAdapter().setPostion(6);
        }

    }



    public void setListView(ListView listView1, ListView listView2) {
        setListViewOnTouchAndScrollListener(listView1,listView2);
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }


    /**
     *  两个listView 同步滚动
     * @param listView1
     * @param listView2
     */
    public void setListViewOnTouchAndScrollListener(final ListView listView1, final ListView listView2){

        //设置listview2列表的scroll监听，用于滑动过程中左右不同步时校正
        listView2.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //如果停止滑动
                if(scrollState == 0 || scrollState == 1){
                    //获得第一个子view
                    View subView = view.getChildAt(0);

                    if(subView !=null){
                        final int top = subView.getTop();
                        final int top1 = listView1.getChildAt(0).getTop();
                        final int position = view.getFirstVisiblePosition();

                        //如果两个首个显示的子view高度不等
                        if(top != top1){
                            listView1.setSelectionFromTop(position, top);
                            listView2.setSelectionFromTop(position, top);
                        }
                    }
                }

            }

            public void onScroll(AbsListView view, final int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                View subView = view.getChildAt(0);
                if(subView != null){
                    final int top = subView.getTop();
                    listView1.setSelectionFromTop(firstVisibleItem, top);
                    listView2.setSelectionFromTop(firstVisibleItem, top);
                }
            }
        });

        //设置listview1列表的scroll监听，用于滑动过程中左右不同步时校正

        listView1.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == 0 || scrollState == 1){
                    //获得第一个子view
                    View subView = view.getChildAt(0);

                    if(subView !=null){
                        final int top = subView.getTop();
                        final int top1 = listView2.getChildAt(0).getTop();
                        final int position = view.getFirstVisiblePosition();

                        //如果两个首个显示的子view高度不等
                        if(top != top1){
                            listView1.setSelectionFromTop(position, top);
                            listView2.setSelectionFromTop(position, top);
                        }
                    }
                }
            }
            int t, vt;
            @Override
            public void onScroll(AbsListView view, final int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                try {
                    View vtop = view.getChildAt(0);
                    t = vtop.getTop();
                    vt = view.getPaddingTop();
                    // 判断滚动到顶部
                    if (listView1.getFirstVisiblePosition() == 0 && t == vt) {
                        handler.sendMessage(handler.obtainMessage(0, true));
                    } else
                        handler.sendMessage(handler.obtainMessage(0, false));

                    if(vtop != null){
                        final int top = vtop.getTop();
                        listView1.setSelectionFromTop(firstVisibleItem, top);
                        listView2.setSelectionFromTop(firstVisibleItem, top);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upmouth:
                if (isShrink) {
                    weekView.flingToPrevious();
                } else
                    calendarView.flingToPrevious();

                break;
            case R.id.nextmouth:
                if (isShrink) {
                    weekView.flingToNext();
                } else
                    calendarView.flingToNext();
                break;
        }
    }
}
