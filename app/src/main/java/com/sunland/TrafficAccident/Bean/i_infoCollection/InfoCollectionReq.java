package com.sunland.TrafficAccident.Bean.i_infoCollection;

import com.sunland.TrafficAccident.Bean.BaseRequest;

public class InfoCollectionReq extends BaseRequest {


    private String sgfslk;  //事故发生路口：1、路口中央；2、路口进口处；3、路口出口处；4、右转弯处

    private String sgfsld;  //事故发生路段：1、机动车道；2、非机动车道处；3、人行道处；4、单位小区或小支路开口处；
                                          //5、道路渐变段（100米内车道增加或较少）；6、中央分隔带；7、机非隔离带
    private String sgfswzms;    //事故发生位置描述

    private String dltj;    //道路条件：1、普通道路；2、桥梁；3、隧道；4、匝道；5、长下坡；6、陡坡；7、急转；8、施工路段；9、结冰路面；10、湿滑路面；

    private String dltjms;  //道路条件描述

    private String zmtj;    //照明条件：1、灯光无影响；2、灯光干扰；3、灯光过暗

    private String bzbx;    //标志标线：1、标志标线完整清晰；2、标线残缺模糊；3、标志提醒缺失；4、标志标线不一致

    private String csqk;    //车损情况：1、无车损；2、轻微损；3、严重损坏

    private String ms;  //其他信息描述


    private String sgbh; //事故编号


    public String getSgfslk() {
        return sgfslk;
    }

    public void setSgfslk(String sgfslk) {
        this.sgfslk = sgfslk;
    }


    public String getSgfsld() {
        return sgfsld;
    }

    public void setSgfsld(String sgfsld) {
        this.sgfsld = sgfsld;
    }


    public String getSgfswzms() {
        return sgfswzms;
    }

    public void setSgfswzms(String sgfswzms) {
        this.sgfswzms = sgfswzms;
    }


    public String getDltj() {
        return dltj;
    }

    public void setDltj(String dltj) {
        this.dltj = dltj;
    }


    public String getDltjms() {
        return dltjms;
    }

    public void setDltjms(String dltjms) {
        this.dltjms = dltjms;
    }


    public String getZmtj() {
        return zmtj;
    }

    public void setZmtj(String zmtj) {
        this.zmtj = zmtj;
    }


    public String getBzbx() {
        return bzbx;
    }

    public void setBzbx(String bzbx) {
        this.bzbx = bzbx;
    }


    public String getCsqk() {
        return csqk;
    }

    public void setCsqk(String csqk) {
        this.csqk = csqk;
    }


    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }


    public String getSgbh() {
        return sgbh;
    }

    public void setSgbh(String sgbh) {
        this.sgbh = sgbh;
    }











}
