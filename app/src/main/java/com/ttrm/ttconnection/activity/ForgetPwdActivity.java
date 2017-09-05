package com.ttrm.ttconnection.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.CodeCountDownTimer;
import com.ttrm.ttconnection.util.MyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 忘记密码
 */
public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener {

    private Button forget_sure;
    private EditText forget_phone;
    private EditText forget_smscode;
    private EditText forget_pwd1;
    private EditText forget_pwd2;
    private Button forget_btn_code;
    private String TAG = "ForgetPwdActivity";
    private String sms_token;
    //短信验证计时
    private CodeCountDownTimer mCodeCountDownTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ActivityUtil.add(this);
        initViews();
        initDatas();
    }

    private void initDatas() {
        //倒计时
        mCodeCountDownTimer = new CodeCountDownTimer(60 * 1000L, 1000L, forget_btn_code, R.drawable.btn_purple,
                R.drawable.btn_purple);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ForgetPwdActivity.class);
        intent.setClass(context, ForgetPwdActivity.class);
    }

    private void initViews() {
        setToolBar("忘记密码");
        forget_phone = (EditText) findViewById(R.id.forget_phone);
        forget_smscode = (EditText) findViewById(R.id.forget_smscode);
        forget_pwd1 = (EditText) findViewById(R.id.forget_pwd1);
        forget_pwd2 = (EditText) findViewById(R.id.forget_pwd2);
        forget_sure = (Button) findViewById(R.id.forget_sure);
        forget_btn_code = (Button) findViewById(R.id.forget_btn_code);
        forget_sure.setOnClickListener(this);
        forget_btn_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_btn_code:
                if (TextUtils.isEmpty(forget_phone.getText().toString().trim())) {
                    MyUtils.showToast(ForgetPwdActivity.this, "请输入手机号");
                    return;
                }
                if (!MyUtils.isPhoneNumber(forget_phone.getText().toString().trim())) {
                    MyUtils.showToast(ForgetPwdActivity.this, "请输入正确的手机号");
                    return;
                }
                getSms();
                break;
            case R.id.forget_sure:
                if (TextUtils.isEmpty(forget_phone.getText().toString().trim())) {
                    MyUtils.showToast(ForgetPwdActivity.this, "请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(forget_smscode.getText().toString().trim())) {
                    MyUtils.showToast(ForgetPwdActivity.this, "请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(forget_pwd1.getText().toString().trim()) || TextUtils.isEmpty(forget_pwd2.getText().toString().trim())) {
                    MyUtils.showToast(ForgetPwdActivity.this, "请输入密码");
                    return;
                }
                if (!MyUtils.isPhoneNumber(forget_phone.getText().toString().trim())) {
                    MyUtils.showToast(ForgetPwdActivity.this, "请输入正确的手机号");
                    return;
                }
                if (!forget_pwd1.getText().toString().trim().equals(forget_pwd2.getText().toString().trim())) {
                    MyUtils.showToast(ForgetPwdActivity.this, "两次密码不一致");
                    return;
                }
                if (!TextUtils.isEmpty(sms_token)) {
                    EditPwd();
                }
                break;
        }
    }

    /**
     * 获取短信验证码
     */
    private void getSms() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_SMS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    String errorMsg = jsonObject.getString("errorMsg");
                    if (errorCode == 1) {
                        MyUtils.showToast(ForgetPwdActivity.this, "验证码已发送至您手机");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        sms_token = jsonObject1.getString("sms_token");
                        mCodeCountDownTimer.start();
                    } else {
                        MyUtils.showToast(ForgetPwdActivity.this, errorMsg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(ForgetPwdActivity.this, "请检查网络设置");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "2");
                map.put("phone", forget_phone.getText().toString().trim());
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(ForgetPwdActivity.this).add(stringRequest);
    }

    /**
     * 重置密码
     */
    private void EditPwd() {
        String url = HttpAddress.BASE_URL + HttpAddress.RESET_PWD;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    String errorMsg = jsonObject.getString("errorMsg");
                    if (errorCode == 1) {
                        MyUtils.showToast(ForgetPwdActivity.this, errorMsg);
                        finish();
                    } else {
                        MyUtils.showToast(ForgetPwdActivity.this, errorMsg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(ForgetPwdActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", forget_phone.getText().toString().trim());
                map.put("smsCode", forget_smscode.getText().toString().trim());
                map.put("password", forget_pwd1.getText().toString().trim());
                map.put("sms_token", sms_token);
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(ForgetPwdActivity.this).add(stringRequest);
    }
}
