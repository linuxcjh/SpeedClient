package com.rongfeng.speedclient.client.entry;

import java.io.Serializable;

/**
 * AUTHOR: Alex
 * DATE: 26/11/2016 15:04
 */

public class AddContractTransModel implements Serializable{


    private String csrId;//客户ID
    private String conName;//合同名称
    private String conNumber;//合同编号
    private String productId;//产品ID
    private String returnedMoney;//已回款
    private String conRental;//合同金额
    private String remainingBalance;//剩余欠款
    private String businessId;//商机ID
    private String remark;//备注
    private String transactionDate;//成交日期
    private String conId;

    public String getReturnedMoney() {
        return returnedMoney;
    }

    public void setReturnedMoney(String returnedMoney) {
        this.returnedMoney = returnedMoney;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getCsrId() {
        return csrId;
    }

    public void setCsrId(String csrId) {
        this.csrId = csrId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConNumber() {
        return conNumber;
    }

    public void setConNumber(String conNumber) {
        this.conNumber = conNumber;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getConRental() {
        return conRental;
    }

    public void setConRental(String conRental) {
        this.conRental = conRental;
    }

    public String getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(String remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
