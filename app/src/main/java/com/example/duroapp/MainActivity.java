package com.example.duroapp;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView texto;
    //Button button1, button2, button3,button4;
    CardView button1, button2, button3,button4;

    Firebase firebaseReference;
    String valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //getActionBar().hide();


///

        Intent mServiceIntent = new Intent(MainActivity.this, ServiceNoDelay.class);
        Toast.makeText(getApplicationContext(), "if efuera", Toast.LENGTH_SHORT).show();

        if (isMyServiceRunning(ServiceNoDelay.class)==false) {
            startService(mServiceIntent);
            Toast.makeText(getApplicationContext(), "if entro", Toast.LENGTH_SHORT).show();

        }
   }


    @Override
    protected void onStart(){
        super.onStart();

        texto = (TextView) findViewById(R.id.text1);

        //Editar
        //button1 = (Button) findViewById(R.id.flash);
        //button2 = (Button) findViewById(R.id.audio);
        //button3 = (Button) findViewById(R.id.red);
        //button4 = (Button) findViewById(R.id.todo);

        button1 = (CardView) findViewById(R.id.flash);
        button2 = (CardView) findViewById(R.id.audio);
        button3 = (CardView) findViewById(R.id.red);
        button4 = (CardView) findViewById(R.id.todo);


        firebaseReference = new Firebase("https://condominiumsegurity.firebaseio.com/");
        //https://condominiumsegurity.firebaseio.com/
        //         firebaseReference = new Firebase("https://duroapp-50d3f.firebaseio.com/data/type");

        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(getApplicationContext(), "activityDataChange", Toast.LENGTH_SHORT).show();

                String text = dataSnapshot.getValue(String.class);
                texto.setText(text);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }


        });


        //FLASH
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("flash");
                //Toast.makeText(getApplicationContext(), "ApreteFLASH", Toast.LENGTH_SHORT).show();
            }
        });

        //AUDIO
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("siren");
                //Toast.makeText(getApplicationContext(), "ApreteAUDIO", Toast.LENGTH_SHORT).show();
            }
        });

        //RED
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("red");
                //Toast.makeText(getApplicationContext(), "ApreteRED", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("all");
                //Toast.makeText(getApplicationContext(), "ApreteTODO", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Actividad Destruida", Toast.LENGTH_SHORT).show();

        //activities.remove(this);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Toast.makeText(getApplicationContext(), "true ", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();

        return false;
    }
}
