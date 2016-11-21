package com.rongfeng.speedclient.client.entry;

import java.io.Serializable;

/**
 * 联系人
 * AUTHOR: Alex
 * DATE: 3/12/2015 17:17
 */
public class ContactPersonModel implements Serializable{
    private String fileId;//	联系人头像数据
    private String name;//	联系人姓名
    private String position;//	职位
    private String phone;//	电话
    private String gender;//	性别	1男 0女
    private String birthday;//	生日	格式【yyyy-MM-dd】
    private String constellation;//	`星座
    private String carType;//	车型
    private String email;//	邮箱
    private String qq;//	QQ
    private String remark;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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
}
