package com.rongfeng.speedclient.schedule.model;

/**
 * Created by 唐磊 on 2015/12/25.
 */
public class RequestScheduleMonthModel {

    private String theMonth;//	月份	yyyy-MM	not  null
    private String customerId;//	客户ID
    private String contactsId;//	联系人ID
    private String thatDay;//

    public String getThatDay() {
        return thatDay;
    }

    public void setThatDay(String thatDay) {
        this.thatDay = thatDay;
    }

    public String getTheMonth() {
        return theMonth;
    }

    public void setTheMonth(String theMonth) {
        this.theMonth = theMonth;
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
