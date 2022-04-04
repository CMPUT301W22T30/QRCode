package com.example.qrcodeteam30.controllerclass;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

/**
 * A wrapper class for the method that generating device unique id
 */
public class DeviceUniqueIDController {
    /**
     * Get a unique ID for each device
     * @param context
     * @return
     */
    public static String getDeviceUniqueID(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("UniqueIDStorage", Context.MODE_PRIVATE);
        String str = mPrefs.getString("UniqueID", null);

        if (str == null) {
            SharedPreferences.Editor prefEditor = mPrefs.edit();
            str = UUID.randomUUID().toString();
            prefEditor.putString("UniqueID", str);
            prefEditor.commit();
        }
        return str;
    }
}
