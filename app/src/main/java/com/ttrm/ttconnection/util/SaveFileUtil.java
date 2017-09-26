package com.ttrm.ttconnection.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by MaRufei on 2017/9/24.
 */

public class SaveFileUtil {

    private static final String SD_PATH =  Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String IN_PATH =  Environment.getExternalStorageDirectory().getAbsolutePath();
    private static String TAG="SaveFileUtil";

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }
    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        MyUtils.Loge(TAG,"1111111111111111111");
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;

        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        MyUtils.Loge(TAG,"savePath;;"+savePath);
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MyUtils.Loge(TAG,"filePic;;"+filePic);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            MyUtils.Loge(TAG,"E;;"+e.getMessage());
            return null;
        }
        MyUtils.Loge(TAG,"savePath;;"+savePath);

        return filePic.getAbsolutePath();
    }
}
