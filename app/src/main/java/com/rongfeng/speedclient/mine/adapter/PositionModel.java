package com.rongfeng.speedclient.mine.adapter;

import com.rongfeng.speedclient.client.entry.ImageListModel;

import java.util.List;

/**
 * AUTHOR: Alex
 * DATE: 27/12/2016 16:35
 */

public class PositionModel {

    private String longitude;
    private String latitude;
    private String type; //类型（0位置1图片）
    private String address;
    private String relatedPositionId;//
    private String createTime;
    private List<ImageListModel> jsonArrayPositionImg;//

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRelatedPositionId() {
        return relatedPositionId;
    }

    public void setRelatedPositionId(String relatedPositionId) {
        this.relatedPositionId = relatedPositionId;
    }

    public List<ImageListModel> getJsonArrayPositionImg() {
        return jsonArrayPositionImg;
    }

    public void setJsonArrayPositionImg(List<ImageListModel> jsonArrayPositionImg) {
        this.jsonArrayPositionImg = jsonArrayPositionImg;
    }
}
