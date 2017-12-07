package com.ttrm.ttconnection;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.ttrm.ttconnection.activity.BDAddActivity;
import com.ttrm.ttconnection.activity.BannerActivity;
import com.ttrm.ttconnection.activity.BaoJiActivity;
import com.ttrm.ttconnection.activity.BaseActivity;
import com.ttrm.ttconnection.activity.EditNameActivity;
import com.ttrm.ttconnection.activity.LocationAddActivity;
import com.ttrm.ttconnection.activity.LoginActivity;
import com.ttrm.ttconnection.activity.MyRewardActivity;
import com.ttrm.ttconnection.activity.SignActivity;
import com.ttrm.ttconnection.activity.UserInfoActivity;
import com.ttrm.ttconnection.activity.WebActivity;
import com.ttrm.ttconnection.activity.WithdrawCashActivity;
import com.ttrm.ttconnection.entity.BannerBean;
import com.ttrm.ttconnection.entity.BaoJiStatusBean;
import com.ttrm.ttconnection.entity.CanonBean;
import com.ttrm.ttconnection.entity.ListNumBean;
import com.ttrm.ttconnection.entity.RecomeInfo;
import com.ttrm.ttconnection.entity.VersionInfoBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.LXRUtil;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.UpdateManger;
import com.ttrm.ttconnection.util.VolleyUtils;
import com.ttrm.ttconnection.view.ImageCycleView;
import com.ttrm.ttconnection.view.MyAdvertisementView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final int DOWN_ERROR = 0;
    private static TextView dialog_loading_num;
    private static AlertDialog dlg;
    private static AlertDialog dlg1;
    private Button btn_main;
    private ImageView main_info;
    private static String TAG = "MainActivity";
    private LinearLayout main_ll_sign;
    private ImageCycleView main_banner;
    private LinearLayout main_ll_bdadd;
    private LinearLayout main_ll_yjjf;
    private LinearLayout main_ll_dqjf;
    private LinearLayout main_ll_clear;
    private LinearLayout main_ll_jcdshy;
    private BannerBean bannerBean;
    private LinearLayout main_ll_bj;
    private static MainActivity ma;

    private String type = "1";//识别一键加粉还是地区加粉
    private String addType; //被动加粉 开启和关闭的开关

    static int num = 0;


    private static List<CanonBean.DataBean.PhoneListBean> dataList = new ArrayList<CanonBean.DataBean.PhoneListBean>();//一键加粉数据集合


    private static int currentCount = 0;//已添加添加通讯录总条数

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KeyUtils.SAVE_CODE:
                    currentCount = (int) msg.obj;
                    MyUtils.Loge(TAG, "currentCount:" + currentCount + "--msg.obj:" + msg.obj);
//                    if (currentCount == dataList.size()) {
//                        Toast.makeText(MyApplication.mContext, "添加成功", Toast.LENGTH_SHORT).show();
                    dlg.dismiss();
                    getNums();
                    MyAdvertisementView myAdvertisementView = new MyAdvertisementView(ma, R.layout.dialog_location_success);
                    myAdvertisementView.showDialog();
                    myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                        @Override
                        public void onEvent() {
                            MyUtils.Loge("AAA", "打开微信");
                            try {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setComponent(cmp);
                                ma.startActivity(intent);
                            } catch (Exception e) {
                                // TODO: handle exception
                                MyUtils.showToast(ma, "检查到您手机没有安装微信，请安装后使用该功能");
                            }
                        }
                    });
                    break;
                case KeyUtils.DELETE_CODE:
