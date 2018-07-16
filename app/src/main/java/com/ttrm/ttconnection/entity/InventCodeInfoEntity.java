package com.ttrm.ttconnection.entity;

import java.util.List;

/**
 * Created by MaRufei
 * on 2018/7/5.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class InventCodeInfoEntity {

    /**
     * errorCode : 1
     * errorMsg : 获取兑换码数据成功
     * data : {"codeList":[{"id":"280","code":"1036b844-37b2-2610-b49e","uptotime":"","status":"1","addtime":"20180703225213","userid":null,"diacount":"200","type":"1","prouserid":"10670","orderid":"715"},{"id":"281","code":"cc19ffcb-066d-1ec6-ee20","uptotime":"","status":"1","addtime":"20180703225213","userid":null,"diacount":"200","type":"1","prouserid":"10670","orderid":"715"},{"id":"282","code":"f07b0980-bda9-bc04-4625","uptotime":"","status":"1","addtime":"20180703225213","userid":null,"diacount":"200","type":"1","prouserid":"10670","orderid":"715"}]}
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
        private List<CodeListBean> codeList;

        public List<CodeListBean> getCodeList() {
            return codeList;
        }

        public void setCodeList(List<CodeListBean> codeList) {
            this.codeList = codeList;
        }

        public static class CodeListBean {
            /**
             * id : 280
             * code : 1036b844-37b2-2610-b49e
             * uptotime :
             * status : 1
             * addtime : 20180703225213
             * userid : null
             * diacount : 200
             * type : 1
             * prouserid : 10670
             * orderid : 715
             */

            private String id;
            private String code;
            private String uptotime;
            private String status;
            private String addtime;
            private Object userid;
            private String diacount;
            private String type;
            private String prouserid;
            private String orderid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getUptotime() {
                return uptotime;
            }

            public void setUptotime(String uptotime) {
                this.uptotime = uptotime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public Object getUserid() {
                return userid;
            }

            public void setUserid(Object userid) {
                this.userid = userid;
            }

            public String getDiacount() {
                return diacount;
            }

            public void setDiacount(String diacount) {
                this.diacount = diacount;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getProuserid() {
                return prouserid;
            }

            public void setProuserid(String prouserid) {
                this.prouserid = prouserid;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }
        }
    }
}
