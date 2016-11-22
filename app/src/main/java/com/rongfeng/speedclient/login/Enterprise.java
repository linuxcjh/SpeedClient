package com.rongfeng.speedclient.login;

import java.util.List;

/**
 * dq
 * 2016/1/23
 */
public class Enterprise {


    //    private String userId;//             用户ID
//    private String userName;//           用户姓名
    private String isCompany;//          是否账号多个公司 ：0 一个公司 1多个公司
    //    private String tenementId;//         租户ID
//    private String departmentId;//       部门ID
//    private String departmentName;//     部门名称
//    private String isLead;//是否管理者
//    private String userPosition;//职位
//    private String userPhone;
//    private String userImageUrl;
//    private String isAlly;
//    private String isAllyPlayers;
//    private String userEmail;
    private List<User> arr;

    public String getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(String isCompany) {
        this.isCompany = isCompany;
    }

    public List<User> getArr() {
        return arr;
    }

    public void setArr(List<User> arr) {
        this.arr = arr;
    }

}