package com.ttrm.ttconnection.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.activity.ForgetPwdActivity;
import com.ttrm.ttconnection.activity.Main2Activity;
import com.ttrm.ttconnection.entity.LoginBean;
import com.ttrm.ttconnection.http.HttpAddress;
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

public class LoginFragment extends Fragment implements View.OnClickListener{
    private View view;
    private TextView login_forget;
    private EditText login_phone;
    private EditText login_pwd;
    private Button login_btn;
    private String TAG="LoginFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_login,null);
        initViews();
        initData();
        return view;
    }

    private void initData() {
        if(!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_phone))){
            login_phone.setText(SaveUtils.getString(KeyUtils.user_phone));
        }
    }

    private void initViews() {
        login_forget=(TextView)view.findViewById(R.id.login_forget);
        login_phone=(EditText)view.findViewById(R.id.login_phone);
        login_pwd=(EditText)view.findViewById(R.id.login_pwd);
        login_btn=(Button)view.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        login_forget.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_forget:
//                ForgetPwdActivity.startActivity(getActivity());
                Intent intent=new Intent(getActivity(),ForgetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.login_btn:
                if(TextUtils.isEmpty(login_phone.getText().toString().trim())){
                    MyUtils.showToast(getActivity(),"请填写手机号");
                    return;
                }
                if(TextUtils.isEmpty(login_pwd.getText().toString().trim())){
                    MyUtils.showToast(getActivity(),"请填写密码");
                    return;
                }
//                if(!MyUtils.isPhoneNumber(login_phone.getText().toString().trim())){
//                    MyUtils.showToast(getActivity(),"请填写正确的手机号");
//                    return;
//                }
                login();
                break;
        }
    }
    public void login(){
//        MyUtils.showToast(getContext(),"手机号："+
//                login_phone.getText().toString().trim()+"\n密码："+
//                login_pwd.getText().toString().trim()+"\n时间戳："+
//                MyUtils.getTimestamp()+"\n签名："+
//                MyUtils.getSign());
        String url= HttpAddress.BASE_URL+HttpAddress.LOGIN;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
//                MyUtils.showToast(getContext(),"返回JSON数据：--"+ response);
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
                        startActivity(new Intent(getActivity(), Main2Activity.class));
                        getActivity().finish();
                    }else {
                        MyUtils.showToast(getActivity(),errorMsg);
                    }
                }catch (Exception e){
                    MyUtils.Loge(TAG,"e:"+e.getMessage());
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
                Map<String ,String > map=new HashMap<>();
                map.put("phone",login_phone.getText().toString().trim());
                map.put("password",login_pwd.getText().toString().trim());
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
