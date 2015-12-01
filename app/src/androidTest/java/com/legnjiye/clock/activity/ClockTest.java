package com.legnjiye.clock.activity;

import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.lengjiye.clock.business.ClockDbEntity;
import com.lengjiye.clock.business.DbUtilsOperation;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lz on 2015/1/27.
 */
public class ClockTest extends AndroidTestCase {

    public void testSelect() {
        DbUtilsOperation operation = new DbUtilsOperation(getContext());
        ClockDbEntity timerDbEntity = new ClockDbEntity();
        timerDbEntity.setOnOff(0);
        timerDbEntity.setRepeat(1);
        timerDbEntity.setTime(System.currentTimeMillis());
        timerDbEntity.setTittle("起床了");
        timerDbEntity.setStartTime(System.currentTimeMillis() - 25 * 60 * 60 * 1000);
        operation.insert(timerDbEntity, null);
    }


    public List<String> testGetRingtoneTitleList() {
        List<String> resArr = new ArrayList<String>();
        RingtoneManager manager = new RingtoneManager(mContext);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        if (cursor.moveToFirst()) {
            do {
                resArr.add(cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX));
            } while (cursor.moveToNext());
        }
        LogUtils.e("resArr:" + resArr);
        return resArr;
    }

    /**
     * 获取系统默认铃声的Ringtone对象
     *
     * @return 系统默认铃声的Ringtone对象
     */
    public void testGetSystemDefultRingtone() {
        Uri uri = RingtoneManager.getActualDefaultRingtoneUri(mContext,
                RingtoneManager.TYPE_ALARM);
        LogUtils.e("uri:" + uri);
        Ringtone ringtone = RingtoneManager.getRingtone(mContext, uri);
        LogUtils.e("getTitle:" + ringtone.getTitle(mContext));
    }

    public void testGetAssetsFile() {
        String path = "file:///android_asset/abc.text";
        File file = new File(path);
//        if (file.exists()) {
//            LogUtils.e("name:" + file.getName());
//        }
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            fileInputStream.available();
//            InputStream in = mContext.getResources().getAssets().open("default_image.png");
//获取文件的字节数
//            int lenght = in.available();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
