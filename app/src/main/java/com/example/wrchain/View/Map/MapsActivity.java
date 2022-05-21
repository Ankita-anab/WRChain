package com.example.wrchain.View.Map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wrchain.R;
import com.example.wrchain.View.MainActivity;
import com.example.wrchain.View.ModalClass.Locationhelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
        {

  Boolean isPermissionGranted;
  GoogleMap mgoogleMap;
  FloatingActionButton fab;
  Button saveaddress;
  ImageView marker;
  private FusedLocationProviderClient mLocationClient;
  private int GPS_REQUEST_CODE=9001;
  Locationhelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent=new Intent(getIntent());
        fab=findViewById(R.id.fab);
        saveaddress=findViewById(R.id.saveaddress);

        checkMypermission();
        initMap();

        mLocationClient = new FusedLocationProviderClient(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrLoc();
            }
        });

        saveaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                FirebaseDatabase.getInstance().getReference("CurrentLocation")
                        .setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(MapsActivity.this, "Location Saved", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MapsActivity.this, "Location Not Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
               finish();

            }
        });

    }
    private void geoLocate(View view)
    {

    }

            private void initMap() {
                if(isPermissionGranted)
                {
                    if(isGPSenable())
                    {
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(this);
                    }

                }
            }
       private Boolean isGPSenable()
       {
           LocationManager locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
           boolean providerEnable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
           if(providerEnable)
           {
               return true;
           }
           else
           {
               AlertDialog alertDialog =new AlertDialog.Builder(this)
                       .setTitle("GPS Permission")
                       .setMessage("GPS is required for this app to work..Please enable GPS")
                       .setPositiveButton("Yes",((dialogInterface, i) -> {
                           Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                           startActivityForResult(intent,GPS_REQUEST_CODE);
                       }))
                       .setCancelable(false)
                       .show();


           }

          return false;
       }


            @SuppressLint("MissingPermission")
            private void getCurrLoc() {

            mLocationClient.getLastLocation().addOnCompleteListener(task -> {

               if(task.isSuccessful())
               {
                   Location location= task.getResult();
                   gotoLocation(location.getLatitude(),location.getLongitude());
               }
           });
    }

            private void gotoLocation(double latitude, double longitude) {

                 LatLng latLng = new LatLng(latitude, longitude);


                Geocoder gecoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addressList = gecoder.getFromLocation(latitude,longitude,1);
                    if(addressList.size()>0)
                    {
                        Address address1 =addressList.get(0);
                        CameraUpdate cameraUpdate =CameraUpdateFactory.newLatLngZoom(latLng,18f);
                        mgoogleMap.moveCamera(cameraUpdate);
                        mgoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        MarkerOptions option= new MarkerOptions();

                        option.position(new LatLng(address1.getLatitude(),address1.getLongitude()));
                        option.title(address1.getLocality());
                        option.draggable(true);
                        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                       Toast.makeText(this, address1.getAddressLine(0), Toast.LENGTH_SHORT).show();
                       mgoogleMap.addMarker(option);

                        helper = new Locationhelper(
                                address1.getLongitude(),
                                address1.getLatitude(),
                                address1.getAddressLine(0),
                                address1.getLocality()

                        );

                    }

                }

            catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }


            private void checkMypermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {

                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Toast.makeText(MapsActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        isPermissionGranted=true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",getPackageName(),"");
                        intent.setData(uri);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


}

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
     mgoogleMap =googleMap;
     mgoogleMap.setMyLocationEnabled(true);


    }

            @Override
            public void onConnected(@Nullable Bundle bundle) {

            }

            @Override
            public void onConnectionSuspended(int i) {

            }

            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                if(requestCode == GPS_REQUEST_CODE)
                {
                    LocationManager locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
                    boolean providerEnable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if(providerEnable)
                    {
                        Toast.makeText(this, "GPS is enable", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this, "GPS is not enable", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }