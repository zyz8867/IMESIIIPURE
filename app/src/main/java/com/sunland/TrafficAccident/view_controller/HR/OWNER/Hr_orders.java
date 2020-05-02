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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.sunland.TrafficAccident.Bean.BaseRequest;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.DialogUtils;
import com.sunland.TrafficAccident.Utils.VersionCheckUtils;
import com.sunland.TrafficAccident.adapter.Horder;
import com.sunland.TrafficAccident.adapter.HorderAdapter;
import com.sunland.TrafficAccident.adapter.Hotel;
import com.sunland.TrafficAccident.adapter.Room;
import com.sunland.TrafficAccident.adapter.RoomAdapter;
import com.sunland.TrafficAccident.view_controller.Ac_base_query;
import com.sunland.TrafficAccident.view_controller.Frg_dialog;
import com.sunland.TrafficAccident.view_controller.HR.Util;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Hr_orders  extends Ac_base_query implements Frg_dialog.Callback{


    @BindView(R.id.hr_order)
    RadioButton my_order1;

    @BindView(R.id.hr_hotel)
    RadioButton my_hotel1;

    @BindView(R.id.hotel_order_list)
    ListView listviewroom3;


    List<Horder> horders = new ArrayList<>();

    String username;


   // ArrayList<String> array1;

    ArrayList<ArrayList<String>> array1_horder;

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
        else if(i == 2){
            Toast.makeText(getApplicationContext(), "Wrong Format", Toast.LENGTH_SHORT).show();

        }

        else if(i == 730){

            // String[][] test = (String [][])array1_room.toArray(new String[0][0]);
            if(array1_horder != null){
                //int i = 9;
                //Toast.makeText(Hr_room.this, array1_room.get(0).get(2), Toast.LENGTH_LONG).show();

                //Toast.makeText(Hr_room.this, String.valueOf(array1_room.size()), Toast.LENGTH_LONG).show();

                //cats.add(new Room("double","11","22","qqrgtrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrhrhrergf"));

                for(int c = 0; c < array1_horder.size(); c++){
                    horders.add(new Horder(array1_horder.get(c).get(0).toString(),array1_horder.get(c).get(1).toString(),
                            array1_horder.get(c).get(2).toString(), array1_horder.get(c).get(3).toString(),
                            array1_horder.get(c).get(4).toString(), array1_horder.get(c).get(5).toString(),
                            array1_horder.get(c).get(6).toString(), array1_horder.get(c).get(7).toString()));
                }

                HorderAdapter adapter = new HorderAdapter(Hr_orders.this, R.layout.hr_hotel_order_item, horders);
                ((ListView) findViewById(R.id.hotel_order_list)).setAdapter(adapter);

            }

        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.hr_orders);
        showToolBar(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //fm = getSupportFragmentManager();


        Intent intent = getIntent();
        Bundle bundle1 = intent.getBundleExtra("bundle");

        username = bundle1.getString("username");



        initView();

        //Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();




        RadioGroup radiogroup = (RadioGroup)findViewById(R.id.cusmain);

        radiogroup.setOnCheckedChangeListener((group, checkedId) -> {

            // TODO Auto-generated method stub
            if(checkedId==R.id.hr_order)
            {


            }else if(checkedId==R.id.hr_hotel)
            {


                Bundle bundle = new Bundle();  //得到bundle对象
                bundle.putString("username", username);  //key-"sff",通过key得到value-"value值"(String型)


                hopToActivity(Hr_myhotel.class, bundle);
                finish();

            }

        });




        listviewroom3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = position;
                Horder horder = horders.get(position);




//hopToActivity(Hr_mineorder.class);


                mHandler.sendEmptyMessage(99);



            }
        });

    }






    private void initView(){

        Drawable drawableFirst = getResources().getDrawable(R.drawable.vector_drawable_order);
        drawableFirst.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        my_order1.setCompoundDrawables(null, drawableFirst, null, null);//只放上面

        Drawable drawableSecond = getResources().getDrawable(R.drawable.vector_drawable_mine_1);
        drawableSecond.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        my_hotel1.setCompoundDrawables(null, drawableSecond, null, null);//只放上面


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Connection conn;
                    conn = Util.openConnection(URL, USER, PASSWORD);



                    array1_horder = Util.my_order(conn, username);
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