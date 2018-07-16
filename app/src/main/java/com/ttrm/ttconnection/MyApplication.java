package com.ttrm.ttconnection;


import android.content.Context;
import android.view.View;

import com.meiqia.core.MQManager;
import com.meiqia.core.bean.MQMessage;
import com.meiqia.core.callback.OnGetMessageListCallback;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.mob.MobApplication;
import com.tencent.bugly.crashreport.CrashReport;
import com.ttrm.ttconnection.entity.NewApkData;
import com.ttrm.ttconnection.util.MyUtils;

import java.util.List;

/**
 * Created by MaRufei
 * time on 2017/8/17
 * Phone 13213580912
 * Email 867814102@qq.com
 */

public class MyApplication extends MobApplication {
    private String TAG = "MyApplication";
    private static MyApplication mInstance;
    public static Context mContext;
    public static String update_url;    //更新APP地址
    public static String update_content;    //更新APP内容
    public static boolean isB_location = true;     //是否已经定位
    /**
     * 选择的被动加粉的条目
     */
    public static int pos = 0;


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

        MQConfig.init(this, "9fc8e84d40be46b86e377a89425f4ca0", new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
                MyUtils.Loge(TAG, "初始化美洽成功");

            }

            @Override
            public void onFailure(int code, String message) {
                MyUtils.Loge(TAG, "初始化美洽失败");
            }
        });

    }

    public static MyApplication getInstance() {
        return mInstance;
    }

}
