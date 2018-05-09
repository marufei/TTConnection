package com.ttrm.ttconnection.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.SaveUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ttrm.ttconnection.MyApplication.mContext;


/**
 * TODO 首页
 */
public class SplashActivity extends BaseActivity {
    private String TAG = "SplashActivity";
    private static final int PERMISSION_REQUEST = 1;
    //    private IFLYNativeAd nativeAd;
//    private NativeADDataRef adItem;
    TimeCount timeCount;
    private Button adv_next;
    private ImageView img_poster;
    private TextView adt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**为了使全屏广告满屏显示，可以通过移除标题栏和状态栏实现*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        ActivityUtil.add(this);
        initViews();
        timeCount = new TimeCount(3000, 1000);
//        initData();
        //初始化科大讯飞广告
//        initkd();
        initData();
    }


    private void initViews() {
        adv_next = (Button) findViewById(R.id.adv_next);
        img_poster = (ImageView) findViewById(R.id.img_poster);
        adt = (TextView) findViewById(R.id.startup_adt);
    }


    /**
     * 初始化科大讯飞广告
     */
//    public void initkd() {
//
//        if (nativeAd == null) {
//            nativeAd = new IFLYNativeAd(this, HttpAddress.ADD_ID, iflyNativeListener);
//            //设置下载广告前，弹窗提示
//            nativeAd.setParameter(AdKeys.DOWNLOAD_ALERT, "true");
//        }
//        int count = 1; // 一次拉取的广告条数,当前仅支持一条
//        nativeAd.loadAd(count);
//    }
//
//
//    IFLYNativeListener iflyNativeListener = new IFLYNativeListener() {
//        //广告加载成功
//        @Override
//        public void onADLoaded(List<NativeADDataRef> list) {
//            MyUtils.Loge(TAG, "广告加载成功");
//            //启动倒计时
//            timeCount.start();
//            if (list.size() > 0) {
//                adItem = list.get(0);
//
//                adv_next.setVisibility(View.VISIBLE);
//                adt.setVisibility(View.VISIBLE);
//
////                $.id(R.id.img_poster).image(adItem.getImage(), false, true);
//                Picasso.with(SplashActivity.this).load(adItem.getImage()).into(img_poster);
//                if (!TextUtils.isEmpty(adItem.getAdSourceMark()))
////                    $.id(R.id.startup_adt).text(adItem.getAdSourceMark() + "广告");
//                    adt.setText(adItem.getAdSourceMark() + "广告");
//                else
////                    $.id(R.id.startup_adt).text("广告");
//                    adt.setText("广告");
////                $.id(R.id.img_poster).clicked(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        adItem.onClicked(view);
////                    }
////                });
//                img_poster.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        adItem.onClicked(view);
//                    }
//                });
//                //原生广告需上传点击位置
//                findViewById(R.id.img_poster).setOnTouchListener(new View.OnTouchListener() {
//
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if (adItem.onClicked(SplashActivity.this.findViewById(R.id.img_poster)))
////                            LogUtil.e(TAG, "科大讯飞广告  点击上报");
//
//                            switch (event.getAction()) {
//                                case MotionEvent.ACTION_DOWN:
//                                    nativeAd.setParameter(AdKeys.CLICK_POS_DX, event.getX() + "");
//                                    nativeAd.setParameter(AdKeys.CLICK_POS_DY, event.getY() + "");
//                                    break;
//                                case MotionEvent.ACTION_UP:
//                                    nativeAd.setParameter(AdKeys.CLICK_POS_UX, event.getX() + "");
//                                    nativeAd.setParameter(AdKeys.CLICK_POS_UY, event.getY() + "");
//                                    break;
//                                default:
//                                    break;
//                            }
//                        return false;
//                    }
//                });
//
//                if (adItem.onExposured(SplashActivity.this.findViewById(R.id.img_poster))) {
//                }
////                    LogUtil.e(TAG, "科大讯飞广告  展示上报");
//            }
//        }
//
//        //广告加载失败
//        @Override
//        public void onAdFailed(AdError adError) {
//            //直接启动主页面
////            startUp(2);
//            MyUtils.Loge(TAG, "加载广告失败：" + adError.getErrorCode() + "\n" + adError.getMessage());
////            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            initData();
////            LogUtil.e(TAG, "加载广告失败：" + adError.getErrorCode() + "\n" + adError.getMessage());
//        }
//
//        @Override
//        public void onConfirm() {
//
//        }
//
//        @Override
//        public void onCancel() {
//
//        }
//    };
    private void initData() {
        if (!TextUtils.isEmpty(SaveUtils.getString(KeyUtils.user_login_token)))

        {
            startActivity(new Intent(SplashActivity.this, Main2Activity.class));
            finish();

        } else

        {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }

    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        //倒计时完成
        @Override
        public void onFinish() {
            adv_next.setText("跳过");
//            startUp(2);
//            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            initData();
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

            adv_next.setText(millisUntilFinished / 1000 + " 秒");
        }
    }

}
