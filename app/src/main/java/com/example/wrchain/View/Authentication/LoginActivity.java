package com.example.wrchain.View.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wrchain.R;
import com.example.wrchain.View.AdminDashboard;
import com.example.wrchain.View.MainActivity;
import com.example.wrchain.View.ModalClass.Users;
import com.example.wrchain.View.Retailer_MainActivity;
import com.example.wrchain.View.SavedSharedPreference;
import com.example.wrchain.View.Wholesaler_MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity<Mauth> extends AppCompatActivity {
    TextView forgetpassword, signup;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText email, password;
    Button login;
    ProgressDialog progressDialog;
    private FirebaseAuth Mauth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;//used to store current user of account
    DataSnapshot snapshot;
    Users users;
    Switch active;
    SharedPreferences Shared_pref;

    ImageView google, facebook;
    Spinner usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgetpassword = findViewById(R.id.forgetpassword);
        signup = findViewById(R.id.signup);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        active = findViewById(R.id.active);
        login = findViewById(R.id.login);
        google = findViewById(R.id.google);
        facebook = findViewById(R.id.facebook);
        Mauth = FirebaseAuth.getInstance();


        // Intent intent= new Intent(getIntent());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUserAccount();
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GoogleSignInActivity.class);
                startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FacebokSignInActivity.class);
                startActivity(intent);
            }
        });

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


    }

    private void loginUserAccount() {
        // show the visibility of progress dialog to show loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Custom Login....");

        // Take the value of  edit texts in Strings
        String emailT, passwordT;
        emailT = email.getText().toString().trim();
        passwordT = password.getText().toString().trim();
        // validations for input email and password

        if (TextUtils.isEmpty(emailT)) {
            email.setError("Please enter email");
            email.requestFocus();
            return;
        }
        if (!emailT.matches(emailPattern)) {
            email.setError("Please provide valid email!!");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passwordT)) {
            password.setError("Please enter password!!");
            password.requestFocus();
            return;
        }
        progressDialog.show();

        // signin existing user
        Mauth.signInWithEmailAndPassword(emailT, passwordT)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())//If account login successful print message and send user to main Activity
                        {
                            String uid = task.getResult().getUser().getUid();
                              Toast.makeText(LoginActivity.this,"user id"+uid,Toast.LENGTH_SHORT).show();

                            //when click on login button put data on shared preferences
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            firebaseDatabase.getReference().child("UsersDetail").child(uid).child("user_type")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String usertype = snapshot.getValue(String.class);
                                            if (usertype.equals("Admin")) {
                                                Toast.makeText(LoginActivity.this, "Welcome to Admin Dashboard " + usertype, Toast.LENGTH_SHORT).show();
                                                SavedSharedPreference.setDataLogin(LoginActivity.this, true);
                                                SavedSharedPreference.setEmail(LoginActivity.this, "EmailT");
                                                SavedSharedPreference.setUserType(LoginActivity.this, "Admin");
                                                progressDialog.dismiss();
                                                Intent intent = new Intent(LoginActivity.this, AdminDashboard.class);
                                                startActivity(intent);
                                                finish();
                                            } else if (usertype.equals("Consumer")) {
                                                Toast.makeText(LoginActivity.this, "Welcome to User Dashboard " + usertype, Toast.LENGTH_SHORT).show();
                                                SavedSharedPreference.setDataLogin(LoginActivity.this, true);
                                                SavedSharedPreference.setEmail(LoginActivity.this, "EmailT");
                                                SavedSharedPreference.setUserType(LoginActivity.this, "Consumer");
                                                progressDialog.dismiss();
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                finish();
                                            } else if (usertype.equals("Wholesaler")) {
                                                Toast.makeText(LoginActivity.this, "Welcome to Wholesaler Dashboard " + usertype, Toast.LENGTH_SHORT).show();
                                                SavedSharedPreference.setDataLogin(LoginActivity.this, true);
                                                SavedSharedPreference.setEmail(LoginActivity.this, "EmailT");
                                                SavedSharedPreference.setUserType(LoginActivity.this, "Wholesaler");
                                                progressDialog.dismiss();
                                                startActivity(new Intent(LoginActivity.this, Wholesaler_MainActivity.class));
                                                finish();

                                            } else if (usertype.equals("Retailer")) {
                                                Toast.makeText(LoginActivity.this, "Welcome to Retailer Dashboard" + usertype, Toast.LENGTH_SHORT).show();
                                                SavedSharedPreference.setDataLogin(LoginActivity.this, true);
                                                SavedSharedPreference.setEmail(LoginActivity.this, "EmailT");
                                                SavedSharedPreference.setUserType(LoginActivity.this, "Retailer");
                                                progressDialog.dismiss();
                                                startActivity(new Intent(LoginActivity.this, Retailer_MainActivity.class));
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(LoginActivity.this, "No user is there", Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        } else//Print the error message incase of failure
                        {
                            String msg = task.getException().toString();
                            Toast.makeText(LoginActivity.this, "Database Error: " + msg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }


    @Override
    protected void onStart() {
        //Check if user has already signed in if yes send to mainActivity
        //This to avoid signing in everytime you open the app.
        super.onStart();
      if(SavedSharedPreference.getDataLogin(this))
      {
          if(SavedSharedPreference.getUserType(this).equals("Admin"))
          {
              startActivity(new Intent(LoginActivity.this,AdminDashboard.class));
              finish();
          }
          else  if(SavedSharedPreference.getUserType(this).equals("Consumer"))
          {
              startActivity(new Intent(LoginActivity.this,MainActivity.class));
              finish();
          }
          else  if(SavedSharedPreference.getUserType(this).equals("Wholesaler"))
          {
              startActivity(new Intent(LoginActivity.this, Wholesaler_MainActivity.class));
              finish();
          }
          else  if(SavedSharedPreference.getUserType(this).equals("Retailer"))
          {
              startActivity(new Intent(LoginActivity.this,Retailer_MainActivity.class));
              finish();
          }
          else
          {
              Toast.makeText(this, "Login Again", Toast.LENGTH_SHORT).show();
          }


      }


    }
}