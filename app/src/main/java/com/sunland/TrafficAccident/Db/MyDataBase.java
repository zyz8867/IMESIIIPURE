package com.sunland.TrafficAccident.Db;

//import android.arch.persistence.room.Database;
//import android.arch.persistence.room.RoomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created By YPT on 2019/2/12/012
 * project name: parkeSystem
 */
@Database(entities = {UrlBean.class, UploadCarInfoBean.class, UploadPicBean.class,UserInfoBean.class}, version = 2, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {

    public abstract UrlBeanDao getUrlBeanDao();

    public abstract UploadCarInfoDao getUploadDao();

    public abstract UploadPicDao getUploadPicDao();

    public abstract UserInfoDao getUserInfoDao();
}
