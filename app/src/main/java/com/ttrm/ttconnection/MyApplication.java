package com.ttrm.ttconnection;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by MaRufei
 * time on 2017/8/17
 * Phone 13213580912
 * Email 867814102@qq.com
 */

public class MyApplication extends Application {
    private static MyApplication mInstance;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
    }
    public static MyApplication getInstance() {
        return mInstance;
    }

}
