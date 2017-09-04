package com.ttrm.ttconnection.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * TODO 每日签到
 */
public class SignActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView sign_tv_zs;
    private TextView sign_tv_regcode;
    private TextView sign_tv_sign;
    private TextView sign_tv_tb;
    private TextView sign_tv_oneadd;
    private TextView sign_tv_locationadd;
    private TextView sign_tv_wx;
    private TextView sign_tv_circle;
    private TextView sign_tv_qq;
    private TextView sign_tv_space;
    private String TAG="SignActivity";
    private String type;
    private Platform plat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ActivityUtil.add(this);
        initViews();
    }

    private void initViews() {
        sign_tv_zs=(TextView)findViewById(R.id.sign_tv_zs);
        sign_tv_regcode=(TextView)findViewById(R.id.sign_tv_regcode);
        sign_tv_sign=(TextView)findViewById(R.id.sign_tv_sign);
        sign_tv_sign.setOnClickListener(this);
        sign_tv_tb=(TextView)findViewById(R.id.sign_tv_tb);
        sign_tv_tb.setOnClickListener(this);
        sign_tv_oneadd=(TextView)findViewById(R.id.sign_tv_oneadd);
        sign_tv_oneadd.setOnClickListener(this);
        sign_tv_locationadd=(TextView)findViewById(R.id.sign_tv_locationadd);
        sign_tv_locationadd.setOnClickListener(this);
        sign_tv_wx=(TextView)findViewById(R.id.sign_tv_wx);
        sign_tv_wx.setOnClickListener(this);
        sign_tv_circle=(TextView)findViewById(R.id.sign_tv_circle);
        sign_tv_circle.setOnClickListener(this);
        sign_tv_qq=(TextView)findViewById(R.id.sign_tv_qq);
        sign_tv_qq.setOnClickListener(this);
        sign_tv_space=(TextView)findViewById(R.id.sign_tv_space);
        sign_tv_space.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDiamonds();
    }

    /**
     * 查询钻石数量
     */
    public void selectDiamonds(){
        String url= HttpAddress.BASE_URL+HttpAddress.SELECT_DIAMONDS;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    if(errorCode==1){
                        JSONObject jsonObject1=jsonObject.getJSONObject("data");
                        String diamondCount=jsonObject1.getString("diamondCount");
                        sign_tv_zs.setText(diamondCount);
                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(SignActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(SignActivity.this).add(stringRequest);
    }

    /**
     * 获取钻石
     */
    public  void getDiamonds(){
        String url=HttpAddress.BASE_URL+HttpAddress.GET_DIAMONDS;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(SignActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("login_token",SaveUtils.getString(KeyUtils.user_login_token));
                map.put("type",type);
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(SignActivity.this).add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_tv_sign:
                break;
            case R.id.sign_tv_tb:
                break;
            case R.id.sign_tv_oneadd:
                break;
            case R.id.sign_tv_locationadd:
                break;
            case R.id.sign_tv_wx:
//                plat = ShareSDK.getPlatform(Wechat.NAME);
                myShare("");
                break;
            case R.id.sign_tv_circle:
                break;
            case R.id.sign_tv_qq:
                break;
            case R.id.sign_tv_space:
                break;
        }
    }
    /**
     * 分享
     */
    private void myShare(String platform) {
//        MyUtils.Loge(TAG, "Regcode:" + getApplicationContext().getUser().getRegcode());
//        MyUtils.Loge(TAG, "url:" + getApplicationContext().getUser().getAppurl());
        if (true) {
            OnekeyShare oks = new OnekeyShare();
//            if (platform != null)
//                oks.setPlatform(platform);
            //关闭sso授权
            oks.disableSSOWhenAuthorize();

////             分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
//            oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));

//            oks.setCustomerLogo(bmp,"蓝狐微商",null);
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks.setCallback(new MyCallBalk());

            oks.setTitle("分享标题");

            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl("www.baidu.com");

            // text是分享文本，所有平台都需要这个字段
            oks.setText("分享测试");

            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//            oks.setImagePath(path);//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl("www.baidu.com");
//            oks.setImageUrl("file:///android_asset/icon_launcher.png");
//            if (type.equals("1") ||type.equals("2"))
                oks.setImageUrl("http://os93x76c3.bkt.clouddn.com/header@3x.png");
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//            oks.setComment("我是测试评论文本");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(getString(R.string.app_name));

            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl("www.baidu.com");

            // 启动分享GUI
            oks.show(this);
        }

    }
    class MyCallBalk implements PlatformActionListener {

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
    }
}
