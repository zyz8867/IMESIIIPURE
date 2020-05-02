package com.sunland.TrafficAccident.crash;

import androidx.multidex.MultiDexApplication;

/**
 * 监控全局异常
 */
public class CrashApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(this);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//        com.baidu.mapapi.SDKInitializer.initialize(getApplicationContext());
    }
}
