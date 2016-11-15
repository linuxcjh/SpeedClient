package com.rongfeng.speedclient.login;

/**
 * Created by 唐磊 on 2015/11/25.
 */
public class User {
    private String userId;//             用户ID
    private String userName;//           用户姓名
    private String isCompany;//          是否账号多个公司 ：0 一个公司 1多个公司
    private String tenementId;//         租户ID
    private String departmentId;//       部门ID
    private String departmentName;//     部门名称
    private String isLead;//是否管理者
    private String userPosition;//职位
    private String userPhone;
    private String userImageUrl;
    private String isAlly;
    private String isAllyPlayers;
    private String userEmail;
    private String tenementName;//    "tenementName":"华为科技",
    private String userAccount;
    private String userSupervisorId	;//	上级领导ID
    private String userSupervisorName	;//	上级领导名称
    private String schemaFlag;//唯一schema标识
    private String isSystem;//是不是系统管理员
    private String isLose;//isLose 0：正常；1：账号过期；2：手机与账号不匹配
    private String isEffect;//大于0 ：账号未生效：否则生效可用   已废弃
    private String helpUrl;
    private String userAccessionTime;//入职时间
    private String userNum;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getUserAccessionTime() {
        return userAccessionTime;
    }

    public void setUserAccessionTime(String userAccessionTime) {
        this.userAccessionTime = userAccessionTime;
    }

    public String getHelpUrl() {
        return helpUrl;
    }

    public void setHelpUrl(String helpUrl) {
        this.helpUrl = helpUrl;
    }

    public String getIsEffect() {
        return isEffect;
    }

    public void setIsEffect(String isEffect) {
        this.isEffect = isEffect;
    }

    public String getIsLose() {
        return isLose;
    }

    public void setIsLose(String isLose) {
        this.isLose = isLose;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    public String getSchemaFlag() {
        return schemaFlag;
    }

    public void setSchemaFlag(String schemaFlag) {
        this.schemaFlag = schemaFlag;
    }

    public String getUserSupervisorId() {
        return userSupervisorId;
    }

    public void setUserSupervisorId(String userSupervisorId) {
        this.userSupervisorId = userSupervisorId;
    }

    public String getUserSupervisorName() {
        return userSupervisorName;
    }

    public void setUserSupervisorName(String userSupervisorName) {
        this.userSupervisorName = userSupervisorName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getTenementName() {
        return tenementName;
    }

    public void setTenementName(String tenementName) {
        this.tenementName = tenementName;
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

    public String getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(String isCompany) {
        this.isCompany = isCompany;
    }

    public String getTenementId() {
        return tenementId;
    }

    public void setTenementId(String tenementId) {
        this.tenementId = tenementId;
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

    public String getIsLead() {
        return isLead;
    }

    public void setIsLead(String isLead) {
        this.isLead = isLead;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getIsAlly() {
        return isAlly;
    }

    public void setIsAlly(String isAlly) {
        this.isAlly = isAlly;
    }

    public String getIsAllyPlayers() {
        return isAllyPlayers;
    }

    public void setIsAllyPlayers(String isAllyPlayers) {
        this.isAllyPlayers = isAllyPlayers;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
