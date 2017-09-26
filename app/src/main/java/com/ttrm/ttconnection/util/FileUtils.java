
package com.ttrm.ttconnection.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;


/**
 * 名称：FileUtils.java
 * 描述：文件操作类.
 *
 * @author CLD
 * @date：2015-11-10
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    /**
     * 默认APP根目录.
     */
    private static String downloadRootDir = null;

    /**
     * 默认下载图片文件目录.
     */
    private static String imageDownloadDir = null;

    /**
     * 默认下载文件目录.
     */
    private static String fileDownloadDir = null;

    /**
     * 默认缓存目录.
     */
    private static String cacheDownloadDir = null;

    /**
     * 默认下载数据库文件的目录.
     */
    private static String dbDownloadDir = null;

    /**
     * 剩余空间大于200M才使用SD缓存.
     */
    private static int freeSdSpaceNeededToCache = 200 * 1024 * 1024;


    public static final int TYPE_IMAGE = 1;
    public static String FILE_TYPE_IMAGE = "img";
    public static String FILE_SUFFIX_IMAGE_JPEG = "jpg";
    public static final int TYPE_AUDIO = 2;
    public static String FILE_TYPE_AUDIO = "aud";
    public static String FILE_SUFFIX_AUDIO = "aud1";
    public static final int TYPE_VIDEO = 3;
    public static String FILE_TYPE_VIDEO = "vido";
    public static String FILE_SUFFIX_VIDEO = "vido1";


    /**
     * 描述：通过文件的本地地址从SD卡读取图片.
     *
     * @param file the file
     * @return Bitmap 图片
     */
    public static Bitmap getBitmapFromSD(File file) {
        Bitmap bitmap = null;
        if (!TextUtils.isEmpty(file.toString())) {
            try {
                //SD卡是否存在
                if (!isCanUseSD()) {
                    return null;
                }
                //文件是否存在
                if (!file.exists()) {
                    return null;
                }
                //文件存在
                bitmap = ImageUtils.getBitmap(file);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
                MyUtils.Loge(TAG,e.getMessage());
                return null;
            }

        } else {
            return null;
        }
    }


    /**
     * 描述：从sd卡中的文件读取到byte[].
     *
     * @param path sd卡中文件路径
     * @return byte[]
     */
    public static byte[] getByteArrayFromSD(String path) {
        byte[] bytes = null;
        ByteArrayOutputStream out = null;
        try {
            File file = new File(path);
            //SD卡是否存在
            if (!isCanUseSD()) {
                return null;
            }
            //文件是否存在
            if (!file.exists()) {
                return null;
            }

            long fileSize = file.length();
            if (fileSize > Integer.MAX_VALUE) {
                return null;
            }

            FileInputStream in = new FileInputStream(path);
            out = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int size = 0;
            while ((size = in.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }
            in.close();
            bytes = out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            MyUtils.Loge(TAG,e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    MyUtils.Loge(TAG,e.getMessage());
                }
            }
        }
        return bytes;
    }


    /**
     * 描述：从指定文件中读取数据
     *
     * @param context the content
     * @param path
     */
    public static String readFromFile(Context context, String path) {
        String str = "";
        FileInputStream fin = null;
        File file = new File(context.getFilesDir() + "/" + path);
        try {
            if (file.exists()) {
                fin = new FileInputStream(file);
                int length = fin.available();
                byte[] buffer = new byte[length];
                fin.read(buffer);
                str = new String(buffer, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyUtils.Loge(TAG,e.getMessage());
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException e) {
                e.printStackTrace();
                MyUtils.Loge(TAG,e.getMessage());
            }
        }
        return str;
    }


    /**
     * 描述：将数据写入指定文件中
     *
     * @param context the content
     * @param path
     * @param result
     */
    public static void writeToFile(Context context, String path, String result) {

        FileOutputStream fout = null;
        File file = new File(context.getFilesDir() + "/" + path);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fout = new FileOutputStream(file);
            byte[] bytes = result.getBytes();
            fout.write(bytes);

        } catch (Exception e) {
            e.printStackTrace();
            MyUtils.Loge(TAG,e.getMessage());
        } finally {
            try {
                if (fout != null)
                    fout.close();
            } catch (IOException e) {
                e.printStackTrace();
                MyUtils.Loge(TAG,e.getMessage());
            }
        }

    }


    /**
     * 描述：将byte数组写入文件.
     *
     * @param path    the path
     * @param content the content
     * @param create  the create
     */
    public static void writeByteArrayToSD(String path, byte[] content, boolean create) {

        FileOutputStream fos = null;
        try {
            File file = new File(path);
            //SD卡是否存在
            if (!isCanUseSD()) {
                return;
            }
            //文件是否存在
            if (!file.exists()) {
                if (create) {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                        file.createNewFile();
                    }
                } else {
                    return;
                }
            }
            fos = new FileOutputStream(path);
            fos.write(content);

        } catch (Exception e) {
            e.printStackTrace();
            MyUtils.Loge(TAG,e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    MyUtils.Loge(TAG,e.getMessage());
                }
            }
        }
    }

    /**
     * 将bitmap写入文件.
     *
     * @param path
     * @param bitmap png
     */
    public static void writeBitmapToSD(String path, Bitmap bitmap, boolean create) {

        FileOutputStream fos = null;
        try {
            File file = new File(path);
            //SD卡是否存在
            if (!isCanUseSD()) {
                return;
            }
            //文件是否存在
            if (!file.exists()) {
                if (create) {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                        file.createNewFile();
                    }
                }
            }
            fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
            MyUtils.Loge(TAG,e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    MyUtils.Loge(TAG,e.getMessage());
                }
            }
        }
    }

    /**
     * 拷贝Assets目录内容到sd卡目录
     *
     * @param context
     * @param assetDir "dir"
     * @param outDir   完整sd卡路径
     */
    public static void copyAssets2SD(Context context, String assetDir, String outDir) {
        String[] files;
        try {
            files = context.getAssets().list(assetDir);
            File outDirFile = new File(outDir);
            if (!outDirFile.exists()) {
                outDirFile.mkdirs();
            }

            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];

                String[] filesChild = context.getAssets().list(fileName);
                if (filesChild != null && filesChild.length > 0) {
                    copyAssets2SD(context, fileName, outDir + "/" + fileName);
                } else {
                    InputStream in = null;
                    if (!TextUtils.isEmpty(assetDir)) {
                        in = context.getAssets().open(assetDir + "/" + fileName);
                    } else {
                        in = context.getAssets().open(fileName);
                    }
                    File outFile = new File(outDir + "/" + fileName);
                    if (outFile.exists()) {
                        outFile.delete();
                    }
                    outFile.createNewFile();
                    OutputStream out = new FileOutputStream(outFile);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    in.close();
                    out.close();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyUtils.Loge(TAG,e.getMessage());
        }
    }

    /**
     * 描述：SD卡是否能用.
     *
     * @return true 可用,false不可用
     */
    public static boolean isCanUseSD() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
            MyUtils.Loge(TAG,e.getMessage());
        }
        return false;
    }


    /**
     * 计算sdcard上的剩余空间.
     *
     * @return the int
     */
    public static int freeSpaceOnSD() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / 1024 * 1024;
        return (int) sdFreeMB;
    }

    /**
     * 根据文件的最后修改时间进行排序.
     */
    public static class FileLastModifSort implements Comparator<File> {

        /* (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(File arg0, File arg1) {
            if (arg0.lastModified() > arg1.lastModified()) {
                return 1;
            } else if (arg0.lastModified() == arg1.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }


    /**
     * 删除所有缓存文件.
     *
     * @return true, if successful
     */
    public static boolean clearDownloadFile() {
        try {
            File fileDirectory = new File(downloadRootDir);
            deleteFile(fileDirectory);
        } catch (Exception e) {
            e.printStackTrace();
            MyUtils.Loge(TAG,e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除文件.
     *
     * @return true, if successful
     */
    public static boolean deleteFile(File file) {

        try {
            if (!isCanUseSD()) {
                return false;
            }
            if (file == null) {
                return true;
            }
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            } else {
                file.delete();
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            MyUtils.Loge(TAG,e.getMessage());
            return false;
        }
    }


    /**
     * 描述：读取Assets目录的文件内容.
     *
     * @param context  the context
     * @param name     the name
     * @param encoding the encoding
     * @return the string
     */
    public static String readAssetsByName(Context context, String name, String encoding) {
        String text = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getAssets().open(name));
            bufReader = new BufferedReader(inputReader);
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = bufReader.readLine()) != null) {
                buffer.append(line);
            }
            text = new String(buffer.toString().getBytes(), encoding);
        } catch (Exception e) {
            e.printStackTrace();
            MyUtils.Loge(TAG,e.getMessage());
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                }
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                MyUtils.Loge(TAG,e.getMessage());
            }
        }
        return text;
    }


    /**
     * Gets the free sd space needed to cache.
     *
     * @return the free sd space needed to cache
     */
    public static int getFreeSdSpaceNeededToCache() {
        return freeSdSpaceNeededToCache;
    }


    public static File createMediaFile(Context context, int fileType, String fileDir, String fileName) {
        File file = null;
        String timeStamp = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileDir);
        }

        if (TextUtils.isEmpty(fileName)) {
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        }

        File mediaFile = null;
        if (fileType == TYPE_IMAGE) {
            mediaFile = new File(file.getPath() + File.separator + "IMG_" + timeStamp + "." + FILE_SUFFIX_IMAGE_JPEG);
        }
        return mediaFile;
    }

}
