package com.example.wrchain.View.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wrchain.R;
import com.example.wrchain.View.Authentication.LoginActivity;
import com.example.wrchain.View.MainActivity;
import com.example.wrchain.View.SavedSharedPreference;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileFragment extends Fragment {
TextView email;
Button logout;
FirebaseAuth mAuth;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_user_profile, container, false);

       logout=view.findViewById(R.id.logout);
       logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              // FirebaseAuth.getInstance().signOut();
              // startActivity(new Intent(getActivity(),LoginActivity.class));

           }
       });
       return view;
    }
}