package com.ttrm.ttconnection.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ttrm.ttconnection.MyApplication;
import com.ttrm.ttconnection.R;
import com.ttrm.ttconnection.http.HttpAddress;
import com.ttrm.ttconnection.util.ActivityUtil;
import com.ttrm.ttconnection.util.MyUtils;
import com.ttrm.ttconnection.util.UpdateManger;

public class AutoAddActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout auto_add_rl1;
    private RelativeLayout auto_add_rl2;
    private RelativeLayout auto_add_rl3;
    private RelativeLayout auto_add_rl4;
    private RelativeLayout auto_add_rl5;
    private Button auto_add_rl6;
    private String TAG="AutoAddActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.add(this);
        setContentView(R.layout.activity_auto_add);
        initViews();
    }

    private void initViews() {
        setToolBar("自动加粉");
        auto_add_rl1=findViewById(R.id.auto_add_rl1);
        auto_add_rl2=findViewById(R.id.auto_add_rl2);
        auto_add_rl3=findViewById(R.id.auto_add_rl3);
        auto_add_rl4=findViewById(R.id.auto_add_rl4);
        auto_add_rl5=findViewById(R.id.auto_add_rl5);
        auto_add_rl6=findViewById(R.id.auto_add_rl6);
        auto_add_rl1.setOnClickListener(this);
        auto_add_rl2.setOnClickListener(this);
        auto_add_rl3.setOnClickListener(this);
        auto_add_rl4.setOnClickListener(this);
        auto_add_rl5.setOnClickListener(this);
        auto_add_rl6.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(  MyUtils.checkFacebookExist(AutoAddActivity.this,"com.weijietech.weassist")){
         auto_add_rl6.setText("打开微商工具箱");
        }else{
            auto_add_rl6.setText("点击下载微商工具箱");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.auto_add_rl1:
                Intent intent = new Intent(this, Web2Activity.class);
                intent.putExtra("URL", HttpAddress.H5_SSJF);
                intent.putExtra("title", "视频简介");
                startActivity(intent);
                break;
            case R.id.auto_add_rl2:
                Intent intent1 = new Intent(this, Web2Activity.class);
                intent1.putExtra("URL", HttpAddress.H5_FJDR);
                intent1.putExtra("title", "视频简介");
                startActivity(intent1);
                break;
            case R.id.auto_add_rl3:
                Intent intent2 = new Intent(this, Web2Activity.class);
                intent2.putExtra("URL", HttpAddress.H5_TTLJF);
                intent2.putExtra("title", "视频简介");
                startActivity(intent2);
                break;
            case R.id.auto_add_rl4:
                Intent intent3 = new Intent(this, Web2Activity.class);
                intent3.putExtra("URL", HttpAddress.H5_SMJF);
                intent3.putExtra("title", "视频简介");
                startActivity(intent3);
                break;
            case R.id.auto_add_rl5:
                Intent intent4 = new Intent(this, Web2Activity.class);
                intent4.putExtra("URL", HttpAddress.H5_QLJF);
                intent4.putExtra("title", "视频简介");
                startActivity(intent4);
                break;
            case R.id.auto_add_rl6:
                if(  MyUtils.checkFacebookExist(AutoAddActivity.this,"com.weijietech.weassist")){
                    //TODO 跳转到工具箱
                    PackageManager packageManager = getPackageManager();
                    Intent intent5 = packageManager.getLaunchIntentForPackage("com.weijietech.weassist");
                    startActivity(intent5);
                }else{
                    //TODO 下载
                    MyApplication.update_url = "http://video.lanhuwangluo.cn/wsgjx.apk";
                    MyApplication.update_content ="下载微商工具箱";
                    selectPermission();
                }

                break;
        }
    }

    /**
     * 判断读写权限
     */
    private void selectPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {       //6.0以上运行时权限
            if (AutoAddActivity.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                MyUtils.Loge(TAG, "READ permission IS NOT granted...");

                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    MyUtils.Loge(TAG, "11111111111111");
                } else {
                    // 0 是自己定义的请求coude
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                    MyUtils.Loge(TAG, "222222222222");
                }
            } else {
                MyUtils.Loge(TAG, "READ permission is granted...");
                downloadApk();
            }
        } else {
            downloadApk();
        }
    }

    /**
     * 下载APP
     */
    private void downloadApk() {

        new UpdateManger(AutoAddActivity.this, 1,2).checkUpdateInfo();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    // request successfully, handle you transactions
                    downloadApk();
                } else {

                    // permission denied
                    // request failed
                    MyUtils.Loge(TAG, "权限失败");
                }
                break;
        }
    }

}
