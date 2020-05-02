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
 * Created By YPT on 2019/2/12/012
 * project name: parkeSystem
 */
@Dao
public interface UrlBeanDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     void insert(UrlBean urlbean);

    @Delete
     void delete(UrlBean urlbean);

    @Query("SELECT * FROM t_urls ORDER BY timeStamp ASC")
     List<UrlBean> getAllUrls();


}
