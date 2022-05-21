package com.example.wrchain.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


public class Retailer_MainActivity extends AppCompatActivity {
ImageView logout,edit,addproduct,addshop,viewproduct,order,nearbyshop,help;
    TextView retailer_name, retailer_email;
    String name, email, uid;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_main);
        edit=findViewById(R.id.edit);
        addproduct=findViewById(R.id.addproduct);
        addshop=findViewById(R.id.addshop);
        viewproduct=findViewById(R.id.viewproduct);
        order=findViewById(R.id.order);
        nearbyshop=findViewById(R.id.nearbyshop);
        help=findViewById(R.id.help);
        logout=findViewById(R.id.logout);
        retailer_email=findViewById(R.id.retailer_email);
        retailer_name=findViewById(R.id.retailer_name);

        user= FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("UsersDetail");
        uid=user.getUid();
        getretail_detail();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Retailer_MainActivity.this, EditVenderProfile.class));

            }
        });

        addshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Retailer_MainActivity.this, AddShop.class));
            }
        });
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Retailer_MainActivity.this, addproduct.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Retailer_MainActivity.this, LoginActivity.class));
                SavedSharedPreference.clearData(Retailer_MainActivity.this);
                finish();

            }
        });
        viewproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Retailer_MainActivity.this, EditandDelete_Products.class));
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Retailer_MainActivity.this, OrderList_by_user.class));
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(Retailer_MainActivity.this);
//                ViewGroup viewGroup = findViewById(android.R.id.content);
//                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.helpdialog_design, viewGroup, false);
//                builder.setView(dialogView);
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.helpdialog_design);
                Button dialogButton = (Button) dialog.findViewById(R.id.call);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent callIntent = new Intent(Intent.ACTION_CALL);
//                        callIntent.setData(Uri.parse("tel:7807708098"));
//
//                        if (ActivityCompat.checkSelfPermission(Retailer_MainActivity.this,
//                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                            return;
//                        }
//                        startActivity(callIntent);
                       dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void getretail_detail() {
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    email = snapshot.child("email").getValue().toString();
                    name=snapshot.child("username").getValue().toString();
                    retailer_name.setText(name);
                    retailer_email.setText(email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}


