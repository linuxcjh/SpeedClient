package com.rongfeng.speedclient.schedule.model;


import java.io.Serializable;

/**
 * Created by 唐磊 on 2015/12/28.
 */
public class ReceiveScheduleDetailsModel implements Serializable {

    private String scheduleId;//	日程ID
    private String content;//	日程内容
    private String startTime;//	日程开始时间	全天：yyyy-MM-dd 非全天：yyyy-MM-dd HH:mm
    private String endTime;//	日程结束时间	全天：yyyy-MM-dd 非全天：yyyy-MM-dd HH:mm
    private String isAllDay ;//    0-否；1-全天；
    private String remindTypeId   ;//    提醒类型ID
    private String remindTypeName;//	提醒类型名称
    private String customerId;//	关联客户ID
    private String customerName;//	关联客户名称
    private String remark;//	备注

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

    public String getIsAllDay() {
        return isAllDay;
    }

    public void setIsAllDay(String isAllDay) {
        this.isAllDay = isAllDay;
    }

    public String getRemindTypeId() {
        return remindTypeId;
    }

    public void setRemindTypeId(String remindTypeId) {
        this.remindTypeId = remindTypeId;
    }

    public String getRemindTypeName() {
        return remindTypeName;
    }

    public void setRemindTypeName(String remindTypeName) {
        this.remindTypeName = remindTypeName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
