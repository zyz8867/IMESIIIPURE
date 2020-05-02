package com.sunland.TrafficAccident.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created By YPT on 2019/3/19/019
 * project name: parkeSystem
 */
public class AssetsReader {

    private static AssetsReader assetsReader;
    private Context context;

    private AssetsReader(Context context) {
        this.context = context;
    }

    public static AssetsReader getInstance(Context context) {
        if (assetsReader == null) {
            synchronized (AssetsReader.class) {
                if (assetsReader == null) {
                    assetsReader = new AssetsReader(context);
                }
            }
        }
        return assetsReader;
    }

    public SQLiteDatabase readAssetDb(String dirPath, String dbName) {
        File file = new File(dirPath, dbName);

        if (file.exists()) {
            return SQLiteDatabase.openOrCreateDatabase(file, null);
        } else {
            try {
                InputStream is = context.getAssets().open(dbName);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                int count = 0;
                while ((count = is.read(bytes)) > 0) {
                    fos.write(bytes, 0, count);
                }
                fos.flush();
                fos.close();
                is.close();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return SQLiteDatabase.openOrCreateDatabase(file, null);
        }
    }

}
