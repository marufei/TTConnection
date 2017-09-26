package com.ttrm.ttconnection.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ttrm.ttconnection.MainActivity;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.BaoJiStatusBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.view.MyAdvertisementView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 用户中心
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "UserInfoActivity";
    private TextView info_tv_diamond;
    private TextView info_tv_phone;
    private TextView info_tv_content;
    private LinearLayout info_ll_add;
    private LinearLayout info_ll_bj;
    private LinearLayout info_ll_diamond;
    private LinearLayout info_ll_change;
    private LinearLayout info_ll_sm;
    private LinearLayout info_ll_name;
    private LinearLayout info_ll_version;
    private LinearLayout info_ll_custom;
    private Button info_btn_loginout;
    private EditText et_name;
    private String addType;
    private BaoJiStatusBean bjStatus;
    private TextView info_tv_bmdl;
    private TextView activity_user_info_bj;
    private TextView activity_user_info_bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ActivityUtil.add(this);
        initViews();
        initData();
        getBjStatusFirst();
        getAddStatusFirst();
    }

    private void initData() {
        info_tv_phone.setText(MyUtils.Replace_phone_Str(SaveUtils.getString(KeyUtils.user_phone)));
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.setClass(context, UserInfoActivity.class);
    }

    private void initViews() {
        setToolBar("个人中心");
        info_tv_diamond = (TextView) findViewById(R.id.info_tv_diamond);
        info_tv_phone = (TextView) findViewById(R.id.info_tv_phone);
        info_tv_content = (TextView) findViewById(R.id.info_tv_content);
        info_ll_add = (LinearLayout) findViewById(R.id.info_ll_add);
        info_ll_bj = (LinearLayout) findViewById(R.id.info_ll_bj);
        info_ll_diamond = (LinearLayout) findViewById(R.id.info_ll_diamond);
        info_ll_change = (LinearLayout) findViewById(R.id.info_ll_change);
        info_ll_sm = (LinearLayout) findViewById(R.id.info_ll_sm);
        info_ll_name = (LinearLayout) findViewById(R.id.info_ll_name);
        info_ll_version = (LinearLayout) findViewById(R.id.info_ll_version);
        info_ll_custom = (LinearLayout) findViewById(R.id.info_ll_custom);
        info_btn_loginout = (Button) findViewById(R.id.info_btn_loginout);
        info_ll_add.setOnClickListener(this);
        info_ll_bj.setOnClickListener(this);
        info_ll_diamond.setOnClickListener(this);
        info_ll_change.setOnClickListener(this);
        info_ll_sm.setOnClickListener(this);
        info_ll_name.setOnClickListener(this);
        info_ll_version.setOnClickListener(this);
        info_ll_custom.setOnClickListener(this);
        info_btn_loginout.setOnClickListener(this);

        info_tv_bmdl = (TextView) findViewById(R.id.info_tv_bmdl);
        activity_user_info_bj=(TextView)findViewById(R.id.activity_user_info_bj);
        activity_user_info_bd=(TextView)findViewById(R.id.activity_user_info_bd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_ll_add:
                MyUtils.Loge(TAG, "点击了加粉");
                getAddStatus();
                break;
            case R.id.info_ll_bj:
                if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_name))) {
                    getBjStatus();
                } else {
                    showAlertDialog("提示", "请完善一下您的姓名再继续爆机吧~", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(UserInfoActivity.this, EditNameActivity.class));
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
            case R.id.info_ll_diamond:      //获取钻石
                startActivity(new Intent(this, SignActivity.class));
                break;
            case R.id.info_ll_change://兑换码
                startActivity(new Intent(this, RedeemActivity.class));
                break;
            case R.id.info_ll_sm:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("URL", HttpAddress.URL_H5_READ);
                intent.putExtra("title", "新手教学");
                startActivity(intent);
                break;
            case R.id.info_ll_name:     //修改昵称
//                myName();
                startActivity(new Intent(UserInfoActivity.this, EditNameActivity.class));
                break;
            case R.id.info_ll_version:

                break;
            case R.id.info_ll_custom:       //联系客服
                Intent intent1 = new Intent(this, WebActivity.class);
                intent1.putExtra("URL", HttpAddress.URL_H5_DELETE);
                intent1.putExtra("title", "咨询客服");
                startActivity(intent1);

                break;
            case R.id.info_btn_loginout:
                showAlertDialog("提示", "确认退出吗？", "确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loginOut();
                        dialogInterface.dismiss();
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDiamondCount();
        if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_name))) {
            info_tv_bmdl.setText(SaveUtils.getString(KeyUtils.user_name));
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
                                MyAdvertisementView myAdvertisementView = new MyAdvertisementView(UserInfoActivity.this, R.layout.dialog_bj_ing);
                                myAdvertisementView.showDialog();
                                myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                                    @Override
                                    public void onEvent() {
                                        MyUtils.Loge(TAG, "朕知道了");
                                        activity_user_info_bj.setText("爆机中...");
                                    }
                                });
                            }
                            if (bjStatus.getData().getStatus() == 0) {
                                //无爆机
                                activity_user_info_bj.setText("爆机");
                                startActivity(new Intent(UserInfoActivity.this, BaoJiActivity.class));
                            }
                            ActivityUtil.toLogin(UserInfoActivity.this, bjStatus.getErrorCode());
                        }
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfoActivity.this, "网络有问题");
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
        Volley.newRequestQueue(this).add(stringRequest);
    }
    /**
     * 初始化爆机状态
     */
    private void getBjStatusFirst() {
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
                                activity_user_info_bj.setText("爆机中...");
                            }
                            if (bjStatus.getData().getStatus() == 0) {
                                //无爆机
                                activity_user_info_bj.setText("爆机");
                            }
                            ActivityUtil.toLogin(UserInfoActivity.this, bjStatus.getErrorCode());
                        }
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfoActivity.this, "网络有问题");
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
        Volley.newRequestQueue(this).add(stringRequest);
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
                        int status = jsonObject1.getInt("status");        //状态1被动加粉中（开启）2被动加粉中（关闭）0无被加加粉
                        switch (status) {
                            case 0:
                                startActivity(new Intent(UserInfoActivity.this, BDAddActivity.class));
                                break;
                            case 1:
                                MyAdvertisementView myAdvertisementView = new MyAdvertisementView(UserInfoActivity.this, R.layout.dialog_bd_close);
                                myAdvertisementView.showDialog();
                                myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                                    @Override
                                    public void onEvent() {
                                        MyUtils.Loge(TAG, "微信回调成功，点击了按钮");
                                        addType = "2";
                                        activity_user_info_bd.setText("被动加粉已关闭");
                                        selectAddStatus();
                                    }
                                });
                                break;

                            case 2:

                                MyAdvertisementView myAdvertisementView1 = new MyAdvertisementView(UserInfoActivity.this, R.layout.dialog_bd_open);
                                myAdvertisementView1.showDialog();
                                myAdvertisementView1.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                                    @Override
                                    public void onEvent() {
                                        MyUtils.Loge(TAG, "微信回调成功，点击了按钮");
                                        addType = "1";
                                        activity_user_info_bd.setText("被动加粉已开启");
                                        selectAddStatus();
                                    }
                                });
                                break;
                        }
                    }
                    ActivityUtil.toLogin(UserInfoActivity.this, errorCode);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfoActivity.this, "网络有问题");
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
     * 初始化被加状态
     */
    private void getAddStatusFirst() {
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
                        int status = jsonObject1.getInt("status");        //状态1被动加粉中（开启）2被动加粉中（关闭）0无被加加粉
                        switch (status) {
                            case 0:
                                activity_user_info_bd.setText("被动加粉");
                                break;
                            case 1:
                               activity_user_info_bd.setText("被动加粉已开启");
                                break;

                            case 2:
                               activity_user_info_bd.setText("被动加粉已关闭");
                                break;
                        }
                    }
                    ActivityUtil.toLogin(UserInfoActivity.this, errorCode);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfoActivity.this, "网络有问题");
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
                    MyUtils.showToast(UserInfoActivity.this, errorMsg);
                    ActivityUtil.toLogin(UserInfoActivity.this, errorCode);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfoActivity.this, "网络有问题");
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
        Volley.newRequestQueue(this).add(stringRequest);
    }

    /**
     * 获取钻石数量
     */
    private void getDiamondCount() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_DIAMONDCOUNT;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String diamondCount = data.getString("diamondCount");
                        info_tv_diamond.setText(diamondCount);
                    }
                    ActivityUtil.toLogin(UserInfoActivity.this, errorCode);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfoActivity.this, "网络有问题");

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
     * 退出
     */
    private void loginOut() {
        String url = HttpAddress.BASE_URL + HttpAddress.LOGIN_OUT;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    String errorMsg = jsonObject.getString("errorMsg");
                    if (errorCode == 1) {
//                        SaveUtils.setString(KeyUtils.user_phone,"");
                        SaveUtils.setString(KeyUtils.user_login_token, "");
                        SaveUtils.setString(KeyUtils.user_id, "");
                        SaveUtils.setString(KeyUtils.user_name, "");
                        SaveUtils.setString(KeyUtils.user_regcode, "");
                        SaveUtils.setString(KeyUtils.user_time, "");
                        SaveUtils.setString(KeyUtils.user_UID, "");
                        startActivity(new Intent(UserInfoActivity.this, LoginActivity.class));
                        ActivityUtil.exitAll();
                    }
                } catch (Exception e) {
                    MyUtils.Loge(TAG, "e:" + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfoActivity.this, "网络有问题");
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
        Volley.newRequestQueue(UserInfoActivity.this).add(stringRequest);
    }

    /**
     * 我的昵称
     */
    public void myName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.dialog1_user_info, null);//布局文件
        TextView tv_sure = (TextView) layout.findViewById(R.id.dialog_tv_commit);
        TextView tv_cancel = (TextView) layout.findViewById(R.id.dialog_tv_cancel);
        et_name = (EditText) layout.findViewById(R.id.dialog_et_name);
        builder.setView(layout);
        final AlertDialog dlg = builder.create();

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.Loge(TAG, "str_name:" + et_name.getText().toString().trim());
                if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                    MyUtils.showToast(UserInfoActivity.this, "请输入昵称");
                    return;
                }
                editName();
                dlg.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
        dlg.show();
    }

    public void editName() {
        String url = HttpAddress.BASE_URL + HttpAddress.EDIT_NAME;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    String errorMsg = jsonObject.getString("errorMsg");
                    MyUtils.showToast(UserInfoActivity.this, errorMsg);
                    ActivityUtil.toLogin(UserInfoActivity.this, errorCode);
                } catch (Exception e) {
                    MyUtils.Loge(TAG, "e:" + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfoActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("nickname", et_name.getText().toString().trim());
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(UserInfoActivity.this).add(stringRequest);
    }


}
