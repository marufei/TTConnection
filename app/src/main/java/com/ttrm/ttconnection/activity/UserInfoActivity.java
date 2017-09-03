package com.ttrm.ttconnection.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
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
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 用户中心
 */
public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initViews();
        initData();

    }

    private void initData() {
        info_tv_phone.setText(SaveUtils.getString(KeyUtils.user_phone));
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.setClass(context, UserInfoActivity.class);
    }

    private void initViews() {
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_ll_add:
                break;
            case R.id.info_ll_bj:
                break;
            case R.id.info_ll_diamond:
                break;
            case R.id.info_ll_change://兑换码
                startActivity(new Intent(this,RedeemActivity.class));
                break;
            case R.id.info_ll_sm:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("URL",HttpAddress.URL_H5_READ);
                startActivity(intent);
                break;
            case R.id.info_ll_name:
                myName();
                break;
            case R.id.info_ll_version:

                break;
            case R.id.info_ll_custom:

                break;
            case R.id.info_btn_loginout:

                loginOut();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDiamondCount();
    }

    /**
     * 获取钻石数量
     */
    private void getDiamondCount() {
        String url=HttpAddress.BASE_URL+HttpAddress.GET_DIAMONDCOUNT;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    if(errorCode==1){
                        JSONObject data = jsonObject.getJSONObject("data");
                        String diamondCount = data.getString("diamondCount");
                        info_tv_diamond.setText(diamondCount);
                    }
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfoActivity.this,"网络有问题");

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
     * 退出
     */
    private void loginOut() {
        String url=HttpAddress.BASE_URL+HttpAddress.LOGIN_OUT;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    String errorMsg=jsonObject.getString("errorMsg");
                    if(errorCode==1){
                        System.exit(0);
                    }
                }catch (Exception e){
                    MyUtils.Loge(TAG,"e:"+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(UserInfoActivity.this,"网络有问题");
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
                MyUtils.Loge(TAG,"str_name:"+et_name.getText().toString().trim());
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
                    if (errorCode == 1) {
                        MyUtils.showToast(UserInfoActivity.this, errorMsg);
                    } else {
                        MyUtils.showToast(UserInfoActivity.this, errorMsg);
                    }
                } catch (Exception e) {
                    MyUtils.Loge(TAG,"e:"+e.getMessage());
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
