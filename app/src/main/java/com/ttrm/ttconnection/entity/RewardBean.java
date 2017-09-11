package com.ttrm.ttconnection.entity;

/**
 * Created by MaRufei on 2017/9/9.
 */

public class RewardBean {

    /**
     * errorCode : 1
     * errorMsg : 获取推荐信息成功！
     * data : {"recomCount":"1","income":"0","balance":"50"}
     */

    private int errorCode;
    private String errorMsg;
    private DataBean data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * recomCount : 1
         * income : 0
         * balance : 50
         */

        private String recomCount;
        private String income;
        private String balance;

        public String getRecomCount() {
            return recomCount;
        }

        public void setRecomCount(String recomCount) {
            this.recomCount = recomCount;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }
}
