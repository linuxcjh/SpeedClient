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
    private boolean isSelect;//1 选中 0为选中


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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
