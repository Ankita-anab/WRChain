package com.example.wrchain.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.wrchain.R;
import com.example.wrchain.View.Authentication.LoginActivity;

public class Wholesaler_MainActivity extends AppCompatActivity {
    ImageView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholesaler_main);
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Wholesaler_MainActivity.this, LoginActivity.class));
                SavedSharedPreference.clearData(Wholesaler_MainActivity.this);
                finish();

            }
        });

    }
}