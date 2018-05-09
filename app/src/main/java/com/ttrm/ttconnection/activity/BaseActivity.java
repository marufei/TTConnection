package com.ttrm.ttconnection.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.util.MyUtils;

public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbar_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
    }

    /**
     * 设置toolbar标题
     */
    public void setToolBar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) {
            return;
        } else {
            toolbar.setNavigationIcon(R.drawable.vector_drawable_base_arrow);
            toolbar.setTitle(title);
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    /**
     * 设置toolbar 菜单按钮
     */
    public void setMenuBtn(String title, final Context context, final Class activity2) {
        toolbar_btn = (TextView) findViewById(R.id.toolbar_btn);
        toolbar_btn.setVisibility(View.VISIBLE);
        toolbar_btn.setText(title);
        toolbar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, activity2));
            }
        });
    }

    /**
     * 含有标题、内容、两个按钮的对话框
     **/
    public void showAlertDialog(String title, String message,
                                String positiveText,
                                DialogInterface.OnClickListener onClickListener,
                                String negativeText,
                                DialogInterface.OnClickListener onClickListener2) {
        new AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton(positiveText, onClickListener)
                .setNegativeButton(negativeText, onClickListener2).setCancelable(false)
                .show();
    }

    /**
     * 含有一个标题、内容、一个按钮的对话框
     **/
    public void showAlertDialog2(String title, String message,
                                 String positiveText,
                                 DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton(positiveText, onClickListener).setCancelable(false)
                .show();
    }
}
