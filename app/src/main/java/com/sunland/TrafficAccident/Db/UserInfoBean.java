package com.sunland.TrafficAccident.Db;

//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;
//import android.support.annotation.NonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userInfo")
public class UserInfoBean {
    @NonNull
    @PrimaryKey
    public int lxh;//流水号
    public String yhdm;
    public String imei;
    public String imsi;
    public String gpsx;
    public String gpsy;

    public int getLxh() {
        return lxh;
    }

    public void setLxh(int lxh) {
        this.lxh = lxh;
    }

    @NonNull
    public String getYhdm() {
        return yhdm;
    }

    public void setYhdm(@NonNull String yhdm) {
        this.yhdm = yhdm;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getGpsx() {
        return gpsx;
    }

    public void setGpsx(String gpsx) {
        this.gpsx = gpsx;
    }

    public String getGpsy() {
        return gpsy;
    }

    public void setGpsy(String gpsy) {
        this.gpsy = gpsy;
    }

}
