package com.ttrm.ttconnection.http;

/**
 * Created by marufei
 * time on 2017/8/16
 */

public class HttpAddress {
    /**
     * 基地址
     */
    public static final String BASE_URL = "http://116.62.195.53/tt/index.php/Api";
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
    public static final String EDIT_NAME = "/UserCenter/modifyNickname";
    /**
     * 退出登录
     */
    public static final String LOGIN_OUT = "/UserCenter/logout";
    /**
     * 查询钻石数量
     */
    public static final String SELECT_DIAMONDS = "/Diamond/getDiamondCount";
    /**
     * 获取钻石
     */
    public static final String GET_DIAMONDS = "/Diamond/addDiamond";
    /**
     * banner
     */
    public static final String GET_BANNER = "/Public/getBannerList";
    /**
     * 获取被加规则
     */
    public static final String GET_BA_RULE = "/Edjf/getjfRule";
    /**
     * 开通加粉
     */
    public static final String ADD_PAY = "/Pay/prepay";
    /**
     * 检测单删好友
     */
    public static final String URL_H5_DELETE = "http://www.tiantianrenmai.com/web/testperson.html";
    /**
     * 新手教学
     */
    public static final String URL_H5_READ = "http://www.tiantianrenmai.com/web/meathod.html";

    /**
     * 获取加粉
     */
    public static final String GET_CANON = "/Jf/jf";
    /**
     * 提交提现申请
     */
    public static final String TO_CASH="/UserCenter/toCash";
    /**
     * 兑换码兑换
     */
    public static final String TO_DH="/UserCenter/todh";
    /**
     * 获取钻石数量
     */
    public static final String GET_DIAMONDCOUNT="/Diamond/getDiamondCount";


}
