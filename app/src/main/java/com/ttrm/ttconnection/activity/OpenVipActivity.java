package com.ttrm.ttconnection.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.adapter.OpenVipLvAdapter;
import com.ttrm.ttconnection.entity.OpenVipBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.PayUtil;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.VolleyUtils;
import com.ttrm.ttconnection.view.ListViewForScrollview;
import com.ttrm.ttconnection.wxapi.WXPayEntryActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OpenVipActivity extends BaseActivity implements View.OnClickListener, WXPayEntryActivity.OnSuccessListenner {

    private ListViewForScrollview vip_lv;
    private LinearLayout vip_ll_wx;
    private ImageView vip_wx;
    private LinearLayout vip_ll_alipay;
    private ImageView vip_alipay;
    private Button vip_open;
    private String TAG = "OpenVipActivity";
    private OpenVipLvAdapter adapter;
    private OpenVipBean openVipBean;
    private int pos; //选中第pos个规则
    private String payType; //支付方式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vip);
        ActivityUtil.add(this);
        WXPayEntryActivity wxPayEntryActivity = new WXPayEntryActivity();
        wxPayEntryActivity.setOnSuccessListenner(this);
        initViews();
        initDatas();
        initEvent();
        payType = "1";
        vip_alipay.setImageResource(R.drawable.vector_drawable_pay_n);
        vip_wx.setImageResource(R.drawable.vector_drawable_pay_y);
    }

    private void initEvent() {

    }

    private void initDatas() {
        getVipRule();
    }

    /**
     * 获取VIP规则
     */
    private void getVipRule() {
        String url = HttpAddress.BASE_URL + HttpAddress.RULE_VIP;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "获取VIP规则：" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        Gson gson = new Gson();
                        openVipBean = gson.fromJson(response, OpenVipBean.class);
                        if (openVipBean != null) {
                            setViews();
                        }
                    }
                    ActivityUtil.toLogin(OpenVipActivity.this, errorCode);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(OpenVipActivity.this, "网络有问题");
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
        VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void setViews() {
        openVipBean.getData().getRuleList().get(0).setSelect(true);
        adapter = new OpenVipLvAdapter(OpenVipActivity.this, openVipBean.getData().getRuleList());
        vip_lv.setAdapter(adapter);
        vip_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (openVipBean.getData().getRuleList().get(position).isSelect()) {
                    openVipBean.getData().getRuleList().get(position).setSelect(false);
                    pos = -1;
                } else {
                    openVipBean.getData().getRuleList().get(position).setSelect(true);
                    pos = position;
                    for (int i = 0; i < openVipBean.getData().getRuleList().size(); i++) {
                        if (i != position) {
                            openVipBean.getData().getRuleList().get(i).setSelect(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initViews() {
        setToolBar("开通会员");
        vip_lv = (ListViewForScrollview) findViewById(R.id.vip_lv);
        vip_ll_wx = (LinearLayout) findViewById(R.id.vip_ll_wx);
        vip_ll_wx.setOnClickListener(this);
        vip_wx = (ImageView) findViewById(R.id.vip_wx);
        vip_ll_alipay = (LinearLayout) findViewById(R.id.vip_ll_alipay);
        vip_ll_alipay.setOnClickListener(this);
        vip_alipay = (ImageView) findViewById(R.id.vip_alipay);
        vip_open = (Button) findViewById(R.id.vip_open);
        vip_open.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vip_ll_wx:
                payType = "1";
                setlectType(payType);
                break;
            case R.id.vip_ll_alipay:
                payType = "2";
                setlectType(payType);
                break;
            case R.id.vip_open:
                if (pos < 0) {
                    MyUtils.showToast(OpenVipActivity.this, "请选择加粉类型");
                    return;
                }
                if (TextUtils.isEmpty(payType)) {
                    MyUtils.showToast(OpenVipActivity.this, "请选择支付方式");
                    return;
                }
                openVip();
                break;

        }
    }

    /**
     * 开通VIP
     */
    private void openVip() {
        PayUtil.open2Pay(this, payType, openVipBean.getData().getRuleList().get(pos).getId(), "1", "1");
    }

    /**
     * 选择支付类型
     *
     * @param payType
     */
    private void setlectType(String payType) {
        switch (payType) {
            case "1":
                vip_alipay.setImageResource(R.drawable.vector_drawable_pay_n);
                vip_wx.setImageResource(R.drawable.vector_drawable_pay_y);
                break;
            case "2":
                vip_wx.setImageResource(R.drawable.vector_drawable_pay_n);
                vip_alipay.setImageResource(R.drawable.vector_drawable_pay_y);
                break;
        }
    }

    /**
     * 微信支付成功回调
     */
    @Override
    public void OnSuccess() {
        finish();
    }
}
