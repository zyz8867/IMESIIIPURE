package com.sunland.TrafficAccident.adapter;

public class Horder {

    private String customer_name;
    private String hotelowner_name;
    private String hotel_name;
    private String room_type;
    private String order_day;
    private String num_room;
    private String price;
    private String order_made_time;


    public  Horder(String customer_name, String hotelowner_name,
                 String hotel_name, String room_type,
                   String order_day, String num_room, String price,
                   String order_made_time){

        this.customer_name = customer_name;
        this.hotel_name = hotel_name;
        this.hotelowner_name = hotelowner_name;
        this.room_type = room_type;
        this.order_day = order_day;
        this.num_room = num_room;
        this.price = price;
        this.order_made_time = order_made_time;

    }

    public String getCustomer_name(){
        return customer_name;
    }

    public String getHotel_name(){
        return hotel_name;
    }

    public String getHotelowner_name(){
        return hotelowner_name;
    }

    public String getRoom_type(){
        return  room_type;
    }

    public String getOrder_day(){
        return  order_day;
    }

    public String getNum_room(){
        return  num_room;
    }

    public String getPrice(){
        return  price;
    }

    public String getOrder_made_time(){
        return order_made_time;
    }


}
