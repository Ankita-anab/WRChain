package com.example.wrchain.View.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wrchain.R;
import com.example.wrchain.View.ModalClass.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity
{
    Users users;
    Spinner usertype;

TextView login;
EditText name,email,password, number;
String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
Button signup;
ProgressDialog progressDialog;
private FirebaseAuth Mauth;
ImageView google, facebook;
//database
    DataSnapshot snapshot;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        login=findViewById(R.id.login);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        number=findViewById(R.id.number);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        google=findViewById(R.id.google);
        facebook=findViewById(R.id.facebook);
        usertype=findViewById(R.id.usertype);
        Mauth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        //databaseReference = firebaseDatabase.getReference("UsersDetail");
//spinner
    String[] User_Type=getResources().getStringArray(R.array.user_type);
       //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,User_Type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        usertype.setAdapter(adapter);

        usertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //


        //spinner
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               RegisterNewUser();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignupActivity.this,GoogleSignInActivity.class);
                startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignupActivity.this,FacebokSignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void RegisterNewUser() {
        // show the visibility of progress Dialog to show loading
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Custom SignIn....");


        // Take the value of  edit texts in Strings
        String nameT, emailT, passwordT,usertypeT, numberT;
        nameT=name.getText().toString();
        emailT = email.getText().toString().trim();
        numberT = number.getText().toString().trim();
        passwordT = password.getText().toString().trim();
        usertypeT =usertype.getSelectedItem().toString().trim();
        //database
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("User");
        //
        // Validations for input email and password
        if(usertypeT.equals("Pick one"))
        {
            Toast.makeText(getApplicationContext(), "Select User Type", Toast.LENGTH_LONG).show();
            usertype.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(nameT)) {
            name.setError("Please enter name");
            name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(numberT)) {
            number.setError("Please enter number");
            number.requestFocus();
            return;
        }
        if(numberT.length() > 10)
        {
            number.setError("Please enter number");
            number.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(emailT)) {
            email.setError("Please enter email");
            email.requestFocus();
            return;
        }
        if(!emailT.matches(emailPattern))
        {
            email.setError("Please provide valid email!!");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passwordT)) {
            password.setError("Please enter password!!");
            password.requestFocus();
            return;
        }
        if(passwordT.length() < 6)
        {
            password.setError("Password too short!!!");
            password.requestFocus();
            return;
        }

        // create new user or register new user
        progressDialog.show();
          Mauth.createUserWithEmailAndPassword(emailT, passwordT)
          .addOnCompleteListener(new OnCompleteListener<AuthResult>()
          {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful())
        {
            users= new Users(nameT,emailT,passwordT,usertypeT,numberT);

           firebaseDatabase.getReference("UsersDetail")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(SignupActivity.this, "Failed to registered...", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            }
                        }
                    });


        }
        else{
            Toast.makeText(SignupActivity.this, "User already exists...", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
        }
        });
        }


    }




