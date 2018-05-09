package com.ttrm.ttconnection.activity;

import android.support.v7.app.AppCompatActivity;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.adapter.InventLvAdapter;
import com.ttrm.ttconnection.entity.InventBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.VolleyUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventActivity extends BaseActivity {

    private PullToRefreshListView invent_lv;
    private InventLvAdapter adapter;
    private String TAG = "InventActivity";
    private int currentPage = 1;
    private int refresh_type=3;
    private static final int REFRESH_UP = 2;
    private static final int REFRESH_DOWN = 3;
    private InventBean inventBean;
    private List<InventBean.DataBean.RecomLogBean> list = new ArrayList<>();
    private TextView invent_kong;
    private TextView invent_time;
    private TextView invent_reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invent);
        ActivityUtil.add(this);
        initView();
        initEvent();
        initDatas();
    }

    private void initEvent() {
        invent_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                refresh_type = REFRESH_DOWN;
                initDatas();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                refresh_type = REFRESH_UP;
                initDatas();
            }
        });
    }

    private void initView() {
        setToolBar("邀请奖励明细");
        invent_lv = (PullToRefreshListView) findViewById(R.id.invent_lv);
        invent_lv.setMode(PullToRefreshBase.Mode.BOTH);
        invent_kong = (TextView) findViewById(R.id.invent_kong);
        invent_time=(TextView)findViewById(R.id.invent_time);
        invent_reward=(TextView)findViewById(R.id.invent_reward);


    }

    private void initDatas() {
        MyUtils.Loge(TAG, "进入initDatas（）");
        String url = HttpAddress.BASE_URL + HttpAddress.INVENT_RECORD;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "推荐记录---response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errcode = jsonObject.getInt("errorCode");
                    if (errcode == 1) {
                        Gson gson = new Gson();
                        inventBean = gson.fromJson(response, InventBean.class);
                        if (inventBean != null) {
                            setViews();
                        }
                    } else {
                        ActivityUtil.toLogin(InventActivity.this, errcode);
                    }
                } catch (Exception e) {
                    MyUtils.Loge(TAG, "推荐记录---e.getmessage():" + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(InventActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                map.put("p", String.valueOf(currentPage));
                map.put("pageSize","10");
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(InventActivity.this).addToRequestQueue(stringRequest);
    }

    private void setViews() {
        invent_time.setText(inventBean.getData().getMonth());
        invent_reward.setText(inventBean.getData().getReward());
        if (refresh_type == REFRESH_DOWN) {
            list.clear();
        }
        list.addAll(inventBean.getData().getRecomLog());
        if (currentPage == 1 && list.size() == 0) {
            //TODO 空界面
            invent_kong.setVisibility(View.VISIBLE);
            invent_lv.setVisibility(View.GONE);
        } else {
            if (inventBean.getData().getRecomLog().size() == 0) {
                MyUtils.showToast(InventActivity.this, "没有数据了...");
            } else {
                invent_kong.setVisibility(View.GONE);
                invent_lv.setVisibility(View.VISIBLE);
                if (adapter == null) {
                    adapter = new InventLvAdapter(list,this);
                    invent_lv.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
            invent_lv.onRefreshComplete();
        }
    }
}
