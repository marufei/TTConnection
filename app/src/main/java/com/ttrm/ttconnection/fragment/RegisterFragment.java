package com.ttrm.ttconnection.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.activity.Main2Activity;
import com.ttrm.ttconnection.activity.RegisterActivity;
import com.ttrm.ttconnection.entity.Contant;
import com.ttrm.ttconnection.entity.LoginBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.CodeCountDownTimer;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.VolleyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MaRufei
 * time on 2017/8/21
 * Phone 13213580912
 * Email 867814102@qq.com
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button register_btn_code;
    private String TAG = "RegisterFragment";
    private String sms_token = "";
    private EditText register_phone;
    private EditText register_pwd1;
    private EditText register_smscode;
    private EditText register_pwd2;
    private EditText register_regcode;
    private Button register_btn_register;
    //短信验证计时
    private CodeCountDownTimer mCodeCountDownTimer = null;
    private String regcode;
    private ImageView register_iv_code;
    private ImageView register_iv_refresh;
    private EditText register_yzcode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, null);
        initViews();
        initDatas();
        return view;
    }

    private void initDatas() {
        //倒计时
        mCodeCountDownTimer = new CodeCountDownTimer(60 * 1000L, 1000L, register_btn_code, R.drawable.btn_white,
                R.drawable.btn_white);
        register_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (register_phone.length() == 11) {
                    getInviteCode();
                }
            }
        });
    }

    /**
     * 获取web邀请码
     */
    private void getInviteCode() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_WEB_REG;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    String errorMsg = jsonObject.getString("errorMsg");
                    if (errorCode == 1) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String regcode = jsonObject1.getString("regcode");

                        if (!TextUtils.isEmpty(regcode)) {
                            register_regcode.setText(regcode);
                        }
                    } else if (errorCode == 40001) {
                        ActivityUtil.toLogin(getActivity(), errorCode);
                    } else {
                        MyUtils.showToast(getActivity(), errorMsg);
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
                map.put("phone", register_phone.getText().toString().trim());
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void initViews() {
        register_btn_code = (Button) view.findViewById(R.id.register_btn_code);
        register_btn_code.setOnClickListener(this);
        register_phone = (EditText) view.findViewById(R.id.register_phone);
        register_pwd1 = (EditText) view.findViewById(R.id.register_pwd1);
        register_smscode = (EditText) view.findViewById(R.id.register_smscode);
        register_pwd2 = (EditText) view.findViewById(R.id.register_pwd2);
        register_regcode = (EditText) view.findViewById(R.id.register_regcode);
        register_btn_register = (Button) view.findViewById(R.id.register_btn_register);
        register_btn_register.setOnClickListener(this);
        register_iv_code = (ImageView) view.findViewById(R.id.register_iv_code);
        register_iv_refresh = (ImageView) view.findViewById(R.id.register_iv_refresh);
        register_iv_refresh.setOnClickListener(this);
        register_yzcode=(EditText)view.findViewById(R.id.register_yzcode);
        Picasso.with(getActivity()).load(HttpAddress.CODE_H5).memoryPolicy(MemoryPolicy.NO_CACHE).into(register_iv_code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_code:
                MyUtils.Loge(TAG, "点击了按钮");
                if (TextUtils.isEmpty(register_phone.getText().toString().trim())) {
                    MyUtils.showToast(getActivity(), "请填写手机号");
                    return;
                }
                if (!MyUtils.isPhoneNumber(register_phone.getText().toString().trim())) {
                    MyUtils.showToast(getActivity(), "请填写正确的手机号");
                    return;
                }
                getSms();
                break;
            case R.id.register_btn_register:
                if (TextUtils.isEmpty(register_phone.getText().toString().trim())) {
                    MyUtils.showToast(getActivity(), "请填写手机号");
                    return;
                }
                if (!MyUtils.isPhoneNumber(register_phone.getText().toString().trim())) {
                    MyUtils.showToast(getActivity(), "请填写正确的手机号");
                    return;
                }

//                if(!isTrueOfImageCode()){
//                    return;
//                }
                if (TextUtils.isEmpty(register_regcode.getText().toString().trim())) {
                    regcode = "";
                } else {
                    regcode = register_regcode.getText().toString().trim();
                }
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                intent.putExtra("phone", register_phone.getText().toString().trim());
                intent.putExtra("regcode", regcode);
                startActivity(intent);
                break;
            case R.id.register_iv_refresh:
                MyUtils.Loge(TAG, "点击刷新");
                Picasso.with(getActivity()).load(HttpAddress.CODE_H5).memoryPolicy(MemoryPolicy.NO_CACHE).into(register_iv_code);
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
                        MyUtils.showToast(getActivity(), "验证码已发送至您手机");
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        sms_token = jsonObject1.getString("sms_token");
                        MyUtils.Loge(TAG, "SMS_TOKEN:" + sms_token);
                        mCodeCountDownTimer.start();
                    } else if (errorCode == 40001) {
                        ActivityUtil.toLogin(getActivity(), errorCode);
                    } else {
                        MyUtils.showToast(getActivity(), errorMsg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(), "请检查网络设置");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "1");
                map.put("phone", register_phone.getText().toString().trim());
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /**
     * 注册
     */
    public void register() {
        String url = HttpAddress.BASE_URL + HttpAddress.REGISTER;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    String errorMsg = jsonObject.getString("errorMsg");
                    if (errorCode == 1) {
//                        MainActivity.startActivity1(getActivity());
//                        startActivity(new Intent(getActivity(),MainActivity.class));
//                        getActivity().finish();
//                        Gson gson=new Gson();
//                        RegisterBean registerBean=gson.fromJson(response,RegisterBean.class);
//                        if(registerBean!=null){
//
//                        }
                        login();
                    } else if (errorCode == 40001) {
                        ActivityUtil.toLogin(getActivity(), errorCode);
                    } else {
                        MyUtils.showToast(getActivity(), errorMsg);
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
                map.put("phone", register_phone.getText().toString().trim());
                map.put("smsCode", register_smscode.getText().toString().trim());
                map.put("password", register_pwd1.getText().toString().trim());
                map.put("sms_token", sms_token);
                map.put("regCode", register_regcode.getText().toString().trim());
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /**
     * 登录
     */
    public void login() {
        String url = HttpAddress.BASE_URL + HttpAddress.LOGIN;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    String errorMsg = jsonObject.getString("errorMsg");
                    if (errorCode == 1) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response, LoginBean.class);
                        if (loginBean != null) {
                            SaveUtils.setString(KeyUtils.user_id, loginBean.getData().getUserInfo().getId());
                            SaveUtils.setString(KeyUtils.user_name, loginBean.getData().getUserInfo().getNickname());
                            SaveUtils.setString(KeyUtils.user_login_token, loginBean.getData().getUserInfo().getLogin_token());
                            SaveUtils.setString(KeyUtils.user_phone, loginBean.getData().getUserInfo().getPhone());
                            SaveUtils.setString(KeyUtils.user_regcode, loginBean.getData().getUserInfo().getRegcode());
                            SaveUtils.setString(KeyUtils.user_UID, loginBean.getData().getUserInfo().getUID());
                            SaveUtils.setString(KeyUtils.user_time, loginBean.getData().getUserInfo().getRegtime());
                        }
                        startActivity(new Intent(getActivity(), Main2Activity.class));
                        getActivity().finish();
                    } else if (errorCode == 40001) {
                        ActivityUtil.toLogin(getActivity(), errorCode);
                    } else {
                        MyUtils.showToast(getActivity(), errorMsg);
                    }
                } catch (Exception e) {
                    MyUtils.Loge(TAG, "e:" + e.getMessage());
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
                map.put("phone", register_phone.getText().toString().trim());
                map.put("password", register_pwd1.getText().toString().trim());
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /**
     * 验证图形验证码
     */
    public boolean isTrueOfImageCode(){
        final boolean[] isTrue = {false};
        String url=HttpAddress.BASE_URL+HttpAddress.IMAGE_CODE;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"验证码---response:"+response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    String errorMsg=jsonObject.getString("errorMsg");
                    if(errorCode==1){
                        isTrue[0]=true;
                    }else {
                        isTrue[0]=false;
                        MyUtils.showToast(getActivity(),errorMsg);
                    }
                }catch (Exception e){
                    isTrue[0]=false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(),"网络有问题");
                isTrue[0] =false;
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if(TextUtils.isEmpty(Contant.cookie)) {
                    //获取头部信息
                    Response<String> r = super.parseNetworkResponse(response);
                    //获取cookie头部信息
                    Map<String, String> head = response.headers;
                    String cookies = head.get("Set-Cookie");
                    //;分隔获取sessionId
                    Contant.cookie = cookies.substring(0, cookies.indexOf(";"));
                    MyUtils.Loge(TAG, "cookie:" + Contant.cookie);
                    return r;
                }else {
                    return super.parseNetworkResponse(response);
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //获取session值
                MyUtils.Loge(TAG, "getHeaders()----cookie:" + Contant.cookie);
                if (!TextUtils.isEmpty(Contant.cookie)) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Cookie", Contant.cookie);//设置session
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("imgCode",register_yzcode.getText().toString().trim());
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
        return isTrue[0];
    }
}
