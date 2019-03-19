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
    Button button1, button2, button3,button4;
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

        //Editar
        button1 = (Button) findViewById(R.id.flash);
        button2 = (Button) findViewById(R.id.audio);
        button3 = (Button) findViewById(R.id.red);
        button4 = (Button) findViewById(R.id.todo);


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

        //FLASH
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("11000");
                Toast.makeText(getApplicationContext(), "ApreteFLASH", Toast.LENGTH_SHORT).show();
            }
        });

        //AUDIO
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("10100");
                Toast.makeText(getApplicationContext(), "ApreteAUDIO", Toast.LENGTH_SHORT).show();
            }
        });

        //RED
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("10010");
                Toast.makeText(getApplicationContext(), "ApreteRED", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseReference.setValue("10001");
                Toast.makeText(getApplicationContext(), "ApreteTODO", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
