package com.sunland.TrafficAccident.view_controller.HR;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class Util {


    public static Connection openConnection(String url, String user,
                                            String password) {
        Connection conn = null;

        try {
            final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            conn = null;
        }

        return conn;
    }


    private static int operation(Connection conn, String sql) {
        if (conn == null) {
            return 1;
        }

        Statement statement = null;

        try {
            statement = conn.createStatement();
            if (statement != null) {
                statement.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 2;
        }

        return 0;
    }



    public static String login(Connection conn, String username, String pwd) {

        if (conn == null) {
            return "1";
        }

        Statement statement = null;
        ResultSet result = null;
        String usertype = "";
        try {

            statement = conn.createStatement();
            String sql_query = "select * from userinfo where username=\'" +username+ "\'AND pwd=\'"+ pwd + "\';";
            result = statement.executeQuery(sql_query);
            if(result != null){
                result.next();
                int user_typeColumnIndex = result.findColumn("usertype");
                usertype = result.getString(user_typeColumnIndex);
            }else {
                usertype = "2";
            }

        } catch (SQLException e) {
            usertype = "2";
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return usertype;
    }

    public static int register(Connection conn, String username, String pwd, String phone, String user_type, String name, String id) {
        String sql = "insert into userinfo (username, pwd, phone, usertype, name, id) values (\'" +username+ "\', \'"+pwd +"\', \'"+phone +"\', \'"+user_type + "\', \'"+name +"\', \'"+id +"\');";
        return operation(conn, sql);
    }

    public static ArrayList like_list(Connection conn, String username) {
        ArrayList<ArrayList<String>> room_info = new ArrayList<ArrayList<String>>();
        ArrayList<String> data;
        if (conn == null) {
            return null;
        }

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            String sql_query = "SELECT room.username,room.hotel_name, hotel.country, hotel.province, hotel.city, hotel.street, hotel.description, room.room, room.price, room.amount, room.description FROM  likelist, room, hotel where  CONVERT(likelist.hotel_name USING utf8) COLLATE utf8_unicode_ci = hotel.hotel_name and CONVERT(likelist.room_type USING utf8) COLLATE utf8_unicode_ci=room.room and hotel.username=room.username and likelist.username=\'"+username+"\';";
            result = statement.executeQuery(sql_query);
            if(result != null){
                result.next();
                int usernameColumnIndex = result.findColumn("username");
                int hotel_nameColumnIndex = result.findColumn("hotel_name");
                int countryColumnIndex = result.findColumn("country");
                int provinceColumnIndex = result.findColumn("province");
                int cityColumnIndex = result.findColumn("city");
                int streetColumnIndex = result.findColumn("street");
                int hotel_descriptionColumnIndex = result.findColumn("hotel.description");
                int roomColumnIndex = result.findColumn("room");
                int priceColumnIndex = result.findColumn("price");
                int amountColumnIndex = result.findColumn("amount");
                int room_descriptionColumnIndex = result.findColumn("room.description");
                while (!result.isAfterLast()) {
                    data = new ArrayList<>();
                    data.add(result.getString(usernameColumnIndex));
                    data.add(result.getString(hotel_nameColumnIndex));
                    data.add(result.getString(countryColumnIndex));
                    data.add(result.getString(provinceColumnIndex));
                    data.add(result.getString(cityColumnIndex));
                    data.add(result.getString(streetColumnIndex));
                    data.add(result.getString(hotel_descriptionColumnIndex));
                    data.add(result.getString(roomColumnIndex));
                    data.add(result.getString(priceColumnIndex));
                    data.add(result.getString(amountColumnIndex));
                    data.add(result.getString(room_descriptionColumnIndex));
                    room_info.add(data);
                    result.next();
                }
            }else {
                room_info = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            room_info = null;
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();

            }
        }
        return room_info;
    }

    public static int add_into_like_list(Connection conn, String username, String hotel_name, String room_type) {
        String sql = "insert into likelist (hotel_name, room_type, username) select \'"+hotel_name+"\', \'"+room_type+"\', \'"+username+"\' from dual where not exists ( select * from likelist where likelist.hotel_name= \'"+hotel_name+"\'AND room_type= \'"+room_type+"\' AND username =\'"+username+"\')";

        return operation(conn, sql);
    }

    public static int delete_from_like_list(Connection conn, String username, String hotel_name, String room_type) {
        String sql = "delete from likelist where hotel_name=\'" +hotel_name+ "\'AND username=\'"+username + "\' AND room_type =\'"+room_type+"\';";
        return operation(conn, sql);
    }

    public static ArrayList<String> view_hotel(Connection conn, String username) {
        ArrayList<String>  hotel_info = new ArrayList<String>();

        if (conn == null) {
            return null;
        }

        Statement statement = null;
        ResultSet result = null;
        try {

            statement = conn.createStatement();
            String sql_query = "select * from hotel where username=\'" + username + "\';";
            result = statement.executeQuery(sql_query);
            if(result != null){
                result.next();
                int usernameColumnIndex = result.findColumn("username");
                int hotel_nameColumnIndex = result.findColumn("hotel_name");
                int countryColumnIndex = result.findColumn("country");
                int provinceColumnIndex = result.findColumn("province");
                int cityColumnIndex = result.findColumn("city");
                int streetColumnIndex = result.findColumn("street");
                int descriptionColumnIndex = result.findColumn("description");

                while (!result.isAfterLast()) {
                    hotel_info.add(result.getString(usernameColumnIndex));
                    hotel_info.add(result.getString(hotel_nameColumnIndex));
                    hotel_info.add(result.getString(countryColumnIndex));
                    hotel_info.add(result.getString(provinceColumnIndex));
                    hotel_info.add(result.getString(cityColumnIndex));
                    hotel_info.add(result.getString(streetColumnIndex));
                    hotel_info.add(result.getString(descriptionColumnIndex));
                    result.next();
                }
            }else {
                hotel_info = null;
            }

        } catch (SQLException e) {
            hotel_info = null;
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();

            }
        }
        return hotel_info;
    }

    private static ArrayList<String> hotel_detail(Connection conn, String hotel_name) {
        ArrayList<String>  hotel_info = new ArrayList<String>();

        if (conn == null) {
            return null;
        }

        Statement statement = null;
        ResultSet result = null;
        try {

            statement = conn.createStatement();
            String sql_query = "select * from hotel where hotel_name=\'" + hotel_name + "\';";
            result = statement.executeQuery(sql_query);
            if(result != null){
                result.next();
                int usernameColumnIndex = result.findColumn("username");
                int hotel_nameColumnIndex = result.findColumn("hotel_name");
                int countryColumnIndex = result.findColumn("country");
                int provinceColumnIndex = result.findColumn("province");
                int cityColumnIndex = result.findColumn("city");
                int streetColumnIndex = result.findColumn("street");
                int descriptionColumnIndex = result.findColumn("description");

                while (!result.isAfterLast()) {
                    hotel_info.add(result.getString(usernameColumnIndex));
                    hotel_info.add(result.getString(hotel_nameColumnIndex));
                    hotel_info.add(result.getString(countryColumnIndex));
                    hotel_info.add(result.getString(provinceColumnIndex));
                    hotel_info.add(result.getString(cityColumnIndex));
                    hotel_info.add(result.getString(streetColumnIndex));
                    hotel_info.add(result.getString(descriptionColumnIndex));
                    result.next();
                }
            }else {
                hotel_info = null;
            }

        } catch (SQLException e) {
            hotel_info = null;
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();

            }
        }
        return hotel_info;
    }




    public static int edit_hotel(Connection conn, String username, String hotel_name, String country, String province, String city, String street, String description) {
        String sql = "";
        ArrayList<String> hotel = view_hotel(conn, username);
        if (hotel == null){
            sql = "insert into hotel (username, hotel_name, country, province, city, street, description) values (\'" +username+ "\', \'"+hotel_name +"\', \'"+country +"\', \'"+province +"\', \'"+city +"\', \'"+street +"\', \'"+description +"\');";
        }else {
            sql = "update hotel SET hotel_name='" + hotel_name+ "', country='"+ country +"', province='"+ province +"', city='"+ city +"', street='"+ street +"', description='"+ description + "' where username=\'" +username + "\';";

        }
        return operation(conn, sql);
    }



    public static int add_room(Connection conn, String username, String hotel_name, String room, int price, int amount, String description) {
        String sql = "insert into room(username, hotel_name, room, price, amount, description) values ('"+username+"','" + hotel_name+"','" + room +"',"+ price + ","+ amount +", '"+description+"');";




        return operation(conn, sql);
    }




    public static int delete_room(Connection conn, String username, String hotel_name, String room) {
        String sql = "delete from room where username='"+username+"' AND hotel_name='"+hotel_name+"' AND room='"+room+"';";
        return operation(conn, sql);
    }
    public static int edit_room(Connection conn, String username, String hotel_name, String new_room, int price, int amount, String description, String old_room) {
        String sql = "update room SET hotel_name='" + hotel_name+ "', room='"+ new_room +"', price="+ price +", amount="+ amount +", description='"+ description + "' where username=\'" +username + "\' and room='"+ old_room +"';";
        return operation(conn, sql);
    }






    public static ArrayList<ArrayList<String>> view_room(Connection conn, String username, String hotel_name) {
        ArrayList<ArrayList<String>> room_info = new ArrayList<ArrayList<String>>();
        ArrayList<String> data = new ArrayList<>();
        if (conn == null) {
            return null;
        }

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            String sql_query = "select * from room where username=\'" + username + "\' AND hotel_name='"+hotel_name + "';";
            result = statement.executeQuery(sql_query);
            if(result != null){
                result.next();
                int usernameColumnIndex = result.findColumn("username");
                int hotel_nameColumnIndex = result.findColumn("hotel_name");
                int roomColumnIndex = result.findColumn("room");
                int priceColumnIndex = result.findColumn("price");
                int amountColumnIndex = result.findColumn("amount");
                int descriptionColumnIndex = result.findColumn("description");

                while (!result.isAfterLast()) {
                    data = new ArrayList<>();
                    data.add(result.getString(usernameColumnIndex));
                    data.add(result.getString(hotel_nameColumnIndex));
                    data.add(result.getString(roomColumnIndex));
                    data.add(result.getInt(priceColumnIndex) + "");
                    data.add(result.getInt(amountColumnIndex) + "");
                    data.add(result.getString(descriptionColumnIndex));
                    room_info.add(data);
                    result.next();
                }
            }else {
                room_info = null;
            }

        } catch (SQLException e) {
            room_info = null;
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();

            }
        }
        return room_info;
    }






    public static ArrayList<ArrayList<String>> filter_hotel(Connection conn, String key_word, int min, int max) {
        ArrayList<ArrayList<String>> room_info = new ArrayList<ArrayList<String>>();
        ArrayList<String> data;
        if (conn == null) {
            return null;
        }

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            String sql_query = "SELECT room.username,room.hotel_name, hotel.country, hotel.province, hotel.city, hotel.street, hotel.description, room.room, room.price, room.amount, room.description FROM room, hotel where room.hotel_name = hotel.hotel_name and room.username = hotel.username and room.hotel_name like '%" + key_word + "%' AND room.price between "+ min + " and " +max+";";
            result = statement.executeQuery(sql_query);
            if(result != null){
                result.next();
                int usernameColumnIndex = result.findColumn("username");
                int hotel_nameColumnIndex = result.findColumn("hotel_name");
                int countryColumnIndex = result.findColumn("country");
                int provinceColumnIndex = result.findColumn("province");
                int cityColumnIndex = result.findColumn("city");
                int streetColumnIndex = result.findColumn("street");
                int hotel_descriptionColumnIndex = result.findColumn("hotel.description");
                int roomColumnIndex = result.findColumn("room");
                int priceColumnIndex = result.findColumn("price");
                int amountColumnIndex = result.findColumn("amount");
                int room_descriptionColumnIndex = result.findColumn("room.description");
                while (!result.isAfterLast()) {
                    data = new ArrayList<>();
                    data.add(result.getString(usernameColumnIndex));
                    data.add(result.getString(hotel_nameColumnIndex));
                    data.add(result.getString(countryColumnIndex));
                    data.add(result.getString(provinceColumnIndex));
                    data.add(result.getString(cityColumnIndex));
                    data.add(result.getString(streetColumnIndex));
                    data.add(result.getString(hotel_descriptionColumnIndex));
                    data.add(result.getString(roomColumnIndex));
                    data.add(result.getString(priceColumnIndex));
                    data.add(result.getString(amountColumnIndex));
                    data.add(result.getString(room_descriptionColumnIndex));
                    room_info.add(data);
                    result.next();
                }
            }else {
                room_info = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            room_info = null;
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();

            }
        }
        return room_info;
    }




    public static ArrayList<ArrayList<String>> near_hotel(Connection conn, String country, String province, String city) {
        ArrayList<ArrayList<String>> room_info = new ArrayList<ArrayList<String>>();
        ArrayList<String> data = new ArrayList<>();
        if (conn == null) {
            return null;
        }

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            String sql_query = "select * from hotel where country=\'" + country + "\' and province='" +province+ "' and city='"+city+"';";
            result = statement.executeQuery(sql_query);
            if(result != null){
                result.next();
                int usernameColumnIndex = result.findColumn("username");
                int hotel_nameColumnIndex = result.findColumn("hotel_name");
                int countryColumnIndex = result.findColumn("country");
                int provinceColumnIndex = result.findColumn("province");
                int cityColumnIndex = result.findColumn("city");
                int streetColumnIndex = result.findColumn("street");
                int descriptionColumnIndex = result.findColumn("description");

                while (!result.isAfterLast()) {
                    data = new ArrayList<>();
                    data.add(result.getString(usernameColumnIndex));
                    data.add(result.getString(hotel_nameColumnIndex));
                    data.add(result.getString(countryColumnIndex));
                    data.add(result.getString(provinceColumnIndex));
                    data.add(result.getString(cityColumnIndex));
                    data.add(result.getString(streetColumnIndex));
                    data.add(result.getString(descriptionColumnIndex));
                    room_info.add(data);
                    result.next();
                }
            }else {
                room_info = null;
            }

        } catch (SQLException e) {
            room_info = null;
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();

            }
        }
        return room_info;
    }




    public static int make_order(Connection conn, String customer, String hotel_owner, String hotel_name, String room_type, String order_day, int num_of_rooms, int price) {
        int room_total = get_room_number(conn, hotel_owner, room_type);
        int room_used = used_room(conn, hotel_owner, room_type, order_day);
        if (room_total - room_used > num_of_rooms) {
            String sql = "insert into orders (customer_name, hotelowner_name, hotel_name, room, order_day, num_of_rooms, price, order_make_time,order_status) values (\'" +customer+ "\', \'"+hotel_owner +"\', \'"+hotel_name +"\', \'"+room_type + "\', \'"+order_day +"\', "+num_of_rooms +", "+price +", now(), 'await');";
            return operation(conn, sql);
        }else {
            return 3;
        }




    }





    private static int used_room(Connection conn, String hotel_owner, String room_type, String order_day) {
        int room_used = -1;
        if (conn == null) {
            return -1;
        }

        Statement statement = null;
        ResultSet result = null;
        try {

            statement = conn.createStatement();
            String sql_query = "SELECT count(*) as used_room FROM orders WHERE order_day='" + order_day+"' AND room='"+room_type+"' AND hotelowner_name='"+hotel_owner+"';";
            result = statement.executeQuery(sql_query);
            if(result != null){
                result.next();
                int ColumnIndex = result.findColumn("used_room");
                while (!result.isAfterLast()) {
                    room_used = result.getInt(ColumnIndex);
                    result.next();
                }
            }else {
                System.out.println("No result");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();

            }
        }
        return room_used;
    }





    private static int get_room_number(Connection conn, String hotel_owner, String room_type) {
        int room_num = -1;
        if (conn == null) {
            return room_num;
        }

        Statement statement = null;
        ResultSet result = null;
        try {

            statement = conn.createStatement();
            String sql_query = "select amount from room where username=\'" + hotel_owner + "\' and room='"+room_type+"';";
            result = statement.executeQuery(sql_query);
            if(result != null){
                result.next();
                int amountColumnIndex = result.findColumn("amount");
                while (!result.isAfterLast()) {
                    room_num = result.getInt(amountColumnIndex);
                    result.next();
                }
            }else {
                System.out.println("No result");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();

            }
        }
        return room_num;
    }




    public static ArrayList<ArrayList<String>> my_order(Connection conn, String username) {
        ArrayList<ArrayList<String>> order_info = new ArrayList<ArrayList<String>>();
        ArrayList<String> data = new ArrayList<>();
        if (conn == null) {
            return null;
        }

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            String sql_query = "select * from orders where hotelowner_name='" + username +"';";
            result = statement.executeQuery(sql_query);
            if(result != null){
                result.next();
                int customer_nameColumnIndex = result.findColumn("customer_name");
                int hotelowner_nameColumnIndex = result.findColumn("hotelowner_name");
                int hotel_nameColumnIndex = result.findColumn("hotel_name");
                int roomColumnIndex = result.findColumn("room");
                int order_dayColumnIndex = result.findColumn("order_day");
                int num_of_roomsColumnIndex = result.findColumn("num_of_rooms");
                int priceColumnIndex = result.findColumn("price");
                int order_make_timeColumnIndex = result.findColumn("order_make_time");

                while (!result.isAfterLast()) {
                    data = new ArrayList<>();
                    data.add(result.getString(customer_nameColumnIndex));
                    data.add(result.getString(hotelowner_nameColumnIndex));
                    data.add(result.getString(hotel_nameColumnIndex));
                    data.add(result.getString(roomColumnIndex));
                    data.add(result.getString(order_dayColumnIndex));
                    data.add(result.getString(num_of_roomsColumnIndex));
                    data.add(result.getString(priceColumnIndex));
                    data.add(result.getString(order_make_timeColumnIndex));
                    order_info.add(data);
                    result.next();
                }
            }else {
                order_info = null;
            }

        } catch (SQLException e) {
            order_info = null;
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {
                sqle.printStackTrace();

            }
        }
        return order_info;
    }


}
