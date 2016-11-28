package com.rongfeng.speedclient.client.entry;

import java.io.Serializable;
import java.util.List;

/**
 * 跟进记录
 * AUTHOR: Alex
 * DATE: 3/12/2015 17:17
 */
public class ClientRecordAllModel implements Serializable {


    private String followUpCount;//跟进数量
    private List<ClientRecordModel> followUpJSONArray;

    public String getFollowUpCount() {
        return followUpCount;
    }

    public void setFollowUpCount(String followUpCount) {
        this.followUpCount = followUpCount;
    }

    public List<ClientRecordModel> getFollowUpJSONArray() {
        return followUpJSONArray;
    }

    public void setFollowUpJSONArray(List<ClientRecordModel> followUpJSONArray) {
        this.followUpJSONArray = followUpJSONArray;
    }
}
