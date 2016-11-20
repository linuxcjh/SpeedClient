package com.rongfeng.speedclient.schedule.model;

/**
 * Created by 唐磊 on 2015/12/25.
 */
public class RequestAddScheduleModel {

    private String content;//	日程内容		not  null
    private String startTime;//	日程开始时间	全天：yyyy-MM-dd 非全天：yyyy-MM-dd HH:mm	not  null
    private String endTime;//	日程结束时间	全天：yyyy-MM-dd 非全天：yyyy-MM-dd HH:mm	not  null
    private String isAllDay;//	是否全天	0-否；1-全天；	not  null
    private String remindTypeId;//	提醒类型ID		not  null
    private String remindTypeName;//	提醒类型名称		not  null
    private String customerId;//	关联客户ID		not  null
    private String customerName;//	关联客户名称		not  null
    private String contactsInfo;//	关联联系人信息	[     {"id":"联系人ID","name":"联系人名称"},     {"id":"联系人ID","name":"联系人名称"} ]
    private String teamworkInfo;//	关联协作人信息	[     {"id":"协作人ID","name":"协作人名称"},     {"id":"协作人ID","name":"协作人名称"} ]
    private String remark;//	备注
    private String pictures;//	图片信息	[     {"fileId":"文件ID"},     {"fileId":"文件ID"} ]
    private String panFiles;//	企业网盘文件ID列表【预留】	[     {"fileId":"文件ID"},     {"fileId":"文件ID"} ]
    private String scheduleId;

    public String getScheduleId() {return scheduleId;}

    public void setScheduleId(String scheduleId) {this.scheduleId = scheduleId;}

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

    public String getContactsInfo() {
        return contactsInfo;
    }

    public void setContactsInfo(String contactsInfo) {
        this.contactsInfo = contactsInfo;
    }

    public String getTeamworkInfo() {
        return teamworkInfo;
    }

    public void setTeamworkInfo(String teamworkInfo) {
        this.teamworkInfo = teamworkInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getPanFiles() {
        return panFiles;
    }

    public void setPanFiles(String panFiles) {
        this.panFiles = panFiles;
    }
}
