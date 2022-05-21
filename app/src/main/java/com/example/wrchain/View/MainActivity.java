package com.example.wrchain.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.wrchain.R;
import com.example.wrchain.View.Authentication.LoginActivity;
import com.example.wrchain.View.Fragments.CartFragment;
import com.example.wrchain.View.Fragments.HomeFragment;
import com.example.wrchain.View.Fragments.UserProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    FrameLayout frameLayout;
    BottomNavigationView bottomnavigation;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        frameLayout=findViewById(R.id.framelayout);
        bottomnavigation=findViewById(R.id.bottomnavigation);
        mAuth=FirebaseAuth.getInstance();


        setSupportActionBar(toolbar);
        //open Home Fragment
        showHomeFragment();


        //Navigationbar
        bottomnavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        toolbar.setTitle("Home");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.framelayout, new HomeFragment())
                                .commit();
                        return true;

                    case R.id.account:
                        toolbar.setTitle("User Account");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.framelayout, new UserProfileFragment())
                                .commit();
                        return true;
                    case R.id.cart:
                        toolbar.setTitle("Cart");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.framelayout, new CartFragment())
                                .commit();
                        return true;

                }
                return true;
            }
        });
    }


    private void showHomeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new HomeFragment()).commit();
    }


    //tollbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.search:
                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.favorite:
                Toast.makeText(this, "Favorite Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                SavedSharedPreference.clearData(MainActivity.this);
                finish();
                break;
            case R.id.voice_recognition:
                Toast.makeText(this, "Voice Recognition Clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }



}