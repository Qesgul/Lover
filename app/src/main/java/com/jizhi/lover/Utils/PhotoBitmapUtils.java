package com.jizhi.lover.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zheng_liu on 2018/2/2.
 */

public class PhotoBitmapUtils {

    /**
     * 存放拍摄图片的文件夹
     */
    private static final String FILES_NAME = "/User";
    private static final String FILE_NAME = "User";
    /**
     * 图片种类
     */
    public static final String IMAGE_TYPE = ".jpg";

    // 防止实例化
    private PhotoBitmapUtils() {
    }

    /**
     * 获取手机可存储路径
     *
     * @param context 上下文
     * @return 手机可存储路径
     */
    public static String getPhoneRootPath(Context context) {
        // 是否有SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || !Environment.isExternalStorageRemovable()) {
            // 获取SD卡根目录
            return context.getExternalCacheDir().getPath();
        } else {
            // 获取apk包下的缓存路径
            return context.getCacheDir().getPath();
        }
    }
    /**
     * 使用当前系统时间作为上传图片的名称
     *
     * @return 存储的根路径+图片名称
     */
    public static String getPhotoFileName(Context context) {
        File file = new File(getPhoneRootPath(context)+FILES_NAME);
        // 判断文件是否已经存在，不存在则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.toString();
    }

    /**
     * 保存Bitmap图片在SD卡中
     * 如果没有SD卡则存在手机中
     *
     * @param mbitmap 需要保存的Bitmap图片
     * @return 保存成功时返回图片的路径，失败时返回null
     */
    public static String savePhotoToSD(Bitmap mbitmap, Context context) {
        FileOutputStream outStream = null;
        String fileName = getPhotoFileName(context);
        try {
            outStream = new FileOutputStream(fileName);
            // 把数据写入文件，100表示不压缩
            mbitmap.compress(Bitmap.CompressFormat.PNG, 10, outStream);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (outStream != null) {
                    // 记得要关闭流！
                    outStream.close();
                }
                if (mbitmap != null) {
                    mbitmap.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
