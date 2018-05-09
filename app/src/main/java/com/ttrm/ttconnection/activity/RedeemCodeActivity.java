package com.ttrm.ttconnection.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.adapter.RedeemCodeLvAdapter;
import com.ttrm.ttconnection.entity.OpenVipBean;
import com.ttrm.ttconnection.entity.RedeemCodeBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.VolleyUtils;
import com.ttrm.ttconnection.view.ListViewForScrollview;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RedeemCodeActivity extends BaseActivity implements View.OnClickListener, RedeemCodeLvAdapter.IRedeemCodeListener {

    private Button redeem_code_change;
    private ListViewForScrollview redeem_code_lv;
    private String TAG = "RedeemCodeActivity";
    private RedeemCodeBean redeemCodeBean;
    private RedeemCodeLvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_code);
        ActivityUtil.add(this);
        initViews();
    }

    private void initViews() {
        redeem_code_change = (Button) findViewById(R.id.redeem_code_change);
        redeem_code_change.setOnClickListener(this);
        redeem_code_lv = (ListViewForScrollview) findViewById(R.id.redeem_code_lv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.redeem_code_change:
                startActivity(new Intent(RedeemCodeActivity.this,MoreRedeemCodeActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRedeemCodeList();
    }

    /**
     * 获取兑换码列表
     */
    private void getRedeemCodeList() {
        String url = HttpAddress.BASE_URL + HttpAddress.REDEEM_CODE_LIST;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "获取兑换码列表：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        Gson gson = new Gson();
                        redeemCodeBean = gson.fromJson(response, RedeemCodeBean.class);
                        if (redeemCodeBean != null && redeemCodeBean.getData() != null) {
                            setViews();
                        }
                    }
                    ActivityUtil.toLogin(RedeemCodeActivity.this, errorCode);
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(RedeemCodeActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("dia", "400");
                map.put("p", "1");
                map.put("pageSize", "10");
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setViews() {
        setToolBar("兑换码领取");
        adapter = new RedeemCodeLvAdapter(this, redeemCodeBean.getData().getCodeList());
        adapter.setiRedeemCodeListener(this);
        redeem_code_lv.setAdapter(adapter);
    }

    @Override
    public void onCopy(int i) {
        MyUtils.Loge(TAG, "点击了复制" + i);
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", redeemCodeBean.getData().getCodeList().get(i).getCode());
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        MyUtils.showToast(this,"已复制到粘贴板");
    }

    @Override
    public void onDelete(int i) {
        MyUtils.Loge(TAG, "点击了删除" + i);
        deleteRedeemCode(i);
    }

    /**
     * 删除兑换码
     */
    private void deleteRedeemCode(final int i) {
        String url = HttpAddress.BASE_URL + HttpAddress.REDEEM_CODE_DELETE;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "获取VIP规则：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        getRedeemCodeList();
                    }
                    ActivityUtil.toLogin(RedeemCodeActivity.this, errorCode);
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(RedeemCodeActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("id", redeemCodeBean.getData().getCodeList().get(i).getId());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);

    }
}
