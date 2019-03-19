package com.example.duroapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import static android.content.ContentValues.TAG;

public class MyServiceTest extends Service {
    public MyServiceTest() {
    }
    private Firebase f = new Firebase("https://duroapp-50d3f.firebaseio.com/data/type");
    private ValueEventListener handler;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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
                Toast.makeText(getApplicationContext(), "pene"+text, Toast.LENGTH_SHORT).show();

                try {
                    if (((text).equals("duro"))) {

                        Intent intent = new Intent(MyServiceTest.this, MyIntentService2.class);
                        startService(intent);
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


        return START_STICKY;
    }
}
