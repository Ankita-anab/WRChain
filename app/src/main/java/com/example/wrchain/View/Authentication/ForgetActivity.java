package com.example.wrchain.View.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wrchain.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetActivity extends AppCompatActivity {
TextView login;
EditText email;
Button forgetpassword;
private String emailT;
FirebaseAuth mAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        login=findViewById(R.id.login);
        email=findViewById(R.id.email);
        forgetpassword=findViewById(R.id.resestpassword);
        mAuth=FirebaseAuth.getInstance();

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               emailT=email.getText().toString().trim();
               if(emailT.isEmpty() || !emailT.matches(emailPattern))
               {
                   email.requestFocus();
                   email.setError("Required & Valid Email ID");
               }
               else
               {
                  forgetpassword();
               }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ForgetActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void forgetpassword() {
        mAuth.sendPasswordResetEmail(emailT).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgetActivity.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgetActivity.this,LoginActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(ForgetActivity.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}