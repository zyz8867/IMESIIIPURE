package com.sunland.TrafficAccident.Bean.i_savePic;

import com.sunland.TrafficAccident.Bean.BaseRequest;

import java.util.List;

public class SavePicReq extends BaseRequest {
    private String sgbh;// 事故编号
    private List<Pic> picturelist;//照片对象列表

    private String sgxcpz;

    public String getSgbh() {
        return sgbh;
    }

    public void setSgbh(String sgbh) {
        this.sgbh = sgbh;
    }

    public String getSgxcpz() {
        return sgxcpz;
    }

    public void setSgxcpz(String sgxcpz) {
        this.sgxcpz = sgxcpz;
    }



    public List<Pic> getPicturelist() {
        return picturelist;
    }

    public void setPicturelist(List<Pic> picturelist) {                       // list 非接口内容
        this.picturelist = picturelist;
    }



    private String tp;//	Base64 照片内容



    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

}
