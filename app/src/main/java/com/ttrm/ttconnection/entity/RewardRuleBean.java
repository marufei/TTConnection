package com.ttrm.ttconnection.entity;

import java.util.List;

/**
 * Created by MaRufei
 * on 2017/12/7.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class RewardRuleBean {

    /**
     * errorCode : 1
     * errorMsg : 获取钻石类型成功！
     * data : {"cataList":[{"id":"1","name":"签到","cataid":"1","diacount":"10"},{"id":"2","name":"一键加粉","cataid":"2","diacount":"10"},{"id":"3","name":"地区加粉","cataid":"3","diacount":"10"},{"id":"4","name":"微信好友分享","cataid":"4","diacount":"5"},{"id":"5","name":"朋友圈分享","cataid":"5","diacount":"10"},{"id":"6","name":"qq好友分享","cataid":"6","diacount":"5"},{"id":"7","name":"qq动态分享","cataid":"7","diacount":"10"},{"id":"8","name":"兑换码兑换","cataid":"8","diacount":"0"},{"id":"9","name":"淘宝浏览","cataid":"9","diacount":"20"},{"id":"10","name":"购买爆机","cataid":"19","diacount":"0"},{"id":"11","name":"注册奖励","cataid":"10","diacount":"30"},{"id":"12","name":"推荐注册奖励","cataid":"11","diacount":"30"}]}
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
        private List<CataListBean> cataList;

        public List<CataListBean> getCataList() {
            return cataList;
        }

        public void setCataList(List<CataListBean> cataList) {
            this.cataList = cataList;
        }

        public static class CataListBean {
            /**
             * id : 1
             * name : 签到
             * cataid : 1
             * diacount : 10
             */

            private String id;
            private String name;
            private String cataid;
            private String diacount;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCataid() {
                return cataid;
            }

            public void setCataid(String cataid) {
                this.cataid = cataid;
            }

            public String getDiacount() {
                return diacount;
            }

            public void setDiacount(String diacount) {
                this.diacount = diacount;
            }
        }
    }
}
