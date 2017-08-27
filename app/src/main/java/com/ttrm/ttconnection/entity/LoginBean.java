package com.ttrm.ttconnection.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/26.
 */

public class LoginBean implements Serializable {

    /**
     * errorCode : 1
     * errorMsg : 登录成功！
     * data : {"userInfo":{"id":"3","phone":"15251759989","nickname":"天天3332","regtime":"20170815125826","regcode":"144U","UID":"3","login_token":"efdmc7qn463kjsd4neld1pdq10"}}
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
         * userInfo : {"id":"3","phone":"15251759989","nickname":"天天3332","regtime":"20170815125826","regcode":"144U","UID":"3","login_token":"efdmc7qn463kjsd4neld1pdq10"}
         */

        private UserInfoBean userInfo;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class UserInfoBean {
            /**
             * id : 3
             * phone : 15251759989
             * nickname : 天天3332
             * regtime : 20170815125826
             * regcode : 144U
             * UID : 3
             * login_token : efdmc7qn463kjsd4neld1pdq10
             */

            private String id;
            private String phone;
            private String nickname;
            private String regtime;
            private String regcode;
            private String UID;
            private String login_token;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getRegtime() {
                return regtime;
            }

            public void setRegtime(String regtime) {
                this.regtime = regtime;
            }

            public String getRegcode() {
                return regcode;
            }

            public void setRegcode(String regcode) {
                this.regcode = regcode;
            }

            public String getUID() {
                return UID;
            }

            public void setUID(String UID) {
                this.UID = UID;
            }

            public String getLogin_token() {
                return login_token;
            }

            public void setLogin_token(String login_token) {
                this.login_token = login_token;
            }
        }
    }
}
