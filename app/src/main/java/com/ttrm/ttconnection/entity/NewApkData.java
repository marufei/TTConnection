package com.ttrm.ttconnection.entity;

/**
 * Created by MaRufei on 2017/9/28.
 */

public class NewApkData {

    /**
     * errorCode : 1
     * errorMsg : 获取最新版本信息成功!
     * data : {"version":{"id":"1","version":"1.4","url":"http://www.tiantianrenmai/app/app-anzhimarket-release.apk","msg":"第4版","type":"1","addtime":"20170817125800","sversion":"1"}}
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
         * version : {"id":"1","version":"1.4","url":"http://www.tiantianrenmai/app/app-anzhimarket-release.apk","msg":"第4版","type":"1","addtime":"20170817125800","sversion":"1"}
         */

        private VersionBean version;

        public VersionBean getVersion() {
            return version;
        }

        public void setVersion(VersionBean version) {
            this.version = version;
        }

        public static class VersionBean {
            /**
             * id : 1
             * version : 1.4
             * url : http://www.tiantianrenmai/app/app-anzhimarket-release.apk
             * msg : 第4版
             * type : 1
             * addtime : 20170817125800
             * sversion : 1
             */

            private String id;
            private String version;
            private String url;
            private String msg;
            private String type;
            private String addtime;
            private String sversion;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getSversion() {
                return sversion;
            }

            public void setSversion(String sversion) {
                this.sversion = sversion;
            }
        }
    }
}
