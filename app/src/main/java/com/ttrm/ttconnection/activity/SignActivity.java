package com.ttrm.ttconnection.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class SignActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView sign_tv_zs;
    private TextView sign_tv_regcode;
    private TextView sign_tv_sign;
    private TextView sign_tv_tb;
    private TextView sign_tv_oneadd;
    private TextView sign_tv_locationadd;
    private TextView sign_tv_wx;
    private TextView sign_tv_circle;
    private TextView sign_tv_qq;
    private TextView sign_tv_space;
    private String TAG="SignActivity";
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initViews();
    }

    private void initViews() {
        sign_tv_zs=(TextView)findViewById(R.id.sign_tv_zs);
        sign_tv_regcode=(TextView)findViewById(R.id.sign_tv_regcode);
        sign_tv_sign=(TextView)findViewById(R.id.sign_tv_sign);
        sign_tv_sign.setOnClickListener(this);
        sign_tv_tb=(TextView)findViewById(R.id.sign_tv_tb);
        sign_tv_tb.setOnClickListener(this);
        sign_tv_oneadd=(TextView)findViewById(R.id.sign_tv_oneadd);
        sign_tv_oneadd.setOnClickListener(this);
        sign_tv_locationadd=(TextView)findViewById(R.id.sign_tv_locationadd);
        sign_tv_locationadd.setOnClickListener(this);
        sign_tv_wx=(TextView)findViewById(R.id.sign_tv_wx);
        sign_tv_wx.setOnClickListener(this);
        sign_tv_circle=(TextView)findViewById(R.id.sign_tv_circle);
        sign_tv_circle.setOnClickListener(this);
        sign_tv_qq=(TextView)findViewById(R.id.sign_tv_qq);
        sign_tv_qq.setOnClickListener(this);
        sign_tv_space=(TextView)findViewById(R.id.sign_tv_space);
        sign_tv_space.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDiamonds();
    }

    /**
     * 查询钻石数量
     */
    public void selectDiamonds(){
        String url= HttpAddress.BASE_URL+HttpAddress.SELECT_DIAMONDS;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    if(errorCode==1){
                        JSONObject jsonObject1=jsonObject.getJSONObject("data");
                        String diamondCount=jsonObject1.getString("diamondCount");
                        sign_tv_zs.setText(diamondCount);
                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(SignActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(SignActivity.this).add(stringRequest);
    }

    /**
     * 获取钻石
     */
    public  void getDiamonds(){
        String url=HttpAddress.BASE_URL+HttpAddress.GET_DIAMONDS;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(SignActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("login_token",SaveUtils.getString(KeyUtils.user_login_token));
                map.put("type",type);
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(SignActivity.this).add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_tv_sign:
                break;
            case R.id.sign_tv_tb:
                break;
            case R.id.sign_tv_oneadd:
                break;
            case R.id.sign_tv_locationadd:
                break;
            case R.id.sign_tv_wx:
                break;
            case R.id.sign_tv_circle:
                break;
            case R.id.sign_tv_qq:
                break;
            case R.id.sign_tv_space:
                break;
        }
    }
}
