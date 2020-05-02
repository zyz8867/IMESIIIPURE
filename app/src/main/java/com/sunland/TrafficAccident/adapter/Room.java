package com.sunland.TrafficAccident.adapter;

public class Room {


    private  String room_type;

    private  String price;

    private  String amount;

    private  String  description;




    public  Room(String room_type, String price,
                 String amount, String description){

        this.room_type = room_type;
        this.price = price;
        this.amount = amount;
        this.description = description;

    }



    public String getRoom_type(){
        return room_type;
    }

    public String getPrice(){
        return price;
    }

    public String getAmount(){
        return amount;
    }

    public String getDescription(){
        return description;
    }


}
