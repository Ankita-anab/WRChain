package com.example.wrchain.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.wrchain.R;

public class SplashScreen_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       Handler mHandler = new Handler();
       Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen_Activity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        };
        // its trigger runnable after 4000 millisecond.
        mHandler.postDelayed(mRunnable,1000);

    }
}