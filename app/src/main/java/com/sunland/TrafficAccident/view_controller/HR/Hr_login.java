package com.sunland.TrafficAccident.view_controller.HR;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mylhyl.superdialog.SuperDialog;
import com.sunland.TrafficAccident.Bean.BaseRequest;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.DialogUtils;
import com.sunland.TrafficAccident.Utils.VersionCheckUtils;
import com.sunland.TrafficAccident.view_controller.Ac_base_query;
import com.sunland.TrafficAccident.view_controller.Ac_login;
import com.sunland.TrafficAccident.view_controller.Ac_setting;
import com.sunland.TrafficAccident.view_controller.Frg_dialog;
import com.sunland.TrafficAccident.view_controller.HR.CUSTOMER.Hr_hotels;
import com.sunland.TrafficAccident.view_controller.HR.OWNER.Hr_myhotel;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.sql.Connection;

import butterknife.BindView;
import butterknife.OnClick;

public class Hr_login  extends Ac_base_query implements Frg_dialog.Callback{




    @BindView(R.id.user_name)
    public EditText username;

    @BindView(R.id.password)
    public EditText password;

    @BindView(R.id.pwd_icon1)
    public ImageView icon;

    private int icon_state = 0;



    String LOGIN;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                HANDLE(LOGIN);
            }



        }
    };



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.ac_login);
        showToolBar(false);



        icon.setImageDrawable(getResources().getDrawable(R.drawable.vector_drawable_hiddenpassword));
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");

        String str1 = bundle.getString("A");

        //Toast.makeText(this, str1, Toast.LENGTH_SHORT).show();

        SharedPreferences sp1=getSharedPreferences("login",0);
        username.setText(sp1.getString("username",""));
        password.setText(sp1.getString("password",""));

    }


    //1：连接错误，2：用户名或密码错误 customer, hotel_owner:登录成功
     private void HANDLE(String states){


        if (states.equals("customer")){

           // Toast.makeText(this, "user", Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor=getSharedPreferences("login",0).edit();
            editor.putString("username", username.getText().toString());
            editor.putString("password",password.getText().toString());
            editor.commit();


            Bundle bundle1 = new Bundle();  //得到bundle对象
            bundle1.putString("username", username.getText().toString());



            hopToActivity(Hr_hotels.class, bundle1);
        }

        else if(states.equals("hotel_owner")){

           // Toast.makeText(this, "hotel", Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor=getSharedPreferences("login",0).edit();
            editor.putString("username", username.getText().toString());
            editor.putString("password",password.getText().toString());
            editor.commit();

            Bundle bundle1 = new Bundle();  //得到bundle对象
            bundle1.putString("username", username.getText().toString());



            hopToActivity(Hr_myhotel.class, bundle1);

        }
        else if(states.equals("1")){

//            new SuperDialog.Builder(this).setRadius(10)
//                    .setAlpha(1f)
//                    .setTitle("Warning").setMessage("Network Error, please check it")
//                    .setPositiveButton("Close", new SuperDialog.OnClickPositiveListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    }).build();

            Toast.makeText(this, "Network Error, please check it", Toast.LENGTH_SHORT).show();


        }
        else if(states.equals("2")){

            Toast.makeText(this, "Wrong password or account doesn't exist", Toast.LENGTH_SHORT).show();



        }

       // System.out.println(states.toString());




     }


    @OnClick({R.id.login_bt, R.id.rr, R.id.pwd_icon1})
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.pwd_icon1:

                if(icon_state == 0) {

                    icon.setImageDrawable(getResources().getDrawable(R.drawable.vector_drawable_showpassword));
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    icon_state = 1;
                }
                else if(icon_state == 1){

                    icon.setImageDrawable(getResources().getDrawable(R.drawable.vector_drawable_hiddenpassword));
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    icon_state = 0;

                }


                break;
            case R.id.login_bt:

               // icon.setImageDrawable(getResources().getDrawable(R.drawable.icon));

                // hopToActivity(Ac_manager.class);
                dialogUtils.showDialog(Hr_login.this, "Logging", DialogUtils.TYPE_PROGRESS, new DialogUtils.OnCancelClickListener() {
                    @Override
                    public void onCancel() {
                        //mRequestManager.cancelAll();
                        dialogUtils.dialogDismiss();
                    }
                }, null);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogUtils.dialogDismiss();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Connection conn;
                            conn = Util.openConnection(URL, USER, PASSWORD);


                            //0：登录成功，1：连接错误，2：用户名或密码错误
                             LOGIN = Util.login(conn, username.getText().toString(), password.getText().toString());
                            //HANDLE(LOGIN);

                            mHandler.sendEmptyMessage(100);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                thread.start();

                    }
                }, 1800);
                break;


            case R.id.rr:


                hopToActivity(Hr_pre_register.class);
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
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