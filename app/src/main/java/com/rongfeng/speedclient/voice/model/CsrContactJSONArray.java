package com.rongfeng.speedclient.voice.model;

public class CsrContactJSONArray {
	
    private String csrContactId;
    private String name;
    private String phone;
    private String updateTime;
    
    
	public CsrContactJSONArray () {
		
	}

    public CsrContactJSONArray(String csrContactId, String name, String phone) {
        this.csrContactId = csrContactId;
        this.name = name;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCsrContactId() {
        return this.csrContactId;
    }

    public void setCsrContactId(String csrContactId) {
        this.csrContactId = csrContactId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    
}
