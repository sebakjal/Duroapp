package com.example.duroapp;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(SensorRestarterBroadcastReceiver.class.getSimpleName(), "Service Stops! Oops!!!!");
            context.startService(new Intent(context, MainService.class));

        }
    }


