package com.example.duroapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import static android.content.ContentValues.TAG;

public class SegPlano2 extends Service {
    MediaPlayer mp;
    private Camera camara;
    private CameraManager micamara;
    private String idCamara;
    public int datorecivido = 1;

    private Firebase f = new Firebase("https://duroapp-50d3f.firebaseio.com/data/type");
    private ValueEventListener handler;


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "Creado Servidor", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "Servicio iniciado...");
        //Toast.makeText(getApplicationContext(), "ONSTART2", Toast.LENGTH_SHORT).show();

       /* Intent intent2 = new Intent(SegPlano2.this, MyServiceTest.class);
        startService(intent2);
*/
        handler = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                //valor = texto.toString();
                Toast.makeText(getApplicationContext(), "pene"+text, Toast.LENGTH_SHORT).show();

                try {
                    if (((text).equals("duro"))) {
                        Intent intent = new Intent(SegPlano2.this, MyIntentService2.class);
                        startService(intent);
                        Intent intent2 = new Intent(SegPlano2.this, MyIntentService.class);
                        startService(intent2);


                        Toast.makeText(getApplicationContext(), "ENTRO", Toast.LENGTH_SHORT).show();

                        Thread.sleep(1000);
                        f.setValue("blando");
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        };

        f.addValueEventListener(handler);


        return START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //startService (new Intent(this, MainActivity.class));
        //Intent intent = new Intent(SegPlano2.this, SegPlano3.class);
        //startService(intent);
     /*   Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
        Log.d(TAG, "Servicio destruido...");
        Toast.makeText(getApplicationContext(), "servicio destruido", Toast.LENGTH_SHORT).show();
        */
        Intent intent2 = new Intent(SegPlano2.this, MyServiceTest.class);
        startService(intent2);


    }

}