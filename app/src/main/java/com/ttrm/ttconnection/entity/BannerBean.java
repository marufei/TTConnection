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
     * data : {"bannerList":[{"id":"15","url":"http://www.tiantianrenmai.com/tt/Public/Upload/goodsimg/20170912/59b780e976b52.jpg","orders":"0","type":"1","link":"http://mp.weixin.qq.com/s/L-GPyxtYaZ4-gY2qH79VMg","location":"1","localurl":"/tt/Public/Upload/goodsimg/20170912/59b780e976b52.jpg"},{"id":"17","url":"http://www.tiantianrenmai.com/tt/Public/Upload/goodsimg/20170921/59c37e3c1b27a.jpg","orders":"0","type":"1","link":"https://mp.weixin.qq.com/s/1jHx7pwoB-enMEJHplFFXQ","location":"1","localurl":"/tt/Public/Upload/goodsimg/20170921/59c37e3c1b27a.jpg"}],"popBannerList":[{"id":"22","url":"http://www.tiantianrenmai.com/tt/Public/Upload/goodsimg/20170921/59c37bdb5830a.png","orders":"2","type":"1","link":"http://www.baidu.com","location":"2","localurl":"/tt/Public/Upload/goodsimg/20170921/59c37bdb5830a.png"}]}
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
             * id : 15
             * url : http://www.tiantianrenmai.com/tt/Public/Upload/goodsimg/20170912/59b780e976b52.jpg
             * orders : 0
             * type : 1
             * link : http://mp.weixin.qq.com/s/L-GPyxtYaZ4-gY2qH79VMg
             * location : 1
             * localurl : /tt/Public/Upload/goodsimg/20170912/59b780e976b52.jpg
             */

            private String id;
            private String url;
            private String orders;
            private String type;
            private String link;
            private String location;
            private String localurl;

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

            public String getLocalurl() {
                return localurl;
            }

            public void setLocalurl(String localurl) {
                this.localurl = localurl;
            }
        }

        public static class PopBannerListBean {
            /**
             * id : 22
             * url : http://www.tiantianrenmai.com/tt/Public/Upload/goodsimg/20170921/59c37bdb5830a.png
             * orders : 2
             * type : 1
             * link : http://www.baidu.com
             * location : 2
             * localurl : /tt/Public/Upload/goodsimg/20170921/59c37bdb5830a.png
             */

            private String id;
            private String url;
            private String orders;
            private String type;
            private String link;
            private String location;
            private String localurl;

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

            public String getLocalurl() {
                return localurl;
            }

            public void setLocalurl(String localurl) {
                this.localurl = localurl;
            }
        }
    }
}
