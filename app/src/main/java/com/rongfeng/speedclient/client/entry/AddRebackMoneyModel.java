package com.rongfeng.speedclient.client.entry;

/**
 * AUTHOR: Alex
 * DATE: 26/11/2016 15:49
 */

public class AddRebackMoneyModel {

    private String csrId;//客户ID
    private String conId;//合同ID
    private String gatheringMoney;//收款金额
    private String residueMoney;//剩余金额
    private String remark;//备注
    private String collectionDate;//收款日期

    public String getCsrId() {
        return csrId;
    }

    public void setCsrId(String csrId) {
        this.csrId = csrId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getGatheringMoney() {
        return gatheringMoney;
    }

    public void setGatheringMoney(String gatheringMoney) {
        this.gatheringMoney = gatheringMoney;
    }

    public String getResidueMoney() {
        return residueMoney;
    }

    public void setResidueMoney(String residueMoney) {
        this.residueMoney = residueMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }
}
