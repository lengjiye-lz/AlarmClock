package com.lengjiye.clock.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.lengjiye.clock.utils.LoggerUtils;
import com.lidroid.xutils.util.LogUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by lz on 2015-1-27.
 */
public class MallApplication extends Application {

    public static final String TAG = "ApplicationController";
    private List<Activity> mList = new LinkedList<Activity>();
    public static Map<String, Activity> activityMap = new HashMap<String, Activity>();
    private static MallApplication sInstance;
    public Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        appContext = getApplicationContext();
//        HYUserSessionCacheBean.shareInstance().initUserAndTokenFromDb(); //初始化usercachebean的user和token
        // TODO 设置是否显示、输出log日志
//		LogUtils.allowD = false;
//		LogUtils.allowE = false;
//		LogUtils.allowI = false;
//		LogUtils.allowV = false;
//		LogUtils.allowW = false;
//		LogUtils.allowWtf = false;
        // 实例化customLogger写日志到本地
        LogUtils.customLogger = new LoggerUtils(getApplicationContext());
    }

    public static synchronized MallApplication getInstance() {
        if (null == sInstance) {
            sInstance = new MallApplication();
        }
        return sInstance;

    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public static void exit() {

        try {
            for (Activity activity : activityMap.values()) {
                activity.finish();
            }

            // System.exit(0);
        } catch (Exception e) {

        }
    }
}
