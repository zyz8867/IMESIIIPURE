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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.mylhyl.superdialog.SuperDialog;
import com.sunland.TrafficAccident.Bean.BaseRequest;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.DialogUtils;
import com.sunland.TrafficAccident.Utils.VersionCheckUtils;
import com.sunland.TrafficAccident.adapter.Room;
import com.sunland.TrafficAccident.adapter.RoomAdapter;
import com.sunland.TrafficAccident.view_controller.Ac_base_query;
import com.sunland.TrafficAccident.view_controller.Frg_dialog;
import com.sunland.TrafficAccident.view_controller.HR.Hr_register;
import com.sunland.TrafficAccident.view_controller.HR.Util;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class Hr_editroom  extends Ac_base_query implements Frg_dialog.Callback{


    @BindView(R.id.list1)
    ListView list1;

    @BindView(R.id.field)
    TextView field1;

    @BindView(R.id.value)
    EditText value1;
    private List<Room> cats = new ArrayList<>();



    String username;
    String hotelname;
    String price;
    String type;
    String description;
    String amount;



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //HANDLE_register(msg.what);
            Handle_hotel(msg.what);

        }
    };

    private void Handle_hotel(int i){

        if(i == 0){
            Toast.makeText(getApplicationContext(), "Updated Succeccfully", Toast.LENGTH_SHORT).show();




        }
        else if(i == 1){
            Toast.makeText(getApplicationContext(), "Wrong Connection", Toast.LENGTH_SHORT).show();

        }
        else if(i == 2){
            Toast.makeText(getApplicationContext(), "Wrong Format", Toast.LENGTH_SHORT).show();

        }
        else if(i == 3){
            Toast.makeText(getApplicationContext(), "Empty entry is not allowed", Toast.LENGTH_SHORT).show();

        }
        else if(i == 4){
            Toast.makeText(getApplicationContext(), "Should be number", Toast.LENGTH_SHORT).show();

        }


    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.hr_editroom);
        showToolBar(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //fm = getSupportFragmentManager();


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        hotelname = bundle.getString("hotelname");

        username = bundle.getString("username");
        price = bundle.getString("price");
        type = bundle.getString("type");
        amount = bundle.getString("amount");
        description = bundle.getString("description");


        cats.add(new Room(type, price, amount, description));


    RoomAdapter adapter = new RoomAdapter(Hr_editroom.this, R.layout.hr_room_item, cats);
                ((ListView) findViewById(R.id.list1)).setAdapter(adapter);

        TextView textView = (TextView)findViewById(R.id.text_notuse1);

        textView.requestFocus();

        //Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();


    }

    @OnClick({R.id.edit, R.id.field})
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.field:

                final String[] strings = {"Price", "Description", "Amount"};

                new SuperDialog.Builder(this)
                        //.setAlpha(0.5f)
                        //.setGravity(Gravity.CENTER)
                        //.setTitle("上传头像", ColorRes.negativeButton)
                        .setCanceledOnTouchOutside(false)
                        .setItems(strings, new SuperDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                                if(position == 0){

                                    field1.setText("Price");

                                }

                                else if(position == 1){
                                    field1.setText("Description");

                                }
                                else if(position == 2){
                                    field1.setText("Amount");
                                }



                            }
                        })
                        .setNegativeButton("Cancle", null)
                        .build();


               // Toast.makeText(getApplicationContext(),"field", Toast.LENGTH_SHORT).show();


                break;

            case R.id.edit:



                dialogUtils.showDialog(Hr_editroom.this, "Handling", DialogUtils.TYPE_PROGRESS, new DialogUtils.OnCancelClickListener() {
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
                                    //int edit_room(Connection conn, String username, String hotel_name, String room,
                                    // int price, int amount, String description

                                    if(field1.getText().toString().equals("") || value1.getText().toString().equals("")){
                                        mHandler.sendEmptyMessage(3);

                                    }
                                    else if(field1.getText().toString().equals("Price")){

                                        Pattern pattern = Pattern.compile("[0-9]*");
                                        Matcher isNum = pattern.matcher(value1.getText().toString());
                                        if( isNum.matches() ){

                                            int i = Util.edit_room(conn, username, hotelname, type, Integer.parseInt( value1.getText().toString()),
                                            Integer.parseInt( amount), description, type);

                                           // 0：编辑成功，1：连接错误，2：格式错误
                                            mHandler.sendEmptyMessage(i);

                                        }
                                        else{
                                            mHandler.sendEmptyMessage(4);

                                        }


                                    }

                                    else if(field1.getText().toString().equals("Description")){

                                        int i = Util.edit_room(conn, username, hotelname, type, Integer.parseInt( price ),
                                                Integer.parseInt( amount), value1.getText().toString(), type);

                                        // 0：编辑成功，1：连接错误，2：格式错误
                                        mHandler.sendEmptyMessage(i);

                                    }
                                    else if(field1.getText().toString().equals("Amount")){

                                        Pattern pattern = Pattern.compile("[0-9]*");
                                        Matcher isNum = pattern.matcher(value1.getText().toString());
                                        if( isNum.matches() ){

                                            int i = Util.edit_room(conn, username, hotelname, type, Integer.parseInt( price ),
                                                    Integer.parseInt( value1.getText().toString()), description, type);

                                            // 0：编辑成功，1：连接错误，2：格式错误
                                            mHandler.sendEmptyMessage(i);

                                        }
                                        else{
                                            mHandler.sendEmptyMessage(4);

                                        }

                                    }






                                    //HANDLE(LOGIN);

                                    //mHandler.sendEmptyMessage(i);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        thread.start();
                    }
                }, 400);





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