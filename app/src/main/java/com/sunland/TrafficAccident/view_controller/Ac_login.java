package com.sunland.TrafficAccident.view_controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sunland.TrafficAccident.Bean.BaseRequest;
import com.sunland.TrafficAccident.Bean.IVariable;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.DialogUtils;
import com.sunland.TrafficAccident.Utils.VersionCheckUtils;
import com.sunland.TrafficAccident.view_controller.HR.Util;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.sql.Connection;
import java.util.List;

import butterknife.OnClick;
import butterknife.BindView;


public class Ac_login  extends Ac_base_query implements Frg_dialog.Callback{


    private static final String REMOTE_IP = "129.204.19.135:3306";
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/test";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    int state = 0;
    private String type = "customer";

    private Connection conn;

    @BindView(R.id.user_name)
    public EditText username;

    @BindView(R.id.password)
    public EditText password;







    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_login);
        showToolBar(false);











    }





    @OnClick({R.id.login_bt})
    public void onClick(View view) {


         // hopToActivity(Ac_manager.class);




        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {


                    conn = Util.openConnection(URL, USER, PASSWORD);

                    //boolean bool = Util.login(conn, username.getText().toString(), password.getText().toString(), type);




                    //Toast.makeText(this,  String.valueOf(bool), Toast.LENGTH_SHORT).show();

                   //System.out.println(bool);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }});
        thread.start();


    }





    @Override
    public BaseRequest assembleRequestObj(String i_name) {


        return null;
    }

    @Override
    public void onDataReceived(String reqId, String reqName, ResultBase bean) {



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
    public void onNegativeClicked(int id, String tag) {
        finish();
    }

    @Override
    public void onPositiveClicked(int id, String tag) {
        askPermission = false;
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        mIntent.setData(Uri.fromParts("package", this.getPackageName(), null));
        this.startActivity(mIntent);
    }


}
