package com.ttrm.ttconnection.entity;

/**
 * Created by MaRufei
 * on 2018/7/5.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class InventCodeDocEntity {

    /**
     * errorCode : 1
     * errorMsg : 获取兑换码文案说明成功
     * data : {"doc":{"id":"1","content":"&lt;p&gt;官方说明：&lt;/p&gt;&lt;p&gt;每次购买被动加粉都会赠送钻石激活码&lt;/p&gt;&lt;p&gt;【用途】赠送好友，也可用于销售&lt;/p&gt;&lt;p&gt;【使用方法】添添人脉-&amp;gt;我的-&amp;gt;兑换码-&amp;gt;兑换&lt;/p&gt;&lt;p&gt;【失效周期】钻石兑换码没有失效期&lt;/p&gt;&lt;p&gt;【激活码价值】每个激活码都可以兑换200个钻石&lt;br/&gt;&lt;/p&gt;","addtime":"1","title":"兑换码文案","location_at":"1"}}
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
         * doc : {"id":"1","content":"&lt;p&gt;官方说明：&lt;/p&gt;&lt;p&gt;每次购买被动加粉都会赠送钻石激活码&lt;/p&gt;&lt;p&gt;【用途】赠送好友，也可用于销售&lt;/p&gt;&lt;p&gt;【使用方法】添添人脉-&amp;gt;我的-&amp;gt;兑换码-&amp;gt;兑换&lt;/p&gt;&lt;p&gt;【失效周期】钻石兑换码没有失效期&lt;/p&gt;&lt;p&gt;【激活码价值】每个激活码都可以兑换200个钻石&lt;br/&gt;&lt;/p&gt;","addtime":"1","title":"兑换码文案","location_at":"1"}
         */

        private DocBean doc;

        public DocBean getDoc() {
            return doc;
        }

        public void setDoc(DocBean doc) {
            this.doc = doc;
        }

        public static class DocBean {
            /**
             * id : 1
             * content : &lt;p&gt;官方说明：&lt;/p&gt;&lt;p&gt;每次购买被动加粉都会赠送钻石激活码&lt;/p&gt;&lt;p&gt;【用途】赠送好友，也可用于销售&lt;/p&gt;&lt;p&gt;【使用方法】添添人脉-&amp;gt;我的-&amp;gt;兑换码-&amp;gt;兑换&lt;/p&gt;&lt;p&gt;【失效周期】钻石兑换码没有失效期&lt;/p&gt;&lt;p&gt;【激活码价值】每个激活码都可以兑换200个钻石&lt;br/&gt;&lt;/p&gt;
             * addtime : 1
             * title : 兑换码文案
             * location_at : 1
             */

            private String id;
            private String content;
            private String addtime;
            private String title;
            private String location_at;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getLocation_at() {
                return location_at;
            }

            public void setLocation_at(String location_at) {
                this.location_at = location_at;
            }
        }
    }
}
