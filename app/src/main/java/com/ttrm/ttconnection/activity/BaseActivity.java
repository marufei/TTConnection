package com.ttrm.ttconnection.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ttrm.ttconnection.R;

public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
    }
    /**
     * 设置toolbar标题
     */
    public void setToolBar(String title){
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        if(toolbar==null){
            return;
        }else {
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
}
