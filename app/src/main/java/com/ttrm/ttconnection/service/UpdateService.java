package com.ttrm.ttconnection.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.ttrm.ttconnection.MyApplication;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.util.MyUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/6.
 */

public class UpdateService extends Service {
    String TAG="TAG--UpdateService";
    private NotificationManager nm;
    private Notification notification;
    private RemoteViews views;
    private int notificationId = 0x123;
    int i=0;
    MyApplication app;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        app= (MyApplication) getApplicationContext();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = android.R.drawable.stat_sys_download;
        //notification.icon=android.R.drawable.stat_sys_download_done;
        notification.tickerText = getString(R.string.app_name) + "更新";
        notification.when = System.currentTimeMillis();
        notification.defaults = Notification.DEFAULT_LIGHTS;

        //设置任务栏中下载进程显示的views
        views = new RemoteViews(getPackageName(), R.layout.view_nf_update);
        notification.contentView = views;

//        PendingIntent contentIntent=PendingIntent.getActivity(this,0,new Intent(this,City.class),0);
//        notification.setLatestEventInfo(this,"","", contentIntent);

        //将下载任务添加到任务栏中
        nm.notify(notificationId, notification);


        //启动线程开始执行下载任务
        downFile();
        return super.onStartCommand(intent, flags, startId);
    }


    //下载更新文件
    private void downFile() {
        String url=app.getmNewApkData().getData().getVersion().getUrl();
        MyUtils.Loge(TAG,"URL="+url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath() + "/lh/",
                        "lhws_"+ System.currentTimeMillis()+".apk") {
                    @Override
                    public void inProgress(float progress, long l) {
//                        MyUtils.Loge(TAG,"+progress="+progress);
                        if ((progress*100)>=(2*i)){
                            i++;
                            views.setTextViewText(R.id.tv_process, "已下载" + MyUtils.Intercept_Int_Point(progress*100+"") + "%");
                            views.setProgressBar(R.id.pb_download, 100, MyUtils.Str2Int(MyUtils.Intercept_Int_Point(progress*100+"")), false);
                            notification.contentView = views;
                            nm.notify(notificationId, notification);
                        }

                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        i=0;
                        MyUtils.Loge(TAG,e.toString());
                        nm.cancel(notificationId);
                    }

                    @Override
                    public void onResponse(File file) {
                        //下载完成后清除所有下载信息，执行安装提示
                        i=0;
                        nm.cancel(notificationId);
                        Instanll(file, UpdateService.this);
                        //停止掉当前的服务
                        stopSelf();

                    }
                });


    }


    //安装下载后的apk文件
    private void Instanll(File file, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    @Override
    public void onDestroy() {
        try {
            nm.cancel(notificationId);
            //停止掉当前的服务
            stopSelf();
        }catch (Exception e){

        }

        super.onDestroy();
    }
}
