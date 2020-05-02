package com.sunland.TrafficAccident.view_controller.HR.OWNER;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mylhyl.superdialog.SuperDialog;
import com.sunland.TrafficAccident.Bean.BaseRequest;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.DialogUtils;
import com.sunland.TrafficAccident.Utils.VersionCheckUtils;
import com.sunland.TrafficAccident.adapter.Room;
import com.sunland.TrafficAccident.adapter.RoomAdapter;
import com.sunland.TrafficAccident.view_controller.Ac_base_query;
import com.sunland.TrafficAccident.view_controller.Frg_dialog;
import com.sunland.TrafficAccident.view_controller.HR.Util;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class Hr_room extends Ac_base_query implements Frg_dialog.Callback {

    @BindView(R.id.hotel_title)
    TextView title11;

    @BindView(R.id.listroom)
    ListView listviewroom;


    //private String[] data = {"暹罗猫", "布偶猫", "折耳猫", "短毛猫", "波斯猫", "蓝猫", "森林猫", "孟买猫","缅因猫","埃及猫","伯曼猫","缅甸猫","新加坡猫","美国短尾猫","巴厘猫"};
    private List<Room> cats = new ArrayList<>();

    String hotelname, username;

    String PRICE, TYPE, DESCRIPTION, AMOUNT;

    ArrayList<ArrayList<String>> array1_room;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //HANDLE_register(msg.what);
            //Handle_hotel(msg.what);
            Handle_room(msg.what);

        }
    };

    private void Handle_room(int j){
        if(j == 730){

           // String[][] test = (String [][])array1_room.toArray(new String[0][0]);
            if(array1_room != null){
                int i = 9;
                //Toast.makeText(Hr_room.this, array1_room.get(0).get(2), Toast.LENGTH_LONG).show();

                //Toast.makeText(Hr_room.this, String.valueOf(array1_room.size()), Toast.LENGTH_LONG).show();

                //cats.add(new Room("double","11","22","qqrgtrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrhrhrergf"));

                for(int c = 0; c < array1_room.size(); c++){
                    cats.add(new Room(array1_room.get(c).get(2), array1_room.get(c).get(3), array1_room.get(c).get(4), array1_room.get(c).get(5)));
                }

                RoomAdapter adapter = new RoomAdapter(Hr_room.this, R.layout.hr_room_item, cats);
                ((ListView) findViewById(R.id.listroom)).setAdapter(adapter);

            }





        }

        else if(j == 6){
            Toast.makeText(Hr_room.this, "Successfully deleted", Toast.LENGTH_LONG).show();

        }
        else if (j == 7){
            Toast.makeText(Hr_room.this, "Network Error", Toast.LENGTH_LONG).show();

        }
        else if(j == 8){
            Toast.makeText(Hr_room.this, "Wrong Format", Toast.LENGTH_LONG).show();

        }

        else if(j == 1){
                            final String[] strings = {"Edit this room", "Delete this room ", "Add a new room"};

                new SuperDialog.Builder(this)
                        //.setAlpha(0.5f)
                        //.setGravity(Gravity.CENTER)
                        //.setTitle("上传头像", ColorRes.negativeButton)
                        .setCanceledOnTouchOutside(false)
                        .setItems(strings, new SuperDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                                if(position == 0){
                                    //Toast.makeText(Hr_room.this, PRICE, Toast.LENGTH_LONG).show();

                                    mHandler.sendEmptyMessage(10);

                                    //Toast.makeText(Hr_room.this, strings[position], Toast.LENGTH_LONG).show();
                                }
                                else if(position == 1){

                                    mHandler.sendEmptyMessage(11);
                                    //Toast.makeText(Hr_room.this, strings[position], Toast.LENGTH_LONG).show();
                                }
                                else if(position == 2){
                                    //mHandler.sendEmptyMessage(12);
                                    Bundle bundle = new Bundle();  //得到bundle对象
                                    bundle.putString("username", username);  //key-"sff",通过key得到value-"value值"(String型)
                                    bundle.putString("hotelname", hotelname);  //key-"iff",value-175


                                    hopToActivity(Hr_addroom.class, bundle);
                                    finish();
                                    //Toast.makeText(Hr_room.this, strings[position], Toast.LENGTH_LONG).show();
                                }
                                else if(position == 3){


                                    //hopToActivity(Hr_addroom.class);

                                    //Toast.makeText(Hr_room.this, strings[position], Toast.LENGTH_LONG).show();
                                }



                            }
                        })
                        .setNegativeButton("Cancle", null)
                        .build();
        }
        else if(j == 10){
            new SuperDialog.Builder(this).setRadius(10)
                    .setAlpha(1f)
                    .setTitle("Comfirm").setMessage("You are going to edit this room")
                    .setPositiveButton("Yes", new SuperDialog.OnClickPositiveListener() {
                        @Override
                        public void onClick(View v) {


                            Bundle bundle = new Bundle();  //得到bundle对象
                            bundle.putString("username", username);  //key-"sff",通过key得到value-"value值"(String型)
                            bundle.putString("hotelname", hotelname);  //key-"iff",value-175
                            bundle.putString("price",PRICE);
                            bundle.putString("type",TYPE);
                            bundle.putString("amount",AMOUNT);
                            bundle.putString("description",DESCRIPTION);

                            hopToActivity(Hr_editroom.class, bundle);
                            finish();



                            //Toast.makeText(v.getContext(), TYPE, Toast.LENGTH_LONG).show();


                        }
                    })
                    .setNegativeButton("Cancle", null)
                    .build();
        }
        else if(j == 11){
            new SuperDialog.Builder(this).setRadius(10)
                    .setAlpha(1f)
                    .setTitle("Comfirm").setMessage("You are going to delete this room")
                    .setPositiveButton("Yes", new SuperDialog.OnClickPositiveListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(v.getContext(), "delete", Toast.LENGTH_LONG).show();

                            dialogUtils.showDialog(Hr_room.this, "Deleting", DialogUtils.TYPE_PROGRESS, new DialogUtils.OnCancelClickListener() {
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


                                                int i = Util.delete_room(conn,  username, hotelname, TYPE);
                                                mHandler.sendEmptyMessage(i + 6);



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


                        }
                    })
                    .setNegativeButton("Cancle", null)
                    .build();
        }
        else if(j == 12){

            new SuperDialog.Builder(this).setRadius(10)
                    .setAlpha(1f)
                    .setTitle("Comfirm").setMessage("You are going to add a  new room")
                    .setPositiveButton("Yes", new SuperDialog.OnClickPositiveListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(v.getContext(), "new", Toast.LENGTH_LONG).show();


                        }
                    })
                    .setNegativeButton("Cancle", null)
                    .build();

        }



    }

    public void initView(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Connection conn;
                    conn = Util.openConnection(URL, USER, PASSWORD);



                    array1_room = Util.view_room(conn, username, hotelname);
                    //cats.add(new Room("single","111","2","regerfgergf"));


                    //HANDLE(LOGIN);

                    mHandler.sendEmptyMessage(730);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.hr_viewroom);
        showToolBar(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //fm = getSupportFragmentManager();


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");

        hotelname = bundle.getString("hotelname");
        username = bundle.getString("username");

        title11.setText(hotelname);

        initView();

        //Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();

        //cats.add(new Room("single","111","2","regerfgergf"));







//        cats.add(new Room("double","11","22","qqrgtrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrhrhrergf"));
//
//


//







        listviewroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = position;
                Room cat = cats.get(position);
                //System.out.println("fewfewfewfewfewfwef" + i);
                //Toast.makeText(Hr_room.this, cat.getRoom_type(), Toast.LENGTH_SHORT).show();

                PRICE = cat.getPrice();
                TYPE = cat.getRoom_type();
                DESCRIPTION = cat.getDescription();
                AMOUNT = cat.getAmount();

                mHandler.sendEmptyMessage(1);



            }
        });
    }








    @OnClick({R.id.hotel_title})
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.hotel_title:

                final String[] strings = { "Add a new room"};

                new SuperDialog.Builder(this)
                        //.setAlpha(0.5f)
                        //.setGravity(Gravity.CENTER)
                        //.setTitle("上传头像", ColorRes.negativeButton)
                        .setCanceledOnTouchOutside(false)
                        .setItems(strings, new SuperDialog.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                                if(position == 0){
                                    //Toast.makeText(Hr_room.this, PRICE, Toast.LENGTH_LONG).show();

                                    //mHandler.sendEmptyMessage(10);

                                    Bundle bundle = new Bundle();  //得到bundle对象
                                    bundle.putString("username", username);  //key-"sff",通过key得到value-"value值"(String型)
                                    bundle.putString("hotelname", hotelname);  //key-"iff",value-175


                                    hopToActivity(Hr_addroom.class, bundle);

                                    //Toast.makeText(Hr_room.this, strings[position], Toast.LENGTH_LONG).show();
                                }



                            }
                        })
                        .setNegativeButton("Cancle", null)
                        .build();
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