package com.ttrm.ttconnection.activity;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
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
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, url);
//            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url == null) return false;

                try {
                    if(url.startsWith("weixin://") //微信
                            || url.startsWith("alipays://") //支付宝
                            || url.startsWith("mailto://") //邮件
                            || url.startsWith("tel://")//电话
                            || url.startsWith("dianping://")//大众点评
                            || url.startsWith("mqqwpa://")//qq
                        //其他自定义的scheme
                            ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }

//                //处理http和https开头的url
//                select_friend_wv.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
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
