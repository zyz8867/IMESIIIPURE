package com.sunland.TrafficAccident.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sunland.TrafficAccident.R;
import com.sunland.TrafficAccident.view_controller.HR.OWNER.Hr_myhotel;
import com.sunland.TrafficAccident.view_controller.HR.OWNER.Hr_room;

import java.util.List;

public class RoomAdapter extends ArrayAdapter<Room> {
    private View.OnClickListener listener;
    private int resourceId;



    public RoomAdapter(Context context, int resource, List<Room> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     final View.OnClickListener listener;

        Room room = getItem(position);//获取当前项的实例
        View view;
        ViewHolder viewHolder;

        //View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();


            viewHolder.price = (TextView) view.findViewById(R.id.Prize);
            viewHolder.type = (TextView) view.findViewById(R.id.Type);
            viewHolder.description = (TextView) view.findViewById(R.id.Description);
            viewHolder.amount = (TextView) view.findViewById(R.id.Amount);





            view.setTag(viewHolder);//保存
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//取出
        }

        viewHolder.price.setText(room.getPrice());
        viewHolder.type.setText(room.getRoom_type());
        viewHolder.description.setText(room.getDescription());
        viewHolder.amount.setText(room.getAmount());



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
        TextView price;
        TextView type;
        TextView description;
        TextView amount;




    }


}
