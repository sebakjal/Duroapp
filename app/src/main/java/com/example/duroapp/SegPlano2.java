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
    public void onTaskRemoved(Intent rootIntent) {
        startService (new Intent(this, MainActivity.class));
        Log.d(TAG, "Servicio destruido...");
        Toast.makeText(getApplicationContext(), "servicio destruido", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "Creado Servidor", Toast.LENGTH_SHORT).show();


        handler = new ValueEventListener() {

           @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               String text = dataSnapshot.getValue(String.class);
               //valor = texto.toString();
               Toast.makeText(getApplicationContext(), "pene"+text, Toast.LENGTH_SHORT).show();

               try {
                   if (((text).equals("duro"))) {
                       //Intent intent = new Intent(SegPlano2.this, MyIntentService2.class);
                       //startService(intent);


                       ///////
                       ///////
                       micamara = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                       // Asignacion camara dispositivo flash
                       try {
                           idCamara = micamara.getCameraIdList()[0];
                       } catch (Exception e) {
                           e.printStackTrace();
                       }

                       for (int i = 0; i < 5; i++) {
                           try {
                               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                   Thread.sleep(250);
                                   micamara.setTorchMode(idCamara, true);
                                   camara = Camera.open();
                                   Camera.Parameters parameters = camara.getParameters();
                                   parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                                   camara.setParameters(parameters);
                               }
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                           try {
                               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                   Thread.sleep(250);
                                   micamara.setTorchMode(idCamara, false);
                                   camara.stopPreview();
                                   camara.release();

                               }
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                       }
                       ////////
                       //////



























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
    }

}