package com.ttrm.ttconnection.activity;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.adapter.BDAddLvAdapter;
import com.ttrm.ttconnection.entity.BDAddBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.PayUtil;
import com.ttrm.ttconnection.util.SaveUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

/**
 * TODO 被动加粉
 */
public class BDAddActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView bdadd_lv;
    private String TAG="BDAddActivity";
    private BDAddBean bdAddBean;
    private BDAddLvAdapter adapter;
    private Button bdadd_open;
    public static BDAddActivity bdAddActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdadd);
        bdAddActivity=this;
        initViews();
        initData();
    }

    /**
     * 获取加粉规则
     */
    private void initData() {
        String url= HttpAddress.BASE_URL+HttpAddress.GET_BA_RULE;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG,"response:"+response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    int errorCode=jsonObject.getInt("errorCode");
                    if(errorCode==1){
                        Gson gson=new Gson();
                        bdAddBean=gson.fromJson(response,BDAddBean.class);
                        if(bdAddBean!=null){
                            setViews();
                        }
                    }else {

                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(BDAddActivity.this,"网络有问题");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("timeStamp",MyUtils.getTimestamp());
                map.put("sign",MyUtils.getSign());
                map.put("login_token", SaveUtils.getString(KeyUtils.user_login_token));
                return map;
            }
        };
        Volley.newRequestQueue(BDAddActivity.this).add(stringRequest);
    }

    /**
     * 开通加粉
     */
    private void openAdd(){
        MyUtils.Loge(TAG,"点击开通");
        MyUtils.Loge(TAG,"ruleId"+bdAddBean.getData().getRuleList().get(0).getId());
        PayUtil.toPay(BDAddActivity.this,"1",bdAddBean.getData().getRuleList().get(0).getId());
    }

    private void setViews() {
        adapter=new BDAddLvAdapter(BDAddActivity.this,bdAddBean.getData().getRuleList());
        bdadd_lv.setAdapter(adapter);
        bdadd_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(bdAddBean.getData().getRuleList().get(position).isSelect()){
                    bdAddBean.getData().getRuleList().get(position).setSelect(false);
                }else {
                    bdAddBean.getData().getRuleList().get(position).setSelect(true);
                    for(int i=0;i<bdAddBean.getData().getRuleList().size();i++){
                        if(i!=position){
                            bdAddBean.getData().getRuleList().get(i).setSelect(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initViews() {
        bdadd_lv=(ListView)findViewById(R.id.bdadd_lv);
        bdadd_open=(Button)findViewById(R.id.bdadd_open);
        bdadd_open.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bdadd_open:
                openAdd();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 0x1:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(BDAddActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(BDAddActivity.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},0X2);
                    }else {
                        openAdd();
                    }
                }else {
                    MyUtils.showToast(BDAddActivity.this,"权限获取失败");
                }
                break;
            case 0x2:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAdd();
                }else {
                    MyUtils.showToast(BDAddActivity.this,"权限获取失败");
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}