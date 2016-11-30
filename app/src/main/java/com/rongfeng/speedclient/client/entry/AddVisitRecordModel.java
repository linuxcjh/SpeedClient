package com.rongfeng.speedclient.client.entry;

/**
 * AUTHOR: Alex
 * DATE: 26/11/2016 11:40
 */

public class AddVisitRecordModel {

    private String csrId ;//客户ID
    private String address ;//详细地址
    private String customerName ;//客户名称
    private String longitude ;//经度
    private String latitude ;//纬度
    private String content ;//内容
    private String visitTime;//拜访时间
    private String followUpImages;

    public String getFollowUpImages() {
        return followUpImages;
    }

    public void setFollowUpImages(String followUpImages) {
        this.followUpImages = followUpImages;
    }

    public String getCsrId() {
        return csrId;
    }

    public void setCsrId(String csrId) {
        this.csrId = csrId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }
}
