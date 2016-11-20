package com.rongfeng.speedclient.schedule;

import android.os.Bundle;

import com.rongfeng.speedclient.R;
import com.rongfeng.speedclient.common.BaseActivity;
import com.rongfeng.speedclient.common.utils.DateUtil;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.ButterKnife;

import static com.rongfeng.speedclient.common.Constant.str;

/**
 * Created by 唐磊 on 2015/12/9.
 */
public class ScheduleAddEditActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_add_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
    }


    public String getMDWString(String dateString) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.setTime(DateUtil.strToDate(dateString));
        int month = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int day = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        int week = c.get(Calendar.DAY_OF_WEEK) - 1;// 获取当前日期是周期
        String dateStr = month + "月" + day + "日" + str[week];
        return dateStr;
    }


}
