package com.rongfeng.speedclient.voice.model;

import org.json.*;


public class CsrContactJSONArray {
	
    private String csrContactId;
    private String name;
    private String updateTime;
    
    
	public CsrContactJSONArray () {
		
	}	
        
    public CsrContactJSONArray (JSONObject json) {
    
        this.csrContactId = json.optString("csrContactId");
        this.name = json.optString("name");
        this.updateTime = json.optString("updateTime");

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
