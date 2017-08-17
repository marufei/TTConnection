package com.ttrm.ttconnection.http;

/**
 * Created by marufei
 * time on 2017/8/16
 */

public class HttpAddress {
    /**
     * 基地址
     */
    public static final String BASE_URL="http://116.62.195.53/tt/index.php/Api";
    /**
     * 注册
     */
    public static final String REGISTER = "/Public/reg";
    /**
     * 登录
     */
    public static final String LOGIN = "/Public/login";
    /**
     * 重置密码
     */
    public static final String RESET_PWD = "/Public/resetPassword";
    /**
     * 短信接口 type 验证码类型1注册2找回密码
     */
    public static final String GET_SMS = "/Public/fsSmsCode";
}
