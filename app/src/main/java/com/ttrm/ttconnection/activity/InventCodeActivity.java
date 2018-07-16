package com.ttrm.ttconnection.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.ttrm.ttconnection.adapter.InventCodeLvAdapter;
import com.ttrm.ttconnection.adapter.InventLvAdapter;
import com.ttrm.ttconnection.entity.InventBean;
import com.ttrm.ttconnection.entity.InventCodeEntity;
import com.ttrm.ttconnection.entity.VipInfoBean;
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

public class InventCodeActivity extends BaseActivity {

    private PullToRefreshListView invent_code_lv;
    private String TAG = "InventCodeActivity";
    private int currentPage = 1;
    private InventCodeEntity inventCodeEntity;
    private int refresh_type=3;
    private static final int REFRESH_UP = 2;
    private static final int REFRESH_DOWN = 3;
    private List<InventCodeEntity.DataBean.OrderListBean> list = new ArrayList<>();
    private TextView invent_code_kong;
    private InventCodeLvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invent_code);
        initViews();
        initData();
        initListener();
    }

    private void initListener() {
        invent_code_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 1;
                refresh_type = REFRESH_DOWN;
                getInventCode();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                refresh_type = REFRESH_UP;
                getInventCode();
            }
        });
        invent_code_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(InventCodeActivity.this,InventCodeInfoActivity.class);
                intent.putExtra("id",list.get(i-1).getId());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        getInventCode();
    }

    private void initViews() {
        setToolBar("激活码获取记录");
        invent_code_lv = findViewById(R.id.invent_code_lv);
        invent_code_lv.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new InventCodeLvAdapter(this);
        invent_code_lv.setAdapter(adapter);
        invent_code_kong = findViewById(R.id.invent_code_kong);
    }

    /**
     * 获取充值列表
     */
    private void getInventCode() {
        String url = HttpAddress.BASE_URL + HttpAddress.INVENT_CODE;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "充值列表---:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        Gson gson = new Gson();
                        inventCodeEntity = gson.fromJson(response, InventCodeEntity.class);
                        if (inventCodeEntity != null) {
                            setViews();
                        }
                    }
                    ActivityUtil.toLogin(InventCodeActivity.this, errorCode);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(InventCodeActivity.this, "网络有问题");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("p", String.valueOf(currentPage));
                map.put("pageSize", "10");
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        VolleyUtils.getInstance(InventCodeActivity.this).addToRequestQueue(stringRequest);
    }

    private void setViews() {
        if (refresh_type == REFRESH_DOWN) {
            list.clear();
        }
        list.addAll(inventCodeEntity.getData().getOrderList());
        if (currentPage == 1 && list.size() == 0) {
            //TODO 空界面
            invent_code_kong.setVisibility(View.VISIBLE);
            invent_code_lv.setVisibility(View.GONE);
        } else {
            if (inventCodeEntity.getData().getOrderList().size() == 0) {
                MyUtils.showToast(InventCodeActivity.this, "没有数据了...");
            } else {
                invent_code_kong.setVisibility(View.GONE);
                invent_code_lv.setVisibility(View.VISIBLE);

                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }
            invent_code_lv.onRefreshComplete();
        }
    }
}
