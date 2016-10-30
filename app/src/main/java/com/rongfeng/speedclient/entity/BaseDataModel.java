package com.rongfeng.speedclient.entity;

import java.io.Serializable;

/**
 * AUTHOR: Alex
 * DATE: 12/11/2015 11:20
 */
public class BaseDataModel implements Serializable {


    /**
     * dictionaryId : 3c429b769f2645b89401730e22ac281a
     * dictionaryName : 陪产假
     * dictionarySort : 1
     */

    private String dictionaryId;
    private String dictionaryName;
    private String is_select;//1 选中 0为选中

    public String getIs_select() {
        return is_select;
    }

    public void setIs_select(String is_select) {
        this.is_select = is_select;
    }

    public BaseDataModel(){

    }

    public BaseDataModel(String dictionaryId, String dictionaryName) {
        this.dictionaryId = dictionaryId;
        this.dictionaryName = dictionaryName;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }


    public String getDictionaryId() {
        return dictionaryId;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }



}
