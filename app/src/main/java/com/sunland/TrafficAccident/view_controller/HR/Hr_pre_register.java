package com.sunland.TrafficAccident.view_controller.HR;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mylhyl.superdialog.SuperDialog;
import com.sunland.TrafficAccident.Bean.BaseRequest;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.VersionCheckUtils;
import com.sunland.TrafficAccident.view_controller.Ac_base_query;
import com.sunland.TrafficAccident.view_controller.Frg_dialog;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.sql.Connection;

import butterknife.BindView;
import butterknife.OnClick;

public class Hr_pre_register  extends Ac_base_query implements Frg_dialog.Callback{



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.hr_pre_register);
        showToolBar(false);


    }


    @OnClick({R.id.customer,R.id.hotelowner})
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.hotelowner:

                Bundle bundle = new Bundle();
                bundle.putString("type", "hotel_owner");



                hopToActivity(Hr_register.class, bundle);
                finish();
                break;


            case R.id.customer:

                Bundle bundle1 = new Bundle();
                bundle1.putString("type", "customer");



                hopToActivity(Hr_register.class, bundle1);

                break;


        }


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