package com.example.nearby;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import com.example.nearby.freegments.AllFragment;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

public class MyLocationService extends BroadcastReceiver {


    public static final String ACTION_PROCESS_UPDET="com.example.nearby";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent!=null){

            final String action=intent.getAction();
            if (ACTION_PROCESS_UPDET.equals(action)){
                LocationResult result=LocationResult.extractResult(intent);
                if (result!=null){
                    Location location =result.getLastLocation();
                    LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                    try {
                        AllFragment.getInstance().location(latLng);
                    }catch (Exception ex){

                    }
                }
            }
        }

    }
}