//                    dlg1.dismiss();
//                    Toast.makeText(MyApplication.mContext, "删除成功", Toast.LENGTH_SHORT).show();
                    break;
                case KeyUtils.LOADING_CODE:
                    dlg.show();
                    int count = (int) msg.obj;
                    dialog_loading_num.setText(String.valueOf(count));
                    break;
            }
        }
    };
    public static Handler handler3 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KeyUtils.DELETE_CODE:
                    dlg1.dismiss();
                    Toast.makeText(MyApplication.mContext, "删除成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private Handler handler4 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWN_ERROR:
                    //下载apk失败
                    Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private long exitTime = 0l;


    private Button main_cash;
    private VersionInfoBean versionBean;
    private List<String> bannerTypeList = new ArrayList<>();
    private List<String> bannerPicList = new ArrayList<>();
    private RecomeInfo recomeInfo;
    private TextView main_account;
    private TextView main_num;
    private TextView main_income;
    private BaoJiStatusBean bjStatus;
    private Button main_invite;
    private static ListNumBean listNumBean;
    private static TextView main_location_num;
    private static TextView main_one_num;
    private TextView main_tv_bd;
    private TextView main_tv_bj;
    private int status;   // 被动加粉状态
    private TextView mian_tv_sign;
    private WebView main_wv;
    private String urlShow;
    private ImageView iv_reward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityUtil.add(this);
        ma = this;
        initView();
        initData();
        //假的加载动画
        showLoading();
        //删除动画
        deleteDialog();
    }

    private void initData() {
        getBanner();
//        getVersion();
        getInfo();
        initWebview();
    }

    private void initWebview() {
        urlShow = HttpAddress.PHONE_H5;
        MyUtils.Loge("aaa", "改后----urlShow::" + urlShow);
        WebSettings webSettings = main_wv.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);

        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);//WebView中包含一个ZoomButtonsController，当使用web.getSettings().setBuiltInZoomControls(true);启用后，用户一旦触摸屏幕，就会出现缩放控制图标。
//        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webSettings.setPluginsEnabled(true);//可以使用插件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//设置加载进来的页面自适应手机屏幕
        webSettings.setAllowFileAccess(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);//启用Dom内存（不加就显示不出来）
        main_wv.setWebChromeClient(new WebChromeClient());
