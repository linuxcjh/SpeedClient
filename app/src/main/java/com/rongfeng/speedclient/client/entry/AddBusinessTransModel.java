package com.rongfeng.speedclient.client.entry;

import java.io.Serializable;

/**
 * AUTHOR: Alex
 * DATE: 25/11/2016 22:08
 */

public class AddBusinessTransModel implements Serializable{

    private String csrId;//
    private String businessName;//商机名称
    private String businessId;//商机ID
    private String predictMoney;//预计金额
    private String predictTime;//预计时间
    private String businessStage;//商机阶段    （意向，洽谈，商务，成交）
    private String productId;//商机产品Id
    private String remark;//
    private String businessStageId;
    private String businessStageName;
    private String productName;//商机产品名称
    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

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
