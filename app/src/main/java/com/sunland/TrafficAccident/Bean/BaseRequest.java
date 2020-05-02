package com.sunland.TrafficAccident.Bean;


import com.sunland.TrafficAccident.Utils.Utils;

import java.util.Date;

public class BaseRequest {

    public String yhdm ;//	用户代码
    public String imei;//	终端串号（一般15位）
    public String imsi;//	SIM卡串号（一般15位）
    public String pdaTime ;//	yyyy-MM-dd 24hh:mi:ss格式
    public String gpsx;//	经度
    public String gpsY;//	纬度

    public String getYhdm() {
        return yhdm;
    }

    public void setYhdm(String yhdm) {
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