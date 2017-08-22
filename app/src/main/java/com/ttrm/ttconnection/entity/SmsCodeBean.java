package com.ttrm.ttconnection.entity;

import java.io.Serializable;

/**
 * Created by MaRufei
 * time on 2017/8/22
 * Phone 13213580912
 * Email 867814102@qq.com
 */

public class SmsCodeBean implements Serializable {

    /**
     * errorCode : 1
     * errorMsg : 短信验证码发送成功!
     * data : {"sms_token":"35b4af34a22346e9d658c4fed4e254ac"}
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
         * sms_token : 35b4af34a22346e9d658c4fed4e254ac
         */

        private String sms_token;

        public String getSms_token() {
            return sms_token;
        }

        public void setSms_token(String sms_token) {
            this.sms_token = sms_token;
        }
    }
}
