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
     * data : {"ruleList":[{"id":"4","diamondcount":"9.90","addcount":"65","codegroup":"3","remark":"被加40-60人左右（体验）","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/990.png"},{"id":"9","diamondcount":"19.90","addcount":"150","codegroup":"6","remark":"被加120-140人","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/1990.png"},{"id":"13","diamondcount":"39.90","addcount":"320","codegroup":"12","remark":"被加280-310人","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/3990.png"},{"id":"5","diamondcount":"99.00","addcount":"1000","codegroup":"16","remark":"保底被加1000人","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/9900.png"},{"id":"6","diamondcount":"198.00","addcount":"2000","codegroup":"32","remark":"保底被加2000人（赠送1680钻）","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/19800.png"}]}
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
             * diamondcount : 9.90
             * addcount : 65
             * codegroup : 3
             * remark : 被加40-60人左右（体验）
             * imgurl : http://www.tiantianrenmai.com/tt/Public/Upload/img/990.png
             */

            private String id;
            private String diamondcount;
            private String addcount;
            private String codegroup;
            private String remark;
            private String imgurl;
            private boolean select;

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

            public String getCodegroup() {
                return codegroup;
            }

            public void setCodegroup(String codegroup) {
                this.codegroup = codegroup;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }
        }
    }
}
