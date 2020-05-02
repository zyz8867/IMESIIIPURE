package com.sunland.TrafficAccident;

import androidx.multidex.MultiDexApplication;

import com.sunland.TrafficAccident.crash.CrashApplication;

public class MyApplication extends CrashApplication {

private String E = "a";
    public void onCreate() {
        super.onCreate();
        aaa();
    }


    public String aaa(){

        return E;
    }

}
