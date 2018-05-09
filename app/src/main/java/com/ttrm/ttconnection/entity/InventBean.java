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
public class InventBean {

    /**
     * errorCode : 1
     * errorMsg : 获取推荐列表成功
     * data : {"reward":"220","month":"2018年02月","recomLog":[{"id":"12499","userid":"10670","recomeduserid":"22467","addtime":"1517677264","reward":"100","remark":"邀请152****9989注册成功！"},{"id":"12495","userid":"10670","recomeduserid":"10671","addtime":"1517671107","reward":"30","remark":"邀请133****1565注册成功！"},{"id":"12496","userid":"10670","recomeduserid":"10672","addtime":"1517671107","reward":"30","remark":"邀请188****2001注册成功！"},{"id":"12497","userid":"10670","recomeduserid":"10673","addtime":"1517671107","reward":"30","remark":"邀请177****7812注册成功！"},{"id":"12498","userid":"10670","recomeduserid":"10674","addtime":"1517671107","reward":"30","remark":"邀请152****9610注册成功！"}]}
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
         * reward : 220
         * month : 2018年02月
         * recomLog : [{"id":"12499","userid":"10670","recomeduserid":"22467","addtime":"1517677264","reward":"100","remark":"邀请152****9989注册成功！"},{"id":"12495","userid":"10670","recomeduserid":"10671","addtime":"1517671107","reward":"30","remark":"邀请133****1565注册成功！"},{"id":"12496","userid":"10670","recomeduserid":"10672","addtime":"1517671107","reward":"30","remark":"邀请188****2001注册成功！"},{"id":"12497","userid":"10670","recomeduserid":"10673","addtime":"1517671107","reward":"30","remark":"邀请177****7812注册成功！"},{"id":"12498","userid":"10670","recomeduserid":"10674","addtime":"1517671107","reward":"30","remark":"邀请152****9610注册成功！"}]
         */

        private String reward;
        private String month;
        private List<RecomLogBean> recomLog;

        public String getReward() {
            return reward;
        }

        public void setReward(String reward) {
            this.reward = reward;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public List<RecomLogBean> getRecomLog() {
            return recomLog;
        }

        public void setRecomLog(List<RecomLogBean> recomLog) {
            this.recomLog = recomLog;
        }

        public static class RecomLogBean {
            /**
             * id : 12499
             * userid : 10670
             * recomeduserid : 22467
             * addtime : 1517677264
             * reward : 100
             * remark : 邀请152****9989注册成功！
             */

            private String id;
            private String userid;
            private String recomeduserid;
            private String addtime;
            private String reward;
            private String remark;

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

            public String getReward() {
                return reward;
            }

            public void setReward(String reward) {
                this.reward = reward;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
