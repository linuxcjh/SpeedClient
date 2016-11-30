package com.rongfeng.speedclient.client.entry;

/**
 * AUTHOR: Alex
 * DATE: 30/11/2016 17:26
 */

public class RebackMoneyModel {

    private String businessStageId;//收款ID
    private String gatheringMoney;//收款金额
    private String predictMoney;//剩余金额
    private String gatheringDate;//收款日期


    public String getBusinessStageId() {
        return businessStageId;
    }

    public void setBusinessStageId(String businessStageId) {
        this.businessStageId = businessStageId;
    }

    public String getGatheringMoney() {
        return gatheringMoney;
    }

    public void setGatheringMoney(String gatheringMoney) {
        this.gatheringMoney = gatheringMoney;
    }

    public String getPredictMoney() {
        return predictMoney;
    }

    public void setPredictMoney(String predictMoney) {
        this.predictMoney = predictMoney;
    }

    public String getGatheringDate() {
        return gatheringDate;
    }

    public void setGatheringDate(String gatheringDate) {
        this.gatheringDate = gatheringDate;
    }
}
