package com.example.wrchain.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wrchain.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Edit_AdminProfile extends AppCompatActivity {
ImageView admin_img;
EditText name, email, number;
Button savedetail;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
private FirebaseUser user;

FirebaseStorage storage;
FirebaseAuth auth;
String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_profile);
        admin_img=findViewById(R.id.admin_img);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        number=findViewById(R.id.number);
        savedetail=findViewById(R.id.save_detail);

        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UsersDetail");
        uid=user.getUid();
        getdata();

      admin_img.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent= new Intent();
              intent.setAction(Intent.ACTION_GET_CONTENT);
              intent.setType("image/*");
              startActivityForResult(intent, 33);
          }
      });

    }

    private void getdata() {
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                   String emailT = snapshot.child("email").getValue().toString();
                   String nameT=snapshot.child("username").getValue().toString();
                   String numberT=snapshot.child("number").getValue().toString();
                    name.setText(nameT);
                    email.setText(emailT);
                    number.setText(numberT);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData()!=null)
        {
            Uri profile =data.getData();
            admin_img.setImageURI(profile);
            final StorageReference storageReference=storage.getReference().child("UserImage")
                    .child(FirebaseAuth.getInstance().getUid());
            storageReference.putFile(profile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Edit_AdminProfile.this, "Upload Image", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}