//        加载需要显示的网页main_wv
        main_wv.loadUrl(urlShow);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNums();
        getAddStatus();
        getBjStatus();
        getVersion();
    }

    /**
     * 获取版本信息
     */
    private void getVersion() {
        MyUtils.Loge(TAG, "获取版本 时间戳:" + MyUtils.getTimestamp());
        String url = HttpAddress.BASE_URL + HttpAddress.GET_VERSION;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "版本信息---response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        Gson gson = new Gson();
                        versionBean = gson.fromJson(response, VersionInfoBean.class);
                        if (versionBean != null) {
                            MyApplication.update_url = versionBean.getData().getVersion().getUrl();
                            MyApplication.update_content = versionBean.getData().getVersion().getMsg();
                            if (Double.valueOf(versionBean.getData().getVersion().getSversion()) > MyUtils.getVersionCode(MainActivity.this) && !TextUtils.isEmpty(versionBean.getData().getVersion().getUrl())) {
                                // TODO 下载
//                                showVersionDialog(MainActivity.this,versionBean.getData().getVersion().getUrl());
                                selectPermission();
                            }

                        }
                    }
                    ActivityUtil.toLogin(MainActivity.this, errorCode);
                } catch (Exception e) {
                    MyUtils.Loge(TAG, "e:" + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MainActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "1");
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
    }

    /**
     * 下载APP
     */
    private void downloadApk() {

        new UpdateManger(MainActivity.this, 1).checkUpdateInfo();
    }

    /**
     * 判断读写权限
     */
    private void selectPermission() {
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
                downloadApk();
            }
        } else {
            downloadApk();
        }
    }

    /**
     * 删除加载动画
     */
    private static void showLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ma);
        LayoutInflater inflater = ma.getLayoutInflater();
        final View layout = inflater.inflate(R.layout.dialog_delete, null);
        builder.setView(layout);
        dlg1 = builder.create();
        dlg1.setCanceledOnTouchOutside(false);
    }

    /**
     * 假的加载动画
     */
    private static void deleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ma);
        LayoutInflater inflater = ma.getLayoutInflater();
        final View layout = inflater.inflate(R.layout.dialog_loading, null);
        dialog_loading_num = (TextView) layout.findViewById(R.id.dialog_loading_num);
        builder.setView(layout);
        dlg = builder.create();
        dlg.setCanceledOnTouchOutside(false);
    }

    /**
     * 获取推荐信息
     */
    private void getInfo() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_MAIN_INFO;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "推荐信息:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        Gson gson = new Gson();
                        recomeInfo = gson.fromJson(response, RecomeInfo.class);
                        if (recomeInfo != null && recomeInfo.getData() != null) {
                            main_income.setText(recomeInfo.getData().getIncome());
                            main_num.setText(recomeInfo.getData().getRecomCount());
                            main_account.setText(recomeInfo.getData().getBalance());
                        }
                    }
                    ActivityUtil.toLogin(MainActivity.this, errorCode);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MainActivity.this, "网络有问题");
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

    public static void startActivity1(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setClass(context, MainActivity.class);
    }

    private void initView() {
        btn_main = (Button) findViewById(R.id.btn_main);
        btn_main.setOnClickListener(this);
        main_info = (ImageView) findViewById(R.id.main_info);
        main_info.setOnClickListener(this);
        main_ll_sign = (LinearLayout) findViewById(R.id.main_ll_sign);
        main_ll_sign.setOnClickListener(this);
        main_ll_bdadd = (LinearLayout) findViewById(R.id.main_ll_bdadd);
        main_ll_bdadd.setOnClickListener(this);
        main_ll_yjjf = (LinearLayout) findViewById(R.id.yjjf_linear);
        main_ll_yjjf.setOnClickListener(this);
        main_ll_dqjf = (LinearLayout) findViewById(R.id.dqjf_linear);
        main_ll_dqjf.setOnClickListener(this);
        main_ll_clear = (LinearLayout) findViewById(R.id.clear_linear);
        main_ll_clear.setOnClickListener(this);
        main_ll_jcdshy = (LinearLayout) findViewById(R.id.jcdshy_linear);
        main_ll_jcdshy.setOnClickListener(this);

        main_cash = (Button) findViewById(R.id.main_cash);
        main_cash.setOnClickListener(this);

        main_ll_bj = (LinearLayout) findViewById(R.id.main_ll_bj);
        main_ll_bj.setOnClickListener(this);
        main_banner = (ImageCycleView) findViewById(R.id.main_banner);
        main_account = (TextView) findViewById(R.id.main_account);
        main_num = (TextView) findViewById(R.id.main_num);
        main_income = (TextView) findViewById(R.id.main_income);
        main_invite = (Button) findViewById(R.id.main_invite);
        main_invite.setOnClickListener(this);

        main_location_num = (TextView) findViewById(R.id.main_location_num);
        main_one_num = (TextView) findViewById(R.id.main_one_num);
        main_tv_bd = (TextView) findViewById(R.id.main_tv_bd);
        main_tv_bj = (TextView) findViewById(R.id.main_tv_bj);
        mian_tv_sign = (TextView) findViewById(R.id.mian_tv_sign);
        mian_tv_sign.setOnClickListener(this);
        main_wv = (WebView) findViewById(R.id.main_wv);
        iv_reward = (ImageView) findViewById(R.id.iv_reward);
        Picasso.with(this).load(HttpAddress.MAIN_PNG).memoryPolicy(MemoryPolicy.NO_CACHE).into(iv_reward);
        iv_reward.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_info:
                MyUtils.Loge(TAG, "点击了");
//                UserInfoActivity.startActivity(this);
                startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
                break;
            case R.id.btn_main:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.main_ll_sign:
                startActivity(new Intent(MainActivity.this, SignActivity.class));
                break;
            case R.id.main_ll_bdadd:
                if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_name))) {
                    switch (status) {
                        case 0:
                            startActivity(new Intent(MainActivity.this, BDAddActivity.class));
                            break;
                        case 1:
                            MyAdvertisementView myAdvertisementView = new MyAdvertisementView(MainActivity.this, R.layout.dialog_bd_close);
                            myAdvertisementView.showDialog();
                            myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                                @Override
                                public void onEvent() {
                                    MyUtils.Loge(TAG, "关闭被动加粉界面");
                                    addType = "2";
                                    selectAddStatus();
                                }
                            });
                            break;

                        case 2:
                            MyAdvertisementView myAdvertisementView1 = new MyAdvertisementView(MainActivity.this, R.layout.dialog_bd_open);
                            myAdvertisementView1.showDialog();
                            myAdvertisementView1.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                                @Override
                                public void onEvent() {
                                    MyUtils.Loge(TAG, "关闭 打开被动加粉界面");
                                    addType = "1";
                                    selectAddStatus();
                                }
                            });
                            break;
                    }

                } else {
                    showAlertDialog("提示", "请完善一下您的昵称再继续吧~", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(MainActivity.this, EditNameActivity.class));
                            dialogInterface.dismiss();
                        }
                    }, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                }
                break;
            case R.id.main_ll_bj: //爆机
                if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_name))) {
                    switch (bjStatus.getData().getStatus()) {
                        case 0:
                            //无爆机
                            startActivity(new Intent(MainActivity.this, BaoJiActivity.class));
                            break;
                        case 1:
                            MyAdvertisementView myAdvertisementView = new MyAdvertisementView(MainActivity.this, R.layout.dialog_bj_ing);
                            myAdvertisementView.showDialog();
                            myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                                @Override
                                public void onEvent() {
                                    MyUtils.Loge(TAG, "朕知道了");
                                }
                            });
                            break;
                    }
                } else {
                    showAlertDialog("提示", "请完善一下您的昵称再继续吧~", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(MainActivity.this, EditNameActivity.class));
                            dialogInterface.dismiss();
                        }
                    }, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                }
                break;
            case R.id.yjjf_linear://一键加粉
                type = "1";
