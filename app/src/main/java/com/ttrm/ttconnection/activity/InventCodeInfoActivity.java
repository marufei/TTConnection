package com.ttrm.ttconnection.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.adapter.InventCodeInfoLvAdapter;
import com.ttrm.ttconnection.entity.InventCodeDocEntity;
import com.ttrm.ttconnection.entity.InventCodeInfoEntity;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.VolleyUtils;
import com.ttrm.ttconnection.view.ListViewForScrollview;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InventCodeInfoActivity extends BaseActivity {

    private String TAG = "InventCodeInfoActivity";
    private ListViewForScrollview invent_code_lv;
    private InventCodeInfoLvAdapter adapter;
    private String id;
    private WebView invent_code_info_wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invent_code_info);
        initViews();
        initData();
    }

    private void initViews() {
        setToolBar("激活码详情");
        invent_code_lv = findViewById(R.id.invent_code_info_lv);

        invent_code_info_wv = findViewById(R.id.invent_code_info_wv);
//        invent_code_info_wv.setBackgroundResource(R.drawable.boder_red);
        invent_code_info_wv.setBackgroundColor(0);

    }

    private void initData() {
        getCodeDoc();
        id = getIntent().getStringExtra("id");
        MyUtils.Loge(TAG, "ID::" + id);
        if (!TextUtils.isEmpty(id)) {
            getCodeList();
        }
    }

    /**
     * 说明文案
     */
    private void getCodeDoc() {
        String url = HttpAddress.BASE_URL + HttpAddress.CODE_DOC;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "充值列表---:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        Gson gson = new Gson();
                        InventCodeDocEntity inventCodeDocEntity = gson.fromJson(response, InventCodeDocEntity.class);
                        if (inventCodeDocEntity != null) {
                            String url = inventCodeDocEntity.getData().getDoc().getContent();
                            //  加载、并显示HTML代码
                            MyUtils.Loge(TAG, "富文本：" + url);
                            url = Html.fromHtml(url).toString();
                            invent_code_info_wv.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null);
                            invent_code_info_wv.getSettings().setJavaScriptEnabled(true);
                            invent_code_info_wv.setWebChromeClient(new WebChromeClient());

                        }
                    }
                    ActivityUtil.toLogin(InventCodeInfoActivity.this, errorCode);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(InventCodeInfoActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(InventCodeInfoActivity.this).addToRequestQueue(stringRequest);

    }

    /**
     * 获取所有的钻石列表
     */
    private void getCodeList() {
        String url = HttpAddress.BASE_URL + HttpAddress.CODE_LIST;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "钻石列表---:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        Gson gson = new Gson();
                        InventCodeInfoEntity inventCodeInfoEntity = gson.fromJson(response, InventCodeInfoEntity.class);
                        if (inventCodeInfoEntity != null) {
                            adapter = new InventCodeInfoLvAdapter(InventCodeInfoActivity.this);
                            adapter.setList(inventCodeInfoEntity.getData().getCodeList());
                            invent_code_lv.setAdapter(adapter);
                        }
                    }
                    ActivityUtil.toLogin(InventCodeInfoActivity.this, errorCode);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(InventCodeInfoActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("orderId", id);
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(InventCodeInfoActivity.this).addToRequestQueue(stringRequest);

    }
}
