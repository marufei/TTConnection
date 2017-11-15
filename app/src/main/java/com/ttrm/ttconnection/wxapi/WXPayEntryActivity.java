package com.ttrm.ttconnection.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ttrm.ttconnection.MyApplication;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.activity.BDAddActivity;
import com.ttrm.ttconnection.util.Constants;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.view.MyAdvertisementView;

/**
 * Created by Administrator on 2016/12/21.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    String TAG="TAG--WXPayEntryActivity";
    private IWXAPI api;
    MyApplication app;
    private OnSuccessListenner onSuccessListenner;

    public void setOnSuccessListenner(OnSuccessListenner onSuccessListenner) {
        this.onSuccessListenner = onSuccessListenner;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app= (MyApplication) getApplicationContext();
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if(baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
            MyUtils.Loge(TAG,"onPayFinish,errCode="+baseResp.errCode);
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示");
            MyUtils.Loge(TAG,"errCode:"+baseResp.errCode);
            switch (baseResp.errCode){
                case 0://成功
                    MyAdvertisementView myAdvertisementView = new MyAdvertisementView(this,R.layout.dialog_bd_success);
                    myAdvertisementView.showDialog();
                    myAdvertisementView.setOnEventClickListenner(new MyAdvertisementView.OnEventClickListenner() {
                        @Override
                        public void onEvent() {
//                            onSuccessListenner.OnSuccess();
                            finish();
                        }
                    });
                break;
                case -1://失败
                    MyUtils.Loge(TAG,"支付失败");
                    Toast.makeText(WXPayEntryActivity.this, "支付失败", Toast.LENGTH_SHORT);
                    MyUtils.showToast(WXPayEntryActivity.this, "支付失败");
                    finish();
                    break;
                case -2://取消
                    MyUtils.Loge(TAG,"支付取消");
                    MyUtils.showToast(WXPayEntryActivity.this, "支付取消");
//                    Toast.makeText(WXPayEntryActivity.this, "支付失败", Toast.LENGTH_SHORT);
                    finish();
                    break;
            }
        }
    }
    public interface OnSuccessListenner{
        void OnSuccess();
    }
}
