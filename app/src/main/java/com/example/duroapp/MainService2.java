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
/////////77

/////////
//////////7
/////
///////
////////////7
/////////////

public class MainService2 extends Service {

    MediaPlayer mp;
    Camera camara;
    CameraManager micamara;
    String idCamara;
    int datorecivido = 1;


    public MainService2() {
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
        Log.d(TAG, "Servicio creado...");
        Toast.makeText(getApplicationContext(), "Servicio Creado2", Toast.LENGTH_SHORT).show();

        super.onCreate();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "Servicio iniciado...");

        Toast.makeText(getApplicationContext(), "Servicio Iniciado2", Toast.LENGTH_SHORT).show();

        handler = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                //Toast.makeText(getApplicationContext(), ""+text, Toast.LENGTH_SHORT).show();

                try {
                    //FLASH
                    if (((text).equals("flash"))) {
                        Toast.makeText(getApplicationContext(), "EntroFLASH2", Toast.LENGTH_SHORT).show();
                        Thread.sleep(1000);
                        //flash();
                        siren();
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
                        Intent intent = new Intent(MainService2.this, RedActivity.class);
                        startActivity(intent);
                        Thread.sleep(1000);
                        f.setValue("null");
                    }
                    //TODO
                    if (((text).equals("all"))) {
                        //Toast.makeText(getApplicationContext(), "entroTODO", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainService2.this, RedActivity.class);
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
        super.onDestroy();
        Log.d(TAG, "Servicio destrudio...");
        Toast.makeText(getApplicationContext(), "Servico Destruido2", Toast.LENGTH_SHORT).show();
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
