package com.rongfeng.speedclient.login;

import com.rongfeng.speedclient.common.BaseTransModel;

/**
 * AUTHOR: Alex
 * DATE: 10/11/2015 17:02
 */
public class TransDataModel extends BaseTransModel {


    private String flg;
    private String page;//	当前页
    private String rows = "30";

    private String csrId;

    private String searchNote;//笔记日期
    private String noteContent;

    private String isArrears;//1查询有欠款0查询所有

    private String radarType;//0过去7天跟进    1.未来7天跟进

    private String clientType;

    private String departmentId;//登录人部门ID

    private String searchDepartmentId;//查询部门的ID

    private String departmentName;//新增部门名称

    private String departmentParentId;//登录人部门ID

    private String attention;//0未关注 1 已关注

    private String longitude;
    private String latitude;
    private String type; //类型（0位置1图片）
    private String address;
    private String positionImages;

    //管理查询
    private String queryId;//查询人目标用户ID
    private String startDate;//查询月后边加-01
    private String endDate;//查询月后边加-01

    private String manageStartDate;
    private String manageEndDate;


    private String productName; //新增产品


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManageStartDate() {
        return manageStartDate;
    }

    public void setManageStartDate(String manageStartDate) {
        this.manageStartDate = manageStartDate;
    }

    public String getManageEndDate() {
        return manageEndDate;
    }

    public void setManageEndDate(String manageEndDate) {
        this.manageEndDate = manageEndDate;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPositionImages() {
        return positionImages;
    }

    public void setPositionImages(String positionImages) {
        this.positionImages = positionImages;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getDepartmentParentId() {
        return departmentParentId;
    }

    public void setDepartmentParentId(String departmentParentId) {
        this.departmentParentId = departmentParentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getSearchDepartmentId() {
        return searchDepartmentId;
    }

    public void setSearchDepartmentId(String searchDepartmentId) {
        this.searchDepartmentId = searchDepartmentId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getRadarType() {
        return radarType;
    }

    public void setRadarType(String radarType) {
        this.radarType = radarType;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getIsArrears() {
        return isArrears;
    }

    public void setIsArrears(String isArrears) {
        this.isArrears = isArrears;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getSearchNote() {
        return searchNote;
    }

    public void setSearchNote(String searchNote) {
        this.searchNote = searchNote;
    }

    public String getCsrId() {
        return csrId;
    }

    public void setCsrId(String csrId) {
        this.csrId = csrId;
    }

    public TransDataModel() {
    }


    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public TransDataModel(String flg) {
        this.flg = flg;
    }

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }
}
