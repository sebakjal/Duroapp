package com.example.duroapp;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>

 * helper methods.
 */
public class SirenIntentService extends IntentService {
    MediaPlayer mp;

    public SirenIntentService() {
        super("SirenIntentService");
    }

    @Override
    protected void onHandleIntent(Intent msgIntent) {
        if (msgIntent!=null) {
            mp = MediaPlayer.create(this, R.raw.siren);
            try {
                //mp.createVolumeShaper();
                mp.start();
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
