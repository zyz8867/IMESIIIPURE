package com.sunland.TrafficAccident.Db;

//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.OnConflictStrategy;
//import android.arch.persistence.room.Query;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserInfoBean userInfoBean);

    @Query("SELECT * FROM userInfo where lxh=:lxh")
    UserInfoBean getInfoByLxh(int lxh);
}
