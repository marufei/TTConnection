package com.ttrm.ttconnection.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.ttrm.ttconnection.activity.DoubleRewardActivity;
import com.ttrm.ttconnection.activity.InventActivity;
import com.ttrm.ttconnection.activity.Web2Activity;
import com.ttrm.ttconnection.activity.WebActivity;
import com.ttrm.ttconnection.activity.ZsRewardRuleActivity;
import com.ttrm.ttconnection.dialog.ShareDialog;
import com.ttrm.ttconnection.entity.ShareBean;
import com.ttrm.ttconnection.entity.ShareInfoBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.FileUtils;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.VolleyUtils;
import com.ttrm.ttconnection.view.ImageCycleView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * Created by MaRufei
 * on 2018/2/5.
 * Email: www.867814102@qq.com
 * Phone：13213580912
 * Purpose:TODO
 * update：
 */
public class ZsFragment extends BaseFragment implements View.OnClickListener {

    private static String TAG = "ZsFragment";
    private TextView zs_reward_rule;
    private TextView zs_reward_invent;
    private TextView zs_reward_fb;
    private ImageCycleView zs_banner;
    private List<String> bannerPicList = new ArrayList<>();
    private TextView zs_number_today;
    private TextView zs_number;
    private TextView zs_regcode;
    private Button zs_reward_share;
    private ShareBean shareBean;
    private AlertDialog dlg1;
    private Bitmap bitmap3;
    /**
     * 二维码
     */
    private Bitmap qrBitmap;
    private ShareInfoBean shareInfoBean;
    private Bitmap realBitmap;
    private static String picPath;
    private Platform plat;
    private int shareType;

    private boolean isShare = true; //判断图片是否合成成功，如果合成失败false,如果合成成功true;
    private ImageView zs_reward_share_circle;
    private ImageView zs_reward_share_wx;
    private ImageView zs_reward_share_qq;
    private ImageView zs_reward_share_qzone;
    private AlertDialog dialog;
    private TextView zs_reward_method;
    private RelativeLayout zs_reward_rl;

    @Override
    protected void lazyLoad() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.fragment_zs, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initViews();
        showLoading();
        setBanners();
        initDatas();
    }

    private void initDatas() {
        MyUtils.Loge(TAG, "进入initDatas方法");
        String url = HttpAddress.BASE_URL + HttpAddress.INVENT_INFO;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "获取推荐配置---response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        Gson gson = new Gson();
                        shareBean = gson.fromJson(response, ShareBean.class);
                    } else {
                        ActivityUtil.toLogin(getActivity(), errorCode);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
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
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void initViews() {
        zs_reward_rule = (TextView) getActivity().findViewById(R.id.zs_reward_rule);
        zs_reward_rule.setOnClickListener(this);
        zs_reward_invent = (TextView) getActivity().findViewById(R.id.zs_reward_invent);
        zs_reward_invent.setOnClickListener(this);
        zs_reward_fb = (TextView) getActivity().findViewById(R.id.zs_reward_fb);
        zs_reward_fb.setOnClickListener(this);
        zs_banner = (ImageCycleView) getActivity().findViewById(R.id.zs_banner);
        zs_number_today = (TextView) getActivity().findViewById(R.id.zs_number_today);
        zs_number = (TextView) getActivity().findViewById(R.id.zs_number);
        zs_reward_share = (Button) getActivity().findViewById(R.id.zs_reward_share);
        zs_reward_share.setOnClickListener(this);
        zs_regcode = (TextView) getActivity().findViewById(R.id.zs_regcode);
        if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_regcode))) {
            MyUtils.Loge(TAG, "邀请码不为空");
            zs_regcode.setText("分享邀请码" + SaveUtils.getString(KeyUtils.user_regcode));
        }

        zs_reward_share_circle = (ImageView) getActivity().findViewById(R.id.zs_reward_share_circle);
        zs_reward_share_circle.setOnClickListener(this);
        zs_reward_share_wx = (ImageView) getActivity().findViewById(R.id.zs_reward_share_wx);
        zs_reward_share_wx.setOnClickListener(this);
        zs_reward_share_qq = (ImageView) getActivity().findViewById(R.id.zs_reward_share_qq);
        zs_reward_share_qq.setOnClickListener(this);
        zs_reward_share_qzone = (ImageView) getActivity().findViewById(R.id.zs_reward_share_qzone);
        zs_reward_share_qzone.setOnClickListener(this);

        zs_reward_method = (TextView) getActivity().findViewById(R.id.zs_reward_method);
//        zs_reward_method.setOnClickListener(this);
        zs_reward_rl=(RelativeLayout)getActivity().findViewById(R.id.zs_reward_rl);
        zs_reward_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zs_reward_rule:
                startActivity(new Intent(getActivity(), ZsRewardRuleActivity.class));
                break;
            case R.id.zs_reward_invent:
                startActivity(new Intent(getActivity(), InventActivity.class));
                break;
            case R.id.zs_reward_fb:
                startActivity(new Intent(getActivity(), DoubleRewardActivity.class));
                break;
            case R.id.zs_reward_share:
