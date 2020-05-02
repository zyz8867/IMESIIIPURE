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

import static com.amap.api.maps.model.BitmapDescriptorFactory.getContext;

public class HotelAdapter  extends ArrayAdapter<Hotel>{

    private View.OnClickListener listener;
    private int resourceId;



    public HotelAdapter(Context context, int resource, List<Hotel> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final View.OnClickListener listener;

        Hotel hotel = getItem(position);//获取当前项的实例
        View view;
      ViewHolder viewHolder;

        //View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new HotelAdapter.ViewHolder();


            viewHolder.price = (TextView) view.findViewById(R.id.Price);
            viewHolder.type = (TextView) view.findViewById(R.id.Type1);
            viewHolder.name = (TextView) view.findViewById(R.id.hotel_name);
            viewHolder.address = (TextView) view.findViewById(R.id.address1);
            viewHolder.description = (TextView) view.findViewById(R.id.description11);





            view.setTag(viewHolder);//保存
        } else {
            view = convertView;
            viewHolder = (HotelAdapter.ViewHolder) view.getTag();//取出
        }

        viewHolder.price.setText(hotel.getPrice());
        viewHolder.type.setText(hotel.getRoom_type());
        viewHolder.name.setText(hotel.getHotel_name());
        viewHolder.address.setText(hotel.getStreet() + " " + hotel.getCity() + " " + hotel.getProvince() + " " + hotel.getCountry());

        viewHolder.description.setText(hotel.getDescription());







        return view;
    }


    private class ViewHolder {


        TextView price;
        TextView type;
        TextView name;
        TextView address;
        TextView description;




    }



}
