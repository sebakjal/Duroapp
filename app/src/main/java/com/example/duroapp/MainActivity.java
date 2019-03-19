package com.example.duroapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView texto;
    Button button1, button2, button3;
    Firebase firebaseReference;
    String valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart(){
        super.onStart();


        texto = (TextView) findViewById(R.id.text1);
        button1 = (Button) findViewById(R.id.buttonleft);
        button2 = (Button) findViewById(R.id.buttonright);


        firebaseReference = new Firebase("https://duroapp-50d3f.firebaseio.com/data/type");



        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                texto.setText(text);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }


        });

        Intent intent2 = new Intent(MainActivity.this, MainService.class);
        startService(intent2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("blando");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("duro");

                Toast.makeText(getApplicationContext(), "APRETE MI ANO", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
