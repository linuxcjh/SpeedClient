package com.rongfeng.speedclient.schedule.model;


import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/27.
 */
public class LinkmanModel  implements Serializable{

    private String tips;//	提醒
    private String contactsCategory;//	联系人分类	0其他; 1待执行

    private String fileId;//头像ID
    private String contactsId;//	联系人ID
    private String iconUrl;//	联系人头像
    private String name;//	联系人姓名
    private String position;//	职位
    private String phone;//	电话
    private String gender;//	性别
    private String birthdayOfApp;//	生日	格式【yyyy-MM-dd】
    private String constellation;//	`星座
    private String carType;//	车型
    private String email;//	邮箱
    private String qq;//	QQ
    private String customerName;//	所属客户名称
    private String contactsType;//	联系人类型 [0普通联系人 1线索]
    private String source;//	线索来源
    private String state;//	线索跟进状态
    private String companyName;//	公司名称
    private String province;//	省
    private String city;//	市
    private String district;//	区
    private String contactsAddress;//	联系人详细地址
    private String longitude;//	经度
    private String latitude;//	纬度
    private String teamworkInfo;// 协作人ID、名称	[{"id":"协作人ID","name":"协作人名称"}]
    private String isFocus;//	是否关注 0未关注 1已关注
    private String ownerId;//	归属人ID
    private String ownerName;//	归属人姓名
    private String areaName;//	省市区地址
    private String bizOppsCount;//	商机个数
    private String salesActivityCount;//	销售活动数
    private String scheduleCount;//	日程个数
    private String remark;//	备注
    private String customerId;


    public LinkmanModel(String name, String iconUrl) {
        this.name = name;
        this.iconUrl = iconUrl;
    }
    public LinkmanModel() {
    }
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getContactsCategory() {
        return contactsCategory;
    }

    public void setContactsCategory(String contactsCategory) {
        this.contactsCategory = contactsCategory;
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdayOfApp() {
        return birthdayOfApp;
    }

    public void setBirthdayOfApp(String birthdayOfApp) {
        this.birthdayOfApp = birthdayOfApp;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactsType() {
        return contactsType;
    }

    public void setContactsType(String contactsType) {
        this.contactsType = contactsType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getContactsAddress() {
        return contactsAddress;
    }

    public void setContactsAddress(String contactsAddress) {
        this.contactsAddress = contactsAddress;
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

    public String getTeamworkInfo() {
        return teamworkInfo;
    }

    public void setTeamworkInfo(String teamworkInfo) {
        this.teamworkInfo = teamworkInfo;
    }


    public String getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(String isFocus) {
        this.isFocus = isFocus;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getBizOppsCount() {
        return bizOppsCount;
    }

    public void setBizOppsCount(String bizOppsCount) {
        this.bizOppsCount = bizOppsCount;
    }

    public String getSalesActivityCount() {
        return salesActivityCount;
    }

    public void setSalesActivityCount(String salesActivityCount) {
        this.salesActivityCount = salesActivityCount;
    }

    public String getScheduleCount() {
        return scheduleCount;
    }

    public void setScheduleCount(String scheduleCount) {
        this.scheduleCount = scheduleCount;
    }
}
