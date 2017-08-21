package com.ttrm.ttconnection.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.adapter.LoginPagerAdapter;
import com.ttrm.ttconnection.fragment.LoginFragment;
import com.ttrm.ttconnection.fragment.RegisterFragment;
import com.ttrm.ttconnection.http.HttpAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login_sure;
    private String TAG="LoginActivity";
    private ViewPager login_vp;
    private TabLayout login_tab;
    private List<String> titleList=new ArrayList<>();
    private List<Fragment> fragmentList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initData();
    }

    private void initData() {
        titleList.add("登录");
        titleList.add("注册");
        fragmentList.add(new LoginFragment());
        fragmentList.add(new RegisterFragment());
        LoginPagerAdapter adapter = new LoginPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        login_vp.setAdapter(adapter);
        login_vp.setOffscreenPageLimit(1);
        login_tab.setupWithViewPager(login_vp);
        login_tab.setTabsFromPagerAdapter(adapter);
    }

    private void initViews() {
        login_tab=(TabLayout)findViewById(R.id.login_tab);
        login_vp=(ViewPager)findViewById(R.id.login_vp);
        btn_login_sure = (Button) findViewById(R.id.btn_login_sure);
        btn_login_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_sure:
//                login();
                getSms();
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", "13213580912");
        map.put("smsCode", "");
        map.put("password", "");
        map.put("sms_token", "");
        map.put("regCode", "");
        map.put("timeStamp", "");
        map.put("sign", "");
    }

    /**
     * 获取短信验证码
     */
    private void getSms() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "1");
        map.put("phone", "13213580912");
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://116.62.195.53/tt/index.php/Api" + HttpAddress.GET_SMS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        Volley.newRequestQueue(LoginActivity.this).add(stringRequest);

    }
}
