package com.ttrm.ttconnection.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.MyUtils;

/**
 * TODO web页面
 */
public class WebActivity extends BaseActivity {
    private WebView webView;
    private String urlShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ActivityUtil.add(this);
        String title=getIntent().getStringExtra("title");
        if(!TextUtils.isEmpty(title)) {
            setToolBar(title);
        }
        webView = (WebView) findViewById(R.id.webview);
        Intent intent = getIntent();
        urlShow = intent.getStringExtra("URL");
        MyUtils.Loge("aaa", "urlShow::" + urlShow);
        if (urlShow.length() > 0 && urlShow.contains("https")) {
            urlShow = urlShow.replaceFirst("https", "http");
        }
        if (!TextUtils.isEmpty(urlShow)) {
            initData();
        }
    }

    private void initData() {

        MyUtils.Loge("aaa", "改后----urlShow::" + urlShow);
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);

        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);//WebView中包含一个ZoomButtonsController，当使用web.getSettings().setBuiltInZoomControls(true);启用后，用户一旦触摸屏幕，就会出现缩放控制图标。
//        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webSettings.setPluginsEnabled(true);//可以使用插件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//设置加载进来的页面自适应手机屏幕
        webSettings.setAllowFileAccess(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);//启用Dom内存（不加就显示不出来）
        webView.setWebChromeClient(new WebChromeClient());
//        加载需要显示的网页
        webView.loadUrl(urlShow);

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                MyUtils.Loge("aaa", "webview:url:" + url);
////                view.loadUrl(url);
//                if (url.startsWith("http:") || url.startsWith("https:")) {
//                    return false;
//                }
//                try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(intent);
//                } catch (Exception e) {
//                }
//                return true;
//            }
//        });
        //可以跳转QQ
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
            @Override public WebResourceResponse shouldInterceptRequest (WebView view, String url){
                if (url.startsWith("http") || url.startsWith("https")) {
                    return super.shouldInterceptRequest(view, url);
                } else {
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);
                    return null;
                }
            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.setVisibility(View.GONE);//ZoomButtonsController有一个register和unregister的过程
        webView.destroy();
    }


}
