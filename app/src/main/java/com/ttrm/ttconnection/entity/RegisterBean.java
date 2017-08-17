package com.ttrm.ttconnection.entity;

/**
 * Created by marufei
 * time on 2017/8/16
 */

public class RegisterBean extends BData{

    /**
     * userInfo : {"id":"3","phone":"15251759989","nickname":"天天3332","regtime":"20170815125812","regcode":"144U","UID":"3","login_token":"efdmc7qn463kjsd4neld1pdq10"}
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
         * regtime : 20170815125812
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
