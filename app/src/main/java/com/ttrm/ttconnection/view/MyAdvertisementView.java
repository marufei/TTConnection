package com.ttrm.ttconnection.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ttrm.ttconnection.R;

/**
 * Created by MaRufei on 2017/9/2.
 */

public class MyAdvertisementView extends Dialog implements View.OnClickListener {

    public OnEventClickListenner onEventClickListenner;

    public MyAdvertisementView(Context context) {
        super(context);
        setContentView(R.layout.dialog_main_bj);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(false);
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
        findViewById(R.id.dialog_bt_event).setOnClickListener(this);

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
}
