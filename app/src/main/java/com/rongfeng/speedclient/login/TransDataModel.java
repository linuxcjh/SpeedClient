package com.rongfeng.speedclient.login;

import com.rongfeng.speedclient.common.BaseTransModel;

/**
 * AUTHOR: Alex
 * DATE: 10/11/2015 17:02
 */
public class TransDataModel extends BaseTransModel {


    private String flg;
    private String page;//	当前页

    private String csrId;



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
