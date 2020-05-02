package com.sunland.TrafficAccident.Db;

//import android.arch.persistence.room.Room;
import android.content.Context;

import androidx.room.Room;

/**
 * Created By YPT on 2019/2/12/012
 * project name: parkeSystem
 */
public class OpenRoomDBHelper {
    private static final String DB_FLAG = "Mdb";
    private static OpenRoomDBHelper myOpenRoomDBHelper;

    private static MyDataBase mDb;


    private OpenRoomDBHelper(Context context) {
        mDb = Room.databaseBuilder(context, MyDataBase.class, DB_FLAG).build();
    }

    public static OpenRoomDBHelper createDBHelper(Context context) {
        if (myOpenRoomDBHelper == null) {
            synchronized (OpenRoomDBHelper.class) {
                if (myOpenRoomDBHelper == null) {
                    myOpenRoomDBHelper = new OpenRoomDBHelper(context);
                }
            }
        }
        return myOpenRoomDBHelper;
    }

    public MyDataBase getDb() {
        return mDb;
    }


    public void closeDb() {
        mDb.close();
        myOpenRoomDBHelper = null;
    }

}
