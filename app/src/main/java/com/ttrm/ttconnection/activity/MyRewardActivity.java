package com.ttrm.ttconnection.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.RewardBean;
import com.ttrm.ttconnection.entity.ShareInfoBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class MyRewardActivity extends BaseActivity implements View.OnClickListener {

    private RewardBean rewardBean;
    private TextView reward_tv_reward;
    private TextView reward_tv_balance;
    private TextView reward_tv_yq;
    private TextView reward_tv_recode;
    private Button reward_btn_cash;
    private Button reward_btn_wx;
    private Button reward_btn_qq;
    private Button reward_btn_circle;
    private Button reward_btn_qzone;
    private String TAG="MyRewardActivity";
    private ShareInfoBean shareInfoBean;
    private Platform plat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reward);
        ActivityUtil.add(this);
        initViews();
        initDatas();
    }

    private void initDatas() {
        if(!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_regcode))) {
            reward_tv_recode.setText(SaveUtils.getString(KeyUtils.user_regcode));
        }
        getSignInfo();
        getInfo();
    }

    private void initViews() {
        setToolBar("我的奖励");
        reward_tv_reward=(TextView)findViewById(R.id.reward_tv_reward);
        reward_tv_balance=(TextView)findViewById(R.id.reward_tv_balance);
        reward_tv_yq=(TextView)findViewById(R.id.reward_tv_yq);
        reward_tv_recode=(TextView)findViewById(R.id.reward_tv_recode);
        reward_btn_cash=(Button)findViewById(R.id.reward_btn_cash);
        reward_btn_cash.setOnClickListener(this);
        reward_btn_wx=(Button)findViewById(R.id.reward_btn_wx);
        reward_btn_wx.setOnClickListener(this);
        reward_btn_qq=(Button)findViewById(R.id.reward_btn_qq);
        reward_btn_qq.setOnClickListener(this);
        reward_btn_circle=(Button)findViewById(R.id.reward_btn_circle);
        reward_btn_circle.setOnClickListener(this);
        reward_btn_qzone=(Button)findViewById(R.id.reward_btn_qzone);
        reward_btn_qzone.setOnClickListener(this);
        setMenuBtn("邀请明细",this,InviteActivity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reward_btn_circle:
                selectPermission(WechatMoments.NAME);
                break;
            case R.id.reward_btn_wx:
                selectPermission(Wechat.NAME);
                break;
            case R.id.reward_btn_qq:
                selectPermission(QQ.NAME);
                break;
            case R.id.reward_btn_qzone:
                selectPermission(QZone.NAME);
                break;
            case R.id.reward_btn_cash:
                startActivity(new Intent(MyRewardActivity.this,WithdrawCashActivity.class));
                break;
        }
    }
    /**
     * 获取推荐信息
     */
    public void getInfo(){
        String url= HttpAddress.BASE_URL+HttpAddress.GET_REWARD_DATA;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    Gson gson=new Gson();
                    rewardBean = gson.fromJson(response, RewardBean.class);
                    if(rewardBean!=null){
                        if(rewardBean.getErrorCode()==1) {
                            reward_tv_reward.setText(rewardBean.getData().getIncome()+"元");
                            reward_tv_balance.setText(rewardBean.getData().getBalance()+"元");
                            reward_tv_yq.setText(rewardBean.getData().getRecomCount()+"人");
                        }else {

                        }
                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MyRewardActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
    /**
     * 获取分享配置
     */
    public void getSignInfo(){
        String url=HttpAddress.BASE_URL+HttpAddress.GET_SHARE_INFO;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    Gson gson=new Gson();
                    shareInfoBean=gson.fromJson(response, ShareInfoBean.class);
                    if(shareInfoBean!=null){
                        if(shareInfoBean.getErrorCode()==1){

                        }else {

                        }
                    }
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MyRewardActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
    /**
     * 分享
     */
    private void myShare(String platform) {
//        MyUtils.Loge(TAG, "Regcode:" + getApplicationContext().getUser().getRegcode());
        MyUtils.Loge(TAG, "title:"+shareInfoBean.getData().getConfig().getTitle()+
                "--url:"+shareInfoBean.getData().getConfig().getUrl()+"--context:"+
                shareInfoBean.getData().getConfig().getContent()+"--iamge:"+
                shareInfoBean.getData().getConfig().getImgurl());
        if (true) {
            OnekeyShare oks = new OnekeyShare();
            if (platform != null)
                oks.setPlatform(platform);
            //关闭sso授权
            oks.disableSSOWhenAuthorize();

////             分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
//            oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));

//            oks.setCustomerLogo(bmp,"蓝狐微商",null);
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//            oks.setCallback(new SignActivity.MyCallBalk());

            oks.setTitle(shareInfoBean.getData().getConfig().getTitle());

            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl(shareInfoBean.getData().getConfig().getUrl());

            // text是分享文本，所有平台都需要这个字段
            oks.setText(shareInfoBean.getData().getConfig().getContent());

            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//            oks.setImagePath(path);//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(shareInfoBean.getData().getConfig().getUrl());
//            oks.setImageUrl("file:///android_asset/icon_launcher.png");
//            if (type.equals("1") ||type.equals("2"))
            oks.setImageUrl(shareInfoBean.getData().getConfig().getImgurl());
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//            oks.setComment("我是测试评论文本");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(getString(R.string.app_name));

            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(shareInfoBean.getData().getConfig().getUrl());

            // 启动分享GUI
            oks.show(this);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MyUtils.Loge(TAG, "requestCode=" + requestCode + "; --->" + permissions.toString()
                + "; grantResult=" + grantResults.toString());
        switch (requestCode) {
            case 0: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    // request successfully, handle you transactions
                    plat = ShareSDK.getPlatform(Wechat.NAME);
                    myShare(plat.getName());

                } else {

                    // permission denied
                    // request failed
                    MyUtils.Loge(TAG,"权限失败");
                }

                return;
            }
            default:
                break;

        }
    }
    /**
     * 选择分享平台
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void selectPermission(String name){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {       //6.0以上运行时权限
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                MyUtils.Loge(TAG, "READ permission IS NOT granted...");

                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    MyUtils.Loge(TAG, "11111111111111");
                } else {
                    // 0 是自己定义的请求coude
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                    MyUtils.Loge(TAG, "222222222222");
                }
            } else {
                MyUtils.Loge(TAG, "READ permission is granted...");
                plat = ShareSDK.getPlatform(name);
                myShare(plat.getName());
            }
        }else {
            plat = ShareSDK.getPlatform(name);
            myShare(plat.getName());
        }
    }
}
