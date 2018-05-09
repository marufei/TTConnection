package com.ttrm.ttconnection.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.adapter.DoubleRewardAdapter;
import com.ttrm.ttconnection.entity.DoubleRewardBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.VolleyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DoubleRewardActivity extends BaseActivity {
    private String TAG = "DoubleRewardActivity";
    private ListView double_reward_lv;
    private DoubleRewardAdapter adapter;
    private DoubleRewardBean doubleRewardBean;
    private TextView double_reward_time;
    private TextView double_reward_reward;
    private TextView double_reward_kong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_reward);
        ActivityUtil.add(this);
        initView();
        initDatas();
    }

    private void initView() {
        setToolBar("翻倍奖励明细");
        double_reward_lv = (ListView) findViewById(R.id.double_reward_lv);
        double_reward_time=(TextView)findViewById(R.id.double_reward_time);
        double_reward_reward=(TextView)findViewById(R.id.double_reward_reward);
        double_reward_kong=(TextView)findViewById(R.id.double_reward_kong);

    }

    private void initDatas() {
        MyUtils.Loge(TAG, "进入initDatas（）");
        String url = HttpAddress.BASE_URL + HttpAddress.DOUBLE_REWARD_RECORD;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "翻倍奖励记录---response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errcode = jsonObject.getInt("errorCode");
                    if (errcode == 1) {
                        Gson gson = new Gson();
                        doubleRewardBean = gson.fromJson(response, DoubleRewardBean.class);
                        if (doubleRewardBean != null) {
                            double_reward_time.setText(doubleRewardBean.getData().getMonth());
                            double_reward_reward.setText(doubleRewardBean.getData().getReward());
                            if (doubleRewardBean.getData().getAddedLog().size()>0) {
                                double_reward_kong.setVisibility(View.GONE);
                                double_reward_lv.setVisibility(View.VISIBLE);
                                if (adapter == null) {
                                    adapter = new DoubleRewardAdapter(doubleRewardBean.getData().getAddedLog(), DoubleRewardActivity.this);
                                    double_reward_lv.setAdapter(adapter);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }

                            }else {
                                double_reward_kong.setVisibility(View.VISIBLE);
                                double_reward_lv.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        ActivityUtil.toLogin(DoubleRewardActivity.this, errcode);
                    }
                } catch (Exception e) {
                    MyUtils.Loge(TAG, "推荐记录---e.getmessage():" + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(DoubleRewardActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(DoubleRewardActivity.this).addToRequestQueue(stringRequest);
    }
}
