package com.ttrm.ttconnection.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.MyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login_sure;
    private String TAG="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
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
