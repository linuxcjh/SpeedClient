package com.rongfeng.speedclient.organization.model;

import java.io.Serializable;
import java.util.List;

/**
 * AUTHOR: Alex
 * DATE: 29/11/2016 13:52
 */

public class OrganizationReceivedModel implements Serializable{

    private List<OrganizationInfoModel> jsonArrayUser;//人员集合
    private List<OrganizationInfoModel> jsonArrayDep;//部门

    public List<OrganizationInfoModel> getJsonArrayUser() {
        return jsonArrayUser;
    }

    public void setJsonArrayUser(List<OrganizationInfoModel> jsonArrayUser) {
        this.jsonArrayUser = jsonArrayUser;
    }

    public List<OrganizationInfoModel> getJsonArrayDep() {
        return jsonArrayDep;
    }

    public void setJsonArrayDep(List<OrganizationInfoModel> jsonArrayDep) {
        this.jsonArrayDep = jsonArrayDep;
    }
}
