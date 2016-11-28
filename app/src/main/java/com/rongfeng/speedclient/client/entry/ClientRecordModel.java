package com.rongfeng.speedclient.client.entry;

import java.io.Serializable;

/**
 * 跟进记录
 * AUTHOR: Alex
 * DATE: 3/12/2015 17:17
 */
public class ClientRecordModel implements Serializable{


    private String followUpId;//跟进记录ID
    private String followUpTime;

    private String address;//地址
    private String content;//内容
    private String longitude;//经度
    private String latitude;//纬度


    public String getFollowUpTime() {
        return followUpTime;
    }

    public void setFollowUpTime(String followUpTime) {
        this.followUpTime = followUpTime;
    }

    public String getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(String followUpId) {
        this.followUpId = followUpId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

}
