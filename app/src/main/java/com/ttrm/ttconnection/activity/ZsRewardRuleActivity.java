package com.ttrm.ttconnection.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

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
import com.ttrm.ttconnection.R;
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



/**
 * TODO 钻石奖励规则
 */
public class ZsRewardRuleActivity extends BaseActivity implements View.OnClickListener{

    private ImageView zs_reward_rule_circle;
    private ImageView zs_reward_rule_wx;
    private ImageView zs_reward_rule_qq;
    private ImageView zs_reward_rule_qzone;
    private String TAG="ZsRewardRuleActivity";
    private AlertDialog dlg1;
    private Platform plat;
    private int shareType;
    private boolean isShare;
    private ShareInfoBean shareInfoBean;
    private Bitmap qrBitmap;
    private Bitmap realBitmap;
    private static String picPath;
    private Bitmap bitmap3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zs_reward_rule);
        ActivityUtil.add(this);
        setToolBar("钻石奖励规则");
        initViews();
        showLoading();
        getSignInfo();

    }

    private void initViews() {
        zs_reward_rule_circle=(ImageView)findViewById(R.id.zs_reward_rule_circle);
        zs_reward_rule_circle.setOnClickListener(this);
        zs_reward_rule_wx=(ImageView)findViewById(R.id.zs_reward_rule_wx);
        zs_reward_rule_wx.setOnClickListener(this);
        zs_reward_rule_qq=(ImageView)findViewById(R.id.zs_reward_rule_qq);
        zs_reward_rule_qq.setOnClickListener(this);
        zs_reward_rule_qzone=(ImageView)findViewById(R.id.zs_reward_rule_qzone);
        zs_reward_rule_qzone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.zs_reward_rule_circle:
                shareType = 1;
                selectPermission(WechatMoments.NAME);
                break;
            case R.id.zs_reward_rule_wx:
                shareType = 1;
                selectPermission(Wechat.NAME);
                break;
            case R.id.zs_reward_rule_qq:
                shareType = 1;
                selectPermission(QQ.NAME);
                break;
            case R.id.zs_reward_rule_qzone:
                shareType = 1;
                selectPermission(QZone.NAME);
                break;
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
                    plat = ShareSDK.getPlatform(Wechat.NAME);
                    if (shareType == 1) {
                        if (isShare) {
                            mySharePic(plat.getName());
                        } else {
                            showAlertDialog("温馨提示", "哎呀，专属二维码出小差了，请返回上一步重新生成", "确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
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
                    MyUtils.Loge(TAG, "权限获取失败");
                }

                return;
            }
            default:
                break;

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
                            qrBitmap = generateBitmap(shareInfoBean.getData().getConfig().getUrl()
                                    + "?regCode="
                                    + SaveUtils.getString(KeyUtils.user_regcode), 200, 200);
                            dlg1.dismiss();
                            realBitmap = getRealBitmap();
                            if(realBitmap!=null) {
                                if (!isHavePic(FileUtils.getInnerSDCardPath(), "img-"+SaveUtils.getString(KeyUtils.user_id))) {
                                    picPath = FileUtils.getInnerSDCardPath() + "img-"+SaveUtils.getString(KeyUtils.user_id) + System.currentTimeMillis() + ".jpg";
                                }
                                FileUtils.writeBitmapToSD(picPath, realBitmap, true);
                            }
                            MyUtils.Loge(TAG, "picPath::" + picPath);
                            isShare = true;
                        }
                        ActivityUtil.toLogin(ZsRewardRuleActivity.this, shareInfoBean.getErrorCode());
                    }
                } catch (Exception e) {
                    MyUtils.Loge(TAG, "e:" + e.getMessage());
                    isShare = false;
                    dlg1.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dlg1.dismiss();
                isShare = false;
                MyUtils.showToast(ZsRewardRuleActivity.this, "网络有问题");
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
        VolleyUtils.getInstance(ZsRewardRuleActivity.this).addToRequestQueue(stringRequest);
    }

    /**
     * 删除加载动画
     */
    private void showLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ZsRewardRuleActivity.this);
        LayoutInflater inflater = LayoutInflater.from(ZsRewardRuleActivity.this);
        final View layout = inflater.inflate(R.layout.dialog_delete, null);
        builder.setView(layout);
        dlg1 = builder.create();
        dlg1.setCanceledOnTouchOutside(false);
    }

    /**
     * 生成带二维码的图片bitmap
     */
    private Bitmap getRealBitmap() {
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.share_image);
        bitmap3 = MyUtils.mergeBitmap2(ZsRewardRuleActivity.this,SaveUtils.getString(KeyUtils.user_regcode),bitmap1, qrBitmap);
        return bitmap3;
    }

    /**
     * 选择分享平台
     */
    private void selectPermission(String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0以上运行时权限
            if (ZsRewardRuleActivity.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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
                                //TODO 再刷新
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
        oks.show(ZsRewardRuleActivity.this);
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
            oks.show(ZsRewardRuleActivity.this);
        }

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
}
