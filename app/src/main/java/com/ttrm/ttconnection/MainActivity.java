package com.ttrm.ttconnection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ttrm.ttconnection.activity.BDAddActivity;
import com.ttrm.ttconnection.activity.LoginActivity;
import com.ttrm.ttconnection.activity.SignActivity;
import com.ttrm.ttconnection.activity.UserInfoActivity;
import com.ttrm.ttconnection.entity.BannerBean;
import com.ttrm.ttconnection.entity.CanonBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.LXRUtil;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.view.ImageCycleView;
import com.ttrm.ttconnection.view.MyAdvertisementView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_main;
    private ImageView main_info;
    private String TAG = "MainActivity";
    private LinearLayout main_ll_sign;
    private ImageCycleView main_banner;
    private LinearLayout main_ll_bdadd;
    private LinearLayout main_ll_yjjf;
    private LinearLayout main_ll_dqjf;
    private LinearLayout main_ll_clear;
    private BannerBean bannerBean;
    private LinearLayout main_ll_bj;

    private String type = "1";//识别一键加粉还是地区加粉



    private static List<CanonBean.DataBean.PhoneListBean> dataList = new ArrayList<CanonBean.DataBean.PhoneListBean>();//一键加粉数据集合


    private static int currentCount = 0;//已添加添加通讯录总条数

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case KeyUtils.SAVE_CODE:
                    currentCount = (int)msg.obj;
                    if(currentCount == dataList.size()){
                        Toast.makeText(MyApplication.mContext,"添加成功",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case KeyUtils.DELETE_CODE:
                    Toast.makeText(MyApplication.mContext,"删除成功",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        getBanner();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setClass(context, MainActivity.class);
    }

    private void initView() {
        btn_main = (Button) findViewById(R.id.btn_main);
        btn_main.setOnClickListener(this);
        main_info = (ImageView) findViewById(R.id.main_info);
        main_info.setOnClickListener(this);
        main_ll_sign = (LinearLayout) findViewById(R.id.main_ll_sign);
        main_ll_sign.setOnClickListener(this);
        main_ll_bdadd=(LinearLayout)findViewById(R.id.main_ll_bdadd);
        main_ll_bdadd.setOnClickListener(this);
        main_ll_yjjf = (LinearLayout) findViewById(R.id.yjjf_linear);
        main_ll_yjjf.setOnClickListener(this);
        main_ll_dqjf = (LinearLayout) findViewById(R.id.dqjf_linear);
        main_ll_dqjf.setOnClickListener(this);
        main_ll_clear = (LinearLayout) findViewById(R.id.clear_linear);
        main_ll_clear.setOnClickListener(this);

        main_ll_bj=(LinearLayout)findViewById(R.id.main_ll_bj);
        main_ll_bj.setOnClickListener(this);
        main_banner = (ImageCycleView) findViewById(R.id.main_banner);
        new ImageCycleView.ImageCycleViewListener() {

            @Override
            public void displayImage(String imageURL, ImageView imageView) {

            }

            @Override
            public void onImageClick(int position, View imageView) {

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_info:
                MyUtils.Loge(TAG, "点击了");
//                UserInfoActivity.startActivity(this);
                startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
                break;
            case R.id.btn_main:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.main_ll_sign:
                startActivity(new Intent(MainActivity.this, SignActivity.class));
                break;
            case R.id.main_ll_bdadd:
                startActivity(new Intent(MainActivity.this, BDAddActivity.class));
                break;
            case R.id.main_ll_bj:
                MyAdvertisementView myAdvertisementView = new MyAdvertisementView(this);
                myAdvertisementView.showDialog();
                myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                    @Override
                    public void onEvent() {
                        MyUtils.showToast(MainActivity.this,"点击了按钮");
                    }
                });
                break;
            case R.id.yjjf_linear://一键加粉
                type = "1";
                getCanon();//获取一键加粉数据
                break;
            case R.id.dqjf_linear://地区加粉
                type = "2";
                getCanon();//获取地区加粉数据
                break;
            case R.id.clear_linear://清除通讯录
                LXRUtil.deleteContacts(MainActivity.this);
                break;
        }
    }

    /**
     * 获取banner
     */
    public void getBanner() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_BANNER;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    if(errorCode==1){
                        Gson gson=new Gson();
                        bannerBean = gson.fromJson(response, BannerBean.class);
                    }
                }
                catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MainActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
    }

    private void getCanon(){
        String url= HttpAddress.BASE_URL+HttpAddress.GET_CANON;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"canon:"+response);

                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    if(errorCode==1){
                        Gson gson = new Gson();
                        CanonBean bean = gson.fromJson(response,CanonBean.class);
                        if(bean.getErrorCode() == 1){
                            dataList.clear();
                            dataList.addAll(bean.getData().getPhoneList());
                            for(int i = 0; i < dataList.size(); i++){
                                LXRUtil.addContacts(MainActivity.this,dataList.get(i).getNickname(),dataList.get(i).getPhone(),i);
                            }
                        }
                    }else{
                        Toast.makeText(MainActivity.this,jsonObject.getString("errorMsg"),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(MainActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                map.put("type",type);
                return map;
            }
        };
        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
    }
}
