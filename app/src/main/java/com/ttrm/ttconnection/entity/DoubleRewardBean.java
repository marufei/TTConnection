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
public class DoubleRewardBean {

    /**
     * errorCode : 1
     * errorMsg : 获取额外推荐奖励明细成功
     * data : {"reward":"2000","month":"2018年02月","addedLog":[{"id":"4","userid":"10670","pcount":"5","reward":"250","addtime":"20180204010104","dotime":"20180205172341","status":"2","remark":"邀请5位好友+250钻","phone":"18021407369"},{"id":"1","userid":"10670","pcount":"5","reward":"250","addtime":"20180202000000","dotime":"20180205172356","status":"2","remark":"邀请5位好友+250钻","phone":"18021407369"},{"id":"2","userid":"10670","pcount":"10","reward":"500","addtime":"20180202000000","dotime":"20180204211956","status":"2","remark":"邀请10位好友+500钻","phone":"18021407369"},{"id":"3","userid":"10670","pcount":"20","reward":"1000","addtime":"20180201000000","dotime":"20180204223125","status":"2","remark":"邀请20位好友+1000钻","phone":"18021407369"}]}
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
         * reward : 2000
         * month : 2018年02月
         * addedLog : [{"id":"4","userid":"10670","pcount":"5","reward":"250","addtime":"20180204010104","dotime":"20180205172341","status":"2","remark":"邀请5位好友+250钻","phone":"18021407369"},{"id":"1","userid":"10670","pcount":"5","reward":"250","addtime":"20180202000000","dotime":"20180205172356","status":"2","remark":"邀请5位好友+250钻","phone":"18021407369"},{"id":"2","userid":"10670","pcount":"10","reward":"500","addtime":"20180202000000","dotime":"20180204211956","status":"2","remark":"邀请10位好友+500钻","phone":"18021407369"},{"id":"3","userid":"10670","pcount":"20","reward":"1000","addtime":"20180201000000","dotime":"20180204223125","status":"2","remark":"邀请20位好友+1000钻","phone":"18021407369"}]
         */

        private String reward;
        private String month;
        private List<AddedLogBean> addedLog;

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

        public List<AddedLogBean> getAddedLog() {
            return addedLog;
        }

        public void setAddedLog(List<AddedLogBean> addedLog) {
            this.addedLog = addedLog;
        }

        public static class AddedLogBean {
            /**
             * id : 4
             * userid : 10670
             * pcount : 5
             * reward : 250
             * addtime : 20180204010104
             * dotime : 20180205172341
             * status : 2
             * remark : 邀请5位好友+250钻
             * phone : 18021407369
             */

            private String id;
            private String userid;
            private String pcount;
            private String reward;
            private String addtime;
            private String dotime;
            private String status;
            private String remark;
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

            public String getPcount() {
                return pcount;
            }

            public void setPcount(String pcount) {
                this.pcount = pcount;
            }

            public String getReward() {
                return reward;
            }

            public void setReward(String reward) {
                this.reward = reward;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getDotime() {
                return dotime;
            }

            public void setDotime(String dotime) {
                this.dotime = dotime;
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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}
