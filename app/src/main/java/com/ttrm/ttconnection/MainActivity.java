package com.ttrm.ttconnection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ttrm.ttconnection.activity.LoginActivity;
import com.ttrm.ttconnection.activity.SignActivity;
import com.ttrm.ttconnection.activity.UserInfoActivity;
import com.ttrm.ttconnection.util.MyUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_main;
    private ImageView main_info;
    private String TAG="MainActivity";
    private LinearLayout main_ll_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    public static void startActivity(Context context){
        Intent intent=new Intent(context,MainActivity.class);
        intent.setClass(context,MainActivity.class);
    }

    private void initView() {
        btn_main=(Button)findViewById(R.id.btn_main);
        btn_main.setOnClickListener(this);
        main_info=(ImageView)findViewById(R.id.main_info);
        main_info.setOnClickListener(this);
        main_ll_sign=(LinearLayout)findViewById(R.id.main_ll_sign);
        main_ll_sign.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_info:
                MyUtils.Loge(TAG,"点击了");
//                UserInfoActivity.startActivity(this);
                startActivity(new Intent(MainActivity.this,UserInfoActivity.class));
                break;
            case R.id.btn_main:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.main_ll_sign:
                startActivity(new Intent(MainActivity.this, SignActivity.class));
                break;
        }
    }
}
