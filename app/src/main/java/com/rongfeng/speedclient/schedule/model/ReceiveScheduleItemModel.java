package com.rongfeng.speedclient.schedule.model;

/**
 * Created by Administrator on 2015/12/16.
 */
public class ReceiveScheduleItemModel {
    private String scheduleId;//	日程ID
    private String content;//	日程内容
    private String startTime;//	日程开始时间
    private String endTime;//	日程结束时间
    private String customerName;//	关联客户名称
    private String scheduleState;

    public String getScheduleState() {return scheduleState;}

    public void setScheduleState(String scheduleState) {this.scheduleState = scheduleState;}


    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
