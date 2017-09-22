package com.ttrm.ttconnection.entity;

/**
 * Created by MaRufei on 2017/9/7.
 */

public class ShareInfoBean {

    /**
     * errorCode : 1
     * errorMsg : 获取分享配置成功!
     * data : {"config":{"id":"2","title":"添添人脉为你添添人脉","content":"不好用算我的！能对你温柔如水，也能让你三观尽毁，上添添人脉","url":"http://www.tiantianrenmai.com/tt/index.php/Home/Index/index","imgurl":"http://www.tiantianrenmai.com/web/logo.jpg","imgurl1":"http://www.tiantianrenmai.com/web/ulogo.jpg"}}
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
         * config : {"id":"2","title":"添添人脉为你添添人脉","content":"不好用算我的！能对你温柔如水，也能让你三观尽毁，上添添人脉","url":"http://www.tiantianrenmai.com/tt/index.php/Home/Index/index","imgurl":"http://www.tiantianrenmai.com/web/logo.jpg","imgurl1":"http://www.tiantianrenmai.com/web/ulogo.jpg"}
         */

        private ConfigBean config;

        public ConfigBean getConfig() {
            return config;
        }

        public void setConfig(ConfigBean config) {
            this.config = config;
        }

        public static class ConfigBean {
            /**
             * id : 2
             * title : 添添人脉为你添添人脉
             * content : 不好用算我的！能对你温柔如水，也能让你三观尽毁，上添添人脉
             * url : http://www.tiantianrenmai.com/tt/index.php/Home/Index/index
             * imgurl : http://www.tiantianrenmai.com/web/logo.jpg
             * imgurl1 : http://www.tiantianrenmai.com/web/ulogo.jpg
             */

            private String id;
            private String title;
            private String content;
            private String url;
            private String imgurl;
            private String imgurl1;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public String getImgurl1() {
                return imgurl1;
            }

            public void setImgurl1(String imgurl1) {
                this.imgurl1 = imgurl1;
            }
        }
    }
}
