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
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.adapter.WithdrawInfoAdapter;
import com.ttrm.ttconnection.entity.WithdrawInfoBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.VolleyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 提现明细
 */
public class WithdrawCashInfoActivity extends BaseActivity {

    private PullToRefreshListView withdraw_info_show;
    private String TAG="WithdrawCashInfoActivity";
    private static final int REFRESH_UP=1;
    private static final int REFRESH_DOWN=2;
    private int refreshType;
    private int currentPage=1;
    private WithdrawInfoBean listBean;
    private WithdrawInfoAdapter adapter;
    private TextView withdraw_info_kong;
    private List<WithdrawInfoBean.DataBean.CashLogBean> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_cash_info);
        getCashList();
        initViews();
        setListeners();

    }

    private void setListeners() {
        withdraw_info_show.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage=1;
                refreshType=REFRESH_DOWN;
                getCashList();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                refreshType=REFRESH_UP;
                getCashList();
            }
        });
    }

    private void initViews() {
        setToolBar("提现明细");
        withdraw_info_show=(PullToRefreshListView)findViewById(R.id.withdraw_info_show);
        withdraw_info_show.setMode(PullToRefreshBase.Mode.BOTH);
        withdraw_info_kong=(TextView)findViewById(R.id.withdraw_info_kong);
    }
    /**
     * 获取提现列表
     */
    private void getCashList(){
        String url= HttpAddress.BASE_URL+HttpAddress.GET_CASH_LIST;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    Gson gson=new Gson();
                    listBean=gson.fromJson(response, WithdrawInfoBean.class);
                    if(listBean!=null){
                        if(listBean.getErrorCode()==1){
                            setViews();
                        }
                        ActivityUtil.toLogin(WithdrawCashInfoActivity.this, listBean.getErrorCode());

                    }
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(WithdrawCashInfoActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("timeStamp", MyUtils.getTimestamp());
                map.put("sign", MyUtils.getSign());
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                map.put("p", String.valueOf(currentPage));
                return map;
            }
        };
        VolleyUtils.setTimeOut(stringRequest);
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void setViews() {
        if(currentPage==1&&listBean.getData().getCashLog().size()==0){
            withdraw_info_show.setVisibility(View.GONE);
            withdraw_info_kong.setVisibility(View.VISIBLE);
            return;
        }else {
            MyUtils.Loge(TAG,"展示listview");
            withdraw_info_kong.setVisibility(View.GONE);
            withdraw_info_show.setVisibility(View.VISIBLE);
        }
        if(listBean.getData().getCashLog().size()>0) {
            if (refreshType == REFRESH_UP) {
                list.addAll(listBean.getData().getCashLog());
            } else {
                list.clear();
                list.addAll(listBean.getData().getCashLog());
            }
            MyUtils.Loge(TAG,"list:"+list.size());
            if (adapter == null) {
                adapter = new WithdrawInfoAdapter(WithdrawCashInfoActivity.this, list);
                withdraw_info_show.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }else {
            MyUtils.showToast(WithdrawCashInfoActivity.this,"没有更多了");
        }
        withdraw_info_show.onRefreshComplete();
    }


}
