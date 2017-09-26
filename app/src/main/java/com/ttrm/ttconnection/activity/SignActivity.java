package com.ttrm.ttconnection.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ttrm.ttconnection.MainActivity;
import com.ttrm.ttconnection.MyApplication;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.CanonBean;
import com.ttrm.ttconnection.entity.ShareInfoBean;
import com.ttrm.ttconnection.entity.SignStatusBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.LXRUtil;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.view.MyAdvertisementView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;



/**
 * TODO 每日签到
 */
public class SignActivity extends BaseActivity implements View.OnClickListener {

    private static TextView sign_tv_zs;
    private TextView sign_tv_regcode;
    private TextView sign_tv_sign;
    private TextView sign_tv_tb;
    private TextView sign_tv_oneadd;
    private TextView sign_tv_locationadd;
    private TextView sign_tv_wx;
    private TextView sign_tv_circle;
    private TextView sign_tv_qq;
    private TextView sign_tv_space;
    private static String TAG="SignActivity";
    private String type;
    private Platform plat;
    private SignStatusBean signStatusBean;
    private ShareInfoBean shareInfoBean;
    private static TextView sign_tv_add;
    private static List<CanonBean.DataBean.PhoneListBean> dataList = new ArrayList<CanonBean.DataBean.PhoneListBean>();//一键加粉数据集合
    private String addType;
    private static SignActivity signActivity;
    private static AlertDialog dlg;
    private static TextView dialog_loading_num;
    private static int inputType=0;

    private static int currentCount;
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KeyUtils.SAVE_CODE:
                    currentCount = (int) msg.obj;
                    MyUtils.Loge(TAG,"currentCount:"+currentCount+"--msg.obj:"+msg.obj);
//                    if (currentCount == dataList.size()) {
//                        Toast.makeText(MyApplication.mContext, "添加成功", Toast.LENGTH_SHORT).show();
                    dlg.dismiss();
                    selectDiamonds();
                    MyAdvertisementView myAdvertisementView = new MyAdvertisementView(signActivity,R.layout.dialog_location_success);
                    myAdvertisementView.showDialog();
                    myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                        @Override
                        public void onEvent() {
                            MyUtils.Loge("AAA","打开微信");
                            try {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setComponent(cmp);
                                signActivity.startActivity(intent);
                            } catch (Exception e) {
                                // TODO: handle exception
                                MyUtils.showToast(signActivity, "检查到您手机没有安装微信，请安装后使用该功能");
                            }
                        }
                    });
                    break;
                case KeyUtils.DELETE_CODE:
                    Toast.makeText(MyApplication.mContext, "删除成功", Toast.LENGTH_SHORT).show();
                    break;
                case KeyUtils.LOADING_CODE:
                    dlg.show();
                    int count=(int) msg.obj;
                    dialog_loading_num.setText(String.valueOf(count));
                    break;
            }
        }
    };
    public static Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KeyUtils.SAVE_CODE:
                    currentCount = (int) msg.obj;
                    MyUtils.Loge(TAG,"currentCount:"+currentCount+"--msg.obj:"+msg.obj);
