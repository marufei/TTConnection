package com.ttrm.ttconnection.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;


import com.ttrm.ttconnection.BuildConfig;
import com.ttrm.ttconnection.MyApplication;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.view.RateTextCircularProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/21.
 */
public class UpdateManger {
    // 应用程序Context
    private Context mContext;
    // 提示消息
    private Dialog noticeDialog;// 提示有软件更新的对话框
    private Dialog downloadDialog;// 下载对话框
    private static final String savePath = Environment.getExternalStorageDirectory() + "/update/";// 保存apk的文件夹
    private static String saveFileName = null;
    // 进度条与通知UI刷新的handler和msg常量
    private RateTextCircularProgressBar progressBar;
    private Button dialogsuccess, dialogesc;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private int progress;// 当前进度
    private Thread downLoadThread; // 下载线程
    private boolean interceptFlag = false;// 用户取消下载
    // 通知处理刷新界面的handler
    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    progressBar.setProgress(progress);
                    break;
                case DOWN_OVER:

                    progressBar.setVisibility(View.GONE);
                    dialogsuccess.setVisibility(View.VISIBLE);

                    installApk();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private String TAG = "UpdateManger";

    public UpdateManger(Context context, int version) {
        saveFileName = savePath + "ttrm" + version + ".apk";
        this.mContext = context;
    }

    // 显示更新程序对话框，供主程序调用
    public void checkUpdateInfo() {
        showNoticeDialog();
    }

    /**
     * 升级提示
     * 提示更新了什么
     */
    private void showNoticeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);// Builder，可以通过此builder设置改变AleartDialog的默认的主题样式及属性相关信息
        builder.setTitle("发现新版本");
        builder.setMessage("有新版本更新啦~~");
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //启动下载框
                showDownloadDialog();
            }
        });

//        //判断是否强制更新
//        if (!MDApp.Update_Type) {
//            builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//        }

        noticeDialog = builder.create();
        noticeDialog.setCanceledOnTouchOutside(false);
        noticeDialog.setCancelable(false);
        noticeDialog.show();
    }

    /**
     * 下载提示  圆形进度条
     */
    protected void showDownloadDialog() {
        downloadDialog = new Dialog(mContext, R.style.LoadingDialog);
        downloadDialog.setCancelable(false);
        downloadDialog.setContentView(R.layout.dialog_update_download);
        progressBar = (RateTextCircularProgressBar) downloadDialog.findViewById(R.id.update_progressbar);
        dialogsuccess = (Button) downloadDialog.findViewById(R.id.update_success);
        dialogesc = (Button) downloadDialog.findViewById(R.id.update_esc);
//        if (MDApp.Update_Type) {
//            dialogesc.setVisibility(View.GONE);
//        }
        dialogsuccess.setVisibility(View.GONE);
        downloadDialog.show();
        dialogesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadDialog.dismiss();
                interceptFlag = true;
            }
        });
        dialogsuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                installApk();
            }
        });
        //先看看有没有下载好
        if (new File(saveFileName).exists()) {
            progressBar.setVisibility(View.GONE);
            dialogsuccess.setVisibility(View.VISIBLE);
            //安装吧
            installApk();
        } else {
            //启动下载
            downloadApk();
        }
    }

    /**
     * 启动下载
     */
    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 启动安装界面
     */
    protected void installApk() {

        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
       /* Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");// File.toString()会返回路径信息
        mContext.startActivity(i);*/


        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", apkfile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mContext.startActivity(intent);
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            URL url;
            try {
                url = new URL(MyApplication.update_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream ins = conn.getInputStream();
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream outStream = new FileOutputStream(ApkFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = ins.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 下载进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    outStream.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消停止下载
                outStream.close();
                ins.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
//    public void getDownloadUrl(){
//        String url= AppRequest.getAbsoluteUrl(AppRequest.UPDATA_VERSION);
//        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                try {
//                    JSONObject jsonObject=new JSONObject(s);
//                    apkUrl=jsonObject.getString("url");
//                    versionNum=jsonObject.getString("versionnum");
//                    MyUtils.Loge(TAG,"apkUrl:"+apkUrl);
//                    MyUtils.Loge(TAG,"versionNum:"+versionNum);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(mContext,"网络有问题",Toast.LENGTH_SHORT).show();
//            }
//        });
//        AppRequest.addToRequestQueue(mContext,stringRequest);
//    }


}



