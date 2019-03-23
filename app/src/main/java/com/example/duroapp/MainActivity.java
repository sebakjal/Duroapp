package com.example.duroapp;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.CardView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    CardView button1, button2, button3,button4, button6,button7;
    //Button button1, button2, button3, button4;
    private Firebase firebaseReference = new Firebase("https://condominiumsegurity.firebaseio.com/");


    Camera camara;
    CameraManager micamara;
    String idCamara;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(isServiceRunning(MainService.class) == false ) {
            Toast.makeText(getApplicationContext(), "NO TA CORRIENDO INICIEMOSLO DENUEBVO", Toast.LENGTH_SHORT).show();

            startService(new Intent(this, MainService.class));
        }
        button1 = findViewById(R.id.flash);
        button2 = findViewById(R.id.audio);
        button3 = findViewById(R.id.red);
        button4 = findViewById(R.id.todo);

        //FLASH
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("flash");
            }
        });

        //AUDIO
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("siren");
            }
        });

        //RED
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("red");
            }
        });

        //ALL
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("null");
            }
        });


   }

    @Override
    protected void onStart(){
        super.onStart();






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

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
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
}
