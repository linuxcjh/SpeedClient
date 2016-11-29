package com.rongfeng.speedclient.login;

import com.rongfeng.speedclient.common.BaseTransModel;

/**
 * AUTHOR: Alex
 * DATE: 10/11/2015 17:02
 */
public class TransDataModel extends BaseTransModel {


    private String flg;
    private String page;//	当前页
    private String rows = "20";

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
