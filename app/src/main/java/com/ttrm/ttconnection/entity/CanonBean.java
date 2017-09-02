package com.ttrm.ttconnection.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */

public class CanonBean implements Serializable {

    /**
     * errorCode : 1
     * errorMsg : 一键加粉成功！
     * data : {"phoneList":[{"phone":"18369622141","nickname":"18369622141"}]}
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

    public static class DataBean implements Serializable {
        private List<PhoneListBean> phoneList;

        public List<PhoneListBean> getPhoneList() {
            return phoneList;
        }

        public void setPhoneList(List<PhoneListBean> phoneList) {
            this.phoneList = phoneList;
        }

        public static class PhoneListBean implements Serializable {
            /**
             * phone : 18369622141
             * nickname : 18369622141
             */

            private String phone;
            private String nickname;

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
        }
    }
}