//                getCanon();//获取一键加粉数据
                MyAdvertisementView myAdvertisementView1 = new MyAdvertisementView(MainActivity.this, R.layout.dialog_bj_one);
                myAdvertisementView1.showDialog();
                myAdvertisementView1.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                    @Override
                    public void onEvent() {
                        MyUtils.Loge(TAG, "朕知道了");
                        saveCanon();
                    }
                });

                break;
            case R.id.dqjf_linear://地区加粉
//                type = "2";
//                getCanon();//获取地区加粉数据

                startActivity(new Intent(MainActivity.this, LocationAddActivity.class));
                break;
            case R.id.clear_linear://清除通讯录

                MyAdvertisementView myAdvertisementView = new MyAdvertisementView(MainActivity.this, R.layout.dialog_clear);
                myAdvertisementView.showDialog();
                myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                    @Override
                    public void onEvent() {
                        deleteCanon();
                    }
                });

                break;
            case R.id.jcdshy_linear://检测单删好友
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("URL", HttpAddress.H5_GONGJUXIANG);
                intent.putExtra("title", "检测单删好友");
                startActivity(intent);
                break;
            case R.id.main_cash:
                startActivity(new Intent(MainActivity.this, WithdrawCashActivity.class));
                break;
            case R.id.main_invite:
                startActivity(new Intent(MainActivity.this, MyRewardActivity.class));
                break;
            case R.id.mian_tv_sign:
                startActivity(new Intent(MainActivity.this, SignActivity.class));
                break;
            case R.id.iv_reward:
                startActivity(new Intent(MainActivity.this, MyRewardActivity.class));
                break;
        }
    }

    /**
     * 获取爆机状态
     */
    private void getBjStatus() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_BAOJI_STATUS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "爆机状态：" + response);
                try {
                    Gson gson = new Gson();
                    bjStatus = gson.fromJson(response, BaoJiStatusBean.class);
                    if (bjStatus != null) {
                        if (bjStatus.getErrorCode() == 1) {
                            if (bjStatus.getData().getStatus() == 1) {
                                //爆机中
                                main_tv_bj.setVisibility(View.VISIBLE);
                                main_tv_bj.setText("正在爆机中...");
                            } else {
                                main_tv_bj.setVisibility(View.GONE);
                            }
                        }
                        ActivityUtil.toLogin(MainActivity.this, bjStatus.getErrorCode());
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MainActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                MyUtils.Loge(TAG, "时间戳：" + MyUtils.getTimestamp());
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
    }

    /**
     * 获取被加状态
     */
    private void getAddStatus() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_ADD_STATUS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        status = jsonObject1.getInt("status");//状态1被动加粉中（开启）2被动加粉中（关闭）0无被加加粉
                        switch (status) {
                            case 1:
                                main_tv_bd.setVisibility(View.VISIBLE);
                                main_tv_bd.setText("被动加粉已开启");
                                break;
                            case 2:
                                main_tv_bd.setVisibility(View.VISIBLE);
                                main_tv_bd.setText("被动加粉已关闭");
                                break;
                            default:
                                main_tv_bd.setVisibility(View.GONE);
                                break;
                        }
                    }
                    ActivityUtil.toLogin(MainActivity.this, errorCode);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MainActivity.this, "网络有问题");
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
     * 被动加粉开关
     */
    private void selectAddStatus() {
        String url = HttpAddress.BASE_URL + HttpAddress.SELECT_ADD_STATUS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    String errorMsg = jsonObject.getString("errorMsg");
                    MyUtils.showToast(MainActivity.this, errorMsg);
                    if (errorCode == 1) {
                        getAddStatus();
                    }
                    ActivityUtil.toLogin(MainActivity.this, errorCode);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MainActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", addType);
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
     * 获取banner
     */
    public void getBanner() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_BANNER;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "banner:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        Gson gson = new Gson();
                        bannerBean = gson.fromJson(response, BannerBean.class);
                        if (bannerBean != null) {
                            setBanners();
                            setAdds();
                        }

                    }
                    ActivityUtil.toLogin(MainActivity.this, errorCode);
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MainActivity.this, "网络有问题");
                MyUtils.Loge(TAG, "error::" + error.getMessage());
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
     * 广告弹窗
     */
    private void setAdds() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        MyUtils.Loge(TAG, "当前日期：" + date);
        MyUtils.Loge(TAG, "url：" + bannerBean.getData().getPopBannerList().get(0).getUrl());
        if (SaveUtils.getString(KeyUtils.TIME) != null && SaveUtils.getString(KeyUtils.TIME).equals(date)) {
        } else {
            final MyAdvertisementView myAdvertisementView = new MyAdvertisementView(MainActivity.this, R.layout.dialog_adds, bannerBean.getData().getPopBannerList().get(0).getUrl());
            myAdvertisementView.showDialog();
            myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                @Override
                public void onEvent() {
                    Intent intent = new Intent(MainActivity.this, WebActivity.class);
                    intent.putExtra("URL", bannerBean.getData().getPopBannerList().get(0).getLink());
                    intent.putExtra("title", "详情");
                    startActivity(intent);
                    myAdvertisementView.dismiss();
                }
            });

        }
        SaveUtils.setString(KeyUtils.TIME, date);
    }

    /**
     * 设置banner图
     */
    private void setBanners() {
        for (int i = 0; i < bannerBean.getData().getBannerList().size(); i++) {
            bannerPicList.add(bannerBean.getData().getBannerList().get(i).getUrl());
            bannerTypeList.add(bannerBean.getData().getBannerList().get(i).getType());
        }
        ImageCycleView.ImageCycleViewListener imageCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                Picasso.with(MainActivity.this).load(imageURL).into(imageView);
            }

            @Override
            public void onImageClick(int position, View imageView) {
                switch (bannerBean.getData().getBannerList().get(position).getType()) {
                    case "1":
                        //TODO 跳转web
                        MyUtils.Loge(TAG, "跳转web网页");
                        if (!TextUtils.isEmpty(bannerBean.getData().getBannerList().get(position).getLink())) {
                            Intent intent = new Intent(MainActivity.this, BannerActivity.class);
                            intent.putExtra("URL", bannerBean.getData().getBannerList().get(position).getLink());
                            intent.putExtra("title", "详情");
                            startActivity(intent);
                        }
                        break;
                    case "2":
                        MyUtils.Loge(TAG, "跳转原生方法");
                        break;
                }
            }
        };
        main_banner.setImageResources((ArrayList<String>) bannerPicList, imageCycleViewListener);
        main_banner.startImageCycle();
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
                    } else if (errorCode == 40001) {
                        ActivityUtil.toLogin(MainActivity.this, errorCode);
                    } else {
                        Toast.makeText(MainActivity.this, jsonObject.getString("errorMsg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MainActivity.this, "网络有问题");
                MyUtils.Loge(TAG, "error::" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                map.put("type", type);
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
    }

    /**
     * 判断通讯录权限
     */
    private void saveCanon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 0x1);
                return;
            } else {
                getCanon();//获取数据
            }
        } else {
            getCanon();
        }
    }

    /**
     * 添加到通讯录
     */
    private void addPhone() {
        //添加通讯录
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < dataList.size(); i++) {
                    boolean isLast = false;
                    if (i == dataList.size() - 1) {
                        isLast = true;
                    } else {
                        isLast = false;
                    }
                    LXRUtil.addContacts(MainActivity.this, dataList.get(i).getNickname(), dataList.get(i).getPhone(), i, 1, isLast);
                }
            }
        }).start();
    }

    /**
     * 从通讯录删除
     */
    private void deleteCanon() {
        dlg1.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(MyApplication.mContext, "删除成功", Toast.LENGTH_SHORT).show();
//                MyUtils.showToast(MyApplication.mContext,"删除成功");
//                dlg1.dismiss();

                Message message = Message.obtain();
                message.what = KeyUtils.DELETE_CODE;
                handler3.sendMessage(message);
            }
        }.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 0x2);
                return;
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LXRUtil.deleteContacts(MainActivity.this);
                    }
                }).start();
            }
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LXRUtil.deleteContacts(MainActivity.this);
                }
            }).start();
        }
    }

    /**
     * 剩余加粉数量
     */
    private static void getNums() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_LAST_NUM;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    listNumBean = gson.fromJson(response, ListNumBean.class);
                    if (listNumBean != null) {
                        if (listNumBean.getErrorCode() == 1) {
                            if (Double.valueOf(listNumBean.getData().getRemainYjCount()) > 0) {
                                main_one_num.setText("今天还剩" + listNumBean.getData().getRemainYjCount() + "个名额");
                            } else {
                                main_one_num.setText("今日名额已用完");
                            }
                            if (Double.valueOf(listNumBean.getData().getRemainDqCount()) > 0) {
                                main_location_num.setText("今天还剩" + listNumBean.getData().getRemainDqCount() + "个名额");
                            } else {
                                main_location_num.setText("今日名额已用完");
                            }
                        }
                        ActivityUtil.toLogin(ma, listNumBean.getErrorCode());
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(ma, "网络有问题");
                MyUtils.Loge(TAG, "error::" + error.getMessage());
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
        VolleyUtils.getInstance(ma).addToRequestQueue(stringRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    MyUtils.Loge("stones", "权限回调--获取权限失败");
                    Toast.makeText(MainActivity.this, "请打开手机设置，权限管理，允许添添人脉读取、写入和删除联系人信息后再使用立即加粉", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "权限获取成功", Toast.LENGTH_SHORT).show();
                    MyUtils.Loge("stones", "权限回调--获取权限成功");
                    saveCanon();
                }
                break;
            case 0x2:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "请打开手机设置，权限管理，允许添添人脉读取、写入和删除联系人信息后再使用立即加粉", Toast.LENGTH_SHORT).show();
                } else {
                    saveCanon();
                }
                break;
            case 0:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    // request successfully, handle you transactions
                    downloadApk();
                } else {

                    // permission denied
                    // request failed
                    MyUtils.Loge(TAG, "权限失败");
                }
                break;
        }
    }

    /**
     * 返回键的监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//                ActivityUtil.exitAll();
                finish();
                System.exit(0);
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }
}
