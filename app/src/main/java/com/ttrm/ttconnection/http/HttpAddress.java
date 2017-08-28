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
    /**
     * 修改昵称
     */
    public static final String EDIT_NAME="/UserCenter/modifyNickname";
    /**
     * 退出登录
     */
    public static final String LOGIN_OUT="/UserCenter/logout";
    /**
     * 查询钻石数量
     */
    public static final String SELECT_DIAMONDS="/Diamond/getDiamondCount";
    /**
     * 获取钻石
     */
    public static final String GET_DIAMONDS="/Diamond/addDiamond";
}
