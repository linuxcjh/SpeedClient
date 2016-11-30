package com.rongfeng.speedclient.client.entry;

import com.rongfeng.speedclient.entity.BaseDataModel;

import java.io.Serializable;
import java.util.List;

/**
 * AUTHOR: Alex
 * DATE: 25/11/2016 22:08
 */

public class RecievedClientTransModel implements Serializable{

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
    private List<ContactPersonModel> csrContactJsonArray;

    private String csrId;//客户ID
    private String attention;



    /**
     * 客户画像
     *
     * @return
     */
    private String contactCount;//联系人数量
    private String followUpCount;//根进数量


    private List<BaseDataModel> fixationJsonArray;// josnArray
    private String tagCount ;//标签数量
    private String businessMoney ;//商机金额
    private String  turnoverMoney ;//成交金额
    private String  arrearsMoney ;//欠款金额



    private String customerSourceName;
    private String customerIndustryName;
    private String customerLevelName;


    public String getCustomerSourceName() {
        return customerSourceName;
    }

    public void setCustomerSourceName(String customerSourceName) {
        this.customerSourceName = customerSourceName;
    }

    public String getCustomerIndustryName() {
        return customerIndustryName;
    }

    public void setCustomerIndustryName(String customerIndustryName) {
        this.customerIndustryName = customerIndustryName;
    }

    public String getCustomerLevelName() {
        return customerLevelName;
    }

    public void setCustomerLevelName(String customerLevelName) {
        this.customerLevelName = customerLevelName;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public List<BaseDataModel> getFixationJsonArray() {
        return fixationJsonArray;
    }

    public void setFixationJsonArray(List<BaseDataModel> fixationJsonArray) {
        this.fixationJsonArray = fixationJsonArray;
    }

    public String getTagCount() {
        return tagCount;
    }

    public void setTagCount(String tagCount) {
        this.tagCount = tagCount;
    }

    public String getBusinessMoney() {
        return businessMoney;
    }

    public void setBusinessMoney(String businessMoney) {
        this.businessMoney = businessMoney;
    }

    public String getTurnoverMoney() {
        return turnoverMoney;
    }

    public void setTurnoverMoney(String turnoverMoney) {
        this.turnoverMoney = turnoverMoney;
    }

    public String getArrearsMoney() {
        return arrearsMoney;
    }

    public void setArrearsMoney(String arrearsMoney) {
        this.arrearsMoney = arrearsMoney;
    }

    public String getContactCount() {
        return contactCount;
    }

    public void setContactCount(String contactCount) {
        this.contactCount = contactCount;
    }

    public String getFollowUpCount() {
        return followUpCount;
    }

    public void setFollowUpCount(String followUpCount) {
        this.followUpCount = followUpCount;
    }

    public String getCsrId() {
        return csrId;
    }

    public void setCsrId(String csrId) {
        this.csrId = csrId;
    }


//    public String getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(String createTime) {
//        this.createTime = createTime;
//    }

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

    public List<ContactPersonModel> getCsrContactJsonArray() {
        return csrContactJsonArray;
    }

    public void setCsrContactJsonArray(List<ContactPersonModel> csrContactJsonArray) {
        this.csrContactJsonArray = csrContactJsonArray;
    }

//
////    public List<TeamUserModel> getCsrTeamJsonArray() {
////        return csrTeamJsonArray;
////    }
////
////    public void setCsrTeamJsonArray(List<TeamUserModel> csrTeamJsonArray) {
////        this.csrTeamJsonArray = csrTeamJsonArray;
////    }
////
////    public List<ContactModel> getCsrContactJsonArray() {
////        return csrContactJsonArray;
////    }
////
////    public void setCsrContactJsonArray(List<ContactModel> csrContactJsonArray) {
////        this.csrContactJsonArray = csrContactJsonArray;
//    }
}
