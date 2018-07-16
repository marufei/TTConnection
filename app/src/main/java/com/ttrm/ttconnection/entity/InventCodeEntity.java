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
public class InventCodeEntity{


    /**
     * errorCode : 1
     * errorMsg : 获取订单数据成功
     * data : {"orderList":[{"id":"45423","ordernum":"DD1530666615710","opmoney":"19800","ruleid":"6","addtime":"1530666615","facttime":null,"isauto":"0","codegroup":"32","codenum":"200","restnum":"32","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/19800.png","remark":"支付被动加粉198元套餐"},{"id":"45422","ordernum":"DD153066661162","opmoney":"9900","ruleid":"5","addtime":"1530666611","facttime":null,"isauto":"0","codegroup":"16","codenum":"200","restnum":"16","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/9900.png","remark":"支付被动加粉99元套餐"},{"id":"45421","ordernum":"DD1530666606922","opmoney":"3990","ruleid":"13","addtime":"1530666606","facttime":null,"isauto":"0","codegroup":"12","codenum":"200","restnum":"12","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/3990.png","remark":"支付被动加粉39.9元套餐"},{"id":"45420","ordernum":"DD1530666602952","opmoney":"1989","ruleid":"9","addtime":"1530666602","facttime":null,"isauto":"0","codegroup":"6","codenum":"200","restnum":"6","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/1989.png","remark":"支付被动加粉19.89元套餐"},{"id":"45419","ordernum":"DD1530666595489","opmoney":"990","ruleid":"4","addtime":"1530666596","facttime":null,"isauto":"0","codegroup":"3","codenum":"200","restnum":"3","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/990.png","remark":"支付被动加粉9.9元套餐"}],"count":"5","p":"1","pageSize":"10"}
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
         * orderList : [{"id":"45423","ordernum":"DD1530666615710","opmoney":"19800","ruleid":"6","addtime":"1530666615","facttime":null,"isauto":"0","codegroup":"32","codenum":"200","restnum":"32","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/19800.png","remark":"支付被动加粉198元套餐"},{"id":"45422","ordernum":"DD153066661162","opmoney":"9900","ruleid":"5","addtime":"1530666611","facttime":null,"isauto":"0","codegroup":"16","codenum":"200","restnum":"16","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/9900.png","remark":"支付被动加粉99元套餐"},{"id":"45421","ordernum":"DD1530666606922","opmoney":"3990","ruleid":"13","addtime":"1530666606","facttime":null,"isauto":"0","codegroup":"12","codenum":"200","restnum":"12","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/3990.png","remark":"支付被动加粉39.9元套餐"},{"id":"45420","ordernum":"DD1530666602952","opmoney":"1989","ruleid":"9","addtime":"1530666602","facttime":null,"isauto":"0","codegroup":"6","codenum":"200","restnum":"6","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/1989.png","remark":"支付被动加粉19.89元套餐"},{"id":"45419","ordernum":"DD1530666595489","opmoney":"990","ruleid":"4","addtime":"1530666596","facttime":null,"isauto":"0","codegroup":"3","codenum":"200","restnum":"3","imgurl":"http://www.tiantianrenmai.com/tt/Public/Upload/img/990.png","remark":"支付被动加粉9.9元套餐"}]
         * count : 5
         * p : 1
         * pageSize : 10
         */

        private String count;
        private String p;
        private String pageSize;
        private List<OrderListBean> orderList;

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

        public List<OrderListBean> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListBean> orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            /**
             * id : 45423
             * ordernum : DD1530666615710
             * opmoney : 19800
             * ruleid : 6
             * addtime : 1530666615
             * facttime : null
             * isauto : 0
             * codegroup : 32
             * codenum : 200
             * restnum : 32
             * imgurl : http://www.tiantianrenmai.com/tt/Public/Upload/img/19800.png
             * remark : 支付被动加粉198元套餐
             */

            private String id;
            private String ordernum;
            private String opmoney;
            private String ruleid;
            private String addtime;
            private Object facttime;
            private String isauto;
            private String codegroup;
            private String codenum;
            private String restnum;
            private String imgurl;
            private String remark;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrdernum() {
                return ordernum;
            }

            public void setOrdernum(String ordernum) {
                this.ordernum = ordernum;
            }

            public String getOpmoney() {
                return opmoney;
            }

            public void setOpmoney(String opmoney) {
                this.opmoney = opmoney;
            }

            public String getRuleid() {
                return ruleid;
            }

            public void setRuleid(String ruleid) {
                this.ruleid = ruleid;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public Object getFacttime() {
                return facttime;
            }

            public void setFacttime(Object facttime) {
                this.facttime = facttime;
            }

            public String getIsauto() {
                return isauto;
            }

            public void setIsauto(String isauto) {
                this.isauto = isauto;
            }

            public String getCodegroup() {
                return codegroup;
            }

            public void setCodegroup(String codegroup) {
                this.codegroup = codegroup;
            }

            public String getCodenum() {
                return codenum;
            }

            public void setCodenum(String codenum) {
                this.codenum = codenum;
            }

            public String getRestnum() {
                return restnum;
            }

            public void setRestnum(String restnum) {
                this.restnum = restnum;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
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
