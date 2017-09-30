package com.ttrm.ttconnection.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.ttrm.ttconnection.entity.LoginBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.CodeCountDownTimer;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ttrm.ttconnection.R.id.register_phone;

public class RegisterActivity extends BaseActivity  implements View.OnClickListener{

    private CodeCountDownTimer mCodeCountDownTimer;
    private Button register_tv_regcode;
    private Button register_btn_commit;
    private String TAG="RegisterActivity";
    private String sms_token;
    private String phone;
    private String regcode="";
    private EditText register_et_smscode;
    private EditText register_et_pwd;
    private TextView register_tv_phone;
    private TextView register_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityUtil.add(this);
        initView();
        initDatas();
    }

    private void initDatas() {
        //倒计时
        mCodeCountDownTimer = new CodeCountDownTimer(60 * 1000L, 1000L, register_tv_regcode, R.drawable.btn_white,
                R.drawable.btn_white);
        Intent intent=getIntent();
        phone=intent.getStringExtra("phone");
        regcode=intent.getStringExtra("regcode");

        register_tv_phone.setText(phone);

    }

    private void initView() {
        setToolBar("注册");
        register_tv_regcode=(Button)findViewById(R.id.register_tv_regcode);
        register_tv_regcode.setOnClickListener(this);
        register_btn_commit=(Button)findViewById(R.id.register_btn_commit);
        register_btn_commit.setOnClickListener(this);
        register_et_smscode=(EditText)findViewById(R.id.register_et_smscode);
        register_et_pwd=(EditText)findViewById(R.id.register_et_pwd);
        register_tv_phone=(TextView)findViewById(R.id.register_tv_phone);
        register_pro=(TextView)findViewById(R.id.register_pro);
        register_pro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_tv_regcode:
                getSms();
                break;
            case R.id.register_btn_commit:
                if(TextUtils.isEmpty(register_et_smscode.getText().toString())){
                    MyUtils.showToast(RegisterActivity.this,"请先填写短信验证码");
                    return;
                }
                if(TextUtils.isEmpty(register_et_pwd.getText().toString())){
                    MyUtils.showToast(RegisterActivity.this,"请完善密码");
                    return;
                }
                if(register_et_pwd.getText().toString().trim().length()<6){
                    MyUtils.showToast(RegisterActivity.this,"密码至少为6位字母或数字");
                    return;
                }
                register();
                break;
            case R.id.register_pro:
//                Intent intent=new Intent(RegisterActivity.this,WebActivity.class);
//                intent.putExtra("URL","file:///android_asset/TTRMDEALDoc.html");
//                intent.putExtra("title","注册协议");
//                startActivity(intent);
                Intent intent=new Intent(RegisterActivity.this,ProtocolActivity.class);
                startActivity(intent);
                break;
        }
    }
    /**
     * 获取短信验证码
     */
    private void getSms() {
        String url= HttpAddress.BASE_URL + HttpAddress.GET_SMS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    String errorMsg=jsonObject.getString("errorMsg");
                    if(errorCode==1){
                        MyUtils.showToast(RegisterActivity.this,"验证码已发送至您手机");
                        JSONObject jsonObject1=jsonObject.getJSONObject("data");
                        sms_token=jsonObject1.getString("sms_token");
                        MyUtils.Loge(TAG,"SMS_TOKEN:"+sms_token);
                        mCodeCountDownTimer.start();
                    }else if(errorCode==40001){
                        ActivityUtil.toLogin(RegisterActivity.this, errorCode);
                    }else {
                        MyUtils.showToast(RegisterActivity.this,errorMsg);
                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(RegisterActivity.this,"请检查网络设置");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "1");
                map.put("phone",phone);
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(RegisterActivity.this).add(stringRequest);
    }
    /**
     * 注册
     */
    public void register(){
        String url= HttpAddress.BASE_URL + HttpAddress.REGISTER;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    String errorMsg=jsonObject.getString("errorMsg");
                    if(errorCode==1){
//                        MainActivity.startActivity1(getActivity());
//                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
//                        RegisterActivity.this.finish();
//                        Gson gson=new Gson();
//                        RegisterBean registerBean=gson.fromJson(response,RegisterBean.class);
//                        if(registerBean!=null){
//
//                        }
                        login();
                    }else if(errorCode==40001){
                        ActivityUtil.toLogin(RegisterActivity.this, errorCode);
                    }else {
                        MyUtils.showToast(RegisterActivity.this,errorMsg);
                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(RegisterActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> map=new HashMap<>();
                map.put("phone",phone);
                map.put("smsCode",register_et_smscode.getText().toString().trim());
                map.put("password",register_et_pwd.getText().toString().trim());
                map.put("sms_token",sms_token);
                map.put("regCode",regcode);
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return  map;
            }
        };
        Volley.newRequestQueue(RegisterActivity.this).add(stringRequest);
    }

    /**
     * 登录
     */
    public void login(){
        String url= HttpAddress.BASE_URL+HttpAddress.LOGIN;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    String errorMsg=jsonObject.getString("errorMsg");
                    if(errorCode==1){
                        Gson gson=new Gson();
                        LoginBean loginBean=gson.fromJson(response,LoginBean.class);
                        if(loginBean!=null){
                            SaveUtils.setString(KeyUtils.user_id,loginBean.getData().getUserInfo().getId());
                            SaveUtils.setString(KeyUtils.user_name,loginBean.getData().getUserInfo().getNickname());
                            SaveUtils.setString(KeyUtils.user_login_token,loginBean.getData().getUserInfo().getLogin_token());
                            SaveUtils.setString(KeyUtils.user_phone,loginBean.getData().getUserInfo().getPhone());
                            SaveUtils.setString(KeyUtils.user_regcode,loginBean.getData().getUserInfo().getRegcode());
                            SaveUtils.setString(KeyUtils.user_UID,loginBean.getData().getUserInfo().getUID());
                            SaveUtils.setString(KeyUtils.user_time,loginBean.getData().getUserInfo().getRegtime());
                        }
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        LoginActivity.loginActivity.finish();
                        finish();
                    }else if(errorCode==40001){
                        ActivityUtil.toLogin(RegisterActivity.this, errorCode);
                    }else {
                        MyUtils.showToast(RegisterActivity.this,errorMsg);
                    }
                }catch (Exception e){
                    MyUtils.Loge(TAG,"e:"+e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(RegisterActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String > map=new HashMap<>();
                map.put("phone",phone);
                map.put("password",register_et_pwd.getText().toString().trim());
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(RegisterActivity.this).add(stringRequest);
    }
}
