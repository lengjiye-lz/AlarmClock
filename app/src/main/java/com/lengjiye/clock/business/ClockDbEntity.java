package com.lengjiye.clock.business;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by lz on 2014-12-3 in J1.
 * 闹钟的数据库表
 */
@Table(name = DbUtilsOperation.CLOCK_DB)
public class ClockDbEntity extends DbEntityBase implements Serializable {

    @Column(column = "time")
    public long time;
    @Column(column = "start_time")
    public long startTime;  // 开始启用的时间,毫秒表示
    @Column(column = "tittle")
    public String tittle;  // 标题
    @Column(column = "repeat")
    public int repeat;  // 闹钟重复的时间
    @Column(column = "on_off")
    public int onOff;  // 闹钟的开关
    @Column(column = "shake_switch")
    public int shakeSwitch;  // 震动的开关
    @Column(column = "bg_image")
    public String bgImage;  // 背景图片路径
    @Column(column = "ring_object")
    public byte[] ringObject;  // 音频对象

    public class ColumnName {
        public final static String ID = "id";
        public final static String START_TIME = "start_time";
        public final static String TIME = "time";
        public final static String TITTLE = "tittle";
        public final static String REPEAT = "repeat";
        public final static String ON_OFF = "on_off";
        public final static String SHAKE_SWITCH = "shake_switch";
        public final static String BG_IMAGE = "bg_image";
        public final static String RING_OBJECT = "ring_object";
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getOnOff() {
        return onOff;
    }

    public void setOnOff(int onOff) {
        this.onOff = onOff;
    }

    public int getShakeSwitch() {
        return shakeSwitch;
    }

    public void setShakeSwitch(int shakeSwitch) {
        this.shakeSwitch = shakeSwitch;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public byte[] getRingObject() {
        return ringObject;
    }

    public void setRingObject(byte[] ringObject) {
        this.ringObject = ringObject;
    }
}
