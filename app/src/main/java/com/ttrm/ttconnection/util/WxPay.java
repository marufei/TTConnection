package com.ttrm.ttconnection.util;

import android.app.Activity;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ttrm.ttconnection.entity.WXPayAllData;
import com.ttrm.ttconnection.entity.WXPayData;

/**
 * Created by MaRufei on 2017/9/1.
 */

public class WxPay {
    private Activity activity;
    private IWXAPI iwxapi;
    private String TAG="WxPay";

    public WxPay(Activity activity){
        this.activity=activity;
        iwxapi= WXAPIFactory.createWXAPI(activity,Constants.APP_ID);
        iwxapi.registerApp(Constants.APP_ID);
    }
    public void pay(WXPayAllData.DataBean.WxdataBean mWxPayData){
        MyUtils.Loge(TAG,"--id:"+mWxPayData.getMch_id()+"--prepay_id:"+mWxPayData.getPrepay_id()
        +"--timeStamp:"+mWxPayData.getTimeStamp()+"--sign:"+mWxPayData.getSign().toLowerCase());
        PayReq payReq=new PayReq();
        payReq.appId=Constants.APP_ID;      //开放平台appId
        payReq.partnerId=mWxPayData.getMch_id();        //支付商户号
        payReq.prepayId=mWxPayData.getPrepay_id();
        payReq.packageValue="Sign=WXPay";       //固定值
        payReq.nonceStr=mWxPayData.getNonce_str();
        payReq.timeStamp=mWxPayData.getTimeStamp();
        payReq.sign=mWxPayData.getSign().toLowerCase();
        iwxapi.sendReq(payReq);
    }
}
