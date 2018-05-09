package com.ttrm.ttconnection.entity;

import java.util.List;

/**
 * Created by MaRufei
 * on 2018/2/8.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class ShareBean {

    /**
     * errorCode : 1
     * errorMsg : 获取推荐奖励配置信息成功！
     * data : [{"id":"1","pcount":"5","recomfee":"500","rewardfee":"250","totalfee":"750","orders":"100","remark":"105人"},{"id":"2","pcount":"10","recomfee":"1000","rewardfee":"500","totalfee":"1750","orders":"99","remark":"262.5人"},{"id":"3","pcount":"20","recomfee":"2000","rewardfee":"1000","totalfee":"3750","orders":"98","remark":"562.5人"},{"id":"4","pcount":"30","recomfee":"3000","rewardfee":"1500","totalfee":"6250","orders":"97","remark":"937.5人"},{"id":"5","pcount":"40","recomfee":"4000","rewardfee":"2000","totalfee":"9250","orders":"96","remark":"1387.5人"},{"id":"6","pcount":"50","recomfee":"5000","rewardfee":"2500","totalfee":"12750","orders":"95","remark":"1912.5人"}]
     */

    private int errorCode;
    private String errorMsg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * pcount : 5
         * recomfee : 500
         * rewardfee : 250
         * totalfee : 750
         * orders : 100
         * remark : 105人
         */

        private String id;
        private String pcount;
        private String recomfee;
        private String rewardfee;
        private String totalfee;
        private String orders;
        private String remark;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPcount() {
            return pcount;
        }

        public void setPcount(String pcount) {
            this.pcount = pcount;
        }

        public String getRecomfee() {
            return recomfee;
        }

        public void setRecomfee(String recomfee) {
            this.recomfee = recomfee;
        }

        public String getRewardfee() {
            return rewardfee;
        }

        public void setRewardfee(String rewardfee) {
            this.rewardfee = rewardfee;
        }

        public String getTotalfee() {
            return totalfee;
        }

        public void setTotalfee(String totalfee) {
            this.totalfee = totalfee;
        }

        public String getOrders() {
            return orders;
        }

        public void setOrders(String orders) {
            this.orders = orders;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
