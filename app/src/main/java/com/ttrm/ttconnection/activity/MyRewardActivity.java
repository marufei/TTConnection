package com.ttrm.ttconnection.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;
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
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
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
    private String TAG = "MyRewardActivity";
    private ShareInfoBean shareInfoBean;
    private Platform plat;
    private LinearLayout reward_ll_wx;
    private LinearLayout reward_ll_circle;
    private LinearLayout reward_ll_qq;
    private LinearLayout reward_ll_qzone;
    private TextView reward_tv_content;
    private ImageView reward_iv_pic;
    private ImageView reward_iv_pic1;
    private ImageView reward_iv_ewm;
    private RelativeLayout reward_rv_pic;
    private AlertDialog dlg;
    private ImageView reward_iv_select1;
    private ImageView reward_iv_select2;
    private Bitmap qrBitmap;
    private int shareType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reward);
        ActivityUtil.add(this);
        initViews();
        initDatas();
    }

    private void initDatas() {
        if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_regcode))) {
            reward_tv_recode.setText(SaveUtils.getString(KeyUtils.user_regcode));
        }
        getSignInfo();
        getInfo();
    }

    private void initViews() {
        setToolBar("我的奖励");
        reward_tv_reward = (TextView) findViewById(R.id.reward_tv_reward);
        reward_tv_balance = (TextView) findViewById(R.id.reward_tv_balance);
        reward_tv_yq = (TextView) findViewById(R.id.reward_tv_yq);
        reward_tv_recode = (TextView) findViewById(R.id.reward_tv_recode);
        reward_btn_cash = (Button) findViewById(R.id.reward_btn_cash);
        reward_btn_cash.setOnClickListener(this);
        reward_btn_wx = (Button) findViewById(R.id.reward_btn_wx);
        reward_btn_wx.setOnClickListener(this);
        reward_btn_qq = (Button) findViewById(R.id.reward_btn_qq);
        reward_btn_qq.setOnClickListener(this);
        reward_btn_circle = (Button) findViewById(R.id.reward_btn_circle);
        reward_btn_circle.setOnClickListener(this);
        reward_btn_qzone = (Button) findViewById(R.id.reward_btn_qzone);
        reward_btn_qzone.setOnClickListener(this);
        reward_ll_wx = (LinearLayout) findViewById(R.id.reward_ll_wx);
        reward_ll_wx.setOnClickListener(this);
        reward_ll_circle = (LinearLayout) findViewById(R.id.reward_ll_circle);
        reward_ll_circle.setOnClickListener(this);
        reward_ll_qq = (LinearLayout) findViewById(R.id.reward_ll_qq);
        reward_ll_qq.setOnClickListener(this);
        reward_ll_qzone = (LinearLayout) findViewById(R.id.reward_ll_qzone);
        reward_ll_qzone.setOnClickListener(this);
        reward_tv_content = (TextView) findViewById(R.id.reward_tv_content);
        reward_iv_pic = (ImageView) findViewById(R.id.reward_iv_pic);
        reward_iv_pic1 = (ImageView) findViewById(R.id.reward_iv_pic1);
        reward_rv_pic = (RelativeLayout) findViewById(R.id.reward_rv_pic);
        reward_rv_pic.setOnClickListener(this);
        reward_iv_select1 = (ImageView) findViewById(R.id.reward_iv_select1);
        reward_iv_select1.setOnClickListener(this);
        reward_iv_select2 = (ImageView) findViewById(R.id.reward_iv_select2);
        reward_iv_select2.setOnClickListener(this);
        setMenuBtn("邀请明细", this, InviteActivity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reward_btn_circle:
                MyUtils.Loge(TAG,"shareType:"+shareType);
                if(shareType!=1&&shareType!=2){
                    MyUtils.showToast(MyRewardActivity.this,"请先选择分享类型");
                    return;
                }
                selectPermission(WechatMoments.NAME);
                break;
            case R.id.reward_btn_wx:
                if(shareType!=1&&shareType!=2){
                    MyUtils.showToast(MyRewardActivity.this,"请先选择分享类型");
                    return;
                }
                selectPermission(Wechat.NAME);
                break;
            case R.id.reward_btn_qq:
                if(shareType!=1&&shareType!=2){
                    MyUtils.showToast(MyRewardActivity.this,"请先选择分享类型");
                    return;
                }
                selectPermission(QQ.NAME);
                break;
            case R.id.reward_btn_qzone:
                if(shareType!=1&&shareType!=2){
                    MyUtils.showToast(MyRewardActivity.this,"请先选择分享类型");
                    return;
                }
                selectPermission(QZone.NAME);
                break;
            case R.id.reward_btn_cash:
                startActivity(new Intent(MyRewardActivity.this, WithdrawCashActivity.class));
                break;
            case R.id.reward_ll_wx:
                if(shareType!=1&&shareType!=2){
                    MyUtils.showToast(MyRewardActivity.this,"请先选择分享类型");
                    return;
                }
                selectPermission(Wechat.NAME);
                break;
            case R.id.reward_ll_circle:
                if(shareType!=1&&shareType!=2){
                    MyUtils.showToast(MyRewardActivity.this,"请先选择分享类型");
                    return;
                }
                selectPermission(WechatMoments.NAME);
                break;
            case R.id.reward_ll_qq:
                if(shareType!=1&&shareType!=2){
                    MyUtils.showToast(MyRewardActivity.this,"请先选择分享类型");
                    return;
                }
                selectPermission(QQ.NAME);
                break;
            case R.id.reward_ll_qzone:
                if(shareType!=1&&shareType!=2){
                    MyUtils.showToast(MyRewardActivity.this,"请先选择分享类型");
                    return;
                }
                selectPermission(QZone.NAME);
                break;
            case R.id.reward_rv_pic://放大图片
                showBigPic();
                dlg.show();
                break;
            case R.id.reward_iv_select1:
                shareType = 1;
                reward_iv_select1.setImageResource(R.drawable.vector_drawable_pay_y);
                reward_iv_select2.setImageResource(R.drawable.vector_drawable_pay_n);
                break;
            case R.id.reward_iv_select2:
                shareType=2;
                reward_iv_select1.setImageResource(R.drawable.vector_drawable_pay_n);
                reward_iv_select2.setImageResource(R.drawable.vector_drawable_pay_y);
                break;
        }
    }

    /**
     * 获取推荐信息
     */
    public void getInfo() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_REWARD_DATA;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    rewardBean = gson.fromJson(response, RewardBean.class);
                    if (rewardBean != null) {
                        if (rewardBean.getErrorCode() == 1) {
                            reward_tv_reward.setText(rewardBean.getData().getIncome() + "元");
                            reward_tv_balance.setText(rewardBean.getData().getBalance() + "元");
                            reward_tv_yq.setText(rewardBean.getData().getRecomCount() + "人");
                        } else {

                        }
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MyRewardActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    /**
     * 获取分享配置
     */
    public void getSignInfo() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_SHARE_INFO;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "分享配置-response:" + response);
                try {
                    Gson gson = new Gson();
                    shareInfoBean = gson.fromJson(response, ShareInfoBean.class);
                    if (shareInfoBean != null) {
                        if (shareInfoBean.getErrorCode() == 1) {
                            reward_tv_content.setText(shareInfoBean.getData().getConfig().getContent());
                            Picasso.with(MyRewardActivity.this)
                                    .load(shareInfoBean.getData().getConfig().getImgurl())
                                    .error(R.mipmap.ic_icon)
                                    .into(reward_iv_pic);
                            qrBitmap = generateBitmap(shareInfoBean.getData().getConfig().getUrl()
                                    + "?regCode="
                                    + SaveUtils.getString(KeyUtils.user_regcode), 200, 200);
                            Bitmap realBitmap = getRealBitmap();
                            reward_iv_pic1.setImageBitmap(realBitmap);
                        } else {

                        }
                    }
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MyRewardActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    /**
     * 分享链接
     */
    private void myShare(String platform) {
//        MyUtils.Loge(TAG, "Regcode:" + getApplicationContext().getUser().getRegcode());
        MyUtils.Loge(TAG, "title:" + shareInfoBean.getData().getConfig().getTitle() +
                "--url:" + shareInfoBean.getData().getConfig().getUrl() + "--context:" +
                shareInfoBean.getData().getConfig().getContent() + "--iamge:" +
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


    /**
     * 生成带二维码的图片bitmap
     */
    private Bitmap getRealBitmap() throws Exception {
        Bitmap bitmap1 = MyUtils.getImage(shareInfoBean.getData().getConfig().getImgurl1());
        Bitmap bitmap3 = MyUtils.mergeBitmap(bitmap1, qrBitmap);
        return bitmap3;

    }

    /**
     * 分享图片
     */
    private void mySharePic(String platform) {
        OnekeyShare oks = new OnekeyShare();
        if (platform != null)
            oks.setPlatform(platform);
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(shareInfoBean.getData().getConfig().getTitle());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(shareInfoBean.getData().getConfig().getUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareInfoBean.getData().getConfig().getContent());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(shareInfoBean.getData().getConfig().getImgurl1());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath(Environment.getExternalStorageDirectory()+ "/xx/a.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (platform.getName().equalsIgnoreCase(QQ.NAME)) {
                    paramsToShare.setText(null);
                    paramsToShare.setTitle(null);
                    paramsToShare.setTitleUrl(null);
                    paramsToShare.setImagePath(Environment.getExternalStorageDirectory() + "/xx/a.jpg");
                } else if (platform.getName().equalsIgnoreCase(QZone.NAME)) {
                    paramsToShare.setText(null);
                    paramsToShare.setTitle(null);
                    paramsToShare.setTitleUrl(null);
                    paramsToShare.setImagePath(Environment.getExternalStorageDirectory() + "/xx/a.jpg");
                }

            }
        });
//        oks.setUrl("https://www.baidu.com/");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareInfoBean.getData().getConfig().getUrl());

// 启动分享GUI
        oks.show(this);
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
                    if(shareType==1){
                        mySharePic(plat.getName());
                    }
                    if(shareType==2) {
                        myShare(plat.getName());
                    }

                } else {

                    // permission denied
                    // request failed
                    MyUtils.Loge(TAG, "权限失败");
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
    private void selectPermission(String name) {
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
                if(shareType==1){
                    mySharePic(plat.getName());
                }
                if(shareType==2) {
                    myShare(plat.getName());
                }
            }
        } else {
            plat = ShareSDK.getPlatform(name);
            if(shareType==1){
                mySharePic(plat.getName());
            }
            if(shareType==2) {
                myShare(plat.getName());
            }
        }
    }

    /**
     * 生成二维码
     */
    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 放大图片
     */
    private void showBigPic() {
        MyUtils.Loge(TAG, "url:" + shareInfoBean.getData().getConfig().getImgurl1());
        AlertDialog.Builder builder = new AlertDialog.Builder(MyRewardActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.dialog_pic, null);
        ImageView dialog_iv_pic1 = (ImageView) layout.findViewById(R.id.dialog_iv_pic1);
        RelativeLayout dialog_rv_big = (RelativeLayout) layout.findViewById(R.id.dialog_rv_big);
        MyUtils.Loge(TAG, "dialog_iv_pic1:" + dialog_iv_pic1);
        try {
            Bitmap bitmap = getRealBitmap();
            dialog_iv_pic1.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog_rv_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
        builder.setView(layout);
        dlg = builder.create();
        dlg.setCanceledOnTouchOutside(false);
    }

}