//                    if (currentCount == dataList.size()) {
//                        Toast.makeText(MyApplication.mContext, "添加成功", Toast.LENGTH_SHORT).show();
                    dlg.dismiss();
                    selectDiamonds();
                    MyAdvertisementView myAdvertisementView = new MyAdvertisementView(signActivity,R.layout.dialog_location_success);
                    myAdvertisementView.showDialog();
                    myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                        @Override
                        public void onEvent() {
                            MyUtils.Loge("AAA","打开微信");
                            try {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setComponent(cmp);
                                signActivity.startActivity(intent);
                            } catch (Exception e) {
                                // TODO: handle exception
                                MyUtils.showToast(signActivity, "检查到您手机没有安装微信，请安装后使用该功能");
                            }
                        }
                    });
                    break;
                case KeyUtils.DELETE_CODE:
                    Toast.makeText(MyApplication.mContext, "删除成功", Toast.LENGTH_SHORT).show();
                    break;
                case KeyUtils.LOADING_CODE:
                    dlg.show();
                    int count=(int) msg.obj;
                    dialog_loading_num.setText(String.valueOf(count));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ActivityUtil.add(this);
        signActivity=this;
        initViews();
        //假的加载动画
        showLoading();
    }
    /**
     * 假的加载动画
     */
    private static void showLoading() {
        AlertDialog.Builder builder=new AlertDialog.Builder(signActivity);
        LayoutInflater inflater=signActivity.getLayoutInflater();
        final View layout=inflater.inflate(R.layout.dialog_loading,null);
        dialog_loading_num= (TextView) layout.findViewById(R.id.dialog_loading_num);
        builder.setView(layout);
        dlg=builder.create();
        dlg.setCanceledOnTouchOutside(false);
    }

    private void initViews() {
        setToolBar("每日签到");
        sign_tv_zs=(TextView)findViewById(R.id.sign_tv_zs);
        sign_tv_regcode=(TextView)findViewById(R.id.sign_tv_regcode);
        if(!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_regcode))){
            sign_tv_regcode.setText(SaveUtils.getString(KeyUtils.user_regcode));
        }
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
        sign_tv_add=(TextView)findViewById(R.id.sign_tv_add);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSignInfo();
        selectDiamonds();
        getSignStatus();
    }

    /**
     * 查询钻石数量
     */
    public static void selectDiamonds(){
        String url= HttpAddress.BASE_URL+HttpAddress.SELECT_DIAMONDS;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"钻石数量---response:"+response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    if(errorCode==1){
                        JSONObject jsonObject1=jsonObject.getJSONObject("data");
                        String diamondCount=jsonObject1.getString("diamondCount");
                        String todayCount=jsonObject1.getString("todayCount");
                        if(todayCount.equals("null")) {
                            sign_tv_add.setText("+0");
                        }else {
                            sign_tv_add.setText("+" + todayCount);
                        }
                        sign_tv_zs.setText(diamondCount);
                    }
                    ActivityUtil.toLogin(signActivity, errorCode);
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(signActivity,"网络有问题");
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
        Volley.newRequestQueue(signActivity).add(stringRequest);
    }

    /**
     * 获取钻石
     */
    public  void getDiamonds(){
        String url=HttpAddress.BASE_URL+HttpAddress.GET_DIAMONDS;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    String errorMsg=jsonObject.getString("errorMsg");
                    if(errorCode==1){
                        MyUtils.showToast(SignActivity.this,errorMsg);
                        sign_tv_sign.setBackgroundResource(R.drawable.btn_gray);
                        sign_tv_sign.setClickable(false);
                        selectDiamonds();

                    }else if(errorCode==40001){
                        ActivityUtil.toLogin(signActivity, errorCode);
                    }else {
                        MyUtils.showToast(SignActivity.this,errorMsg);
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
                map.put("login_token",SaveUtils.getString(KeyUtils.user_login_token));
                map.put("type",type);
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(SignActivity.this).add(stringRequest);
    }

    /**
     * 获取签到状态
     */
    private void getSignStatus(){
        String url=HttpAddress.BASE_URL+HttpAddress.GET_SIGN_STATUS;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    Gson gson=new Gson();
                    signStatusBean=gson.fromJson(response, SignStatusBean.class);
                    if(signStatusBean!=null){
                        if(signStatusBean.getErrorCode()==1){
                            switch (signStatusBean.getData().getStatus()){
                                case 0:     //未签到
                                    sign_tv_sign.setBackgroundResource(R.drawable.btn_red);
                                    sign_tv_sign.setClickable(true);
                                    break;
                                case 1:     //已签到
                                    sign_tv_sign.setBackgroundResource(R.drawable.btn_gray);
                                    sign_tv_sign.setClickable(false);
                                    break;
                            }
                        }
                        ActivityUtil.toLogin(signActivity, signStatusBean.getErrorCode());
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
                map.put("login_token",SaveUtils.getString(KeyUtils.user_login_token));
                map.put("timeStamp",MyUtils.getTimestamp());
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

                        }
                        ActivityUtil.toLogin(signActivity, shareInfoBean.getErrorCode());
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
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_tv_sign:
                type="1";
                getDiamonds();
                break;
            case R.id.sign_tv_oneadd:
                addType="1";
                inputType=3;
//                getCanon();//获取一键加粉数据
//                saveCanon();
                MyAdvertisementView myAdvertisementView1 = new MyAdvertisementView(SignActivity.this, R.layout.dialog_bj_one);
                myAdvertisementView1.showDialog();
                myAdvertisementView1.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                    @Override
                    public void onEvent() {
                        MyUtils.Loge(TAG, "朕知道了");
                        saveCanon();
                    }
                });
                break;
            case R.id.sign_tv_locationadd:
                addType="2";
                inputType=4;
