package com.ttrm.ttconnection.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class BDAddBean implements Serializable {

    /**
     * errorCode : 1
     * errorMsg : 获取被动加粉规则列表成功!
     * data : {"ruleList":[{"id":"4","diamondcount":"0.10","addcount":"2000"},{"id":"5","diamondcount":"0.11","addcount":"1000"},{"id":"6","diamondcount":"0.12","addcount":"60"}]}
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
             * id : 4
             * diamondcount : 0.10
             * addcount : 2000
             */

            private String id;
            private String diamondcount;
            private String addcount;
            private boolean select=false;

            public boolean isSelect() {
                return select;
            }

            public void setSelect(boolean select) {
                this.select = select;
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
