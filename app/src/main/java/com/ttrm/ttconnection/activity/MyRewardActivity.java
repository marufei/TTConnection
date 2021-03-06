package com.ttrm.ttconnection.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.Contant;
import com.ttrm.ttconnection.entity.RewardBean;
import com.ttrm.ttconnection.entity.ShareInfoBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.FileUtils;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.VolleyUtils;

import java.io.File;
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
    private Bitmap bitmap3;
    private Bitmap realBitmap;
    private static String picPath;
    private AlertDialog dlg1;
    private boolean isShare = true; //判断图片是否合成成功，如果合成失败false,如果合成成功true;
    private TextView reward_tv_js;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reward);
        ActivityUtil.add(this);
        initViews();
        showLoading();
        initDatas();
    }

    private void initDatas() {
        if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_regcode))) {
            reward_tv_recode.setText(SaveUtils.getString(KeyUtils.user_regcode));
        }
        shareType = 1;
        reward_iv_select1.setImageResource(R.drawable.vector_drawable_pay_y);
        reward_iv_select2.setImageResource(R.drawable.vector_drawable_pay_n);
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
        reward_iv_ewm = (ImageView) findViewById(R.id.reward_iv_ewm);
        setMenuBtn("邀请明细", this, InviteActivity.class);
        reward_tv_js = (TextView) findViewById(R.id.reward_tv_js);
        for (int i = 0; i < Contant.rewardRuleBean.getData().getCataList().size(); i++) {
            if (Contant.rewardRuleBean.getData().getCataList().get(i).getCataid().equals("11")) {
//                reward_tv_js.setText("好友通过您的邀请码注册，您和好友各+"+Contant.rewardRuleBean.getData().getCataList().get(i).getDiacount()+"颗钻石");
                reward_tv_js.setText("好友通过你的邀请码注册，您和好友各获得100钻石奖励，累计邀请50个好友，可获得12750颗钻石奖励，累计爆机（坐等被加）1912人。");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reward_btn_circle:
                MyUtils.Loge(TAG, "shareType:" + shareType);
                if (shareType != 1 && shareType != 2) {
                    MyUtils.showToast(MyRewardActivity.this, "请先选择分享类型");
                    return;
                }
                selectPermission(WechatMoments.NAME);
                break;
            case R.id.reward_btn_wx:
                if (shareType != 1 && shareType != 2) {
                    MyUtils.showToast(MyRewardActivity.this, "请先选择分享类型");
                    return;
                }
                selectPermission(Wechat.NAME);
                break;
            case R.id.reward_btn_qq:
                if (shareType != 1 && shareType != 2) {
                    MyUtils.showToast(MyRewardActivity.this, "请先选择分享类型");
                    return;
                }
                selectPermission(QQ.NAME);
                break;
            case R.id.reward_btn_qzone:
                if (shareType != 1 && shareType != 2) {
                    MyUtils.showToast(MyRewardActivity.this, "请先选择分享类型");
                    return;
                }
                selectPermission(QZone.NAME);
                break;
            case R.id.reward_btn_cash:
                startActivity(new Intent(MyRewardActivity.this, WithdrawCashActivity.class));
                break;
            case R.id.reward_ll_wx:
                if (shareType != 1 && shareType != 2) {
                    MyUtils.showToast(MyRewardActivity.this, "请先选择分享类型");
                    return;
                }
                selectPermission(Wechat.NAME);
                break;
            case R.id.reward_ll_circle:
                if (shareType != 1 && shareType != 2) {
                    MyUtils.showToast(MyRewardActivity.this, "请先选择分享类型");
                    return;
                }
                selectPermission(WechatMoments.NAME);
                break;
            case R.id.reward_ll_qq:
                if (shareType != 1 && shareType != 2) {
                    MyUtils.showToast(MyRewardActivity.this, "请先选择分享类型");
                    return;
                }
                selectPermission(QQ.NAME);
                break;
            case R.id.reward_ll_qzone:
                if (shareType != 1 && shareType != 2) {
                    MyUtils.showToast(MyRewardActivity.this, "请先选择分享类型");
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
                shareType = 2;
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
                            reward_tv_reward.setText(rewardBean.getData().getAlldiaCount() + "颗");
                            reward_tv_balance.setText(rewardBean.getData().getRestCount() + "颗");
                            reward_tv_yq.setText(rewardBean.getData().getRecomCount() + "人");
                        }
                        ActivityUtil.toLogin(MyRewardActivity.this, rewardBean.getErrorCode());
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
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
    }

    /**
     * 获取分享配置
     */
    public void getSignInfo() {
        dlg1.show();
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
                            MyUtils.Loge(TAG, "步骤1 图片--" + shareInfoBean.getData().getConfig().getImgurl());
                            reward_tv_content.setText(shareInfoBean.getData().getConfig().getContent());
                            MyUtils.Loge(TAG, "步骤2");
                            Picasso.with(MyRewardActivity.this)
                                    .load(shareInfoBean.getData().getConfig().getImgurl())
                                    .error(R.mipmap.ic_icon)
                                    .into(reward_iv_pic);
                            MyUtils.Loge(TAG, "步骤3");


                            qrBitmap = generateBitmap(shareInfoBean.getData().getConfig().getUrl()
                                    + "?regCode="
                                    + SaveUtils.getString(KeyUtils.user_regcode), 200, 200);
                            MyUtils.Loge(TAG, "步骤4--qrBitmap:" + qrBitmap);
                            Picasso.with(MyRewardActivity.this).load(shareInfoBean.getData().getConfig().getImgurl1()).into(reward_iv_pic1);
                            MyUtils.Loge(TAG, "步骤5");
                            reward_iv_ewm.setImageBitmap(qrBitmap);
                            MyUtils.Loge(TAG, "步骤6");
                            dlg1.dismiss();
//                            MyUtils.Loge(TAG,"布局获取bitmap--"+reward_rv_pic.getDrawingCache());
                            MyUtils.Loge(TAG, "步骤6.1");
                            realBitmap = getRealBitmap();
                            MyUtils.Loge(TAG, "步骤7");
                            if(realBitmap!=null) {
                                if (!isHavePic(FileUtils.getInnerSDCardPath(), "img1-"+SaveUtils.getString(KeyUtils.user_id))) {
                                    picPath = FileUtils.getInnerSDCardPath() + "img1-"+SaveUtils.getString(KeyUtils.user_id) + System.currentTimeMillis() + ".jpg";
                                }else {
//                                    MyUtils.l(MyRewardActivity.this,"已经有了路径--------");
                                    MyUtils.Loge(TAG,"已经有了路径--------");
                                    FileUtils.writeBitmapToSD(picPath, realBitmap, true);
                                }
                            }else{
//                                MyUtils.showToast(MyRewardActivity.this,"realBitmap为空---------");
                                MyUtils.Loge(TAG,"realBitmap为空---------");
                                showAlertDialog2("温馨提示", "分享图片生成失败，请返回上一页进行刷新", "返回", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                });
                            }
//                            MyUtils.Loge(TAG, "picPath::" + picPath);
                            isShare = true;
                        }
                        ActivityUtil.toLogin(MyRewardActivity.this, shareInfoBean.getErrorCode());
                    }
                } catch (Exception e) {
                    MyUtils.Loge(TAG, "e:" + e.getMessage());
                    isShare = false;
                    dlg1.dismiss();
                    MyUtils.Loge(TAG,"异常抛出");
                    showAlertDialog2("温馨提示", "分享图片生成失败，请返回上一页进行刷新", "返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dlg1.dismiss();
                isShare = false;
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
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
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
            oks.setTitleUrl(shareInfoBean.getData().getConfig().getUrl() + "?regCode=" + SaveUtils.getString(KeyUtils.user_regcode));

            // text是分享文本，所有平台都需要这个字段
            oks.setText(shareInfoBean.getData().getConfig().getContent());

            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//            oks.setImagePath(path);//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(shareInfoBean.getData().getConfig().getUrl() + "?regCode=" + SaveUtils.getString(KeyUtils.user_regcode));
//            oks.setImageUrl("file:///android_asset/icon_launcher.png");
//            if (type.equals("1") ||type.equals("2"))
            oks.setImageUrl(shareInfoBean.getData().getConfig().getImgurl());
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//            oks.setComment("我是测试评论文本");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(getString(R.string.app_name));

            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(shareInfoBean.getData().getConfig().getUrl() + "?regCode=" + SaveUtils.getString(KeyUtils.user_regcode));

            // 启动分享GUI
            oks.show(this);
        }

    }


    /**
     * 生成带二维码的图片bitmap
     */
    private Bitmap getRealBitmap() {
        try {
            Bitmap bitmap1 = MyUtils.returnBitmap(shareInfoBean.getData().getConfig().getImgurl1());
            MyUtils.Loge(TAG, "步骤 bitmap1:" + bitmap1);
            bitmap3 = MyUtils.mergeBitmap(bitmap1, qrBitmap);
            MyUtils.Loge(TAG, "步骤 bitmap3:" + bitmap3);
            MyUtils.Loge(TAG, "步骤6");
        } catch (Exception e) {
            return null;
        }
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
//        oks.setImagePath(picPath);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (platform.getName().equalsIgnoreCase(QQ.NAME) || platform.getName().equalsIgnoreCase(QZone.NAME)) {
                    paramsToShare.setText(null);
                    paramsToShare.setTitle(null);
                    paramsToShare.setTitleUrl(null);
                    paramsToShare.setImagePath(picPath);
                }
