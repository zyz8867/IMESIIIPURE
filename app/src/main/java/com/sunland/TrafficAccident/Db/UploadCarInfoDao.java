package com.sunland.TrafficAccident.Db;

//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Delete;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.OnConflictStrategy;
//import android.arch.persistence.room.Query;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Created By YPT on 2019/3/5/005
 * project name: parkeSystem
 */
@Dao
public interface UploadCarInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UploadCarInfoBean uploadBean);

    @Delete
    void delete(UploadCarInfoBean uploadBean);

    @Query("SELECT * FROM upload WHERE yhdm =:yhdm")
    List<UploadCarInfoBean> queryAllRecords(String yhdm);


}
