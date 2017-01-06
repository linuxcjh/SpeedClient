package com.rongfeng.speedclient.client.entry;

import java.io.Serializable;

/**
 * AUTHOR: Alex
 * DATE: 25/11/2016 22:08
 */

public class AddClientTransModel implements Serializable {

    private String customerType;//	客户类型【1企业客户；2个人客户】
    private String customerName;//	客户名称
    private String customerTel;//
    private String customerAddress;//
    private String longitude;//
    private String latitude;//
    private String customerLevel;//
    private String customerIndustry;//
    private String customerSource;//
    private String remark;//
    private String regionId;//
    private String csrTeamJsonArray;//   协
    private String csrContactJsonArray;
    private String remindTime;//最近提醒时间
//    private List<TeamUserModel> csrTeamJsonArray;//   协作人 teamUserName ，teamUserId
//    private List<ContactModel> csrContactJsonArray;//

    /**
     * 客户列表
     */
    private String csrId;//客户ID
    private String lastTime;//	上次拜访时间	yyyy.MM.dd
    private String followCount;//	拜访次数
    private String businessMoney;//	商机
    private String ownerUserName;//归属人
//    private String createTime;//创建时间


    private String fixationJsonArray;

    private String clientNameWordsSplit; //分词


    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getClientNameWordsSplit() {
        return clientNameWordsSplit;
    }

    public void setClientNameWordsSplit(String clientNameWordsSplit) {
        this.clientNameWordsSplit = clientNameWordsSplit;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
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

    public String getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel;
    }

    public String getCustomerIndustry() {
        return customerIndustry;
    }

    public void setCustomerIndustry(String customerIndustry) {
        this.customerIndustry = customerIndustry;
    }

    public String getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(String customerSource) {
        this.customerSource = customerSource;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCsrTeamJsonArray() {
        return csrTeamJsonArray;
    }

    public void setCsrTeamJsonArray(String csrTeamJsonArray) {
        this.csrTeamJsonArray = csrTeamJsonArray;
    }

    public String getCsrContactJsonArray() {
        return csrContactJsonArray;
    }

    public void setCsrContactJsonArray(String csrContactJsonArray) {
        this.csrContactJsonArray = csrContactJsonArray;
    }

    public String getCsrId() {
        return csrId;
    }

    public void setCsrId(String csrId) {
        this.csrId = csrId;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getFollowCount() {
        return followCount;
    }

    public void setFollowCount(String followCount) {
        this.followCount = followCount;
    }

    public String getBusinessMoney() {
        return businessMoney;
    }

    public void setBusinessMoney(String businessMoney) {
        this.businessMoney = businessMoney;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public String getFixationJsonArray() {
        return fixationJsonArray;
    }

    public void setFixationJsonArray(String fixationJsonArray) {
        this.fixationJsonArray = fixationJsonArray;
    }
}
