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
 * Created By YPT on 2019/3/6/006
 * project name: parkeSystem
 */
@Dao
public interface UploadPicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UploadPicBean uploadPickBean);

    @Delete
    void delete(UploadPicBean uploadPickBean);

    @Query("SELECT * FROM pic WHERE yhdm=:yhdm")
    List<UploadPicBean> queryAllRecords(String yhdm);
}
