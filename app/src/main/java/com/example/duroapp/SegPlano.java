package com.example.duroapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import static android.content.ContentValues.TAG;

public class SegPlano extends Service {
    Firebase firebaseReference;
    //String texto;


    public SegPlano() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Servicio creado...");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "Servicio iniciado...");




        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Servicio destruido...");
    }




}