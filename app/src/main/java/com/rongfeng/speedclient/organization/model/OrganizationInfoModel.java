package com.rongfeng.speedclient.organization.model;

import java.io.Serializable;

/**
 * AUTHOR: Alex
 * DATE: 29/11/2016 13:52
 */

public class OrganizationInfoModel implements Serializable{

    private String userId;//用户ID
    private String userName;//用户名称
    private String phone;//手机号码
    private String inviterName;//邀请人
    private String departmentId;//部门ID
    private String departmentName;//部门名称
    private String userPosition;//职位
    private String isForbidden;//是否激活(0正常使用2未激活)
    private String isPermissions;//是否有权限管理部门 0没有权限 1有权限

    /**
     * 本地程序使用
     */
    private boolean isEdit;//是否有改动 true 有改动  false 无改动
    private int index;//新增加的第几个

    public OrganizationInfoModel() {

    }

    public OrganizationInfoModel(String userId, String departmentName) {
        this.userId = userId;
        this.departmentName = departmentName;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInviterName() {
        return inviterName;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getIsForbidden() {
        return isForbidden;
    }

    public void setIsForbidden(String isForbidden) {
        this.isForbidden = isForbidden;
    }

    public String getIsPermissions() {
        return isPermissions;
    }

    public void setIsPermissions(String isPermissions) {
        this.isPermissions = isPermissions;
    }
}
