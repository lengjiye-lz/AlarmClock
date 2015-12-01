package com.lengjiye.clock.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author wenjing.liu
 * @version 2014-7-2
 */
public class BitmapHelper {
    public static BitmapUtils bitmapUtils;

    private BitmapHelper(Context contenx) {

    }

    /**
     * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
     *
     * @param appContext application context
     * @return
     */

    public static BitmapUtils getBitmapUtils(Context appContext) {
        if (bitmapUtils == null) {
//			// 配置图片缓存路径和缓存大小
//			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//				File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/"
//						+ Constants.APP_DOWNLOAD_PHOTO_CACHE_FOLDER);
//				if (!file.exists()) {
//					file.mkdirs();
//				}
//				bitmapUtils = new BitmapUtils(appContext, file.toString(), 20 * 1024 * 1024, 100 * 1024 * 1024);
//			}
            bitmapUtils = new BitmapUtils(appContext);
        }
        bitmapUtils.configDiskCacheEnabled(true); // 将图片缓存到sd卡,如果要清除则可以调用clearDiskCache()方法进行清除
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        return bitmapUtils;
    }

    /**
     * 设置bitmaputils加载时的图片和加载失败的图片
     *
     * @param loadingRes 加载中图片
     * @param failedRes  加载失败的图片
     * @return BitmapDisplayConfig 用来设置bitmapUtils.display(,,BitmapDisplayConfig
     * )
     */
    public static BitmapDisplayConfig setBitmapDisplayConfig(Drawable loadingRes, Drawable failedRes) {
        BitmapDisplayConfig bitmapConfig = new BitmapDisplayConfig();
        bitmapConfig.setLoadFailedDrawable(failedRes);
        bitmapConfig.setLoadingDrawable(loadingRes);
        return bitmapConfig;
    }

    // 本地读取图片资源文件
    public static Drawable getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        Drawable drawable = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                BitmapFactory.Options optionsBounds = new BitmapFactory.Options();
                optionsBounds.inJustDecodeBounds = true;

                // 获取这个图片的宽和高
                BitmapFactory.decodeFile(pathString, optionsBounds);// 此时返回bm为空
                optionsBounds.inJustDecodeBounds = false;

                // 计算缩放比
                int rateScale = (int) (optionsBounds.outWidth / (float) 100);
                if (rateScale <= 0)
                    rateScale = 1;
                optionsBounds.inSampleSize = rateScale;
                bitmap = BitmapFactory.decodeFile(pathString, optionsBounds);
                drawable = new BitmapDrawable(bitmap);

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return drawable;
    }

    // 读取本地图片
    public static Drawable getLocalBitmap(String pathString) {
        Bitmap bitmap = null;
        Drawable drawable = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;// 图片宽高都为原来的二分之一，即图片为原来的四分之一
                bitmap = BitmapFactory.decodeFile(pathString, options);
                drawable = new BitmapDrawable(bitmap);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return drawable;

    }

    /**
     * 创建文件夹，有就清空，没有就创建。
     */
    public static void createThumbnailFolder() {
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + Constants.CLOCK_THUMBNAIL;
        File file = new File(sdDir);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            delAllFile(sdDir);
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    private static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + File.separator + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + File.separator + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除空文件夹
     * @param folderPath
     */
    private static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩图片
     * @param path 图片路径
     * @param width 要压缩的宽度
     * @return
     */
    public static String getThumbnail(String path, int width) {
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                + Constants.CLOCK_THUMBNAIL;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // 获取这个图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);// 此时返回bm为空
        options.inJustDecodeBounds = false;
        // 计算缩放比
        int be = (int) (options.outWidth / (float) width);
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;
        // 重新读入图片，注意这次要把options.inJustDecodeBounds设为false哦
        try {
            bitmap = BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
//            int w = bitmap.getWidth();
//            int h = bitmap.getHeight();
            String imgName = new File(path).getName();
            File file2 = new File(sdDir + imgName);
            try {
                FileOutputStream out = new FileOutputStream(file2);
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            return sdDir + imgName;
        } else {
            return null;
        }

    }

}
