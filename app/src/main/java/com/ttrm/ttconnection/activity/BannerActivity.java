package com.ttrm.ttconnection.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.util.MyUtils;

public class BannerActivity extends BaseActivity {

    private TextView tv_use_instructions_show;
    private Toolbar toolbar;
    private String urlShow;
    private WebView wv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_instructions2);
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);//（这个对宿主没什么影响，建议声明）
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initView();


        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            setToolBar(title);
        }

        urlShow = intent.getStringExtra("URL");
        MyUtils.Loge("aaa", "urlShow::" + urlShow);
        if (urlShow.length() > 0 && urlShow.contains("https")) {
            urlShow = urlShow.replaceFirst("https", "http");
        }
        if (!TextUtils.isEmpty(urlShow)) {
            initData();
        }


//        Intent intent = getIntent();
//        urlShow = intent.getStringExtra("url");
//        MyUtils.Loge("aaa", "urlShow::" + urlShow);
//        if(urlShow.length()>0&&urlShow.contains("https")){
//            urlShow=urlShow.replaceFirst("https","http");
//        }
//        if(!TextUtils.isEmpty(urlShow)) {
//            initData();
//        }
    }

    private void initData() {

        MyUtils.Loge("aaa", "改后----urlShow::" + urlShow);
        WebSettings webSettings = wv_show.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);

        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(false);//WebView中包含一个ZoomButtonsController，当使用web.getSettings().setBuiltInZoomControls(true);启用后，用户一旦触摸屏幕，就会出现缩放控制图标。
//        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webSettings.setPluginsEnabled(true);//可以使用插件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//设置加载进来的页面自适应手机屏幕
        webSettings.setAllowFileAccess(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);//启用Dom内存（不加就显示不出来）
        wv_show.setDownloadListener(new MyWebViewDownLoadListener()); //捕捉下载
        wv_show.setWebChromeClient(new WebChromeClient());
//        加载需要显示的网页
        wv_show.loadUrl(urlShow);

        if (MyUtils.isQQClientAvailable(this)) {
            //可以跳转QQ
            wv_show.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                    if (url.startsWith("http") || url.startsWith("https")) {
                        return super.shouldInterceptRequest(view, url);
                    } else {
                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(in);
                        return null;
                    }
                }
            });
        } else {
            MyUtils.showToast(this, "请先安装QQ客户端");
        }

    }

    private void initView() {
//        toolbar = (Toolbar) findViewById(R.id.index_toolbar);
//        toolbar.setTitle("新手必看");
//        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setNavigationIcon(R.mipmap.backimg);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        setTitle("详情");
        wv_show = (WebView) findViewById(R.id.wv_my_show);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wv_show.setVisibility(View.GONE);//ZoomButtonsController有一个register和unregister的过程
//        wv_show.destroy();
    }

    /**
     * webview的下载
     */
    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }
}
