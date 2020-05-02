package com.sunland.TrafficAccident.view_controller;




import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public abstract class CheckSelfPermissionActivity extends Ac_base {

    public static String[] permission_required = {
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE
    };

    private List<String> permission_ungranted = new ArrayList<>();
    private final int REQUES_CODE = 0;

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getApplicationInfo().targetSdkVersion >= 23) {
            permission_ungranted.clear();
            addUngrantedPermissions();
            if (permission_ungranted != null && !permission_ungranted.isEmpty()) {
                requestUngrantedPermission();
            }
        }
    }

    private void addUngrantedPermissions() {
        for (String permission : permission_required) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permission_ungranted.add(permission);
            }
        }
    }

    private void requestUngrantedPermission() {
        String[] ungranted = permission_ungranted.toArray(new String[permission_ungranted.size()]);
        ActivityCompat.requestPermissions(this, ungranted, REQUES_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }
}
