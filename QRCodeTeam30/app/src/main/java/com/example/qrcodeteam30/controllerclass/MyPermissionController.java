package com.example.qrcodeteam30.controllerclass;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Handling asking permission
 */
public class MyPermissionController {
    private Context context;
    private Activity activity;

    public MyPermissionController(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    /**
     * Check if there is location permission
     * @return true if there is, false if there isn't
     */
    public boolean hasLocationPermission() {
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }


    /**
     * Request location permission
     */
    public void requestLocationPermission() {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        }, 1);
    }


    /**
     * Check if there is location and writing to storage permission
     * @return true if there is, false if there isn't
     */
    public boolean hasWritingLocationPermission() {
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED);
    }


    /**
     * Request location and writing to storage permission
     */
    public void requestWritingLocationPermission() {
        ActivityCompat.requestPermissions(activity,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
    }
}
