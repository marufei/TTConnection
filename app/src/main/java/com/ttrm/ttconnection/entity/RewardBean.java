package com.ttrm.ttconnection.entity;

/**
 * Created by MaRufei on 2017/9/9.
 */

public class RewardBean {

    /**
     * errorCode : 1
     * errorMsg : 获取推荐信息成功！
     * data : {"recomCount":"0","income":0,"balance":0,"restCount":"30","alldiaCount":"30"}
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
         * recomCount : 0
         * income : 0
         * balance : 0
         * restCount : 30
         * alldiaCount : 30
         */

        private String recomCount;
        private int income;
        private int balance;
        private String restCount;
        private String alldiaCount;

        public String getRecomCount() {
            return recomCount;
        }

        public void setRecomCount(String recomCount) {
            this.recomCount = recomCount;
        }

        public int getIncome() {
            return income;
        }

        public void setIncome(int income) {
            this.income = income;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public String getRestCount() {
            return restCount;
        }

        public void setRestCount(String restCount) {
            this.restCount = restCount;
        }

        public String getAlldiaCount() {
            return alldiaCount;
        }

        public void setAlldiaCount(String alldiaCount) {
            this.alldiaCount = alldiaCount;
        }
    }
}
