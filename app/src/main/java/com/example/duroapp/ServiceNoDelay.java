package com.example.duroapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class ServiceNoDelay extends Service {
    public int counter = 0;
    Context context;

    MediaPlayer mp;
    Camera camara;
    CameraManager micamara;
    String idCamara;
    int datorecivido = 1;

    private Firebase f = new Firebase("https://condominiumsegurity.firebaseio.com/");
    //    private Firebase f = new Firebase("https://duroapp-50d3f.firebaseio.com/data/type");
    private ValueEventListener handler;

    public ServiceNoDelay(Context applicationContext) {
        super();
        context = applicationContext;
        Log.i("HERE", "here service created!");
        Toast.makeText(getApplicationContext(), "ServicioCreado ", Toast.LENGTH_SHORT).show();

    }

    public ServiceNoDelay() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //startTimer();

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
                    if (((text).equals("red"))) {
                        //Toast.makeText(getApplicationContext(), "EntroRED", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ServiceNoDelay.this, RedActivity.class);
                        startActivity(intent);
                        Thread.sleep(1000);
                        f.setValue("null");
                    }
                    //TODO
                    if (((text).equals("all"))) {
                        //Toast.makeText(getApplicationContext(), "entroTODO", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ServiceNoDelay.this, RedActivity.class);
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("EXIT", "ondestroy!");
        Toast.makeText(getApplicationContext(), "Servicio Destruido", Toast.LENGTH_SHORT).show();


        Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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