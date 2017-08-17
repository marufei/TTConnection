package com.ttrm.ttconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.View;
import android.widget.Button;

import com.ttrm.ttconnection.activity.LoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_main=(Button)findViewById(R.id.btn_main);
        btn_main.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
