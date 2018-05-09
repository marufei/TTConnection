package com.ttrm.ttconnection;


import android.content.Context;

import com.mob.MobApplication;
import com.tencent.bugly.crashreport.CrashReport;
import com.ttrm.ttconnection.entity.NewApkData;

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
    public static boolean isB_location=true;     //是否已经定位

    public NewApkData getmNewApkData() {
        return mNewApkData;
    }

    public void setmNewApkData(NewApkData mNewApkData) {
        this.mNewApkData = mNewApkData;
    }

    private NewApkData mNewApkData; //更新APP内容

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext();
        CrashReport.initCrashReport(getApplicationContext(), "585ed57d98", false);
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

}
