package com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.myapplication.home.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    private Runnable runnable;
    private Handler handler;
    private static final int DELAY = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               runnable = this;
               startActivity(new Intent(SplashActivity.this, HomeActivity.class));
               finish();
            }
        },DELAY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onBackPressed() {}

}
