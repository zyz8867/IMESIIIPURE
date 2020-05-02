package com.sunland.TrafficAccident.view_controller;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amap.api.maps.MapsInitializer;
import com.sunland.TrafficAccident.Bean.IVariable;
//import com.sunland.TrafficAccident.Db.MyDataBase;
//import com.sunland.TrafficAccident.Db.OpenRoomDBHelper;
//import com.sunland.TrafficAccident.Db.UserInfoBean;
//import com.sunland.TrafficAccident.Db.UserInfoDao;
import com.sunland.TrafficAccident.Db.MyDataBase;
import com.sunland.TrafficAccident.Db.OpenRoomDBHelper;
import com.sunland.TrafficAccident.Db.UserInfoBean;
import com.sunland.TrafficAccident.Db.UserInfoDao;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.DialogUtils;
import com.sunland.TrafficAccident.Utils.VersionCheckUtils;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

import butterknife.ButterKnife;


public class Ac_base extends AppCompatActivity implements PermissionRequest {

    private Toolbar base_toolbar;
    private FrameLayout base_container;
    private LayoutInflater layoutInflater;
    private TextView tv_banner_title;
    public DialogUtils dialogUtils;

    public ImageButton ib_nav_back;
    public TextView tv_option_text;
    public ImageButton ib_option_view;
    public final int QR_REQUEST_CODE = 0;


    public ExecutorService executorService;
    public boolean isInit = true;//activity是否为初始化状态
    public int hop_src;//跳入该页面的来源
    private boolean hasShowPermissionTip;
    public boolean askPermission;
    public static final int INIT_PERMISSION = 0;
    public static final int REQ_CAMERA_PERMISSION = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_base);


        base_toolbar = findViewById(R.id.base_toolbar);
        base_container = findViewById(R.id.base_container);
        tv_banner_title = findViewById(R.id.banner_title);
        ib_nav_back = findViewById(R.id.nav_back);
        ib_nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_option_text = findViewById(R.id.option_text);
        ib_option_view = findViewById(R.id.option_image);

        setSupportActionBar(base_toolbar);
        layoutInflater = getLayoutInflater();
        dialogUtils = DialogUtils.getInstance();

        MapsInitializer.sdcardDir = Environment.getExternalStorageDirectory() + "/amapOfflineMap";
    }

    public void setContentLayout(int layout) {
        layoutInflater.inflate(layout, base_container, true);
        ButterKnife.bind(Ac_base.this);
    }

    public void setToolBarTitle(String title) {
        tv_banner_title.setText(title);
    }

    public void showToolBar(boolean show) {
        if (show) {
            base_toolbar.setVisibility(View.VISIBLE);
        } else {
            base_toolbar.setVisibility(View.GONE);
        }
    }

    public void showNavButton(boolean show) {
        if (show) {
            ib_nav_back.setVisibility(View.VISIBLE);
        } else {
            ib_nav_back.setVisibility(View.GONE);
        }
    }

    public void showPermissionTipDialog() {
        if (!hasShowPermissionTip) {
            Frg_dialog dialog = new Frg_dialog();
            dialog.setMessage("在设置-应用-萧山停车场-权限中开启相关权限，已正常使用相关功能");
            dialog.setTitle("权限申请");
            dialog.setNeg_btn_msg("取消");
            dialog.setPos_btn_msg("去设置");
            dialog.show(getSupportFragmentManager(), "permission");
            hasShowPermissionTip = true;
        }
    }

    public void hopToActivity(Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void hopToActivity(Class<? extends AppCompatActivity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    public void hopToActivityForResult(Class<? extends AppCompatActivity> clazz, int request_code) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, request_code);
    }

    public void hopToActivityForResult(Class<? extends AppCompatActivity> clazz, Bundle bundle, int request_code) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, request_code);
    }

    public void startQrScan(int requestCode) {
        Intent intent = new Intent();
        intent.setAction("com.sunland.QR_SCAN");
        startActivityForResult(intent, requestCode);
    }

//    public void showLightStatusBar() {
//        if (VersionCheckUtils.isAboveVersion(Build.VERSION_CODES.M)) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.yellow_white));
//            }
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
//    }

    /**
     * 检查应用是否拥有某一权限
     *
     * @param permission
     * @return 有返回true, 无返回false
     */
    public boolean checkPermission(String permission) {
        if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void reqPermissions(String[] permissions, int requestCode) {
        List<String> unGrantPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                unGrantPermissions.add(permission);
            }
        }
        if (!unGrantPermissions.isEmpty())
            ActivityCompat.requestPermissions(this, unGrantPermissions.toArray(new String[unGrantPermissions.size()]), requestCode);
    }

    @Override
    public void reqPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    public void gatherPhoneInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            IVariable.IMEI = telephonyManager.getDeviceId();
            IVariable.IMSI = telephonyManager.getSubscriberId();
        } else {
            showPermissionTipDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        askPermission = true;
        if (requestCode == INIT_PERMISSION) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    showPermissionTipDialog();
                }
            }
        }
    }

    public File createPicFile() {
        StringBuilder sb = new StringBuilder();
        String file_name = sb.append("jpg_").append(new SimpleDateFormat("yyyyMMDD_HHmmss", Locale.CHINA).format(new Date()))
                .append(".jpg").toString();
        String pic_dir_str = this.getExternalCacheDir() + "/pics/";
        File pic_dir = new File(pic_dir_str);
        if (!pic_dir.exists()) {
            if (pic_dir.mkdirs()) {
                //当创建文件夹失败...
            }
        }
        return new File(pic_dir, file_name);
    }

    public Bitmap getThumbnail(File pic_file) {
//        int pre_set_h = 650;
//        int pre_set_w = 480;
        int thumbnail_w = getResources().getDimensionPixelSize(R.dimen.template_pic_width);
        int thumbnail_h = getResources().getDimensionPixelSize(R.dimen.template_pic_height);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pic_file.getAbsolutePath(), options);
        int pic_h = options.outHeight;
        int pic_w = options.outWidth;
        int scale_factor = Math.min(pic_h / thumbnail_h, pic_w / thumbnail_w);
        options.inSampleSize = scale_factor;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(pic_file.getAbsolutePath(), options);
//        options.inSampleSize = Math.min(pic_h / pre_set_h, pic_w / pre_set_w);
//        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        Bitmap upload_pic = BitmapFactory.decodeFile(pic_file.getAbsolutePath(), options);
//        upload_pics.set(add_pic_id, upload_pic);
        return bitmap;
    }

//    // TODO: 2019/6/10/010 appkey值
//    public void saveLog(int operateType, int operationResult, String operateCondition) {
//        try {
//            OperationLog.logging(this
//                    , " ???"
//                    , getApplication().getPackageName()
//                    , operateType
//                    , operationResult
//                    , 1
//                    , operateCondition);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void saveUserInfo() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                MyDataBase myDataBase = OpenRoomDBHelper.createDBHelper(Ac_base.this).getDb();
                UserInfoDao userInfoDao = myDataBase.getUserInfoDao();
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setLxh(1);
                userInfoBean.setYhdm(IVariable.YHDM);
                userInfoBean.setImei(IVariable.IMEI);
                userInfoBean.setImsi(IVariable.IMSI);
                userInfoBean.setGpsx(IVariable.GPSX);
                userInfoBean.setGpsy(IVariable.GPSY);
                userInfoDao.insert(userInfoBean);
            }
        });
    }



}