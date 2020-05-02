package com.sunland.TrafficAccident.Db;

//import android.arch.persistence.room.Entity;
//import android.support.annotation.NonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * Created By YPT on 2019/2/12/012
 * project name: parkeSystem
 */
@Entity(tableName = "t_urls", primaryKeys = {"ip", "port"})

public class UrlBean {

    @NonNull
    public String ip;

    @NonNull
    public String port;

    public String desc;

    public long timeStamp;


}
