package com.rongfeng.speedclient.schedule.action;


import com.rongfeng.speedclient.schedule.model.ReceiveScheduleDetailsModel;

/**
 * Created by 唐磊 on 2015/12/28.
 */
public interface IScheduleDetailsAction {

    public void callBack(ReceiveScheduleDetailsModel model);
}
