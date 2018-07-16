package com.ttrm.ttconnection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.KeyUtils;
import com.ttrm.ttconnection.util.SaveUtils;


/**
 * TODO 首页
 */
public class SplashActivity extends BaseActivity {
    private String TAG = "SplashActivity";
    private static final int PERMISSION_REQUEST = 1;
    TimeCount timeCount;
    private Button adv_next;
    private ImageView img_poster;
    private TextView adt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**为了使全屏满屏显示，可以通过移除标题栏和状态栏实现*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        ActivityUtil.add(this);
        initViews();
        timeCount = new TimeCount(3000, 1000);

        initData();
    }


    private void initViews() {
        adv_next = (Button) findViewById(R.id.adv_next);
        img_poster = (ImageView) findViewById(R.id.img_poster);
        adt = (TextView) findViewById(R.id.startup_adt);
    }




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
            initData();
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

            adv_next.setText(millisUntilFinished / 1000 + " 秒");
        }
    }

}
