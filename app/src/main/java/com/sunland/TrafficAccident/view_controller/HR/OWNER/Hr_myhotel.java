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

import com.sunland.TrafficAccident.Bean.BaseRequest;
import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.Utils.DialogUtils;
import com.sunland.TrafficAccident.Utils.VersionCheckUtils;
import com.sunland.TrafficAccident.view_controller.Ac_base_query;
import com.sunland.TrafficAccident.view_controller.Frg_dialog;
import com.sunland.TrafficAccident.view_controller.HR.CUSTOMER.Hr_mineorder;
import com.sunland.TrafficAccident.view_controller.HR.Hr_register;
import com.sunland.TrafficAccident.view_controller.HR.Util;
import com.sunlandgroup.def.bean.result.ResultBase;

import java.sql.Connection;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class Hr_myhotel  extends Ac_base_query implements Frg_dialog.Callback{


    FragmentManager fm;

    @BindView(R.id.hr_order)
    RadioButton myorder;

    @BindView(R.id.hr_hotel)
    RadioButton myhotel;



    @BindView(R.id.username1)
    TextView username1;

    @BindView(R.id.hotelname1)
    TextView hotelname;

    @BindView(R.id.country1)
    TextView country;

    @BindView(R.id.province1)
    TextView province;

    @BindView(R.id.city1)
    TextView city;

    @BindView(R.id.street1)
    TextView street;

    @BindView(R.id.description1)
    TextView description;



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
        else if(i == 2){
            Toast.makeText(getApplicationContext(), "Wrong Format", Toast.LENGTH_SHORT).show();

        }

        else if(i == 730){

            if(array1 != null){
                hotelname1 = array1.get(1);

                hotelname.setText(array1.get(1));
                country.setText(array1.get(2));
                province.setText(array1.get(3));
                city.setText(array1.get(4));
                street.setText(array1.get(5));
                description.setText(array1.get(6));



            }

        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.hr_hotel);
        showToolBar(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //fm = getSupportFragmentManager();


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");

        username = bundle.getString("username");

        username1.setText(username);

       // Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();


        initView();

        //Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();




        RadioGroup radiogroup = (RadioGroup)findViewById(R.id.owner_main);

        radiogroup.setOnCheckedChangeListener((group, checkedId) -> {

            // TODO Auto-generated method stub
            if(checkedId==R.id.hr_order)
            {



                Bundle bundle1 = new Bundle();  //得到bundle对象
                bundle1.putString("username", username);  //key-"sff",通过key得到value-"value值"(String型)


                hopToActivity(Hr_orders.class, bundle1);
                finish();



            }else if(checkedId==R.id.hr_hotel)
            {



            }

        });






    }

    @OnClick({R.id.hotel_submit1, R.id.Edit_Room})
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.Edit_Room:


                Bundle bundle = new Bundle();  //得到bundle对象
                bundle.putString("username", username);  //key-"sff",通过key得到value-"value值"(String型)
                bundle.putString("hotelname", hotelname1);  //key-"iff",value-175

                hopToActivity(Hr_room.class, bundle);


                break;

            case R.id.hotel_submit1:



                dialogUtils.showDialog(Hr_myhotel.this, "Handling", DialogUtils.TYPE_PROGRESS, new DialogUtils.OnCancelClickListener() {
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



                                    //0：编辑成功，1：连接错误，2：格式错误
                                   int i = Util.edit_hotel(conn, username, hotelname.getText().toString(),
                                           country.getText().toString(),province.getText().toString(),
                                           city.getText().toString()
                                   ,street.getText().toString(),description.getText().toString());



                                    //HANDLE(LOGIN);

                                     mHandler.sendEmptyMessage(i);

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





    private void initView(){

        Drawable drawableFirst = getResources().getDrawable(R.drawable.vector_drawable_order);
        drawableFirst.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        myorder.setCompoundDrawables(null, drawableFirst, null, null);//只放上面

        Drawable drawableSecond = getResources().getDrawable(R.drawable.vector_drawable_mine_1);
        drawableSecond.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        myhotel.setCompoundDrawables(null, drawableSecond, null, null);//只放上面


        /////////////////////////////////////////////////////////////////

        dialogUtils.showDialog(Hr_myhotel.this, "Initializing", DialogUtils.TYPE_PROGRESS, new DialogUtils.OnCancelClickListener() {
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


                            //一个含有7个元素的arraylist，
                            // 数据依次为 username, hotel_name, country, province, city, street, description.
                            // 如果没有数据，将return null
                             array1 = Util.view_hotel(conn, username);



                            //HANDLE(LOGIN);

                            mHandler.sendEmptyMessage(730);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                thread.start();
            }
        }, 1800);




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