package com.rongfeng.speedclient.organization.model;

/**
 * AUTHOR: Alex
 * DATE: 29/11/2016 15:33
 */

public class TransOrganizationModel {
    private String name;
    private String phone;
    private String departmentId;
    private String isExist;


    public String getIsExist() {
        return isExist;
    }

    public void setIsExist(String isExist) {
        this.isExist = isExist;
    }

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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
