package com.ttrm.ttconnection.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.entity.ShareBean;

/**
 * Created by  MaRufei
 * on 2017/10/10.
 * update：
 */

public class ShareDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "ShareDialog";
    private Activity activity;
    private ImageView dialog_share_close;
    private TextView dialog_share_content1;
    private TextView dialog_share_content2;
    private ImageView dialog_share_circle;
    private ImageView dialog_share_wx;
    private ImageView dialog_share_qq;
    private ImageView dialog_share_qzone;
    private ShareBean shareBean;

    public ShareDialog(@NonNull FragmentActivity activity,ShareBean shareBean) {
        super(activity);
        this.activity = activity;
        this.shareBean=shareBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_share);

        Window window = this.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BF777777")));
        initview();
        initeven();

    }

    private void initview() {
        dialog_share_close = (ImageView) findViewById(R.id.dialog_share_close);
        dialog_share_close.setOnClickListener(this);
        dialog_share_content1 = (TextView) findViewById(R.id.dialog_share_content1);
        dialog_share_content2 = (TextView) findViewById(R.id.dialog_share_content2);
        dialog_share_circle = (ImageView) findViewById(R.id.dialog_share_circle);
        dialog_share_circle.setOnClickListener(this);
        dialog_share_wx = (ImageView) findViewById(R.id.dialog_share_wx);
        dialog_share_wx.setOnClickListener(this);
        dialog_share_qq = (ImageView) findViewById(R.id.dialog_share_close);
        dialog_share_qq.setOnClickListener(this);
        dialog_share_qzone = (ImageView) findViewById(R.id.dialog_share_qzone);
        dialog_share_qzone.setOnClickListener(this);
    }

    private void initeven() {
        dialog_share_content1.setText("好友通过您的专属链接注册，您和好友各获得"+shareBean.getData().get(shareBean.getData().size()-1)+"颗钻石。");
        dialog_share_content2.setText("邀请满5人，额外获得250颗钻石奖励，邀请越多，奖励翻倍，邀请满50人，颗累计奖励12750钻，可免费爆机（坐等被加）1912人。");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_share_close:
                dismiss();
                break;
            case R.id.dialog_share_qq:

                break;
            case R.id.dialog_share_wx:

                break;
            case R.id.dialog_share_circle:

                break;
            case R.id.dialog_share_qzone:

                break;
        }
    }


}
