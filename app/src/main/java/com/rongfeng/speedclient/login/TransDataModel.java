package com.rongfeng.speedclient.login;

import com.rongfeng.speedclient.common.BaseTransModel;

/**
 * AUTHOR: Alex
 * DATE: 10/11/2015 17:02
 */
public class TransDataModel extends BaseTransModel {


    private String flg;
    private String page;//	当前页
    private String rows;

    private String csrId;

    private String searchNote;//笔记日期
    private String noteContent;

    private String isArrears;//1查询有欠款0查询所有

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
