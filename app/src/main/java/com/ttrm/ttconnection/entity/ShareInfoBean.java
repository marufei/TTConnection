package com.ttrm.ttconnection.entity;

/**
 * Created by MaRufei on 2017/9/7.
 */

public class ShareInfoBean {

    /**
     * errorCode : 1
     * errorMsg : 获取分享配置成功!
     * data : {"config":{"id":"2","title":"分享内容","content":"分享内存","url":"www.baidu.com","imgurl":"www.baidu.com"}}
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
         * config : {"id":"2","title":"分享内容","content":"分享内存","url":"www.baidu.com","imgurl":"www.baidu.com"}
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
             * title : 分享内容
             * content : 分享内存
             * url : www.baidu.com
             * imgurl : www.baidu.com
             */

            private String id;
            private String title;
            private String content;
            private String url;
            private String imgurl;

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
        }
    }
}
