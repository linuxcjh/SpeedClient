package com.rongfeng.speedclient.client.entry;

/**
 * AUTHOR: Alex
 * DATE: 25/11/2016 22:08
 */

public class AddClientTransModel {

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
//    private List<TeamUserModel> csrTeamJsonArray;//   协作人 teamUserName ，teamUserId
//    private List<ContactModel> csrContactJsonArray;//

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