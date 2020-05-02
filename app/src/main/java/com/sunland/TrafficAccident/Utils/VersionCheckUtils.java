package com.sunland.TrafficAccident.Utils;

import android.os.Build;

public class VersionCheckUtils {

    public static boolean isAboveVersion(int cur_version) {
        if (Build.VERSION.SDK_INT >= cur_version) {
            return true;
        } else {
            return false;
        }

    }
}
