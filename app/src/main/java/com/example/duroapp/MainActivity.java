package com.example.duroapp;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ///Creando Botones señales y TexView Lectura variable Firebase
    TextView texto;
    //Button button1, button2, button3,button4;
    CardView button1, button2, button3,button4,button5;

    // Crea Firebase
    Firebase firebaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //getActionBar().hide();

        /// Intent para abrir Servicio
        Intent mServiceIntent = new Intent(MainActivity.this, ServiceNoDelay.class);
        //Intent mServiceIntent = new Intent(MainActivity.this, MainService.class);

        //Toast.makeText(getApplicationContext(), "¿Esta Activo Servicio?", Toast.LENGTH_SHORT).show();
        if (isMyServiceRunning(ServiceNoDelay.class)==false) {
            startService(mServiceIntent);
        }
    }


    @Override
    protected void onStart(){
        super.onStart();

        /// Editar TexView dato Firebase
        texto = (TextView) findViewById(R.id.text1);

        /// Editar Button
        //button1 = (Button) findViewById(R.id.flash);
        //button2 = (Button) findViewById(R.id.audio);
        //button3 = (Button) findViewById(R.id.red);
        //button4 = (Button) findViewById(R.id.todo);

        /// Editar BardView
        button1 = (CardView) findViewById(R.id.flash);
        button2 = (CardView) findViewById(R.id.audio);
        button3 = (CardView) findViewById(R.id.red);
        button4 = (CardView) findViewById(R.id.todo);

        /// Describe Objeto Firebase
        firebaseReference = new Firebase("https://condominiumsegurity.firebaseio.com/");
        //firebaseReference = new Firebase("https://duroapp-50d3f.firebaseio.com/data/type");

        /// Crea evento escucha cambio de dato en Firebase
        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /// Crea variable string con el valor actual de el dato en Firebase
                String text = dataSnapshot.getValue(String.class);
                /// Escribe en el TexView de la interfaz el valor actual de Firebase
                texto.setText(text);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


        /// Botones para editar el dato en Firebase
        //Flash
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    firebaseReference.setValue("flash");
                    Thread.sleep(1000);
                    firebaseReference.setValue("null");

                }catch (Exception e){

                }

                //Toast.makeText(getApplicationContext(), "ApreteFLASH", Toast.LENGTH_SHORT).show();
            }
        });

        //Audio
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    firebaseReference.setValue("siren");
                    Thread.sleep(1000);
                    firebaseReference.setValue("null");

                }catch (Exception e){

                }                //Toast.makeText(getApplicationContext(), "ApreteAUDIO", Toast.LENGTH_SHORT).show();
            }
        });

        //red
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    firebaseReference.setValue("red");
                    Thread.sleep(1000);
                    firebaseReference.setValue("null");

                }catch (Exception e){

                }                //Toast.makeText(getApplicationContext(), "ApreteRED", Toast.LENGTH_SHORT).show();
            }
        });

        //Todoo
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    firebaseReference.setValue("red");
                    Thread.sleep(1000);
                    firebaseReference.setValue("null");

                }catch (Exception e){

                }                //Toast.makeText(getApplicationContext(), "ApreteTODO", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //Toast.makeText(getApplicationContext(), "Actividad Destruida", Toast.LENGTH_SHORT).show();
        //activities.remove(this);
    }


    /// Funcion Pregunta Activo Servicio
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                //Toast.makeText(getApplicationContext(), "Si esta Activo ", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        //Toast.makeText(getApplicationContext(), "No esta Activo", Toast.LENGTH_SHORT).show();
        return false;
    }
}

