package com.sunland.TrafficAccident.view_controller.HR.OWNER;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.sunland.TrafficAccident.Bean.BaseRequest;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.DialogUtils;
import com.sunland.TrafficAccident.Utils.VersionCheckUtils;
import com.sunland.TrafficAccident.view_controller.Ac_base_query;
import com.sunland.TrafficAccident.view_controller.Frg_dialog;
import com.sunland.TrafficAccident.view_controller.HR.Util;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class Hr_addroom  extends Ac_base_query implements Frg_dialog.Callback{


    @BindView(R.id.typeprice)
    EditText eprice;

    @BindView(R.id.typeroom)
    EditText eroom;

    @BindView(R.id.descri)
    EditText edescribe;

    @BindView(R.id.amountroom)
    EditText eamount;


    String username;
    String hotelname;
    ArrayList<ArrayList<String>> array1;

    int st = 0;




    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //HANDLE_register(msg.what);
            Handle_hotel(msg.what);

        }
    };

    private void Handle_hotel(int i){
        //0：编辑成功，1：连接错误，2：格式错误
        if(i == 0){
            Toast.makeText(getApplicationContext(), "Updated Succeccfully", Toast.LENGTH_SHORT).show();

        }
        else if(i == 1){
            Toast.makeText(getApplicationContext(), "Wrong Connection or Empty Room List", Toast.LENGTH_SHORT).show();
            st = 2;

        }
        else if(i == 2){
            Toast.makeText(getApplicationContext(), "Wrong Format", Toast.LENGTH_SHORT).show();

        }
        else if(i == 730){
            Toast.makeText(getApplicationContext(), array1.get(1).get(2), Toast.LENGTH_SHORT).show();

        }



    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.hr_addroom);
        showToolBar(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");

        username = bundle.getString("username");
        hotelname = bundle.getString("hotelname");

        //fm = getSupportFragmentManager();


       // Intent intent = getIntent();
       // Bundle bundle = intent.getBundleExtra("bundle");

        //username = bundle.getString("username");



        initview();





    }

    @OnClick({R.id.roomadd})
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {


            case R.id.roomadd:


                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher isNum1 = pattern.matcher(eprice.getText().toString());
                Matcher isNum2 = pattern.matcher(eamount.getText().toString());



            if(eprice.getText().toString().equals("") || eroom.getText().toString().equals("")
            || edescribe.getText().toString().equals("") || eamount.getText().toString().equals("")){


                Toast.makeText(getApplicationContext(), "Please enter everything", Toast.LENGTH_SHORT).show();



            }
            else if(!isNum1.matches()){

                Toast.makeText(getApplicationContext(), "price should be integer", Toast.LENGTH_SHORT).show();


            }
            else if(!isNum2.matches()){
                Toast.makeText(getApplicationContext(), "Amount should be integer", Toast.LENGTH_SHORT).show();

            }


            else {

                String z = eroom.getText().toString();


                if(st == 0) {
                    for (int q = 0; q < array1.size(); q++) {

                        if (array1.get(q).get(2).equals(z)) {
                            st = 1;
                        }

                    }
                }

                if(st == 1){
                    Toast.makeText(getApplicationContext(), "Room type must be unique, can not be repeated with existing types", Toast.LENGTH_SHORT).show();

                }
                else{
                    dialogUtils.showDialog(Hr_addroom.this, "Adding", DialogUtils.TYPE_PROGRESS, new DialogUtils.OnCancelClickListener() {
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

                                        //int add_room(Connection conn, String username, String hotel_name, String room, int price, int amount, String description)

                                        int s = Util.add_room(conn,username, hotelname,eroom.getText().toString(), Integer.parseInt( eprice.getText().toString()),
                                                Integer.parseInt(eamount.getText().toString()), edescribe.getText().toString());


                                        mHandler.sendEmptyMessage(s);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            thread.start();
                        }
                    }, 1400);
                }



            }

                break;


        }


    }



    public void initview(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Connection conn;
                    conn = Util.openConnection(URL, USER, PASSWORD);



                    array1 = Util.view_room(conn, username, hotelname);
                    //cats.add(new Room("single","111","2","regerfgergf"));


                    //HANDLE(LOGIN);
                    if(array1 != null) {
                        //mHandler.sendEmptyMessage(730);
                    }
                    else{
                        mHandler.sendEmptyMessage(1);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
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