package com.sunland.TrafficAccident.view_controller.HR;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mylhyl.superdialog.SuperDialog;
import com.sunland.TrafficAccident.Bean.BaseRequest;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.DialogUtils;
import com.sunland.TrafficAccident.Utils.VersionCheckUtils;
import com.sunland.TrafficAccident.view_controller.Ac_base_query;
import com.sunland.TrafficAccident.view_controller.Frg_dialog;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.sql.Connection;

import butterknife.BindView;
import butterknife.OnClick;

public class Hr_register  extends Ac_base_query implements Frg_dialog.Callback{

    @BindView(R.id.Prize)
    public EditText name1;

    @BindView(R.id.ID_number)
    public EditText ID_number1;

    @BindView(R.id.phone_number)
    public EditText phone_number1;

    @BindView(R.id.user_name)
    public EditText username1;

    @BindView(R.id.password)
    public EditText password11;

    @BindView(R.id.password1)
    public EditText password22;

    @BindView(R.id.title)
            public TextView title;

    String type;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            HANDLE_register(msg.what);

        }
    };

    private void HANDLE_register(int i){
        //0：注册成功，1：连接错误，2：字符串过长
        if(i == 0){

            Toast.makeText(this, "You registered a new account successfully!  " + username1.getText().toString(), Toast.LENGTH_SHORT).show();

            hopToActivity(Hr_login.class);
            finish();
        }
        else if(i == 1){

            new SuperDialog.Builder(this).setRadius(10)
                    .setAlpha(1f)
                    .setTitle("Warning").setMessage("Network Error, please check it")
                    .setPositiveButton("Close", new SuperDialog.OnClickPositiveListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).build();

        }
        else if(i == 2){

            Toast.makeText(this, "Registered failed: Strings Too Long or username exists " , Toast.LENGTH_SHORT).show();


        }

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.hr_registerinfo);
        showToolBar(false);

        TextView textView = (TextView)findViewById(R.id.text_notuse1);

        textView.requestFocus();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");

        type = bundle.getString("type");

        if(type.equals("customer")){
            title.setText("Registeration Form - Customer");
        }
        else if(type.equals("hotel_owner")){
            title.setText("Registeration Form - Hotel Owner");

        }

    }


    @OnClick({R.id.hotel_submit1, R.id.parent})
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.parent:

//                InputMethodManager imm = (InputMethodManager)
//                        getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


                break;
            case R.id.hotel_submit1:



                if (name1.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (ID_number1.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter ID number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (phone_number1.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else  if (username1.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (password11.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (password22.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else  if (password11.getText().toString().equals(password22.getText().toString())) {



                    dialogUtils.showDialog(Hr_register.this, "registering", DialogUtils.TYPE_PROGRESS, new DialogUtils.OnCancelClickListener() {
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


                                        //0：注册成功，1：连接错误，2：字符串过长
                                        int REGISTER = Util.register(conn, username1.getText().toString(), password11.getText().toString(),
                                                phone_number1.getText().toString(), type, name1.getText().toString(), ID_number1.getText().toString());
                                        //HANDLE(LOGIN);

                                        mHandler.sendEmptyMessage(REGISTER);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            thread.start();
                        }
                    }, 1800);

                    //Toast.makeText(this, "You successfully registered it", Toast.LENGTH_SHORT).show();


                    break;

                }

        }


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