//                else if (platform.getName().equalsIgnoreCase(QZone.NAME)) {
//                    paramsToShare.setText(null);
//                    paramsToShare.setTitle(null);
//                    paramsToShare.setTitleUrl(null);
//                    paramsToShare.setImagePath(picPath);
//                }
                if (platform.getName().equalsIgnoreCase(Wechat.NAME) || platform.getName().equalsIgnoreCase(WechatMoments.NAME)) {
                    try {
                        paramsToShare.setImageData(realBitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                    if (shareType == 1) {
                        if (isShare) {
                            mySharePic(plat.getName());
                        } else {
//                        MyUtils.showToast(MyRewardActivity.this,"系统忙，请返回上一页稍后再试");
                            showAlertDialog("温馨提示", "哎呀，专属二维码出小差了，请返回上一步重新生成", "确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                    dialogInterface.dismiss();
                                }
                            }, "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                        }
                    }
                    if (shareType == 2) {
                        myShare(plat.getName());
                    }

                } else {

                    // permission denied
                    // request failed
                    MyUtils.Loge(TAG, "权限获取失败");
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
                if (shareType == 1) {
                    if (isShare) {
                        mySharePic(plat.getName());
                    } else {
                        showAlertDialog("温馨提示", "哎呀，专属二维码出小差了，请返回上一步重新生成", "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                dialogInterface.dismiss();
                            }
                        }, "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                    }
                }
                if (shareType == 2) {
                    myShare(plat.getName());
                }
            }
        } else {
            plat = ShareSDK.getPlatform(name);
            if (shareType == 1) {
                if (isShare) {
                    mySharePic(plat.getName());
                } else {
                    showAlertDialog("温馨提示", "哎呀，专属二维码出小差了，请返回上一步重新生成", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            dialogInterface.dismiss();
                        }
                    }, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                }
            }
            if (shareType == 2) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(MyRewardActivity.this, R.style.myDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.dialog_pic, null);
        layout.setBackgroundColor(getResources().getColor(R.color.transparent));
        ImageView dialog_iv_pic1 = (ImageView) layout.findViewById(R.id.dialog_iv_pic1);
//        ImageView dialog_iv_erm=(ImageView)layout.findViewById(R.id.dialog_iv_erm);
//        Picasso.with(MyRewardActivity.this).load(shareInfoBean.getData().getConfig().getImgurl1()).into(dialog_iv_pic1);
//        dialog_iv_erm.setImageBitmap(qrBitmap);
        LinearLayout dialog_rv_big = (LinearLayout) layout.findViewById(R.id.dialog_rv_big);
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



    /**
     * 查询是否有存在分享的图片
     */
    public static boolean isHavePic(String dirPath, String type) {
        File f = new File(dirPath);
        if (!f.exists()) {//判断路径是否存在
            return false;
        }
        File[] files = f.listFiles();
        if (files == null) {//判断权限
            return false;
        }
        for (File _file : files) {//遍历目录
            if (_file.isFile() && _file.getName().startsWith(type)) {
                picPath = _file.getAbsolutePath();//获取文件路径
                return true;
            }
        }
        return false;
    }

    /**
     * 删除加载动画
     */
    private void showLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyRewardActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.dialog_delete, null);
        builder.setView(layout);
        dlg1 = builder.create();
        dlg1.setCanceledOnTouchOutside(false);
    }


}
