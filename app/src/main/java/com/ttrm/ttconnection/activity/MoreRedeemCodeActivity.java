package com.ttrm.ttconnection.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.util.ActivityUtil;

public class MoreRedeemCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_redeem_code);
        ActivityUtil.add(this);
    }
}
