package com.sunland.TrafficAccident.view_controller;


import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.sunland.TrafficAccident.Bean.BaseRequest;
import com.sunland.TrafficAccident.Bean.IVariable;
import com.sunland.TrafficAccident.Utils.Utils;
import com.sunlandgroup.Global;
import com.sunlandgroup.def.bean.result.ResultBase;
import com.sunlandgroup.network.OnRequestCallback;
import com.sunlandgroup.network.RequestManager;

import java.text.SimpleDateFormat;
import java.util.List;



/**
 * Created By YPT on 2019/2/21/021
 * project name: parkeSystem
 */
public abstract class Ac_base_query extends Ac_base implements OnRequestCallback {

    public RequestManager mRequestManager;

    public static final String REMOTE_IP = "129.204.19.135:3306";
    public static final String URL = "jdbc:mysql://" + REMOTE_IP + "/test";
    public static final String USER = "root";
    public static final String PASSWORD = "root";

    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestManager = new RequestManager(this, this);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public <T extends BaseRequest> void assembleBasicRequestObj(T baseRequest) {
        baseRequest.setYhdm(IVariable.YHDM);
        baseRequest.setImei(IVariable.IMEI);
        baseRequest.setImsi(IVariable.IMSI);
        baseRequest.setGpsx(IVariable.GPSX);
        baseRequest.setGpsY(IVariable.GPSY);
        baseRequest.setPdaTime(Utils.timestampToTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        String s = baseRequest.getPdaTime();
        //        String s =(Utils.timestampToTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
//
//                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();


    }

    public void queryData(String i_name, int timeOut) {

        createHttpRequest(i_name,timeOut);
        sendQuery();
    }

    public void queryData(String i_name) {
        createHttpRequest(i_name);
        sendQuery();
    }
    public <T> void queryDataInOneName(String i_name, List<T> data) {
        createHttpRequestInOneName(i_name, data);
        sendQuery();
    }

    //对同一接口进行重复请求，每次请求可以是不同的请求报文内容
    public <T> void createHttpRequestInOneName(String i_name, List<T> data) {
        for (int i = 0; i < data.size(); i++) {
            mRequestManager.addRequest(i_name, Global.ip, Global.port, Global.postfix, i_name,
                    assembleRequestObjInOneName(i_name, data, i), 15);
        }
    }

    public <T> BaseRequest assembleRequestObjInOneName(String i_name, List<T> data, int i) {
        return null;
    }

    public abstract BaseRequest assembleRequestObj(String i_name);

    public abstract void onDataReceived(String reqId, String reqName, ResultBase bean);

    public void createHttpRequest(String i_name) {
        mRequestManager.addRequest(i_name, Global.ip, Global.port, Global.postfix, i_name, assembleRequestObj(i_name), 15);
    }
    public void createHttpRequest(String i_name,int timeOut) {
        mRequestManager.addRequest(i_name, Global.ip, Global.port, Global.postfix, i_name, assembleRequestObj(i_name), timeOut);
    }
    public void sendQuery() {
        mRequestManager.postRequestWithoutDialog();
    }

    @Override
    public <T> void onRequestFinish(String reqId, String reqName, T bean) {
        try {
            onDataReceived(reqId, reqName, (ResultBase) bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 请求返回码是否有异常，有异常return false
     *
     * @param code
     * @return
     */
    public boolean checkResCode(int code) {
        if (code < 0) {
            Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
            return false;
        } else if (code > 0) {
            Toast.makeText(this, "服务器异常", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public Class<?> getBeanClass(String reqId, String reqName) {
        switch (reqName) {
            case IVariable.userLogin:
                return ResultBase.class;

            case IVariable.infoCollection:
                return ResultBase.class;

            case IVariable.savePic:
                return ResultBase.class;

            default:
                return ResultBase.class;
        }
    }
}
