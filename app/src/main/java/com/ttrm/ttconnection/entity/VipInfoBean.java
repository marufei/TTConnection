package com.ttrm.ttconnection.entity;

/**
 * Created by MaRufei
 * on 2018/1/5.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class VipInfoBean {

    /**
     * errorCode : 1
     * errorMsg : 获取vip信息成功
     * data : {"vipStatus":2,"vipTime":0,"vipTimeStr":"0"}
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
         * vipStatus : 2
         * vipTime : 0
         * vipTimeStr : 0
         */

        private int vipStatus;
        private int vipTime;
        private String vipTimeStr;

        public int getVipStatus() {
            return vipStatus;
        }

        public void setVipStatus(int vipStatus) {
            this.vipStatus = vipStatus;
        }

        public int getVipTime() {
            return vipTime;
        }

        public void setVipTime(int vipTime) {
            this.vipTime = vipTime;
        }

        public String getVipTimeStr() {
            return vipTimeStr;
        }

        public void setVipTimeStr(String vipTimeStr) {
            this.vipTimeStr = vipTimeStr;
        }
    }
}
