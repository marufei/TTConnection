package com.ttrm.ttconnection.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.util.ActivityUtil;

public class SelectFriendActivity extends BaseActivity {

    private WebView select_friend_wv;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);
        ActivityUtil.add(this);
        url = getIntent().getStringExtra("URL");
        initViews();
    }

    private void initViews() {
        setToolBar("检测单删好友");
        select_friend_wv=(WebView)findViewById(R.id.select_friend_wv);

        WebSettings webSettings = select_friend_wv.getSettings();
        //支持缩放，默认为true。
        webSettings.setSupportZoom(false);
        //调整图片至适合select_friend_wv的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //设置默认编码
        webSettings.setDefaultTextEncodingName("utf-8");
        //设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);

        //控制select_friend_wv跳转时不调用浏览器
        select_friend_wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        //载入测试hnml
        if (!TextUtils.isEmpty(url)) {
            select_friend_wv.loadUrl(url);
        }
    }

    @Override
    protected void onDestroy() {
        if (select_friend_wv != null) {
            select_friend_wv.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            select_friend_wv.clearHistory();
            ((ViewGroup) select_friend_wv.getParent()).removeView(select_friend_wv);
            select_friend_wv.destroy();
            select_friend_wv = null;
        }
        super.onDestroy();
    }
}
