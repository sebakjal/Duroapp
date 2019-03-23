package com.example.duroapp;

import android.app.ActivityManager;
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

public class MainService extends Service {

    Camera camara;
    CameraManager micamara;
    String idCamara;
    private Firebase firebaseReference = new Firebase("https://condominiumsegurity.firebaseio.com/");

    @Override
    public IBinder onBind(Intent intent) {


        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Servicio creado...");
        super.onCreate();
        Log.d(TAG, "Servicio creado...");

            firebaseReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    try {
                        //FLASH
                        if (((value).equals("flash"))) {
                            flash();
                            Thread.sleep(1000);
                            firebaseReference.setValue("null");
                        }
                        //AUDIO
                        if (((value).equals("siren"))) {
                            siren();
                            Thread.sleep(1000);
                            firebaseReference.setValue("null");
                        }
                        //RED
                        if (((value).equals("red"))) {
                            Intent intent = new Intent(MainService.this, RedActivity.class);
                            startActivity(intent);
                            Thread.sleep(1000);
                            firebaseReference.setValue("null");
                        }
                        //ALL
                        if (((value).equals("all"))) {
                            Intent intent = new Intent(MainService.this, RedActivity.class);
                            startActivity(intent);
                            siren();
                            flash();
                            Thread.sleep(1000);
                            firebaseReference.setValue("null");
                        }


                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }

            });
        }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(getApplicationContext(), "Servicio Destruido", Toast.LENGTH_SHORT).show();
        Log.i("EXIT", "ondestroy!");

       // Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
       // sendBroadcast(broadcastIntent);


    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Toast.makeText(getApplicationContext(), "APP CERRADA", Toast.LENGTH_SHORT).show();

    stopSelf();
    }


    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.example.MyNeatoIntentService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void flash(){

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
    }


    public void siren(){

        MediaPlayer mp;
        mp = MediaPlayer.create(this, R.raw.siren);
        try {
            //mp.createVolumeShaper();
            mp.start();
            //Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

