package com.example.wrchain.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wrchain.R;
import com.example.wrchain.View.Authentication.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminDashboard extends AppCompatActivity {
 ImageView logout,edit,addcategory,deletecategory,verify;
String name, email, uid;
TextView admin_name, admin_email;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        logout=findViewById(R.id.logout);
        edit=findViewById(R.id.edit);
        addcategory=findViewById(R.id.addcategory);
        deletecategory=findViewById(R.id.deletecategory);
        verify=findViewById(R.id.verify);
        admin_email=findViewById(R.id.admin_email);
        admin_name=findViewById(R.id.admin_name);

         user=FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UsersDetail");
        uid=user.getUid();
        getadmin_detail();

        logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminDashboard.this,LoginActivity.class));
            SavedSharedPreference.clearData(AdminDashboard.this);
            finish();
      
        }

    });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this,Edit_AdminProfile.class));

            }
        });

        addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this,Add_ShopCategory.class));
            }
        });

        deletecategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this,ViewCategory.class));

            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this,Verify_toAddShop.class));
            }
        });

    }

    private void getadmin_detail() {
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    email = snapshot.child("email").getValue().toString();
                    name=snapshot.child("username").getValue().toString();
                    admin_name.setText(name);
                    admin_email.setText(email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}