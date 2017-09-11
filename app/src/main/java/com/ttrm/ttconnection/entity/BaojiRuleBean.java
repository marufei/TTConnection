package com.ttrm.ttconnection.entity;

import java.util.List;

/**
 * Created by MaRufei on 2017/9/7.
 */

public class BaojiRuleBean {

    /**
     * errorCode : 1
     * errorMsg : 获取暴击规则列表成功!
     * data : {"ruleList":[{"id":"1","diamondcount":"100","addcount":"20"},{"id":"2","diamondcount":"200","addcount":"50"},{"id":"3","diamondcount":"300","addcount":"90"}]}
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
        private List<RuleListBean> ruleList;

        public List<RuleListBean> getRuleList() {
            return ruleList;
        }

        public void setRuleList(List<RuleListBean> ruleList) {
            this.ruleList = ruleList;
        }

        public static class RuleListBean {
            /**
             * id : 1
             * diamondcount : 100
             * addcount : 20
             */

            private String id;
            private String diamondcount;
            private String addcount;
            private boolean type=false;

            public boolean isType() {
                return type;
            }

            public void setType(boolean type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDiamondcount() {
                return diamondcount;
            }

            public void setDiamondcount(String diamondcount) {
                this.diamondcount = diamondcount;
            }

            public String getAddcount() {
                return addcount;
            }

            public void setAddcount(String addcount) {
                this.addcount = addcount;
            }
        }
    }
}
