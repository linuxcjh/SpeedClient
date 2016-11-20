package com.rongfeng.speedclient.schedule.action;

import com.rongfeng.speedclient.schedule.model.ReceiveScheduleItemModel;
import com.rongfeng.speedclient.utils.calendar.CalendarModel;

import java.util.List;

/**
 * Created by 唐磊 on 2015/12/25.
 */
public interface IScheduleAction {

    public void callBackCalendarMonth(List<CalendarModel> list);

    public void callBackScheduleList(List<ReceiveScheduleItemModel> list);

    public void callBackDeteleItem();
}
