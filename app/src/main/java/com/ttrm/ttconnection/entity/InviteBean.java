package com.ttrm.ttconnection.entity;

import java.util.List;

/**
 * Created by MaRufei on 2017/9/17.
 */

public class InviteBean {


    /**
     * errorCode : 1
     * errorMsg : 获取推荐列表成功
     * data : {"recomLog":[{"id":"4","userid":"961","recomeduserid":"964","addtime":"1504688040","remark":"邀请15556931288注册成功！","fee":0.5},{"id":"3","userid":"961","recomeduserid":"963","addtime":"1504687251","remark":"邀请15357708891注册成功！","fee":0.5}]}
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
        private List<RecomLogBean> recomLog;

        public List<RecomLogBean> getRecomLog() {
            return recomLog;
        }

        public void setRecomLog(List<RecomLogBean> recomLog) {
            this.recomLog = recomLog;
        }

        public static class RecomLogBean {
            /**
             * id : 4
             * userid : 961
             * recomeduserid : 964
             * addtime : 1504688040
             * remark : 邀请15556931288注册成功！
             * fee : 0.5
             */

            private String id;
            private String userid;
            private String recomeduserid;
            private String addtime;
            private String remark;
            private double fee;

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

            public String getRecomeduserid() {
                return recomeduserid;
            }

            public void setRecomeduserid(String recomeduserid) {
                this.recomeduserid = recomeduserid;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public double getFee() {
                return fee;
            }

            public void setFee(double fee) {
                this.fee = fee;
            }
        }
    }
}
