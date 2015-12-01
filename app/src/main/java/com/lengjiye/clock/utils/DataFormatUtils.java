package com.lengjiye.clock.utils;

import com.lidroid.xutils.util.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 数据格式转化通用类
 * Created by lz on 2015/3/30.
 */
public class DataFormatUtils {

    /**
     * 将字节转换为对象
     *
     * @param bytes
     * @return
     */
    public static Object byteToObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);
            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            LogUtils.e("byteToObject:" + e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 将对像转换为字节
     *
     * @param obj
     * @return
     */
    public static byte[] objectToByte(java.lang.Object obj) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
            bo.close();
            oo.close();
        } catch (Exception e) {
            LogUtils.e("objectToByte:" + e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }
}
