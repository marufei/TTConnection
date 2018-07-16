package com.ttrm.ttconnection.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.ttrm.ttconnection.MyApplication;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.adapter.BDAddLvAdapter;
import com.ttrm.ttconnection.entity.BDAddBean;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.PayUtil;
import com.ttrm.ttconnection.util.SaveUtils;
import com.ttrm.ttconnection.util.VolleyUtils;
import com.ttrm.ttconnection.view.DialogPaySuccess;
import com.ttrm.ttconnection.view.ListViewForScrollview;
import com.ttrm.ttconnection.view.MyAdvertisementView;
import com.ttrm.ttconnection.wxapi.WXPayEntryActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 被动加粉
 */
public class BDAddActivity extends BaseActivity implements View.OnClickListener, WXPayEntryActivity.OnSuccessListenner {

    private ListViewForScrollview bdadd_lv;
    private String TAG = "BDAddActivity";
    private BDAddBean bdAddBean;
    private BDAddLvAdapter adapter;
    private Button bdadd_open;
    private ImageView bdadd_alipay;
    private ImageView bdadd_wx;
    private String payType;

    private LinearLayout bdadd_ll_alipay;
    private LinearLayout bdadd_ll_wx;
    private ImageView bdadd_png;
    /**
     * 微信支付是否成功
     */
    private boolean isPaySuccess=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdadd);
        WXPayEntryActivity wxPayEntryActivity = new WXPayEntryActivity();
        wxPayEntryActivity.setOnSuccessListenner(this);
        ActivityUtil.add(this);
        initViews();
        initData();
        payType = "1";
        bdadd_alipay.setImageResource(R.drawable.vector_drawable_pay_n);
        bdadd_wx.setImageResource(R.drawable.vector_drawable_pay_y);
    }

    /**
     * 获取加粉规则
     */
    private void initData() {
        if(!TextUtils.isEmpty(getIntent().getStringExtra("code"))&&getIntent().getStringExtra("code").equals("0")){
           isPaySuccess=true;
        }
        String url = HttpAddress.BASE_URL + HttpAddress.GET_BA_RULE;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyUtils.Loge(TAG, "response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int errorCode = jsonObject.getInt("errorCode");
                    if (errorCode == 1) {
                        Gson gson = new Gson();
                        bdAddBean = gson.fromJson(response, BDAddBean.class);
                        if (bdAddBean != null) {
                            setViews();

                        }
                    }
                    ActivityUtil.toLogin(BDAddActivity.this, bdAddBean.getErrorCode());
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyUtils.showToast(BDAddActivity.this, "网络有问题");
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

    /**
     * 开通加粉
     */
    private void openAdd() {
        MyUtils.Loge(TAG, "点击开通");
        MyUtils.Loge(TAG, "ruleId" + bdAddBean.getData().getRuleList().get(MyApplication.pos).getId());
        PayUtil.toPay(BDAddActivity.this, payType, bdAddBean.getData().getRuleList().get(MyApplication.pos).getId());
    }

    private void setViews() {
        adapter = new BDAddLvAdapter(BDAddActivity.this, bdAddBean.getData().getRuleList());
        bdadd_lv.setAdapter(adapter);
        bdAddBean.getData().getRuleList().get(0).setSelect(true);//默认选中第一个
        bdadd_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bdAddBean.getData().getRuleList().get(position).isSelect()) {
                    bdAddBean.getData().getRuleList().get(position).setSelect(false);
                    MyApplication.pos=-1;
                } else {
                    bdAddBean.getData().getRuleList().get(position).setSelect(true);
                    MyApplication.pos = position;
                    for (int i = 0; i < bdAddBean.getData().getRuleList().size(); i++) {
                        if (i != position) {
                            bdAddBean.getData().getRuleList().get(i).setSelect(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        if(isPaySuccess){
            OnSuccess();
        }

    }

    private void initViews() {
        setToolBar("被动加粉");
        bdadd_lv = (ListViewForScrollview) findViewById(R.id.bdadd_lv);
        bdadd_open = (Button) findViewById(R.id.bdadd_open);
        bdadd_open.setOnClickListener(this);
        bdadd_alipay = (ImageView) findViewById(R.id.bdadd_alipay);
        bdadd_alipay.setOnClickListener(this);
        bdadd_wx = (ImageView) findViewById(R.id.bdadd_wx);
        bdadd_wx.setOnClickListener(this);
        bdadd_ll_alipay = (LinearLayout) findViewById(R.id.bdadd_ll_alipay);
        bdadd_ll_alipay.setOnClickListener(this);
        bdadd_ll_wx = (LinearLayout) findViewById(R.id.bdadd_ll_wx);
        bdadd_ll_wx.setOnClickListener(this);
        bdadd_png=(ImageView)findViewById(R.id.bdadd_png);
        Picasso.with(this).load(HttpAddress.BDADD_PNG).memoryPolicy(MemoryPolicy.NO_CACHE).into(bdadd_png);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bdadd_open:
                if (MyApplication.pos < 0) {
                    MyUtils.showToast(BDAddActivity.this, "请选择加粉类型");
                    return;
                }
                if (TextUtils.isEmpty(payType)) {
                    MyUtils.showToast(BDAddActivity.this, "请选择支付方式");
                    return;
                }

                if (TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_name))) {
                    showAlertDialog("提示", "请完善一下您的昵称再继续吧~", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(BDAddActivity.this, EditNameActivity.class));
                            dialogInterface.dismiss();
                        }
                    }, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                } else {
                    openAdd();
                }
                break;
//            case R.id.bdadd_alipay:
//                payType="2";
//                setlectType(payType);
//                break;
//            case R.id.bdadd_wx:
//                payType="1";
//                setlectType(payType);
//                break;
            case R.id.bdadd_ll_alipay:
                payType = "2";
                setlectType(payType);
                break;
            case R.id.bdadd_ll_wx:
                payType = "1";
                setlectType(payType);
                break;

        }
    }


    /**
     * 选择支付类型
     *
     * @param payType
     */
    private void setlectType(String payType) {
        switch (payType) {
            case "1":
                bdadd_alipay.setImageResource(R.drawable.vector_drawable_pay_n);
                bdadd_wx.setImageResource(R.drawable.vector_drawable_pay_y);
                break;
            case "2":
                bdadd_wx.setImageResource(R.drawable.vector_drawable_pay_n);
                bdadd_alipay.setImageResource(R.drawable.vector_drawable_pay_y);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0x1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(BDAddActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(BDAddActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0X2);
                    } else {
                        openAdd();
                    }
                } else {
                    MyUtils.showToast(BDAddActivity.this, "权限获取失败");
                }
                break;
            case 0x2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAdd();
                } else {
                    MyUtils.showToast(BDAddActivity.this, "权限获取失败");
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 微信支付成功回调
     */
    public void OnSuccess() {
        MyUtils.Loge(TAG, "进入微信支付成功的回调");
        if(bdAddBean!=null&&bdAddBean.getData()!=null&&bdAddBean.getData().getRuleList()!=null&&bdAddBean.getData().getRuleList().get(MyApplication.pos)!=null) {
            DialogPaySuccess dialogPaySuccess = new DialogPaySuccess(this,bdAddBean.getData().getRuleList().get(MyApplication.pos).getImgurl(), bdAddBean.getData().getRuleList().get(MyApplication.pos).getCodegroup());
            dialogPaySuccess.showDialog();
            dialogPaySuccess.setOnEventClickListenner(new DialogPaySuccess.OnEventClickListenner() {
                @Override
                public void onEvent() {
                    startActivity(new Intent(BDAddActivity.this, InventCodeActivity.class));
                    finish();
                }

                @Override
                public void onCancel() {
                    finish();
                }
            });
        }else {
            MyAdvertisementView myAdvertisementView = new MyAdvertisementView(this, R.layout.dialog_bd_success);
            myAdvertisementView.showDialog();
            myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                @Override
                public void onEvent() {
                    MyUtils.Loge(TAG, "微信回调成功，点击了按钮");
                    finish();
                }
            });
        }
    }

    /**
     * 支付宝支付成功回调
     */
    public void AliPayCallback(){
        MyUtils.Loge(TAG,"进入支付宝支出成功的回调AliPayCallback（）");
        if(bdAddBean!=null&&bdAddBean.getData()!=null&&bdAddBean.getData().getRuleList()!=null&&bdAddBean.getData().getRuleList().get(MyApplication.pos)!=null) {
            DialogPaySuccess dialogPaySuccess = new DialogPaySuccess(this,bdAddBean.getData().getRuleList().get(MyApplication.pos).getImgurl(), bdAddBean.getData().getRuleList().get(MyApplication.pos).getCodegroup());
            dialogPaySuccess.showDialog();
            dialogPaySuccess.setOnEventClickListenner(new DialogPaySuccess.OnEventClickListenner() {
                @Override
                public void onEvent() {
                    startActivity(new Intent(BDAddActivity.this, InventCodeActivity.class));
                    finish();
                }

                @Override
                public void onCancel() {
                    finish();
                }
            });
        }else {
            MyAdvertisementView myAdvertisementView = new MyAdvertisementView(this, R.layout.dialog_bd_success);
            myAdvertisementView.showDialog();
            myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                @Override
                public void onEvent() {
                    MyUtils.Loge(TAG, "支付宝回调成功，点击了按钮");
                    finish();
                }
            });
        }
    }
}
