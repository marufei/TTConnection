package com.ttrm.ttconnection.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.adapter.BaojiLvAdapter;
import com.ttrm.ttconnection.entity.BaojiRuleBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.VolleyUtils;
import com.ttrm.ttconnection.view.ListViewForScrollview;
import com.ttrm.ttconnection.view.MyAdvertisementView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BaoJiActivity extends BaseActivity implements View.OnClickListener {

    private ListViewForScrollview baoji_lv;
    private BaojiRuleBean baojiBean;
    private BaojiLvAdapter adapter;
    private String TAG = "BaoJiActivity";
    private String ruleId;
    private Button baoji_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_ji);
        ActivityUtil.add(this);
        initViews();
        setToolBar("爆机");
        getBaojiRule();
    }

    private void initViews() {
        baoji_lv = (ListViewForScrollview) findViewById(R.id.baoji_lv);
        baoji_btn = (Button) findViewById(R.id.baoji_btn);
        baoji_btn.setOnClickListener(this);

    }

    /**
     * 获取爆机规则
     */
    private void getBaojiRule() {
        String url = HttpAddress.BASE_URL + HttpAddress.GET_BAOJI_RULE;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    Gson gson = new Gson();
                    baojiBean = gson.fromJson(response, BaojiRuleBean.class);
                    if (baojiBean != null) {
                        if (baojiBean.getErrorCode() == 1) {
                            setViews();
                        }
                        ActivityUtil.toLogin(BaoJiActivity.this, baojiBean.getErrorCode());
                    }
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(BaoJiActivity.this, "网络有问题");
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
        baojiBean.getData().getRuleList().get(0).setType(true);
        ruleId = baojiBean.getData().getRuleList().get(0).getId();
        adapter = new BaojiLvAdapter(BaoJiActivity.this, baojiBean.getData().getRuleList());
        baoji_lv.setAdapter(adapter);
        baoji_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyUtils.Loge(TAG, "点击了" + i);
                if (baojiBean.getData().getRuleList().get(i).isType()) {
                    baojiBean.getData().getRuleList().get(i).setType(false);
                } else {
                    ruleId = baojiBean.getData().getRuleList().get(i).getId();
                    baojiBean.getData().getRuleList().get(i).setType(true);
                    for (int j = 0; j < baojiBean.getData().getRuleList().size(); j++) {
                        if (j != i) {
                            baojiBean.getData().getRuleList().get(j).setType(false);
                        }
                    }

                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 爆机
     */
    private void baoJi() {
        String url = HttpAddress.BASE_URL + HttpAddress.BAOJI;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "爆机:response:" + response);
                baoji_btn.setClickable(true);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    String errorMsg = jsonObject.getString("errorMsg");
                    if (errorCode == 1) {
                        MyAdvertisementView myAdvertisementView = new MyAdvertisementView(BaoJiActivity.this, R.layout.dialog_bj_ing);
                        myAdvertisementView.showDialog();
                        myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                            @Override
                            public void onEvent() {
                                MyUtils.Loge(TAG, "朕知道了");
                                finish();
                            }
                        });
                    }
                    if (errorCode == 2) {  //钻石不足
                        MyAdvertisementView myAdvertisementView = new MyAdvertisementView(BaoJiActivity.this, R.layout.dialog_bj_no);
                        myAdvertisementView.showDialog();
                        myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                            @Override
                            public void onEvent() {
                                MyUtils.Loge(TAG, "去领钻石");
                                startActivity(new Intent(BaoJiActivity.this, SignActivity.class));
                                finish();
                            }
                        });
                    } else if (errorCode == 40001) {
                        ActivityUtil.toLogin(BaoJiActivity.this, 40001);
                    } else {
                        MyUtils.showToast(BaoJiActivity.this, errorMsg);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                baoji_btn.setClickable(true);
                MyUtils.showToast(BaoJiActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("ruleId", ruleId);
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(ruleId)) {
            MyUtils.showToast(BaoJiActivity.this, "请选择爆机人数");
            return;
        }
        if (TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_name))) {
            showAlertDialog("提示", "请完善一下您的昵称再继续吧~", "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(BaoJiActivity.this, EditNameActivity.class));
                    dialogInterface.dismiss();
                }
            }, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        } else {
            baoji_btn.setClickable(false);
            baoJi();
        }
    }


}
