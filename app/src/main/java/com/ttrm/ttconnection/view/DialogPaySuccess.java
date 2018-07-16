package com.ttrm.ttconnection.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.util.MyUtils;

/**
 * Created by MaRufei on 2017/9/2.
 */

public class DialogPaySuccess extends Dialog implements View.OnClickListener {
    private String num;
    private String TAG = "DialogPaySuccess";
    public OnEventClickListenner onEventClickListenner;
    private ImageView dialog_bt_event;
    private Context context;
    private String url;

    public DialogPaySuccess(Context context, int layoutId) {
        super(context);
        this.context = context;
        setContentView(layoutId);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(false);
    }

    public DialogPaySuccess(Context context, String url, String num) {
        super(context);
        this.context = context;
        this.url = url;
        this.num = num;
        setContentView(R.layout.dialog_pay_success);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(false);

        MyUtils.Loge(TAG, "URL:" + this.url);
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

        ImageView dialog_pay_pic=findViewById(R.id.dialog_pay_pic);
        TextView dialog_pay_num=findViewById(R.id.dialog_pay_num);
        dialog_pay_num.setText(num+"个");
        MyUtils.Loge(TAG, "url::" + url);
        Picasso.with(context).load(url).into(dialog_pay_pic);

        findViewById(R.id.dialog_pay_cancel).setOnClickListener(this);
        findViewById(R.id.dialog_pay_sure).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_pay_sure:
                onEventClickListenner.onEvent();
                dismiss();
                break;
            case R.id.dialog_pay_cancel:
                onEventClickListenner.onCancel();
                dismiss();
                break;
        }

    }

    public interface OnEventClickListenner {
        void onEvent();
        void onCancel();
    }

    public void setBtnBackground(String url) {
        MyUtils.Loge(TAG, "url:" + url);
        Picasso.with(context).load(url).into(dialog_bt_event);
    }
}
