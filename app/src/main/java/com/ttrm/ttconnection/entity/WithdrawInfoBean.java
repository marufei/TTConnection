package com.ttrm.ttconnection.entity;

import java.util.List;

/**
 * Created by MaRufei on 2017/9/17.
 */

public class WithdrawInfoBean {

    /**
     * errorCode : 1
     * errorMsg : 获取提现列表成功
     * data : {"cashLog":[{"id":"2","userid":"961","fee":"50.00","aliaccount":"15556931288","name":"孙加明","addtime":"20170906165043","status":"2","remark":"","optime":"20170907162737","phone":"15556931288"}]}
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
        private List<CashLogBean> cashLog;

        public List<CashLogBean> getCashLog() {
            return cashLog;
        }

        public void setCashLog(List<CashLogBean> cashLog) {
            this.cashLog = cashLog;
        }

        public static class CashLogBean {
            /**
             * id : 2
             * userid : 961
             * fee : 50.00
             * aliaccount : 15556931288
             * name : 孙加明
             * addtime : 20170906165043
             * status : 2
             * remark :
             * optime : 20170907162737
             * phone : 15556931288
             */

            private String id;
            private String userid;
            private String fee;
            private String aliaccount;
            private String name;
            private String addtime;
            private String status;
            private String remark;
            private String optime;
            private String phone;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getAliaccount() {
                return aliaccount;
            }

            public void setAliaccount(String aliaccount) {
                this.aliaccount = aliaccount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getOptime() {
                return optime;
            }

            public void setOptime(String optime) {
                this.optime = optime;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}
