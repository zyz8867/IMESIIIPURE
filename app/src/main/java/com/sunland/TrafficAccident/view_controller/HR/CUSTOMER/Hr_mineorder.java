package com.sunland.TrafficAccident.view_controller.HR.CUSTOMER;

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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mylhyl.superdialog.SuperDialog;
import com.sunland.TrafficAccident.Bean.BaseRequest;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.DialogUtils;
import com.sunland.TrafficAccident.Utils.VersionCheckUtils;
import com.sunland.TrafficAccident.adapter.Hotel;
import com.sunland.TrafficAccident.adapter.HotelAdapter;
import com.sunland.TrafficAccident.view_controller.Ac_base_query;
import com.sunland.TrafficAccident.view_controller.Frg_dialog;
import com.sunland.TrafficAccident.view_controller.HR.OWNER.Hr_editroom;
import com.sunland.TrafficAccident.view_controller.HR.Util;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class Hr_mineorder  extends Ac_base_query implements Frg_dialog.Callback{

    @BindView(R.id.hr_cus_hotel)
    RadioButton jiu;

    @BindView(R.id.hr_cus_mine)
    RadioButton mine;

    @BindView(R.id.hotelname)
    TextView hotelname;

    @BindView(R.id.price)
    TextView price;

    @BindView(R.id.type)
    TextView type;

    @BindView(R.id.address111)
    TextView address111;

    @BindView(R.id.hotel_des)
    TextView hdes;

    @BindView(R.id.room_des)
    TextView rdes;



    @BindView(R.id.number)
    EditText number;

    @BindView(R.id.datePicker)
    DatePicker datePicker;

    //用来存储年月日
    int year,month,day;


    String HU, HOTELNAME, PRICE, ROOMTYPE, ADDRESS, HDES, RDES;

    String username;
    String dd = "";

    String num_room = "0";

    ArrayList<String> array1;

    int spend;

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
            Toast.makeText(getApplicationContext(), "Succeccfully", Toast.LENGTH_SHORT).show();

        }
        else if(i == 1){
            Toast.makeText(getApplicationContext(), "Wrong Connection", Toast.LENGTH_SHORT).show();

        }

        else if(i == 200){
            Toast.makeText(getApplicationContext(), "Lowest price must be smaller than the Highest price", Toast.LENGTH_SHORT).show();

        }
        else if(i == 2){
            Toast.makeText(getApplicationContext(), "Wrong Format", Toast.LENGTH_SHORT).show();

        }

        else if(i == 3){
            Toast.makeText(getApplicationContext(), "No enough room", Toast.LENGTH_SHORT).show();

        }

        else if(i == 73){

            dialogUtils.showDialog(Hr_mineorder.this, "Paying", DialogUtils.TYPE_PROGRESS, new DialogUtils.OnCancelClickListener() {
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

                                    int i = Util.make_order(conn, username, HU, HOTELNAME,ROOMTYPE, dd,
                                            Integer.parseInt(number.getText().toString()), spend);
                               // 0：下单成功，，1：连接错误，2：格式错误 3：剩余房间不足
                                        mHandler.sendEmptyMessage(i);



                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    thread.start();
                }
            }, 1200);

        }

        else if(i == 99){

            dialogUtils.showDialog(Hr_mineorder.this, "Like", DialogUtils.TYPE_PROGRESS, new DialogUtils.OnCancelClickListener() {
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

                                int i = Util.add_into_like_list(conn,username, HOTELNAME,ROOMTYPE);
                                // 0：收藏成功，1：连接错误
                                mHandler.sendEmptyMessage(i);



                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    thread.start();
                }
            }, 1200);

        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.hr_cus_order);
        showToolBar(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);




        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");

        username = bundle.getString("username");
        HOTELNAME = bundle.getString("hotelname");
        PRICE = bundle.getString("price");
        ROOMTYPE = bundle.getString("type");
        ADDRESS = bundle.getString("address");
        HDES = bundle.getString("hdes");
        RDES = bundle.getString("rdes");

        HU = bundle.getString("hu");

        initView();

        // Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();




        RadioGroup radiogroup = (RadioGroup)findViewById(R.id.cusmain);

        radiogroup.setOnCheckedChangeListener((group, checkedId) -> {

            // TODO Auto-generated method stub
            if(checkedId==R.id.hr_cus_hotel)
            {

                Bundle bundle1 = new Bundle();  //得到bundle对象
                bundle.putString("username", username);  //key-"sff",通过key得到value-"value值"(String型)


                hopToActivity(Hr_hotels.class, bundle1);
                finish();
            }else if(checkedId==R.id.hr_cus_mine)
            {
                Bundle bundle1 = new Bundle();  //得到bundle对象
                bundle.putString("username", username);
                hopToActivity(Hr_mine.class, bundle1);
                //finish();
            }

        });


        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //获取选中的年月日
                Hr_mineorder.this.year = year;
                //月份是从0开始的
                Hr_mineorder.this.month = (monthOfYear+1);
                Hr_mineorder.this.day = dayOfMonth;
                //弹窗显示
                dd = Hr_mineorder.this.year+"-"+ Hr_mineorder.this.month+"-"+Hr_mineorder.this.day;
                 Toast.makeText(getApplicationContext(), dd, Toast.LENGTH_SHORT).show();


            }
        });

    }

    @OnClick({R.id.search_hotel})
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {



            case R.id.search_hotel:

                if(dd == ""){
                     Toast.makeText(getApplicationContext(), "You must pick a time by yourself", Toast.LENGTH_SHORT).show();

                }

                else{

                    if(number.getText().toString().isEmpty() ){

                        Toast.makeText(getApplicationContext(), "You must enter the number of room", Toast.LENGTH_SHORT).show();




                    }
                    else{

                         spend = Integer.parseInt(PRICE) * Integer.parseInt(number.getText().toString());
                        String sp = String.valueOf(spend);
                        new SuperDialog.Builder(this).setRadius(10)
                                .setAlpha(1f)
                                .setTitle("Confirm Order").setMessage("This gonna cost  " + sp)
                                .setPositiveButton("Yes", new SuperDialog.OnClickPositiveListener() {
                                    @Override
                                    public void onClick(View v) {

                                        mHandler.sendEmptyMessage(73);

                                    }
                                })
                                .setNegativeButton("Cancle", null)
                                .build();

                    }

                }


                break;

        }


    }


    @OnClick({R.id.Add_Likelist})
    public void Add_Likelist_onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.Add_Likelist:
                mHandler.sendEmptyMessage(99);


        }


    }

    private void initView(){

        Drawable drawableFirst = getResources().getDrawable(R.drawable.vector_drawable_bin);
        drawableFirst.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        jiu.setCompoundDrawables(null, drawableFirst, null, null);//只放上面

        Drawable drawableSecond = getResources().getDrawable(R.drawable.vector_drawable_mine_1);
        drawableSecond.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        mine.setCompoundDrawables(null, drawableSecond, null, null);//只放上面

        hotelname.setText(HOTELNAME);
        price.setText(PRICE);
        type.setText(ROOMTYPE);
        address111.setText(ADDRESS);
        hdes.setText(HDES);
        rdes.setText(RDES);



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
