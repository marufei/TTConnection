package com.ttrm.ttconnection.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ttrm.ttconnection.MyApplication;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.activity.BDAddActivity;
import com.ttrm.ttconnection.entity.PayResult;
import com.ttrm.ttconnection.entity.WXPayAllData;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.view.MyAdvertisementView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/12/22.
 */

public class PayUtil {
    static String TAG = "TAG--PayUtil";

    public static void toPay(final Activity activity, final String payType, final String ruleId) {
//        double opmoney;
//        if (opType!=1&&payType==3){
//            opmoney=pay_money*0.01;
//            MyUtils.Loge(TAG,"opmoney="+opmoney);
//        }else {
//            opmoney=pay_money;
//            MyUtils.Loge(TAG,"opmoney="+opmoney);
//        }
        MyUtils.Loge(TAG,"topay()---1");

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    0x1);
            return;
        }
        MyUtils.Loge(TAG,"topay()---2");
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0x2);
            return;
        }
        MyUtils.Loge(TAG,"topay()---3");

//        MyUtils.Loge(TAG, "login_token=" + app.getUser().getLogin_token()
//                + ",opmoney=" + opmoney + ",payType=" + payType + ",opType=" + opType + ",opNum=" + opNum);

//        if(app!=null&&app.getUser()!=null&&!TextUtils.isEmpty(app.getUser().getLogin_token())) {
        if(!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_login_token))){
            String url= HttpAddress.BASE_URL+HttpAddress.ADD_PAY;
            StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    MyUtils.Loge(TAG,"response:"+response);
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        int errorCode=jsonObject.getInt("errorCode");
                        if(errorCode==1){
                            Gson gson=new Gson();
                            WXPayAllData wxPayAllData=gson.fromJson(response,WXPayAllData.class);
                            if(wxPayAllData!=null) {
                                if (payType.equals("1")) {
                                    MyUtils.Loge(TAG, "调用微信支付");
                                    WxPay wxPay = new WxPay(activity);
                                    wxPay.pay(wxPayAllData.getData().getWxdata());
                                }
                                if(payType.equals("2")){
                                    MyUtils.Loge(TAG,"调用支付宝支付");
                                    //TODO 支付宝支付
                                    AlipayUtil alipayUtil=new AlipayUtil(activity);
                                    alipayUtil.pay(wxPayAllData.getData().getAlidata());
                                    alipayUtil.setListener(new AlipayUtil.OnAlipayListener() {
                                        @Override
                                        public void onCancel(String resultStatus) {
                                            MyUtils.Loge(TAG,"支付宝回调取消");
                                            MyUtils.showToast(activity,"支付取消");
                                        }

                                        @Override
                                        public void onWait(String resultStatus) {
                                            MyUtils.Loge(TAG,"支付宝回调等待");
                                            MyUtils.showToast(activity,"支付失败");
                                        }

                                        @Override
                                        public void onSuccess(PayResult payResult) {
                                            MyUtils.Loge(TAG,"支付宝回调成功");
                                            MyAdvertisementView myAdvertisementView = new MyAdvertisementView(activity, R.layout.dialog_bd_success);
                                            myAdvertisementView.showDialog();
                                            myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                                                @Override
                                                public void onEvent() {
                                                    MyUtils.Loge(TAG,"支付宝回调成功，点击了按钮");
                                                    activity.finish();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        }
                    }catch (Exception e){
                        MyUtils.Loge(TAG,"e:"+e.getMessage());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    MyUtils.showToast(activity,"网络有问题");
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map=new HashMap<>();
                    map.put("payType",payType);
                    map.put("ruleId",ruleId);
                    map.put("login_token",SaveUtils.getString(KeyUtils.user_login_token));
                    return map;
                }
            };
            Volley.newRequestQueue(activity).add(stringRequest);
        }


    }
}
