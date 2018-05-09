package com.ttrm.ttconnection.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.MyUtils;

import java.util.List;

/**
 * TODO web页面
 */
public class WebActivity extends BaseActivity {
    private WebView webView;
    private String urlShow;
    private Toolbar toolbar;
    private String TAG="WebActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ActivityUtil.add(this);
        String title = getIntent().getStringExtra("title");
        webView = (WebView) findViewById(R.id.webview);
        if (!TextUtils.isEmpty(title)) {
            setToolBar1(title);
        }
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

    /**
     * 设置toolbar标题
     */
    public void setToolBar1(String title){
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        if(toolbar==null){
            return;
        }else {
            toolbar.setNavigationIcon(R.drawable.vector_drawable_base_arrow);
            toolbar.setTitle(title);
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyUtils.Loge(TAG,"点击");
                    if (webView.canGoBack()) {
                        MyUtils.Loge(TAG,"点击--if");
                        webView.goBack();
                    } else {
                        MyUtils.Loge(TAG,"点击--else");
                        finish();
                    }
                }
            });
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
        webView.setDownloadListener(new MyWebViewDownLoadListener()); //捕捉下载
        webView.setWebChromeClient(new WebChromeClient());
//        加载需要显示的网页
        webView.loadUrl(urlShow);
        if (MyUtils.isQQClientAvailable(this)) {
            //可以跳转QQ
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.indexOf("ios:##") >= 0) {
                        return true;
                    }
                    if (url.indexOf("task/taskCallback") == -1) {
                        webView.loadUrl(url);
                    }
                    return true;
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
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {

//                    LogUtil.e(TAG, "onPageStarted url=" + url);
//                    //悬赏任务完成 返回
//                    if (url.contains("task/taskCallback")) {
//                        Base_Dialog dialog = new Base_Dialog(Web_Activity.this);
//                        dialog.setCancelable(false);
//                        dialog.setMessage("感谢您进行该任务  点击确定返回萌豆任务中心");
//                        dialog.setOk("确定", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                setResult(123, new Intent().putExtra("tasktype", true));
//                                MDApp.finishActivity();
//                            }
//                        });
//                    }
//
//                    //金豆定存 确认返回
//                    String s = "https://www.mengdouwang.cn/dc/mengdouwang.com";
//                    if (url.equals(s)) {
//                        setResult(99, null);
//                        MDApp.finishActivity();
//                    }

                }

                @Override
                public void onPageFinished(WebView view, String url) {
//                    LogUtil.e(TAG, "onPageFinished url=" + url);
//                    if (loadurl.equals(url)) {
//                        mWebView.clearHistory();
//                    }
                    // addImageClickListner();

//                Log.e(TAG, "onPageFinished WebView title=" + title);
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    MyUtils.showToast(WebActivity.this, "加载错误!");
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                    handler.proceed();
                }
            });
        } else {
            MyUtils.showToast(this, "请先安装QQ客户端");
        }
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {

                setToolBar(title);

                super.onReceivedTitle(view, title);

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                super.onReceivedTouchIconUrl(view, url, precomposed);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        webView.setVisibility(View.GONE);//ZoomButtonsController有一个register和unregister的过程
        webView.destroy();
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

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }


}
