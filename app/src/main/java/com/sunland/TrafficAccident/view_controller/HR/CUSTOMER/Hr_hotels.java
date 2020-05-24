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
import com.sunland.TrafficAccident.adapter.Hotel;
import com.sunland.TrafficAccident.adapter.HotelAdapter;
import com.sunland.TrafficAccident.adapter.Room;
import com.sunland.TrafficAccident.adapter.RoomAdapter;
import com.sunland.TrafficAccident.view_controller.Ac_base_query;
import com.sunland.TrafficAccident.view_controller.Frg_dialog;
import com.sunland.TrafficAccident.view_controller.HR.OWNER.Hr_addroom;
import com.sunland.TrafficAccident.view_controller.HR.OWNER.Hr_room;
import com.sunland.TrafficAccident.view_controller.HR.Util;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Hr_hotels  extends Ac_base_query implements Frg_dialog.Callback{

    @BindView(R.id.hr_cus_hotel)
    RadioButton jiu;

    @BindView(R.id.hr_cus_mine)
    RadioButton mine;

    @BindView(R.id.highest)
            EditText high;

    @BindView(R.id.lowest)
    EditText low;

    @BindView(R.id.key)
    EditText key;
    int h = 100000;
    int l = 0;
    String a = "";

    String HU,NAME, PRICE, TYPE, ADDRESS, HDES, RDES;


    ArrayList<ArrayList<String>> hotel_array;

    @BindView(R.id.lists)
    ListView listviewroom1;


    List<Hotel> hotels = new ArrayList<>();

    String username;
    String hotelname1;

    ArrayList<String> array1;



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
            Toast.makeText(getApplicationContext(), "Wrong Connection", Toast.LENGTH_SHORT).show();

        }

        else if (i == 99){
            final String[] strings = {"View details and order the room"};

            new SuperDialog.Builder(this)
                    //.setAlpha(0.5f)
                    //.setGravity(Gravity.CENTER)
                    //.setTitle("上传头像", ColorRes.negativeButton)
                    .setCanceledOnTouchOutside(false)
                    .setItems(strings, new SuperDialog.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {

                            if(position == 0){
                                Bundle bundle = new Bundle();  //得到bundle对象
                                bundle.putString("username", username);  //key-"sff",通过key得到value-"value值"(String型)
                                //String NAME, PRICE, TYPE, ADDRESS, HDES, RDES;
                                bundle.putString("hotelname", NAME);
                                bundle.putString("price", PRICE);
                                bundle.putString("type", TYPE);
                                bundle.putString("address", ADDRESS);
                                bundle.putString("hdes", HDES);
                                bundle.putString("rdes", RDES);
                                bundle.putString("hu", HU);

                                hopToActivity(Hr_mineorder.class, bundle);
                                finish();

                            }




                        }
                    })
                    .setNegativeButton("Cancle", null)
                    .build();




        }

        else if(i == 200){
            Toast.makeText(getApplicationContext(), "Lowest price must be smaller than the Highest price", Toast.LENGTH_SHORT).show();

        }
        else if(i == 2){
            Toast.makeText(getApplicationContext(), "Wrong Format", Toast.LENGTH_SHORT).show();

        }

        else if(i == 730){

            if(hotel_array != null){
                //hotelname1 = array1.get(1);
                hotels.clear();
                for(int c = 0; c < hotel_array.size(); c++){
                    hotels.add(new Hotel(hotel_array.get(c).get(0), hotel_array.get(c).get(1), hotel_array.get(c).get(2), hotel_array.get(c).get(3),
                            hotel_array.get(c).get(4), hotel_array.get(c).get(5),
                            hotel_array.get(c).get(6), hotel_array.get(c).get(7), hotel_array.get(c).get(8),hotel_array.get(c).get(9),
                            hotel_array.get(c).get(10)));
                }

                HotelAdapter adapter = new HotelAdapter(Hr_hotels.this, R.layout.hr_customer_hotel_item, hotels);
                ((ListView) findViewById(R.id.lists)).setAdapter(adapter);


            }


            else{

                List<Hotel> hotels = new ArrayList<>();


                HotelAdapter adapter = new HotelAdapter(Hr_hotels.this, R.layout.hr_customer_hotel_item, hotels);
                ((ListView) findViewById(R.id.lists)).setAdapter(adapter);
                 Toast.makeText(getApplicationContext(), "No Result", Toast.LENGTH_SHORT).show();

            }

        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.hr_cus_hotel);
        showToolBar(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //fm = getSupportFragmentManager();


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");

        username = bundle.getString("username");

        //username1.setText(username);

        initView();

       // Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();




        RadioGroup radiogroup = (RadioGroup)findViewById(R.id.cusmain);

        radiogroup.setOnCheckedChangeListener((group, checkedId) -> {

            // TODO Auto-generated method stub
            if(checkedId==R.id.hr_cus_hotel)
            {



                //Toast.makeText(getApplicationContext(), "恭喜您,选择正确!",Toast.LENGTH_LONG).show();
            }else if(checkedId==R.id.hr_cus_mine)
            {
                Bundle bundle1 = new Bundle();
                bundle1.putString("username", username);
                hopToActivity(Hr_mine.class, bundle1);
                finish();
            }

        });




        listviewroom1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = position;
                Hotel hotel = hotels.get(position);

                HU = hotel.getHotel_user();
                NAME = hotel.getHotel_name();
                PRICE = hotel.getPrice();
                TYPE = hotel.getRoom_type();
                ADDRESS = hotel.getStreet() + "  " + hotel.getCity() + "  " + hotel.getProvince()
                + "  " + hotel.getCountry();
                HDES = hotel.getHotel_description();
                RDES = hotel.getDescription();


//hopToActivity(Hr_mineorder.class);


                mHandler.sendEmptyMessage(99);




            }
        });
//
   }

    @OnClick({R.id.search_hotel})
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.search_hotel:

                if(key.getText().toString().equals("")){

                }
                else{
                    a = key.getText().toString();
                }

                if(!low.getText().toString().equals("") && !high.getText().toString().equals("")) {
                    l = Integer.parseInt(low.getText().toString());

                    h = Integer.parseInt(high.getText().toString());
                }

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Connection conn;
                            conn = Util.openConnection(URL, USER, PASSWORD);

                            if(l >= h){
                                mHandler.sendEmptyMessage(200);

                            }
                            else {

                                hotel_array = Util.filter_hotel(conn, a, l, h);


                                //HANDLE(LOGIN);

                                mHandler.sendEmptyMessage(730);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                thread.start();

                break;


        }

    }



    private void initView(){

        Drawable drawableFirst = getResources().getDrawable(R.drawable.vector_drawable_bin);
        drawableFirst.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        jiu.setCompoundDrawables(null, drawableFirst, null, null);//只放上面

        Drawable drawableSecond = getResources().getDrawable(R.drawable.vector_drawable_mine_1);
        drawableSecond.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        mine.setCompoundDrawables(null, drawableSecond, null, null);//只放上面


        /////////////////////////////////////////////////////////////////






    }



//////

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
