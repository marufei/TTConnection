package com.ttrm.ttconnection;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mob.MobApplication;

/**
 * Created by MaRufei
 * time on 2017/8/17
 * Phone 13213580912
 * Email 867814102@qq.com
 */

public class MyApplication extends MobApplication {
    private static MyApplication mInstance;
    public static Context mContext;
    public static String update_url;    //更新APP地址
    public static String update_content;    //更新APP内容

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
