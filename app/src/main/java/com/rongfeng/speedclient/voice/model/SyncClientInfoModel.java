package com.rongfeng.speedclient.voice.model;

import java.util.ArrayList;

public class SyncClientInfoModel {
	
    private String customerType;
    private String csrId;
    private ArrayList<CsrContactJSONArray> csrContactJSONArray;
    private String updateTime;
    private String customerName;

    private String clientNameWordsSplit; //分词

    public String getClientNameWordsSplit() {
        return clientNameWordsSplit;
    }

    public void setClientNameWordsSplit(String clientNameWordsSplit) {
        this.clientNameWordsSplit = clientNameWordsSplit;
    }
    
	public SyncClientInfoModel () {
		
	}	
        

    public String getCustomerType() {
        return this.customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCsrId() {
        return this.csrId;
    }

    public void setCsrId(String csrId) {
        this.csrId = csrId;
    }

    public ArrayList<CsrContactJSONArray> getCsrContactJSONArray() {
        return this.csrContactJSONArray;
    }

    public void setCsrContactJSONArray(ArrayList<CsrContactJSONArray> csrContactJSONArray) {
        this.csrContactJSONArray = csrContactJSONArray;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    
}