package com.ttrm.ttconnection.activity;

import android.Manifest;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ttrm.ttconnection.MyApplication;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.CanonBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.LXRUtil;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.VolleyUtils;
import com.ttrm.ttconnection.view.Dialogshow;
import com.ttrm.ttconnection.view.MyAdvertisementView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LocationAddActivity extends BaseActivity implements View.OnClickListener {

    private static TextView dialog_loading_num;
    private static AlertDialog dlg;
    private static int num;
    private static AlertDialog dlg2;
    private TextView location_tv_select;
    private Button location_btn_sure;
    public static String location;
    private Dialogshow dialog;
    private static String TAG = "LocationAddActivity";
    private String type = "2";
    private static LocationAddActivity locatiionAddActivity;
    private static List<CanonBean.DataBean.PhoneListBean> dataList = new ArrayList<>();

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
                    MyAdvertisementView myAdvertisementView = new MyAdvertisementView(locatiionAddActivity, R.layout.dialog_location_success);
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
                                locatiionAddActivity.startActivity(intent);
                            } catch (Exception e) {
                                // TODO: handle exception
                                MyUtils.showToast(locatiionAddActivity, "检查到您手机没有安装微信，请安装后使用该功能");
                            }
                        }
                    });
                    break;
                case KeyUtils.DELETE_CODE:
                    Toast.makeText(MyApplication.mContext, "删除成功", Toast.LENGTH_SHORT).show();
                    break;
                case KeyUtils.LOADING_CODE:
                    dlg.show();
                    int count = (int) msg.obj;
                    dialog_loading_num.setText(String.valueOf(count));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_add);
        ActivityUtil.add(this);
        locatiionAddActivity = this;
        initViews();
        //假的加载动画
        showLoading();
        //加载等待
        showLoad();
    }

    /**
     * 假的加载动画
     */
    private static void showLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(locatiionAddActivity);
        LayoutInflater inflater = locatiionAddActivity.getLayoutInflater();
        final View layout = inflater.inflate(R.layout.dialog_loading, null);
        dialog_loading_num = (TextView) layout.findViewById(R.id.dialog_loading_num);
        builder.setView(layout);
        dlg = builder.create();
        dlg.setCanceledOnTouchOutside(false);
    }

    private void initViews() {
        setToolBar("地区加粉");
        location_tv_select = (TextView) findViewById(R.id.location_tv_select);
        location_btn_sure = (Button) findViewById(R.id.location_btn_sure);
        location_tv_select.setOnClickListener(this);
        location_btn_sure.setOnClickListener(this);
        dialog = new Dialogshow(LocationAddActivity.this);
        dialog.setOnBtnlistenner(new Dialogshow.OnBtnlistenner() {
            @Override
            public void onSure() {
                if (!TextUtils.isEmpty(location)) {
                    location_tv_select.setText(location);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_tv_select:
                dialog.show();
                break;
            case R.id.location_btn_sure:
//                getCanon();
                location_btn_sure.setClickable(false);
                dlg2.show();
                saveCanon();
                break;
        }
    }

    /**
     * 地区加粉
     */
    private void getCanon() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_CANON;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "canon:" + response);
                dlg2.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        dataList.clear();
                        Gson gson = new Gson();
                        CanonBean bean = gson.fromJson(response, CanonBean.class);
                        dataList.addAll(bean.getData().getPhoneList());
//                        saveCanon();
                        addPhone();
                    } else if(errorCode==40001){
                        ActivityUtil.toLogin(LocationAddActivity.this, errorCode);
                    }else {
                        location_btn_sure.setEnabled(true);
                        Toast.makeText(LocationAddActivity.this, jsonObject.getString("errorMsg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    location_btn_sure.setEnabled(true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dlg2.dismiss();
                MyUtils.showToast(LocationAddActivity.this, "网络有问题");
                location_btn_sure.setEnabled(true);
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
     * 加载动画
     */
    private static void showLoad() {
        AlertDialog.Builder builder = new AlertDialog.Builder(locatiionAddActivity);
        LayoutInflater inflater = locatiionAddActivity.getLayoutInflater();
        final View layout = inflater.inflate(R.layout.dialog_show, null);
        builder.setView(layout);
        dlg2 = builder.create();
        dlg2.setCanceledOnTouchOutside(false);
    }

    /**
     * 添加到通讯录
     */
    private void saveCanon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(LocationAddActivity.this,
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

    /**
     * 保存到手机
     */
    private void addPhone(){
        location_btn_sure.setEnabled(true);
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
                    LXRUtil.addContacts(LocationAddActivity.this, dataList.get(i).getNickname(), dataList.get(i).getPhone(), i, 2, isLast);
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.v("stones", "权限回调--获取权限失败");
                    Toast.makeText(LocationAddActivity.this, "请打开手机设置，权限管理，允许天天人脉读取、写入和删除联系人信息后再使用立即加粉", Toast.LENGTH_SHORT).show();
                    location_btn_sure.setEnabled(true);
                } else {
                    Toast.makeText(LocationAddActivity.this, "权限获取成功", Toast.LENGTH_SHORT).show();
                    Log.v("stones", "权限回调--获取权限成功");
                    getCanon();
                }
                break;
            case 0x2:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(LocationAddActivity.this, "请打开手机设置，权限管理，允许天天人脉读取、写入和删除联系人信息后再使用立即加粉", Toast.LENGTH_SHORT).show();
                } else {
                    getCanon();
                }
                break;
        }
    }

}
