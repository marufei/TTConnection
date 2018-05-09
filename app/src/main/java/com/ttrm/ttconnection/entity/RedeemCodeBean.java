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
public class RedeemCodeBean {

    /**
     * errorCode : 1
     * errorMsg : 获取兑换码列表成功
     * data : {"codeList":[{"id":"18912","code":"0f3d157c-777f-8841-45ce","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18911","code":"4a5826dd-16ff-14b9-aeff","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18910","code":"26a3211b-2b38-00c2-e9cf","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18909","code":"23ba9569-a7aa-ac51-39ae","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18908","code":"56cd1f6e-5f94-22e4-9b82","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18907","code":"c9d37a2e-e3a0-be92-10fd","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18906","code":"bad98c73-f325-8c46-f9cc","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18905","code":"f7428838-6d4c-30cb-f9a0","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18904","code":"e1509470-baf0-ad94-18f9","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18903","code":"5e6c6d09-7258-9692-ad98","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"}],"dia":400,"count":"10","p":"1","pageSize":"10"}
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
         * codeList : [{"id":"18912","code":"0f3d157c-777f-8841-45ce","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18911","code":"4a5826dd-16ff-14b9-aeff","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18910","code":"26a3211b-2b38-00c2-e9cf","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18909","code":"23ba9569-a7aa-ac51-39ae","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18908","code":"56cd1f6e-5f94-22e4-9b82","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18907","code":"c9d37a2e-e3a0-be92-10fd","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18906","code":"bad98c73-f325-8c46-f9cc","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18905","code":"f7428838-6d4c-30cb-f9a0","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18904","code":"e1509470-baf0-ad94-18f9","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"},{"id":"18903","code":"5e6c6d09-7258-9692-ad98","uptotime":"","status":"1","addtime":"20180105155508","userid":null,"diacount":"400","type":"1","prouserid":"2361"}]
         * dia : 400
         * count : 10
         * p : 1
         * pageSize : 10
         */

        private int dia;
        private String count;
        private String p;
        private String pageSize;
        private List<CodeListBean> codeList;

        public int getDia() {
            return dia;
        }

        public void setDia(int dia) {
            this.dia = dia;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public List<CodeListBean> getCodeList() {
            return codeList;
        }

        public void setCodeList(List<CodeListBean> codeList) {
            this.codeList = codeList;
        }

        public static class CodeListBean {
            /**
             * id : 18912
             * code : 0f3d157c-777f-8841-45ce
             * uptotime :
             * status : 1
             * addtime : 20180105155508
             * userid : null
             * diacount : 400
             * type : 1
             * prouserid : 2361
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
        }
    }
}
