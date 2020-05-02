package com.sunland.TrafficAccident.adapter;

//username(旅馆主名字), hotel_name,
// country, province, city, street, hotel_description, roomtype, price, amount, description
public class Hotel {

    private String hotel_user;
    private String hotel_name;

    private String country;
    private String province;
    private String city;
    private String street;

    private String hotel_description;
    private String room_type;
    private String price;
    private String amount;
    private String description;

    public  Hotel(String hotel_user, String hotel_name, String country, String province, String city, String street,
                  String hotel_description, String room_type, String price, String amount, String description){

        this.hotel_user = hotel_user;
        this.hotel_name = hotel_name;
        this.country = country;
        this.province = province;
        this.city = city;
        this.street = street;
        this.hotel_description = hotel_description;
        this.room_type = room_type;
        this.price = price;
        this.amount = amount;
        this.description = description;

    }

    public String getHotel_user(){
        return hotel_user;
    }

    public String getHotel_name(){
        return hotel_name;
    }

    public String getCountry(){
        return country;
    }

    public String getProvince(){
        return province;
    }

    public String getCity(){
        return city;
    }

    public String getStreet(){
        return street;
    }

    public String getHotel_description(){
        return hotel_description;
    }

    public String getPrice(){
        return price;
    }

    public String getRoom_type(){
        return room_type;
    }

    public String getDescription(){
        return description;
    }

    public String getAmount(){
        return amount;
    }


}
