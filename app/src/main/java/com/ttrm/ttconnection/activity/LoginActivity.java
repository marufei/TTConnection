package com.ttrm.ttconnection.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.http.HttpRequests;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login_sure;

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
                login();
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        Map<String,String> map=new HashMap<>();
        map.put("phone","13213580912");
        map.put("smsCode","");
        map.put("password","");
        map.put("sms_token","");
        map.put("regCode","");
        map.put("timeStamp","");
        map.put("sign","");
        HttpRequests.getInstance().post(HttpAddress.REGISTER,map)
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        //jsonObject就是我们获取到的json数据
                        //在这里可以做一些成功获取数据的操作
                    }
                });
    }
}
