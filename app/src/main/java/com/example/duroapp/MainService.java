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
    private Firebase f = new Firebase("https://duroapp-50d3f.firebaseio.com/data/type");
    private ValueEventListener handler;
    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Servicio creado...");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "Servicio iniciado...");

        Toast.makeText(getApplicationContext(), "ENTRE A LA WEA", Toast.LENGTH_SHORT).show();

        handler = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String text = dataSnapshot.getValue(String.class);
                //valor = texto.toString();
                Toast.makeText(getApplicationContext(), "pene "+text, Toast.LENGTH_SHORT).show();

                try {
                    if (((text).equals("duro"))) {
                        Toast.makeText(getApplicationContext(), "ENTRO", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(MainService.this, RedActivity.class);
                        startActivity(intent);

                        siren();
                        flash();

                        //Intent intent = new Intent(MainService.this, RedActivity.class);
                        //startActivity(intent);
                       // Intent intent = new Intent(MainService.this, FlashIntentService.class);
                       // startService(intent);
                      //  Intent intent2 = new Intent(MainService.this, SirenIntentService.class);
                      //  startService(intent2);

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


        return START_STICKY;
    }

    public void onDestroy(){
        Log.d(TAG, "Servicio destrudio...");
        stopSelf();
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
