package com.rongfeng.speedclient.utils.calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.utils.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeekAdapter extends BaseAdapter implements OnClickListener {
    private Context context;
    /**
     * 阳历
     */
    private List<Integer> list = new ArrayList<Integer>();
    private HashMap<Integer, String> map;
    private HashMap<Integer, String> hashMap = new HashMap<>();
    private LinearLayout layout;
    private TextView solarCalendar;
    private TextView unarCalendar;
    private int p = -1;
    public GridView gridView; //缓存日历(月)
    private CalendarAdapter calendarAdapter;
    private int mYear;
    private int mMonth;
    private int searchday = 0;
    private int weekOfMonth;


    public WeekAdapter(Context context, List<Integer> list, HashMap<Integer, String> map, int p, CalendarAdapter calendarAdapter) {
        super();
        this.list = list;
        this.map = map;
        this.context = context;
        this.p = p;
        this.calendarAdapter = calendarAdapter;
    }

    public void setData(List<CalendarModel> list) {
        try {
            hashMap.clear();
            for (CalendarModel model : list) {
                hashMap.put(Integer.valueOf(model.getDate()), model.getState());
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        gridView = (GridView) arg2;
        View view = LayoutInflater.from(context).inflate(R.layout.calender_item, null);
        TextView solarCalendar = (TextView) view.findViewById(R.id.solar_calendar);
        TextView unarCalendar = (TextView) view.findViewById(R.id.unar_calendar);
        ImageView identifierImage = (ImageView) view.findViewById(R.id.identifier_image);
        LinearLayout day = (LinearLayout) view.findViewById(R.id.day_layout);
        if (list.get(arg0) != null) {
            solarCalendar.setText(list.get(arg0).toString());
            unarCalendar.setText(map.get(list.get(arg0)));
            view.setTag(R.id.day_layout, day);
            view.setTag(R.id.solar_calendar, solarCalendar);
            view.setTag(R.id.unar_calendar, unarCalendar);
            view.setTag(R.id.grid_item_layout, list.get(arg0));
            view.setTag(arg0);
            day.setTag(R.color.colorBlue, false);
            view.setOnClickListener(this);

            if (list.get(arg0) == searchday) {
                updateItemBackground(view);
            }

            if (arg0 == p) {
                updateItemBackground(view);
            }

             /*某天提示*/
            if (hashMap.containsKey(list.get(arg0))) {
                switch (hashMap.get(list.get(arg0))) {
                    case "0":
                        identifierImage.setBackgroundResource(R.drawable.calendar_p_green);
                        break;
                    case "1":
                        identifierImage.setBackgroundResource(R.drawable.calendar_p_orange);
                        break;
                    case "2":
                        identifierImage.setBackgroundResource(R.drawable.calendar_p_red);
                        view.setTag(R.drawable.calendar_p_red, 2);
                        break;
                    case "3"://请假
                        identifierImage.setBackgroundResource(R.drawable.calendar_p_purple);
                        break;
                }
                identifierImage.setVisibility(View.VISIBLE);
            } else {
                String todayStr = DateUtil.getTime(DateUtil.DEFAULT_DATE_PATTERN);
                String dayStr = DateUtil.getDate(mYear + "-" + mMonth + "-" + list.get(arg0), "yyyy-M-d", DateUtil.DEFAULT_DATE_PATTERN);
                if (!todayStr.equals(dayStr)) view.setTag(R.drawable.calendar_p_red, 1);
            }
        }
        return view;
    }


    @Override
    public void onClick(View v) {
        int day = (int) v.getTag(R.id.grid_item_layout);
        int i = 0;
        try {
            i = (int) v.getTag(R.drawable.calendar_p_red);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String month = mMonth < 10 ? ("0" + mMonth) : mMonth + "";
        String day1 = day < 10 ? ("0" + day) : day + "";
        String dateString = mYear + "-" + month + "-" + day1;
        Intent intent = new Intent("EffectCalendar");
        intent.putExtra("flag", i);
        intent.putExtra("dateString", dateString);
        context.sendBroadcast(intent);

        updateItemBackground(v);
        int d = (int) v.getTag(R.id.grid_item_layout);
        calendarAdapter.update(d);
        calendarAdapter.setDateString(dateString);
    }

    /**
     * 给某一天设为已选状态
     *
     * @param day
     */
    public void setIsflag(int day) {
        this.searchday = day;
    }

    public void updateItemBackground(View v) {
        try {
            layout.setBackground(null);
            if (!(boolean) layout.getTag(R.color.colorBlue)) {
                solarCalendar.setTextColor(context.getResources().getColor(R.color.colorTitleText));
                unarCalendar.setTextColor(context.getResources().getColor(R.color.colorTitleText));

            } else {
                solarCalendar.setTextColor(context.getResources().getColor(R.color.colorBlue));
                unarCalendar.setTextColor(context.getResources().getColor(R.color.colorBlue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        LinearLayout layout = (LinearLayout) v.getTag(R.id.day_layout);
        TextView solarCalendar = (TextView) v.getTag(R.id.solar_calendar);
        TextView unarCalendar = (TextView) v.getTag(R.id.unar_calendar);
        layout.setBackgroundResource(R.drawable.calender_bg_checked);
        solarCalendar.setTextColor(Color.WHITE);
        unarCalendar.setTextColor(Color.WHITE);
        this.layout = layout;
        this.solarCalendar = solarCalendar;
        this.unarCalendar = unarCalendar;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            View view = gridView.getChildAt(0);
            updateItemBackground(view);
        }
    };


    public List<Integer> getList() {
        return list;
    }


    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public void setWeekOfMonth(int weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
        calendarAdapter.setPostion((weekOfMonth - 1) * 7);
    }
}
