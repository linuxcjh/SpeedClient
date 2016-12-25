package com.rongfeng.speedclient.dynamic.model;

/**
 * AUTHOR: Alex
 * DATE: 25/12/2016 21:59
 */

public class DynamicModel {

    private int dynamicType;// 类型1.第一次进入系统2跟进客户3.保存日志4编辑日志5.创建客户6编辑客户7创建商机8编辑商机9新增成交10编辑成交11收款12位置记录
    private String dynamicTitle;//
    private String dynamicVal;//
    private String createTime;//

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
