package com.ttrm.ttconnection.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.ttrm.ttconnection.R;

public class ProtocolActivity extends BaseActivity {

    private WebView protocol_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
        initView();
    }

    private void initView() {
        setToolBar("注册协议");
        protocol_show=(WebView)findViewById(R.id.protocol_show);
        protocol_show.loadUrl("file:///android_asset/TTRMDEALDoc.html");
    }

}
