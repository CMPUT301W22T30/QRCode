package com.example.qrcodeteam30.controllerclass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class DeviceUniqueIDController {
    /**
     * Get a unique ID for each device
     * @param context
     * @param activity
     * @return
     */
    public static String getDeviceUniqueID(Context context, Activity activity) {
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
