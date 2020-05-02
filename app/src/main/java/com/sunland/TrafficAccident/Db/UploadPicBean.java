package com.sunland.TrafficAccident.Db;

//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;
//import android.support.annotation.NonNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created By YPT on 2019/3/6/006
 * project name: parkeSystem
 */
@Entity(tableName = "pic")
public class UploadPicBean {

    @PrimaryKey
    @NonNull
    public String sgbh;//事故编号

    @NonNull
    public String yhdm;//	用户代码

    //public String qzpzh;//强制凭证单号

    public String imei;//	终端串号（一般15位）
    public String imsi;//	SIM卡串号（一般15位）
    public String pdaTime;//	yyyy-MM-dd 24hh:mi:ss格式
    public String gpsx;//	经度
    public String gpsY;//	纬度
    public long lxh;

    public String pic_one;

    public String pic_two;

    public String pic_three;

    public String pic_four;

    public String pic_five;

    public String pic_six;

    @NonNull
    public String getYhdm() {
        return yhdm;
    }

    public void setYhdm(@NonNull String yhdm) {
        this.yhdm = yhdm;
    }

//    @NonNull
//    public String getQzpzh() {
//        return qzpzh;
//    }
//
//    public void setQzpzh(@NonNull String qzpzh) {
//        this.qzpzh = qzpzh;
//    }

    @NonNull
    public long getLxh() {
        return lxh;
    }

    public void setLxh(@NonNull long lxh) {
        this.lxh = lxh;
    }

    public String getPic_one() {
        return pic_one;
    }

    public void setPic_one(String pic_one) {
        this.pic_one = pic_one;
    }

    public String getPic_two() {
        return pic_two;
    }

    public void setPic_two(String pic_two) {
        this.pic_two = pic_two;
    }

    public String getPic_three() {
        return pic_three;
    }

    public void setPic_three(String pic_three) {
        this.pic_three = pic_three;
    }

    public String getPic_four() {
        return pic_four;
    }

    public void setPic_four(String pic_four) {
        this.pic_four = pic_four;
    }

    public String getPic_five() {
        return pic_five;
    }

    public void setPic_five(String pic_five) {
        this.pic_five = pic_five;
    }

    public String getPic_six() {
        return pic_six;
    }

    public void setPic_six(String pic_six) {
        this.pic_six = pic_six;
    }

    @NonNull
    public String getSgbh() {
        return sgbh;
    }

    public void setSgbh(@NonNull String sgbh) {
        this.sgbh = sgbh;
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

    public String getPdaTime() {
        return pdaTime;
    }

    public void setPdaTime(String pdaTime) {
        this.pdaTime = pdaTime;
    }

    public String getGpsx() {
        return gpsx;
    }

    public void setGpsx(String gpsx) {
        this.gpsx = gpsx;
    }

    public String getGpsY() {
        return gpsY;
    }

    public void setGpsY(String gpsY) {
        this.gpsY = gpsY;
    }
}
