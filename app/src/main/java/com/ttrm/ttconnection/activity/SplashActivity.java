package com.ttrm.ttconnection.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.ttrm.ttconnection.MainActivity;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.SaveUtils;

/**
 * TODO 首页
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();
    }

    private void initData() {
        if(!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_login_token))){
            new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(3000);
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }else {
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        }
    }
}
