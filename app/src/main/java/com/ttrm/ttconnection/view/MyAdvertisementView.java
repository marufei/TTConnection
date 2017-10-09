package com.ttrm.ttconnection.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.util.MyUtils;

/**
 * Created by MaRufei on 2017/9/2.
 */

public class MyAdvertisementView extends Dialog implements View.OnClickListener {
    private String TAG="MyAdvertisementView";
    public OnEventClickListenner onEventClickListenner;
    private ImageView dialog_bt_event;
    private Context context;
    private String url;

    public MyAdvertisementView(Context context,int layoutId) {
        super(context);
        this.context=context;
        setContentView(layoutId);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(false);
    }
    public MyAdvertisementView(Context context,int layoutId,String url) {
        super(context);
        this.context=context;
        setContentView(layoutId);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(false);
        this.url=url;
        MyUtils.Loge(TAG,"URL:"+this.url);
//        setBtnBackground(url);
    }

    public void setOnEventClickListenner(OnEventClickListenner onEventClickListenner) {
        this.onEventClickListenner = onEventClickListenner;
    }




    public void showDialog() {
        Window window = getWindow();
        //设置弹窗动画
        window.setWindowAnimations(R.style.style_dialog);
        //设置Dialog背景色
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wl = window.getAttributes();
        //设置弹窗位置
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
        show();
        findViewById(R.id.dialog_btn_close).setOnClickListener(this);
        dialog_bt_event=(ImageView) findViewById(R.id.dialog_bt_event);
        dialog_bt_event.setOnClickListener(this);
        MyUtils.Loge(TAG,"url::"+url);
        Picasso.with(context).load(url).into(dialog_bt_event);
//        setBtnBackground(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_bt_event:
                onEventClickListenner.onEvent();
                dismiss();
                break;
            case R.id.dialog_btn_close:
                dismiss();
                break;
        }

    }

    public interface OnEventClickListenner {
        void onEvent();
    }
    public void setBtnBackground(String url){
        MyUtils.Loge(TAG,"url:"+url);
        Picasso.with(context).load(url).into(dialog_bt_event);
    }
}
