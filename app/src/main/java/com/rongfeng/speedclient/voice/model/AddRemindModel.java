package com.rongfeng.speedclient.voice.model;

/**
 * AUTHOR: Alex
 * DATE: 27/11/2016 11:39
 */

public class AddRemindModel {

    private String remindContent;//提醒内容
    private String remindType;//    提醒类型（1,3,7提前几天） 直接传1,3,7
    private String remindDate;//提醒日期
    private String startHour;//开始时间 格式 20:23
    private String endHour;//开始时间 格式 20:23

    private String csrId;//

    public String getCsrId() {
        return csrId;
    }

    public void setCsrId(String csrId) {
        this.csrId = csrId;
    }

    public String getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(String remindDate) {
        this.remindDate = remindDate;
    }

    public String getRemindContent() {
        return remindContent;
    }

    public void setRemindContent(String remindContent) {
        this.remindContent = remindContent;
    }

    public String getRemindType() {
        return remindType;
    }

    public void setRemindType(String remindType) {
        this.remindType = remindType;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }
}
