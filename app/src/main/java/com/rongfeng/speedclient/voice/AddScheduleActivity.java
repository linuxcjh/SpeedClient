package com.rongfeng.speedclient.voice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.schedule.ScheduleAddEditActivity;
import com.rongfeng.speedclient.schedule.action.IScheduleAction;
import com.rongfeng.speedclient.schedule.model.ReceiveScheduleItemModel;
import com.rongfeng.speedclient.schedule.model.RequestScheduleMonthModel;
import com.rongfeng.speedclient.schedule.presenter.SchedulePersenter;
import com.rongfeng.speedclient.utils.calendar.CalendarListener;
import com.rongfeng.speedclient.utils.calendar.CalendarModel;
import com.rongfeng.speedclient.utils.calendar.CalendarView;
import com.rongfeng.speedclient.utils.calendar.EffectsCalendarView;
import com.rongfeng.speedclient.utils.calendar.WeekListener;

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
public class AddScheduleActivity extends BaseActivity implements CalendarListener, WeekListener, View.OnClickListener, IScheduleAction {

    @Bind(R.id.effectsCalendarView)
    EffectsCalendarView effectsCalendarView;
    @Bind(R.id.cancel_iv)
    TextView cancelIv;
    @Bind(R.id.add_iv)
    TextView addIv;
    private CalendarBroadCastReceiver broadCastReceiver;
    private String dateString;
//    private WeekView weekView;
    private ListView listView;
    private CalendarView calendarView;
    private Map<String, List<CalendarModel>> mapMonth = new HashMap<>();//缓存日历
    private Map<String, List<ReceiveScheduleItemModel>> mapSchedule = new HashMap<>();//缓存日程
    private String month;
    private SchedulePersenter persenter;
    private List<ReceiveScheduleItemModel> data = new ArrayList<>();
    private RequestScheduleMonthModel requestModel = new RequestScheduleMonthModel();
    private int deteltPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_shortcut_remind_layout);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        effectsCalendarView.isDisableWeekView(true);
        calendarView = effectsCalendarView.getCalendarView();
        effectsCalendarView.setIsShrink(false);
        calendarView.setCalendarListener(this);
        calendarView.getCalendart().getAdapter().setDaySelected(false, calendarView.getmDay());
        listView = effectsCalendarView.getListView();
        listView.setVisibility(View.GONE);

        persenter = new SchedulePersenter(this);
        month = calendarView.getYear() + "-" + (calendarView.getMonth() < 10 ? "0" + calendarView.getMonth() : calendarView.getMonth());
        requestModel.setMonth(month);
    }

    @OnClick({R.id.cancel_iv, R.id.add_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_iv:
                setResult(RESULT_OK, new Intent());
                finish();
                break;
            case R.id.add_iv:
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
//        weekView.setData(list);
//        try {
//            weekView.getWeek().getAdapter().setData(list);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void callBackScheduleList(List<ReceiveScheduleItemModel> list) {
        mapSchedule.put(dateString, list);
        data.clear();
        data.addAll(list);

    }


    class CalendarBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            dateString = intent.getStringExtra("dateString");
//            weekView.setDateString(dateString, false);
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
