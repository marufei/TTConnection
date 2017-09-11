package com.ttrm.ttconnection.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ttrm.ttconnection.MainActivity;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.activity.ForgetPwdActivity;
import com.ttrm.ttconnection.entity.RegisterBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.CodeCountDownTimer;
import com.ttrm.ttconnection.util.MyUtils;

import org.json.JSONObject;

import java.util.Date;
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
    private String TAG="RegisterFragment";
    private String sms_token="";
    private EditText register_phone;
    private EditText register_pwd1;
    private EditText register_smscode;
    private EditText register_pwd2;
    private EditText register_regcode;
    private Button register_btn_register;
    //短信验证计时
    private CodeCountDownTimer mCodeCountDownTimer = null;

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
        mCodeCountDownTimer = new CodeCountDownTimer(60 * 1000L, 1000L, register_btn_code, R.drawable.btn_purple,
                R.drawable.btn_purple);
    }

    private void initViews() {
        register_btn_code = (Button) view.findViewById(R.id.register_btn_code);
        register_btn_code.setOnClickListener(this);
        register_phone=(EditText)view.findViewById(R.id.register_phone);
        register_pwd1=(EditText)view.findViewById(R.id.register_pwd1);
        register_smscode=(EditText)view.findViewById(R.id.register_smscode);
        register_pwd2=(EditText)view.findViewById(R.id.register_pwd2);
        register_regcode=(EditText)view.findViewById(R.id.register_regcode);
        register_btn_register=(Button)view.findViewById(R.id.register_btn_register);
        register_btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_code:
                MyUtils.Loge(TAG,"点击了按钮");
                if(TextUtils.isEmpty(register_phone.getText().toString().trim())){
                    MyUtils.showToast(getActivity(),"请填写手机号");
                    return;
                }
                if(!MyUtils.isPhoneNumber(register_phone.getText().toString().trim())){
                    MyUtils.showToast(getActivity(),"请填写正确的手机号");
                }
                getSms();
                break;
            case R.id.register_btn_register:
                if(TextUtils.isEmpty(register_phone.getText().toString().trim())){
                    MyUtils.showToast(getActivity(),"请填写手机号");
                    return;
                }
                if(TextUtils.isEmpty(register_pwd1.getText().toString().trim())){
                    MyUtils.showToast(getActivity(),"请填写密码");
                    return;
                }
                if(TextUtils.isEmpty(register_smscode.getText().toString().trim())){
                    MyUtils.showToast(getActivity(),"请填写验证码");
                    return;
                }
                if(TextUtils.isEmpty(register_pwd2.getText().toString().trim())){
                    MyUtils.showToast(getActivity(),"请填写密码");
                    return;
                }
                if(!MyUtils.isPhoneNumber(register_phone.getText().toString().trim())){
                    MyUtils.showToast(getActivity(),"请填写正确的手机号");
                }
                if(!register_pwd1.getText().toString().trim().equals(register_pwd2.getText().toString().trim())){
                    MyUtils.showToast(getActivity(),"密码不一致，请重新设置");
                }
                if(!TextUtils.isEmpty(sms_token)){
                    register();

                }else {
                    MyUtils.Loge(TAG,"sms_token:"+sms_token);

                }
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
                        MyUtils.showToast(getActivity(),"验证码已发送至您手机");
                        JSONObject jsonObject1=jsonObject.getJSONObject("data");
                        sms_token=jsonObject1.getString("sms_token");
                        MyUtils.Loge(TAG,"SMS_TOKEN:"+sms_token);
                        mCodeCountDownTimer.start();
                    }else {
                        MyUtils.showToast(getActivity(),errorMsg);
                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(),"请检查网络设置");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("type", "1");
                map.put("phone", "13213580912");
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
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
                        getActivity().finish();
                        MainActivity.startActivity(getActivity());
                        Gson gson=new Gson();
                        RegisterBean registerBean=gson.fromJson(response,RegisterBean.class);
                        if(registerBean!=null){

                        }
                    }else {
                        MyUtils.showToast(getActivity(),errorMsg);
                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(getActivity(),"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> map=new HashMap<>();
                map.put("phone",register_phone.getText().toString().trim());
                map.put("smsCode",register_smscode.getText().toString().trim());
                map.put("password",register_pwd1.getText().toString().trim());
                map.put("sms_token",sms_token);
                map.put("regCode",register_regcode.getText().toString().trim());
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return  map;
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}
