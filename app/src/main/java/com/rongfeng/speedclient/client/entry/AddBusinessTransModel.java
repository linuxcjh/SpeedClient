package com.rongfeng.speedclient.client.entry;

/**
 * AUTHOR: Alex
 * DATE: 25/11/2016 22:08
 */

public class AddBusinessTransModel {

    private String csrId;//
    private String businessName;//商机名称
    private String predictMoney;//预计金额
    private String predictTime;//预计时间
    private String businessStage;//商机阶段    （意向，洽谈，商务，成交）
    private String productId;//商机产品Id
    private String remark;//
    private String businessStageId;
    private String businessStageName;

    public String getBusinessStageId() {
        return businessStageId;
    }

    public void setBusinessStageId(String businessStageId) {
        this.businessStageId = businessStageId;
    }

    public String getBusinessStageName() {
        return businessStageName;
    }

    public void setBusinessStageName(String businessStageName) {
        this.businessStageName = businessStageName;
    }

    public String getCsrId() {
        return csrId;
    }

    public void setCsrId(String csrId) {
        this.csrId = csrId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPredictMoney() {
        return predictMoney;
    }

    public void setPredictMoney(String predictMoney) {
        this.predictMoney = predictMoney;
    }

    public String getPredictTime() {
        return predictTime;
    }

    public void setPredictTime(String predictTime) {
        this.predictTime = predictTime;
    }

    public String getBusinessStage() {
        return businessStage;
    }

    public void setBusinessStage(String businessStage) {
        this.businessStage = businessStage;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
