package com.ttrm.ttconnection.util;

/**
 * Created by Administrator on 2016/6/7.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.ttrm.ttconnection.entity.PayResult;


/**
 * 支付宝支付
 *
 * @author lenovo
 */
public class AlipayUtil {
    String TAG = "TAG--AlipayUtil";
    private static final int SDK_PAY_FLAG = 0x123;
    private Activity mActivity;
    private OnAlipayListener mListener;

    public AlipayUtil(Activity activity) {
        this.mActivity = activity;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    MyUtils.Loge(TAG, "payResult =" + payResult.toString());
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        if (mListener != null) {
                            mListener.onSuccess(payResult);
                        }
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            MyUtils.Loge(TAG, "-------2");
                            if (mListener != null) {
                                mListener.onWait(resultStatus);
                            }
                        } else {
                            MyUtils.Loge(TAG, "-------3");
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            if (mListener != null) {
                                mListener.onCancel(resultStatus);
                            }
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 支付
     */
    public void pay(final String alidata) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(alidata, true);
                MyUtils.Loge(TAG,"result="+result);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

//         //必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }




    public void setListener(OnAlipayListener l) {
        mListener = l;
    }

    /**
     * 支付回调接口
     *
     * @author lenovo
     */
    public static abstract class OnAlipayListener {
        /**
         * 支付成功
         */
        public abstract void onSuccess(PayResult payResult);

        /**
         * 支付取消
         */
        public abstract void onCancel(String resultStatus);

        /**
         * 等待确认
         */
        public abstract void onWait(String resultStatus);
    }


}