//                new ShareDialog(getActivity(),shareBean).show();
                shareDialog();
                break;
            case R.id.zs_reward_share_circle:
                shareType = 1;
                selectPermission(WechatMoments.NAME);
                break;
            case R.id.zs_reward_share_wx:
                shareType = 1;
                selectPermission(Wechat.NAME);
                break;
            case R.id.zs_reward_share_qq:
                shareType = 1;
                selectPermission(QQ.NAME);
                break;
            case R.id.zs_reward_share_qzone:
                shareType = 1;
                selectPermission(QZone.NAME);
                break;

            case R.id.dialog_share_close:
                dialog.dismiss();
                break;
            case R.id.dialog_share_qq:
                shareType = 1;
                selectPermission(QQ.NAME);
                break;
            case R.id.dialog_share_wx:
                shareType = 1;
                selectPermission(Wechat.NAME);
                break;
            case R.id.dialog_share_circle:
                shareType = 1;
                selectPermission(WechatMoments.NAME);
                break;
            case R.id.dialog_share_qzone:
                shareType = 1;
                selectPermission(QZone.NAME);
                break;
            case R.id.zs_reward_method:
                break;
            case R.id.zs_reward_rl:
                MyUtils.Loge(TAG,"点击了干货");
                if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_regcode))) {
                    Intent intent = new Intent(getActivity(), Web2Activity.class);
                    intent.putExtra("URL", HttpAddress.H5_GANHUO + SaveUtils.getString(KeyUtils.user_regcode));
                    intent.putExtra("title", "干货");
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * f
     * 设置banner图
     */
    private void setBanners() {
        String[] url = {"https://www.tiantianrenmai.com/tt/public/Upload/img/ewjl1.png", "https://www.tiantianrenmai.com/tt/public/Upload/img/ewjl2.png"};
        for (int i = 0; i < url.length; i++) {
            bannerPicList.add(url[i]);
        }
        ImageCycleView.ImageCycleViewListener imageCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                Picasso.with(getActivity()).load(imageURL).into(imageView);
            }

            @Override
            public void onImageClick(int position, View imageView) {

            }
        };
        zs_banner.setImageResources((ArrayList<String>) bannerPicList, imageCycleViewListener);
        zs_banner.startImageCycle();
    }

    @Override
    public void onResume() {
        super.onResume();
        selectDiamonds();
        getSignInfo();
    }

    /**
     * 查询钻石数量
     */
    public void selectDiamonds() {
        String url = HttpAddress.BASE_URL + HttpAddress.SELECT_DIAMONDS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "钻石数量---response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String diamondCount = jsonObject1.getString("diamondCount");
                        String todayCount = jsonObject1.getString("todayCount");
                        if (todayCount.equals("null")) {
                            zs_number_today.setText("+0");
                        } else {
                            zs_number_today.setText("+" + todayCount);
                        }
                        zs_number.setText(diamondCount);
                    }
                    ActivityUtil.toLogin(getActivity(), errorCode);
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "网络有问题");
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
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
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
                                    + SaveUtils.getString(KeyUtils.user_regcode), 220, 220);
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
                        ActivityUtil.toLogin(getActivity(), shareInfoBean.getErrorCode());
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
                MyUtils.showToast(getActivity(), "网络有问题");
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
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /**
     * 删除加载动画
     */
    private void showLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View layout = inflater.inflate(R.layout.dialog_delete, null);
        builder.setView(layout);
        dlg1 = builder.create();
        dlg1.setCanceledOnTouchOutside(false);
    }

    /**
     * 生成带二维码的图片bitmap
     */
    private Bitmap getRealBitmap() {
        Bitmap bitmap1 = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.share_image);
        bitmap3 = MyUtils.mergeBitmap2(getActivity(),SaveUtils.getString(KeyUtils.user_regcode),bitmap1, qrBitmap);
        return bitmap3;
    }

    /**
     * 选择分享平台
     */
    private void selectPermission(String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0以上运行时权限
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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
        oks.show(getActivity());
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
            oks.show(getActivity());
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
     * 分享dialog
     */
    private void shareDialog() {
        dialog = new AlertDialog.Builder(getActivity())
                .create();
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.dialog_share);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.dialog_animation);

        ImageView dialog_share_close = (ImageView) window.findViewById(R.id.dialog_share_close);
        dialog_share_close.setOnClickListener(this);
        TextView dialog_share_content1 = (TextView) window.findViewById(R.id.dialog_share_content1);
        TextView dialog_share_content2 = (TextView) window.findViewById(R.id.dialog_share_content2);
        ImageView dialog_share_circle = (ImageView) window.findViewById(R.id.dialog_share_circle);
        dialog_share_circle.setOnClickListener(this);
        ImageView dialog_share_wx = (ImageView) window.findViewById(R.id.dialog_share_wx);
        dialog_share_wx.setOnClickListener(this);
        ImageView dialog_share_qq = (ImageView) window.findViewById(R.id.dialog_share_qq);
        dialog_share_qq.setOnClickListener(this);
        ImageView dialog_share_qzone = (ImageView) window.findViewById(R.id.dialog_share_qzone);
        dialog_share_qzone.setOnClickListener(this);
//        dialog_share_content1.setText("好友通过您的专属链接注册，您和好友各获得"+shareBean.getData().get(shareBean.getData().size()-1)+"颗钻石。");
//        dialog_share_content2.setText("邀请满5人，额外获得250颗钻石奖励，邀请越多，奖励翻倍，邀请满50人，颗累计奖励12750钻，可免费爆机（坐等被加）1912人。");

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
