package com.sunland.TrafficAccident.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sunland.TrafficAccident.R;

import org.w3c.dom.Text;

import java.util.List;

public class HorderAdapter extends ArrayAdapter<Horder> {
    private View.OnClickListener listener;
    private int resourceId;



    public HorderAdapter(Context context, int resource, List<Horder> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View.OnClickListener listener;

        Horder horder = getItem(position);//获取当前项的实例
        View view;
        ViewHolder viewHolder;

        //View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();


            viewHolder.customer_name = (TextView) view.findViewById(R.id.cus_name);
            viewHolder.room_type = (TextView) view.findViewById(R.id.Type1);
            viewHolder.order_day = (TextView) view.findViewById(R.id.order_day);
            viewHolder.num_room = (TextView) view.findViewById(R.id.num_room);
            viewHolder.order_made_time = (TextView) view.findViewById(R.id.order_made_time);
            viewHolder.price = (TextView) view.findViewById(R.id.price);




            view.setTag(viewHolder);//保存
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//取出
        }


        viewHolder.customer_name.setText(horder.getCustomer_name());
        viewHolder.room_type.setText(horder.getRoom_type());
        viewHolder.order_day.setText(horder.getOrder_day());
        viewHolder.num_room.setText(horder.getNum_room());
        viewHolder.order_made_time.setText(horder.getOrder_made_time());
        viewHolder.price.setText(horder.getPrice());










//        ((TextView) view.findViewById(R.id.Prize)).setText(room.getPrice());
//        ((TextView) view.findViewById(R.id.Type)).setText(room.getRoom_type());
//        ((TextView) view.findViewById(R.id.Description)).setText(room.getDescription());
//       ((TextView) view.findViewById(R.id.Amount)).setText(room.getAmount());

//        viewHolder.edit.setOnClickListener(listener);
//        //通过setTag 将被点击控件所在条目的位置传递出去
//        viewHolder.edit.setTag(position);





        return view;
    }

    private class ViewHolder {
        TextView customer_name;
        TextView room_type;
        TextView order_day;
        TextView num_room;
        TextView order_made_time;
        TextView price;




    }


}