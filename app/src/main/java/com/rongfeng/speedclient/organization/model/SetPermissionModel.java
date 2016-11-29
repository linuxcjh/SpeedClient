package com.rongfeng.speedclient.organization.model;

/**
 * AUTHOR: Alex
 * DATE: 29/11/2016 21:43
 */

public class SetPermissionModel {

    private String queryId;//设置人员Id
    private String queryName;//设置人员名称
    private String isAlly;//是否有部门权限（0无部门权限 1有部门权限）
    private String contactPosition;//职位
    private String departmentId;//设置部门ID

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getIsAlly() {
        return isAlly;
    }

    public void setIsAlly(String isAlly) {
        this.isAlly = isAlly;
    }

    public String getContactPosition() {
        return contactPosition;
    }

    public void setContactPosition(String contactPosition) {
        this.contactPosition = contactPosition;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
