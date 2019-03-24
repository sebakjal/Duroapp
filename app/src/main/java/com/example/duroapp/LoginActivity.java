package com.example.duroapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText email, pass;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
    }


    private void loginUserAccount() {
        super.onStart();

        String correo, contra;
        correo = email.getText().toString();
        contra = pass.getText().toString();

        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(getApplicationContext(), "Entra email maldito", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(contra)) {
            Toast.makeText(getApplicationContext(), "Entre password", Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.signInWithEmailAndPassword(correo, contra)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login exitoso", Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.GONE);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Falló el login", Toast.LENGTH_LONG).show();
                            //progressBar.setVisibility(View.GONE);
                        }

                    }
                });
    }


    private void initializeUI() {
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);

        loginBtn = findViewById(R.id.login);

    }
}