package com.example.user.calender;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by user on 01/11/2016.
 */

public class Splash extends AppCompatActivity {
    /* durasi load */
    private  final int SPLASH_DISPLAY_LENGTH = 2000;
    Intent mainIntent;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);
        getSupportActionBar().hide();

       // Toast.makeText(this,""+Set.i(this).isFirst(),Toast.LENGTH_SHORT).show();

        if(Set.i(getBaseContext()).isFirst()){
            mainIntent = new Intent(Splash.this,DetailSetting.class);
        }else{
            mainIntent = new Intent(Splash.this,CalenderView.class);
        }

        new Handler().postDelayed(() -> {
            Splash.this.startActivity(mainIntent);
            Splash.this.finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}
