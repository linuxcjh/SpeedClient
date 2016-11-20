package com.rongfeng.speedclient.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.Utils;
import com.rongfeng.speedclient.schedule.action.IScheduleAction;
import com.rongfeng.speedclient.schedule.adapter.ScheduleListAdapter;
import com.rongfeng.speedclient.schedule.model.ReceiveScheduleItemModel;
import com.rongfeng.speedclient.schedule.model.RequestScheduleMonthModel;
import com.rongfeng.speedclient.schedule.presenter.SchedulePersenter;
import com.rongfeng.speedclient.utils.calendar.CalendarListener;
import com.rongfeng.speedclient.utils.calendar.CalendarModel;
import com.rongfeng.speedclient.utils.calendar.EffectsCalendarView;
import com.rongfeng.speedclient.utils.calendar.WeekListener;
import com.rongfeng.speedclient.utils.calendar.WeekView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 唐磊 on 15/12/8.
 * 日程
 */
public class ScheduleActivity extends BaseActivity implements CalendarListener, WeekListener, View.OnClickListener, IScheduleAction {

    @Bind(R.id.back_bt)
    ImageButton backBt;
    @Bind(R.id.add_bt)
    ImageButton addBt;
    @Bind(R.id.effectsCalendarView)
    EffectsCalendarView effectsCalendarView;
    private CalendarBroadCastReceiver broadCastReceiver;
    private String dateString;
    private WeekView weekView;
    private ListView listView;
    private com.rongfeng.speedclient.utils.calendar.CalendarView calendarView;
    private Map<String, List<CalendarModel>> mapMonth = new HashMap<>();//缓存日历
    private Map<String, List<ReceiveScheduleItemModel>> mapSchedule = new HashMap<>();//缓存日程
    private String month;
    private ScheduleListAdapter adapter;
    private View headView;
    private SchedulePersenter persenter;
    private List<ReceiveScheduleItemModel> data = new ArrayList<>();
    private RequestScheduleMonthModel requestModel = new RequestScheduleMonthModel();
    private int deteltPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        headView = LayoutInflater.from(this).inflate(R.layout.schedule_notdate_item, null);
        headView.findViewById(R.id.add_schedule).setOnClickListener(this);
        weekView = effectsCalendarView.getWeekView();
        weekView.initData();
        weekView.setWeekListener(this);
        calendarView = effectsCalendarView.getCalendarView();
        listView = effectsCalendarView.getListView();
        effectsCalendarView.setIsShrink(true);
        calendarView.setCalendarListener(this);
        calendarView.getCalendart().getAdapter().setDaySelected(false, calendarView.getmDay());

        adapter = new ScheduleListAdapter(this, R.layout.schedule_list_item, data);
        listView.setPadding(Utils.dip2px(this, 15), Utils.dip2px(this, 10), Utils.dip2px(this, 15), 0);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                adapter.closeAllSwipeView();
            }
        });

        persenter = new SchedulePersenter(this);
        month = calendarView.getYear() + "-" + (calendarView.getMonth() < 10 ? "0" + calendarView.getMonth() : calendarView.getMonth());
        requestModel.setMonth(month);
//        persenter.searchCalendarWithMonth(requestModel);
    }

    @OnClick({R.id.back_bt, R.id.add_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_bt:
                setResult(RESULT_OK, new Intent());
                finish();
                break;
            case R.id.add_schedule:
            case R.id.add_bt:
                Intent intent = new Intent(this, ScheduleAddEditActivity.class);
                intent.putExtra("dateString", dateString);
                startActivityForResult(intent, 1001);
                break;
        }
    }


    @Override
    public void calendarFlipper() {
        calendarView.getCalendart().getAdapter().setDaySelected(true, 0);
        month = calendarView.getmYear() + "-" + (calendarView.getmMonth() < 10 ? "0" + calendarView.getmMonth() : calendarView.getmMonth());
        List<CalendarModel> list = null;
        boolean isContainsKey = mapMonth.containsKey(month);
        if (isContainsKey) {
            list = mapMonth.get(month);
        }
        effectsCalendarView.calendarFlipper(month, isContainsKey, list, dateString);
    }

    @Override
    public void calendarAnimationEnd() {
        boolean isContainsKey = mapMonth.containsKey(month);
        if (!isContainsKey) {
            requestModel.setMonth(month);
            persenter.searchCalendarWithMonth(requestModel);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        broadCastReceiver = new CalendarBroadCastReceiver();
        IntentFilter filter = new IntentFilter("EffectCalendar");
        registerReceiver(broadCastReceiver, filter);
    }


    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadCastReceiver);
    }


    @Override
    public void weekFlipper(List<Integer> list) {
        effectsCalendarView.weekFlipper(list, dateString);
    }

    @Override
    public void callBackCalendarMonth(List<CalendarModel> list) {
        mapMonth.put(month, list);
        if (dateString != null && dateString.startsWith(month)) {
            calendarView.getCalendart().getAdapter().setDaySelected(true, Integer.valueOf(dateString.split("-")[2]));
        }
        calendarView.getCalendart().getAdapter().setData(list);
        weekView.setData(list);
        try {
            weekView.getWeek().getAdapter().setData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callBackScheduleList(List<ReceiveScheduleItemModel> list) {
        mapSchedule.put(dateString, list);
        data.clear();
        data.addAll(list);
        if (data.size() > 0) {
            listView.removeHeaderView(headView);
        } else {
            listView.setAdapter(null);
            listView.removeHeaderView(headView);
            listView.addHeaderView(headView);
        }
        listView.setAdapter(adapter);
    }



    class CalendarBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            dateString = intent.getStringExtra("dateString");
            weekView.setDateString(dateString, false);
            adapter.setDateString(dateString);
            requestModel.setDate(dateString);
            int i = intent.getIntExtra("flag", 0);
            switch (i) {
                case 0://有日程
                    persenter.searchScheduleListWithDate(requestModel);
                    break;
                case 1:// 无日程
                    data.clear();
                    callBackScheduleList(data);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1001:
                    persenter.searchScheduleListWithDate(requestModel);
                    persenter.searchCalendarWithMonth(requestModel);
                    break;
            }

        }
    }

    @Override
    public void callBackDeteleItem() {

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent());
        finish();
    }
}
