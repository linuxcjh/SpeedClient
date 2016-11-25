package com.rongfeng.speedclient.login;

/**
 * AUTHOR: Alex
 * DATE: 10/11/2015 17:02
 */
public class TransDataModel extends BaseTransModel {


    private String flg;

    public TransDataModel() {
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
