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
import com.ttrm.ttconnection.adapter.InviteInfoAdapter;
import com.ttrm.ttconnection.adapter.WithdrawInfoAdapter;
import com.ttrm.ttconnection.entity.InviteBean;
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

public class InviteActivity extends BaseActivity {

    private PullToRefreshListView invite_show;
    private TextView invite_kong;

    private static final int REFRESH_UP=1;
    private static final int REFRESH_DOWN=2;
    private int refreshType;
    private int currentPage=1;
    private List<InviteBean.DataBean.RecomLogBean> list=new ArrayList<>();
    private String TAG="InviteActivity";
    private InviteBean inviteBean;
    private InviteInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ActivityUtil.add(this);
        initViws();
        setListener();
        getInviteList();
    }

    private void setListener() {
        invite_show.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage=1;
                refreshType=REFRESH_DOWN;
                getInviteList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                refreshType=REFRESH_UP;
                getInviteList();
            }
        });
    }

    /**
     * 获取邀请列表
     */
    private void getInviteList() {
        String url= HttpAddress.BASE_URL+HttpAddress.GET_INVITE_LIST;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    Gson gson=new Gson();
                    inviteBean=gson.fromJson(response, InviteBean.class);
                    if(inviteBean!=null){
                        if(inviteBean.getErrorCode()==1){
                            setViews();
                        }
                        ActivityUtil.toLogin(InviteActivity.this, inviteBean.getErrorCode());
                    }
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(InviteActivity.this,"网络有问题");
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
        VolleyUtils.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setViews() {
        if(currentPage==1&&inviteBean.getData().getRecomLog().size()==0){
            invite_show.setVisibility(View.GONE);
            invite_kong.setVisibility(View.VISIBLE);
            return;
        }else {
            MyUtils.Loge(TAG,"展示listview");
            invite_kong.setVisibility(View.GONE);
            invite_show.setVisibility(View.VISIBLE);
        }
        if(inviteBean.getData().getRecomLog().size()>0) {
            if (refreshType == REFRESH_UP) {
                list.addAll(inviteBean.getData().getRecomLog());
            } else {
                list.clear();
                list.addAll(inviteBean.getData().getRecomLog());
            }
            MyUtils.Loge(TAG,"list:"+list.size());
            if (adapter == null) {
                adapter = new InviteInfoAdapter(InviteActivity.this, list);
                invite_show.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }else {
            MyUtils.showToast(InviteActivity.this,"没有更多了");
        }
        invite_show.onRefreshComplete();
    }

    private void initViws() {
        setToolBar("邀请明细");
        invite_show=(PullToRefreshListView)findViewById(R.id.invite_show);
        invite_show.setMode(PullToRefreshBase.Mode.BOTH);
        invite_kong=(TextView)findViewById(R.id.invite_kong);
    }
}
