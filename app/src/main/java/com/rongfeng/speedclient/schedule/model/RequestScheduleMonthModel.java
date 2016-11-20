package com.rongfeng.speedclient.schedule.model;

/**
 * Created by 唐磊 on 2015/12/25.
 */
public class RequestScheduleMonthModel {

    private String month;//	月份	yyyy-MM	not  null
    private String customerId;//	客户ID
    private String contactsId;//	联系人ID
    private String date;//

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }
}
