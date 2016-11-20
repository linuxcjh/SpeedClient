package com.rongfeng.speedclient.schedule.presenter;

import com.google.gson.reflect.TypeToken;
import com.rongfeng.speedclient.API.XxbService;
import com.rongfeng.speedclient.common.BasePresenter;
import com.rongfeng.speedclient.common.utils.AppTools;
import com.rongfeng.speedclient.schedule.action.IScheduleAction;
import com.rongfeng.speedclient.schedule.model.ReceiveScheduleItemModel;
import com.rongfeng.speedclient.schedule.model.RequestScheduleDetailsModel;
import com.rongfeng.speedclient.schedule.model.RequestScheduleMonthModel;
import com.rongfeng.speedclient.utils.calendar.CalendarModel;

import java.util.List;

/**
 * Created by 唐磊 on 2015/12/25.
 */
public class SchedulePersenter extends BasePresenter {

    private IScheduleAction iScheduleAction;

    public SchedulePersenter(IScheduleAction iScheduleAction) {
        this.iScheduleAction = iScheduleAction;
    }

    @Override
    public void onResponse(String methodName, Object object, int status) {

        if(status==1){
            switch (methodName) {
            		case XxbService.SEARCHCALENDARWITHMONTH:
                        iScheduleAction.callBackCalendarMonth((List<CalendarModel>) object);
            			break;
            		case XxbService.SEARCHSCHEDULELISTWITHDATE:
                        iScheduleAction.callBackScheduleList((List<ReceiveScheduleItemModel>) object);
            			break;
                case XxbService.DELETESCHEDULEWITHID:
                    iScheduleAction.callBackDeteleItem();
                    break;
            		}
        }
    }

    /**
     *根据月份查询某月日程的日历信息
     */
    public void searchCalendarWithMonth(RequestScheduleMonthModel model){
        commonApi(XxbService.SEARCHCALENDARWITHMONTH, AppTools.toMap(model),new TypeToken<List<CalendarModel>>(){});

    }

    /**
     *根据条件查询日程列表信息
     */
    public void searchScheduleListWithDate(RequestScheduleMonthModel model){
        commonApi(XxbService.SEARCHSCHEDULELISTWITHDATE, AppTools.toMap(model),new TypeToken<List<ReceiveScheduleItemModel>>(){});
    }

    public void deleteScheduleWithId(String scheduleId){
        RequestScheduleDetailsModel model=new RequestScheduleDetailsModel();
        model.setScheduleId(scheduleId);
        commonApi(XxbService.DELETESCHEDULEWITHID, AppTools.toMap(model));
    }
}