//                getCanon();//获取地区加粉数据
                saveCanon();
                break;
            case R.id.sign_tv_wx:
                type="4";
                selectPermission(Wechat.NAME);
                break;
            case R.id.sign_tv_circle:
                type="5";
                selectPermission(WechatMoments.NAME);
                break;
            case R.id.sign_tv_qq:
                type="6";
                selectPermission(QQ.NAME);
                break;
            case R.id.sign_tv_space:
                type="7";
                selectPermission(QZone.NAME);
                break;
            case R.id.sign_tv_tb:
                type="9";
                getDiamonds();
                break;
        }
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
            oks.setCallback(new MyCallBalk());

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
     * 一键加粉
     */
    private void getCanon() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_CANON;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "canon:" + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        Gson gson = new Gson();
                        CanonBean bean = gson.fromJson(response, CanonBean.class);
                        if (bean.getErrorCode() == 1) {
                            dataList.clear();
                            dataList.addAll(bean.getData().getPhoneList());
//                            saveCanon();
                            addPhone();
                        }
                    } else if(errorCode==40001){
                        ActivityUtil.toLogin(SignActivity.this, errorCode);
                    }else {
                        Toast.makeText(SignActivity.this, jsonObject.getString("errorMsg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(SignActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                map.put("type", addType);
                return map;
            }
        };
        Volley.newRequestQueue(SignActivity.this).add(stringRequest);
    }

    /**
     * 添加到通讯录
     */
    private void saveCanon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(SignActivity.this,
                    Manifest.permission.WRITE_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 0x1);
                return;
            } else {
               getCanon();
            }
        } else {
            getCanon();
        }
    }
    private void addPhone(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < dataList.size(); i++) {
                    boolean isLast=false;
                    if(i==dataList.size()-1){
                        isLast=true;
                    }else {
                        isLast=false;
                    }
                    LXRUtil.addContacts(SignActivity.this, dataList.get(i).getNickname(), dataList.get(i).getPhone(), i,inputType,isLast);
                }
            }
        }).start();
    }

    /**
     * 分享回调
     */
    class MyCallBalk implements PlatformActionListener {

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            MyUtils.Loge(TAG,"分享成功");
            getDiamonds();
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            MyUtils.Loge(TAG,"分享失败--"+throwable.getMessage());
        }

        @Override
        public void onCancel(Platform platform, int i) {
            MyUtils.Loge(TAG,"分享取消");
        }
    }
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
            case 0x1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.v("stones", "权限回调--获取权限失败");
                    Toast.makeText(SignActivity.this, "请打开手机设置，权限管理，允许天天人脉读取、写入和删除联系人信息后再使用立即加粉", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(SignActivity.this, "权限获取成功", Toast.LENGTH_SHORT).show();
                    Log.v("stones", "权限回调--获取权限成功");
                    getCanon();
                }
                break;
            case 0x2:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SignActivity.this, "请打开手机设置，权限管理，允许天天人脉读取、写入和删除联系人信息后再使用立即加粉", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            LXRUtil.deleteContacts(SignActivity.this);
                        }
                    }).start();
                }
                break;
            
            default:
                break;

        }
    }
}
