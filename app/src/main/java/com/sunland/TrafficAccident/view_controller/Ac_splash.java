package com.sunland.TrafficAccident.view_controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.sunland.TrafficAccident.Bean.BaseRequest;
import com.sunland.TrafficAccident.Bean.IVariable;
import com.sunland.TrafficAccident.Bean.i_userLogin.UserLoginReq;
import com.sunland.TrafficAccident.MyApplication;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.VersionCheckUtils;
import com.sunland.TrafficAccident.view_controller.HR.Hr_login;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.util.concurrent.Executors;



public class Ac_splash  extends Ac_base_query{

    private MyApplication mApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        executorService = Executors.newSingleThreadExecutor();
//        showToolBar(false);
//       mApplication = (MyApplication) getApplication();
//
        gatherPhoneInfo();
           //hopToActivity(MainActivity.class);

        Bundle bundle = new Bundle();  //得到bundle对象
        bundle.putString("A", "value值");  //key-"sff",通过key得到value-"value值"(String型)
        bundle.putString("B", "FDEERWFR");  //key-"iff",value-175


           hopToActivity(Hr_login.class, bundle);

           finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!askPermission) {
            if (VersionCheckUtils.isAboveVersion(Build.VERSION_CODES.M)) {
                String[] permission_required = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA
                };
                reqPermissions(permission_required, INIT_PERMISSION);
            }
        }
    }

    @Override
    public BaseRequest assembleRequestObj(String i_name) {

//                SignInOrBackReq signInOrBackReq = new SignInOrBackReq();
//                assembleBasicRequestObj(signInOrBackReq);
//                signInOrBackReq.setTcsj(tcsj);
//                signInOrBackReq.setState(state);
//                signInOrBackReq.setRemarks(remarks);

//                return signInOrBackReq;

        UserLoginReq userLoginReq = new UserLoginReq();
        assembleBasicRequestObj(userLoginReq);
        return userLoginReq;

    }

    @Override
    public void onDataReceived(String reqId, String reqName, ResultBase bean) {
               ResultBase userLoginRes = bean;
        if (!checkResCode(userLoginRes.getCode())) {
            Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
        }

    }



    public String appendString(String... strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);
            if (i != strings.length - 1) {
                sb.append("@");
            }
        }
        return sb.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "请授予相应权限，以保证应用正常运行", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }

        gatherPhoneInfo();
        queryData(IVariable.userLogin);
    }


}






