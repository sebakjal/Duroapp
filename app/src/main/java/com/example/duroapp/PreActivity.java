package com.example.duroapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreActivity extends AppCompatActivity {

    Button registerBtn2, loginBtn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);

        initializeViews();

        registerBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        loginBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeViews() {
        registerBtn2 = findViewById(R.id.registerBtn2);
        loginBtn2 = findViewById(R.id.loginBtn2);
    }

}
