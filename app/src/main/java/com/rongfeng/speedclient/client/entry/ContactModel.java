package com.rongfeng.speedclient.client.entry;

/**
 * AUTHOR: Alex
 * DATE: 25/11/2016 22:12
 */

public class ContactModel {

    private String name;// 联系人名称
    private String phone;// 联系人电话
    private String contactPosition;// 职位
    private String gender;// 性别【0女 1男】
    private String isPolicymaker;// 是否决策人（0不是决策 人1是决策 人）
    private String birthday;// 生日
    private String qq;// QQ
    private String remark;// 备注
    private String fileId;//      人员头像

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactPosition() {
        return contactPosition;
    }

    public void setContactPosition(String contactPosition) {
        this.contactPosition = contactPosition;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIsPolicymaker() {
        return isPolicymaker;
    }

    public void setIsPolicymaker(String isPolicymaker) {
        this.isPolicymaker = isPolicymaker;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
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
}
