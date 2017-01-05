package com.rongfeng.speedclient.manage.model;

import com.rongfeng.speedclient.client.entry.ImageListModel;

import java.util.List;

/**
 * AUTHOR: Alex
 * DATE: 28/12/2016 14:36
 */

public class ManageFollowModel {

    private String csrId;

    private String followUpId;//跟进ID
    private String followUpTime;//跟进日期
    private String customerName;//客户名称
    private String content;//
    private String longitude;//
    private String latitude;//
    private List<ImageListModel> followUpInImageJSONArray;


    private String businessId;//商机ID
    private String businessName;//商机标题
    private String predictMoney;//预计金额


    private String conId;//成交ID
    private String conName;//标题
    private String conRental;//总额
    private String remainingBalance;//剩余
    private String moneyReceipt;//已收款


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

    public List<ImageListModel> getFollowUpInImageJSONArray() {
        return followUpInImageJSONArray;
    }

    public void setFollowUpInImageJSONArray(List<ImageListModel> followUpInImageJSONArray) {
        this.followUpInImageJSONArray = followUpInImageJSONArray;
    }

    public String getCsrId() {
        return csrId;
    }

    public void setCsrId(String csrId) {
        this.csrId = csrId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConRental() {
        return conRental;
    }

    public void setConRental(String conRental) {
        this.conRental = conRental;
    }

    public String getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(String remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public String getMoneyReceipt() {
        return moneyReceipt;
    }

    public void setMoneyReceipt(String moneyReceipt) {
        this.moneyReceipt = moneyReceipt;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPredictMoney() {
        return predictMoney;
    }

    public void setPredictMoney(String predictMoney) {
        this.predictMoney = predictMoney;
    }

    public String getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(String followUpId) {
        this.followUpId = followUpId;
    }

    public String getFollowUpTime() {
        return followUpTime;
    }

    public void setFollowUpTime(String followUpTime) {
        this.followUpTime = followUpTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
