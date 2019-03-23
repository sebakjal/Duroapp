package com.example.duroapp;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RedActivity extends AppCompatActivity {
    //Crear variable texto
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);
        //Asignacion Variable texto
        txt = (TextView) findViewById(R.id.txt);
        //Funcion Parpadeo texto fondo
        BlinkEffect();
    }

    // Funcion parpadeo Texto Rojo-negro-Rojo
    private void BlinkEffect() {
        int DURACION_SPLASH=3000;
        ObjectAnimator anim = ObjectAnimator.ofInt(txt, "backgroundColor", Color.RED, Color.BLACK, Color.BLACK, Color.RED);
        anim.setDuration(500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setRepeatCount(4);
        //anim.setRepeatCount(Animation.INFINITE);
        anim.start();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Cuando pasen los DURACION_SPLASH segundos, lee siguientes lineas
                // Intent intent = new Intent(RedActivity.this, MainActivity.class);
                //6startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);

    }

}


