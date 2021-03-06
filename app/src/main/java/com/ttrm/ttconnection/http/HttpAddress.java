package com.ttrm.ttconnection.http;

/**
 * Created by marufei
 * time on 2017/8/16
 */

public class HttpAddress {
    /**
     * 基地址(测试)
     */
//    public static final String BASE_URL = "http://116.62.195.53/tt/index.php/Api";  //https://www.tiantianrenmai.com

    /**
     * 基地址(正式)
     */
    public static final String BASE_URL = "https://www.tiantianrenmai.com/tt/index.php/Api";  //https://www.tiantianrenmai.com

    /**
     * 首页手机号轮播
     */
    public static final String PHONE_H5 = "http://www.tiantianrenmai.com/tt/index.php/Home/Index/lb";
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
    public static final String URL_H5_DELETE = "http://www.tiantianrenmai.com/tt/index.php/Home/Index/qqlist";
    /**
     * 新手教学
     */
    public static final String URL_H5_READ = "http://www.tiantianrenmai.com/web/meathod.html";

    /**
     * 一键获取加粉
     */
    public static final String GET_CANON = "/Jf/jf";
    /**
     * 地区加粉
     */
    public static final String LOCATION_CANON = "/Jf/areajf";
    /**
     * 提交提现申请
     */
    public static final String TO_CASH = "/UserCenter/toCash";
    /**
     * 兑换码兑换
     */
    public static final String TO_DH = "/UserCenter/todh";
    /**
     * 获取钻石数量
     */
    public static final String GET_DIAMONDCOUNT = "/Diamond/getDiamondCount";
    /**
     * 获取版本信息
     */
    public static final String GET_VERSION = "/Public/getVersion";
    /**
     * 获取推荐信息
     */
    public static final String GET_MAIN_INFO = "/UserCenter/getRecomeInfo";
    /**
     * 获取被加状态
     */
    public static final String GET_ADD_STATUS = "/Edjf/getjfStatus";
    /**
     * 被动加粉开关
     */
    public static final String SELECT_ADD_STATUS = "/Edjf/switchEdJf";
    /**
     * 获取签到状态
     */
    public static final String GET_SIGN_STATUS = "/Diamond/getSignStatus";
    /**
     * 获取分享配置
     */
    public static final String GET_SHARE_INFO = "/Public/getShareConfig";
    /**
     * 获取爆机规则
     */
    public static final String GET_BAOJI_RULE = "/Hit/ruleList";
    /**
     * 爆机
     */
    public static final String BAOJI = "/Hit/hit";
    /**
     * 获取爆机状态
     */
    public static final String GET_BAOJI_STATUS = "/Hit/getHitStatus";
    /**
     * 获取推荐数据
     */
    public static final String GET_REWARD_DATA = "/UserCenter/getRecomeInfo";
    /**
     * 获取剩余加粉数
     */
    public static final String GET_LAST_NUM = "/Jf/jfCount";
    /**
     * 提现列表
     */
    public static final String GET_CASH_LIST = "/UserCenter/cashLog";

    /**
     * 邀请列表
     */
    public static final String GET_INVITE_LIST = "/UserCenter/recomLog";

    /**
     * web邀请码
     */
    public static final String GET_WEB_REG = "/Public/getRegCode";
    /**
     * 工具箱
     */
    public static final String H5_GONGJUXIANG = "http://www.tiantianrenmai.com/tt/index.php/Home/Index/gj.html";


    /**
     * web验证码
     */
    public static final String CODE_H5 = "https://www.tiantianrenmai.com/tt/index.php/Api/ImgCode/verify";
    /**
     * 校验 图形验证码
     */
    public static final String IMAGE_CODE = "/Public/valImgCode";
    /**
     * 首页图片
     */
    public static final String MAIN_PNG = "http://www.tiantianrenmai.com/tt/Public/Upload/img/fxyl.png";
    /**
     * 被动加粉界面背景
     */
    public static final String BDADD_PNG = "http://www.tiantianrenmai.com/tt/Public/Upload/img/jf.png";
    /**
     * 个人中心兑换码图片
     */
    public static final String INFO_PNG = "http://www.tiantianrenmai.com/tt/Public/Upload/img/viptp.png";
    /**
     * 奖励文案
     */
    public static final String REWARD_RULE = "/Public/getDiaCata";
    /**
     * 获取VIP信息
     */
    public static final String INFO_VIP = "/UserCenter/getVipInfo";
    /**
     * 获取开通VIP规则
     */
    public static final String RULE_VIP = "/Code/getVipRuleList";
    /**
     * 开通会员/购买兑换
     */
    public static final String PAY_PAY = "/VipPay/prepay";
    /**
     * 获取兑换码列表
     */
    public static final String REDEEM_CODE_LIST = "/Code/getVipCodeList";
    /**
     * 删除兑换码
     */
    public static final String REDEEM_CODE_DELETE = "/Code/delCode";
    /**
     * 获取推荐配置
     */
    public static final String INVENT_INFO = "/Recom/getRecomConfig";
    /**
     * 推荐记录
     */
    public static final String INVENT_RECORD = "/Recom/recomLog";
    /**
     * 翻倍奖励记录
     */
    public static final String DOUBLE_REWARD_RECORD = "/Recom/getAddedRewardLog";
    /**
     * 更新用户所在城市
     */
    public static final String UPDATE_CITY = "/UserCenter/upArea";
    /**
     * 邀请到50人干货
     */
    public static final String H5_GANHUO = "http://lh.lanhuwangluo.cn/ttrm/?regCode=";
    /**
     * 视频简介
     * 搜索加粉：http://www.nullku.com/ttrm/ttrm_searchAdd.html
     * 附近的人：http://www.nullku.com/ttrm/ttrm_nearbyAdd.html
     * 通讯录加粉：http://www.nullku.com/ttrm/ttrm_contactAdd.html
     * 扫码加粉：http://www.nullku.com/ttrm/ttrm_scanQrAdd.html
     * 群聊加粉：http://www.nullku.com/ttrm/ttrm_qunAdd.html
     */
    public static final String H5_SSJF = "http://www.nullku.com/ttrm/ttrm_searchAdd.html";
    public static final String H5_FJDR = "http://www.nullku.com/ttrm/ttrm_nearbyAdd.html";
    public static final String H5_TTLJF = "http://www.nullku.com/ttrm/ttrm_contactAdd.html";
    public static final String H5_SMJF = "http://www.nullku.com/ttrm/ttrm_scanQrAdd.html";
    public static final String H5_QLJF = "http://www.nullku.com/ttrm/ttrm_qunAdd.html";

    /**
     * 获取邀请码列表
     */
    public static final String INVENT_CODE="/Order/getOrderList";

    /**
     * 兑换码说明文档
     */
    public static final String CODE_DOC="/Order/getCodeDoc";

    /**
     * 钻石列表
     */
    public static final String CODE_LIST="/Order/codeList";

}
