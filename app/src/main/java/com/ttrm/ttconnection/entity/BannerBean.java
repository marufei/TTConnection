package com.ttrm.ttconnection.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MaRufei on 2017/8/31.
 */

public class BannerBean implements Serializable {

    /**
     * errorCode : 1
     * errorMsg : 获取banner成功!
     * data : {"bannerList":[{"id":"1","url":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2998952575,515779096&fm=26&gp=0.jpg","orders":"100","type":"1","link":"http://www.baidu.com","location":"1"},{"id":"2","url":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2998952575,515779096&fm=26&gp=0.jpg","orders":"99","type":"1","link":"http://www.baidu.com","location":"1"},{"id":"3","url":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2998952575,515779096&fm=26&gp=0.jpg","orders":"98","type":"1","link":"http://www.baidu.com","location":"1"},{"id":"4","url":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2998952575,515779096&fm=26&gp=0.jpg","orders":"97","type":"1","link":"http://www.baidu.com","location":"1"},{"id":"5","url":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2998952575,515779096&fm=26&gp=0.jpg","orders":"96","type":"2","link":"LoginActivity","location":"1"}],"popBannerList":[{"id":"6","url":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2998952575,515779096&fm=26&gp=0.jpg","orders":"96","type":"1","link":"LoginActivity","location":"2"}]}
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
        private List<BannerListBean> bannerList;
        private List<PopBannerListBean> popBannerList;

        public List<BannerListBean> getBannerList() {
            return bannerList;
        }

        public void setBannerList(List<BannerListBean> bannerList) {
            this.bannerList = bannerList;
        }

        public List<PopBannerListBean> getPopBannerList() {
            return popBannerList;
        }

        public void setPopBannerList(List<PopBannerListBean> popBannerList) {
            this.popBannerList = popBannerList;
        }

        public static class BannerListBean {
            /**
             * id : 1
             * url : https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2998952575,515779096&fm=26&gp=0.jpg
             * orders : 100
             * type : 1
             * link : http://www.baidu.com
             * location : 1
             */

            private String id;
            private String url;
            private String orders;
            private String type;
            private String link;
            private String location;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getOrders() {
                return orders;
            }

            public void setOrders(String orders) {
                this.orders = orders;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }
        }

        public static class PopBannerListBean {
            /**
             * id : 6
             * url : https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2998952575,515779096&fm=26&gp=0.jpg
             * orders : 96
             * type : 1
             * link : LoginActivity
             * location : 2
             */

            private String id;
            private String url;
            private String orders;
            private String type;
            private String link;
            private String location;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getOrders() {
                return orders;
            }

            public void setOrders(String orders) {
                this.orders = orders;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }
        }
    }
}
