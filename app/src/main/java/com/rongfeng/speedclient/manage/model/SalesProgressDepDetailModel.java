package com.rongfeng.speedclient.manage.model;

import java.util.List;

/**
 * AUTHOR: Alex
 * DATE: 12/7/2016 16:38
 */
public class SalesProgressDepDetailModel {

    private String targetMoney;    //目标
    private String completeMoney;    //销售
    private String rateMoney;    //百分比
    private List<SalesProgressDepDetailList> completeTargetJSONArray;    //JSONArray对象―12月信息

    public String getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(String targetMoney) {
        this.targetMoney = targetMoney;
    }

    public String getCompleteMoney() {
        return completeMoney;
    }

    public void setCompleteMoney(String completeMoney) {
        this.completeMoney = completeMoney;
    }

    public String getRateMoney() {
        return rateMoney;
    }

    public void setRateMoney(String rateMoney) {
        this.rateMoney = rateMoney;
    }

    public List<SalesProgressDepDetailList> getCompleteTargetJSONArray() {
        return completeTargetJSONArray;
    }

    public void setCompleteTargetJSONArray(List<SalesProgressDepDetailList> completeTargetJSONArray) {
        this.completeTargetJSONArray = completeTargetJSONArray;
    }

    public class SalesProgressDepDetailList {
        private String targetMonth;    //月份数字
        private String targetMoney;    //月目标
        private String contractMoney;    //月销售
        private String monthNumber;    //月份数字
        private String monthNano;    //月份英文简写

        public String getMonthNano() {
            return monthNano;
        }

        public void setMonthNano(String monthNano) {
            this.monthNano = monthNano;
        }

        public String getMonthNumber() {
            return monthNumber;
        }

        public void setMonthNumber(String monthNumber) {
            this.monthNumber = monthNumber;
        }

        public String getTargetMonth() {
            return targetMonth;
        }

        public void setTargetMonth(String targetMonth) {
            this.targetMonth = targetMonth;
        }

        public String getTargetMoney() {
            return targetMoney;
        }

        public void setTargetMoney(String targetMoney) {
            this.targetMoney = targetMoney;
        }

        public String getContractMoney() {
            return contractMoney;
        }

        public void setContractMoney(String contractMoney) {
            this.contractMoney = contractMoney;
        }
    }
}