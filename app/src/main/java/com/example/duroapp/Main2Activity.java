package com.example.duroapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private ConnectionReceiver connectionReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toast.makeText(getApplicationContext(), "actividad creadaa", Toast.LENGTH_SHORT).show();

        connectionReceiver = new ConnectionReceiver();
        intentFilter = new IntentFilter("com.sk.broadcastreceiver.SOME_ACTION");
       // Intent intent = new Intent("com.sk.broadcastreceiver.SOME_ACTION");
       // sendBroadcast(intent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(connectionReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        Toast.makeText(getApplicationContext(), "dESTROYD 2", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        //unregisterReceiver(connectionReceiver);
        //Intent intent = new Intent(
        Intent intent = new Intent(Main2Activity.this, ServiceNoDelay.class);
        startService(intent);

    }

}
