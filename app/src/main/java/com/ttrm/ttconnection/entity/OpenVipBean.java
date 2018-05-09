package com.ttrm.ttconnection.entity;

import java.util.List;

/**
 * Created by MaRufei
 * on 2018/1/5.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class OpenVipBean {

    /**
     * errorCode : 1
     * errorMsg : 获取开通vip规则列表成功!
     * data : {"ruleList":[{"id":"5","vipdiamond":"1","saleprice":0,"remark":"可领10个兑换码","type":"1"},{"id":"6","vipdiamond":"30","saleprice":30,"remark":"可领取300个兑换码","type":"1"},{"id":"7","vipdiamond":"365","saleprice":240,"remark":"可领取3650个兑换码","type":"1"}]}
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
             * id : 5
             * vipdiamond : 1
             * saleprice : 0
             * remark : 可领10个兑换码
             * type : 1
             */

            private String id;
            private String vipdiamond;
            private int saleprice;
            private String remark;
            private String type;
            private boolean isSelect;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVipdiamond() {
                return vipdiamond;
            }

            public void setVipdiamond(String vipdiamond) {
                this.vipdiamond = vipdiamond;
            }

            public int getSaleprice() {
                return saleprice;
            }

            public void setSaleprice(int saleprice) {
                this.saleprice = saleprice;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
