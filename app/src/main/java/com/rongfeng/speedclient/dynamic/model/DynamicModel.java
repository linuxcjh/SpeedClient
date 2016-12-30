package com.rongfeng.speedclient.dynamic.model;

import com.rongfeng.speedclient.client.entry.ImageListModel;

import java.util.List;

/**
 * AUTHOR: Alex
 * DATE: 25/12/2016 21:59
 */

public class DynamicModel {

    private int dynamicType;// 类型1.第一次进入系统2跟进客户3.保存日志4编辑日志5.创建客户6编辑客户7创建商机8编辑商机9新增成交10编辑成交11收款12位置记录
    private String dynamicTitle;//
    private String dynamicVal;//
    private String createTime;//
    private String dynamicTypeName;
    private String customerName;
    private String csrId;
    private String relevanceId;//对应的类型业务id
    private List<ImageListModel> jsonArrayPositionImg;
    private String longitude;
    private String latitude;
    private String address;


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ImageListModel> getJsonArrayPositionImg() {
        return jsonArrayPositionImg;
    }

    public void setJsonArrayPositionImg(List<ImageListModel> jsonArrayPositionImg) {
        this.jsonArrayPositionImg = jsonArrayPositionImg;
    }

    public String getCsrId() {
        return csrId;
    }

    public void setCsrId(String csrId) {
        this.csrId = csrId;
    }

    public String getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(String relevanceId) {
        this.relevanceId = relevanceId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDynamicTypeName() {
        return dynamicTypeName;
    }

    public void setDynamicTypeName(String dynamicTypeName) {
        this.dynamicTypeName = dynamicTypeName;
    }

    public int getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(int dynamicType) {
        this.dynamicType = dynamicType;
    }

    public String getDynamicTitle() {
        return dynamicTitle;
    }

    public void setDynamicTitle(String dynamicTitle) {
        this.dynamicTitle = dynamicTitle;
    }

    public String getDynamicVal() {
        return dynamicVal;
    }

    public void setDynamicVal(String dynamicVal) {
        this.dynamicVal = dynamicVal;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
