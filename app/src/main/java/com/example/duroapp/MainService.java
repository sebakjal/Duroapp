package com.example.duroapp;

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

    MediaPlayer mp;
    Camera camara;
    CameraManager micamara;
    String idCamara;
    int datorecivido = 1;


    public MainService() {
    }
    private Firebase f = new Firebase("https://condominiumsegurity.firebaseio.com/");
    //    private Firebase f = new Firebase("https://duroapp-50d3f.firebaseio.com/data/type");
    private ValueEventListener handler;
    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Servicio creado1...");
        Toast.makeText(getApplicationContext(), "Servicio Creado1", Toast.LENGTH_SHORT).show();
        super.onCreate();

        Intent intent3 = new Intent(MainService.this, MainService2.class);
        stopService(intent3);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "Servicio iniciado1...");

        Toast.makeText(getApplicationContext(), "Servicio Iniciado1", Toast.LENGTH_SHORT).show();

        handler = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                //Toast.makeText(getApplicationContext(), ""+text, Toast.LENGTH_SHORT).show();

                try {
                    //FLASH
                    if (((text).equals("flash"))) {
                        Toast.makeText(getApplicationContext(), "EntroFLASH", Toast.LENGTH_SHORT).show();
                        Thread.sleep(1000);
                        flash();
                        Thread.sleep(1000);
                        f.setValue("null");
                    }
                    //AUDIO
                    if (((text).equals("siren"))) {
                        //Toast.makeText(getApplicationContext(), "EntroAUDIO", Toast.LENGTH_SHORT).show();

                        siren();
                        Thread.sleep(1000);
                        f.setValue("null");
                    }
                    //RED
                    if (((text).equals("redd"))) {
                        //Toast.makeText(getApplicationContext(), "EntroRED", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainService.this, RedActivity.class);
                        startActivity(intent);
                        Thread.sleep(1000);
                        f.setValue("null");
                    }
                    //TODO
                    if (((text).equals("all"))) {
                        //Toast.makeText(getApplicationContext(), "entroTODO", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainService.this, RedActivity.class);
                        startActivity(intent);
                        siren();
                        flash();
                        Thread.sleep(1000);
                        f.setValue("null");
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


        return START_STICKY;
    }

    public void onDestroy(){
        Log.d(TAG, "Servicio destrudio1...");
        Toast.makeText(getApplicationContext(), "Servico Destruido1", Toast.LENGTH_SHORT).show();

        Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);

        //stopSelf();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i(TAG, "TaskRemoved()");
        Toast.makeText(getApplicationContext(), "Task1", Toast.LENGTH_SHORT).show();
        super.onTaskRemoved(rootIntent);
        stopSelf();

        //Intent intent2 = new Intent(MainService.this, MainService.class);
        //startService(intent2);

        //Intent broadcastIntent = new Intent("com.arvi.ActivityRecognition.RestartSensor");
        //sendBroadcast(broadcastIntent);


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

}
