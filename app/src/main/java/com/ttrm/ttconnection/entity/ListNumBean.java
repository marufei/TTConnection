package com.ttrm.ttconnection.entity;

/**
 * Created by MaRufei on 2017/9/12.
 */

public class ListNumBean {

    /**
     * errorCode : 1
     * errorMsg : 获取剩余加粉数成功！
     * data : {"remainYjCount":150,"remainDqCount":150}
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
         * remainYjCount : 150
         * remainDqCount : 150
         */

        private int remainYjCount;
        private int remainDqCount;

        public int getRemainYjCount() {
            return remainYjCount;
        }

        public void setRemainYjCount(int remainYjCount) {
            this.remainYjCount = remainYjCount;
        }

        public int getRemainDqCount() {
            return remainDqCount;
        }

        public void setRemainDqCount(int remainDqCount) {
            this.remainDqCount = remainDqCount;
        }
    }
}
