package com.ttrm.ttconnection.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MaRufei on 2017/9/1.
 */

public class WXPayAllData {

    /**
     * errorCode : 1
     * errorMsg : 微信预支付成功
     * data : {"wxdata":{"return_code":"SUCCESS","return_msg":"OK","appid":"wxfbeeb896fdcebcb8","mch_id":"1487994002","nonce_str":"zvGngA0KooFaYlx2","sign":"22A0FF2366C188BE6F3E77FFF481AFFD","result_code":"SUCCESS","prepay_id":"wx20170901154039e03f3ded0e0513324464","trade_type":"APP","timeStamp":1504251639,"package":"Sign=WXPay","out_trade_no":"DD1504251638421"},"alidata":[]}
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
         * wxdata : {"return_code":"SUCCESS","return_msg":"OK","appid":"wxfbeeb896fdcebcb8","mch_id":"1487994002","nonce_str":"zvGngA0KooFaYlx2","sign":"22A0FF2366C188BE6F3E77FFF481AFFD","result_code":"SUCCESS","prepay_id":"wx20170901154039e03f3ded0e0513324464","trade_type":"APP","timeStamp":1504251639,"package":"Sign=WXPay","out_trade_no":"DD1504251638421"}
         * alidata : []
         */

        private WxdataBean wxdata;
        private List<?> alidata;

        public WxdataBean getWxdata() {
            return wxdata;
        }

        public void setWxdata(WxdataBean wxdata) {
            this.wxdata = wxdata;
        }

        public List<?> getAlidata() {
            return alidata;
        }

        public void setAlidata(List<?> alidata) {
            this.alidata = alidata;
        }

        public static class WxdataBean {
            /**
             * return_code : SUCCESS
             * return_msg : OK
             * appid : wxfbeeb896fdcebcb8
             * mch_id : 1487994002
             * nonce_str : zvGngA0KooFaYlx2
             * sign : 22A0FF2366C188BE6F3E77FFF481AFFD
             * result_code : SUCCESS
             * prepay_id : wx20170901154039e03f3ded0e0513324464
             * trade_type : APP
             * timeStamp : 1504251639
             * package : Sign=WXPay
             * out_trade_no : DD1504251638421
             */

            private String return_code;
            private String return_msg;
            private String appid;
            private String mch_id;
            private String nonce_str;
            private String sign;
            private String result_code;
            private String prepay_id;
            private String trade_type;
            private String timeStamp;
            @SerializedName("package")
            private String packageX;
            private String out_trade_no;

            public String getReturn_code() {
                return return_code;
            }

            public void setReturn_code(String return_code) {
                this.return_code = return_code;
            }

            public String getReturn_msg() {
                return return_msg;
            }

            public void setReturn_msg(String return_msg) {
                this.return_msg = return_msg;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getMch_id() {
                return mch_id;
            }

            public void setMch_id(String mch_id) {
                this.mch_id = mch_id;
            }

            public String getNonce_str() {
                return nonce_str;
            }

            public void setNonce_str(String nonce_str) {
                this.nonce_str = nonce_str;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getResult_code() {
                return result_code;
            }

            public void setResult_code(String result_code) {
                this.result_code = result_code;
            }

            public String getPrepay_id() {
                return prepay_id;
            }

            public void setPrepay_id(String prepay_id) {
                this.prepay_id = prepay_id;
            }

            public String getTrade_type() {
                return trade_type;
            }

            public void setTrade_type(String trade_type) {
                this.trade_type = trade_type;
            }

            public String getTimeStamp() {
                return timeStamp;
            }

            public void setTimeStamp(String timeStamp) {
                this.timeStamp = timeStamp;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getOut_trade_no() {
                return out_trade_no;
            }

            public void setOut_trade_no(String out_trade_no) {
                this.out_trade_no = out_trade_no;
            }
        }
    }
}
