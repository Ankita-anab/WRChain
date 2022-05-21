package com.example.wrchain.View.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wrchain.R;
import com.example.wrchain.View.Adapter.CategoryAdapter;
import com.example.wrchain.View.Adapter.ShopAdapter;
import com.example.wrchain.View.Adapter.SliderAdapter;
import com.example.wrchain.View.Map.LocationMainActivity;
import com.example.wrchain.View.ModalClass.HorizontalCategoryProduct;
import com.example.wrchain.View.ModalClass.Shops;
import com.example.wrchain.View.ModalClass.slider_model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

//    private RecyclerView recyclerView;
//    CategoryAdapter categoryAdapter;

    //location
    RelativeLayout locationdiv;
    TextView addresstxt;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    //

    //banner slider
    private ViewPager bannerslider;
    private List<slider_model> slider_modelList;
    private int current_Page = 1;
    private Timer timer;
    final private long DELAY_TIME = 2000;
    final private long PERIOD_TIME = 2000;
    //

    //HorizontalCategory

    private TextView categoryheading;
    private RecyclerView HorizontalRecycleView, ShopList;

    //

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

     //current location
        locationdiv=view.findViewById(R.id.locationdiv);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("CurrentLocation");
        addresstxt=view.findViewById(R.id.addresstxt);
        getdata();


        //location
        locationdiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LocationMainActivity.class));
            }
        });


        ///

        //banner slide
        bannerslider = view.findViewById(R.id.bannerslider);
        slider_modelList = new ArrayList<slider_model>();

        slider_modelList.add(new slider_model(R.drawable.deliveryprocess));
        slider_modelList.add(new slider_model(R.drawable.blackfriday));


        slider_modelList.add(new slider_model(R.drawable.shopnow));
        slider_modelList.add(new slider_model(R.drawable.onlineshop));
        slider_modelList.add(new slider_model(R.drawable.deliveryprocess));
        slider_modelList.add(new slider_model(R.drawable.blackfriday));


        slider_modelList.add(new slider_model(R.drawable.shopnow));
        slider_modelList.add(new slider_model(R.drawable.onlineshop));

        SliderAdapter sliderAdapter = new SliderAdapter(slider_modelList);
        bannerslider.setAdapter(sliderAdapter);

        bannerslider.setClipToPadding(false);
        bannerslider.setPageMargin(3);
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                current_Page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    pagerLooper();
                }
            }
        };
        bannerslider.addOnPageChangeListener(onPageChangeListener);
        startbannerSlideShow();

        bannerslider.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pagerLooper();
                stopbannerSlideShow();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    startbannerSlideShow();
                }
                return false;
            }
        });

        //banner exit

        //HorizontalCategory
        categoryheading = view.findViewById(R.id.categoryheading);
        HorizontalRecycleView = view.findViewById(R.id.category_items);
        List<HorizontalCategoryProduct> horizontalCategoryModelList = new ArrayList<>();
        horizontalCategoryModelList.add(new HorizontalCategoryProduct(R.drawable.grocery, "Grocery"));
        horizontalCategoryModelList.add(new HorizontalCategoryProduct(R.drawable.electronic_devices, "Electronics"));
        horizontalCategoryModelList.add(new HorizontalCategoryProduct(R.drawable.fashion, "Fashion"));
        horizontalCategoryModelList.add(new HorizontalCategoryProduct(R.drawable.pharmacy, "Pharmacy"));
        horizontalCategoryModelList.add(new HorizontalCategoryProduct(R.drawable.patanjali, "Patanjali"));
        horizontalCategoryModelList.add(new HorizontalCategoryProduct(R.drawable.sports, "Sports"));
        horizontalCategoryModelList.add(new HorizontalCategoryProduct(R.drawable.beauty_product, "Beauty"));
        horizontalCategoryModelList.add(new HorizontalCategoryProduct(R.drawable.hardware_equip, "Hardware"));
        horizontalCategoryModelList.add(new HorizontalCategoryProduct(R.drawable.homedecor, "Home Decor"));
        horizontalCategoryModelList.add(new HorizontalCategoryProduct(R.drawable.furniture, "Furniture"));
        horizontalCategoryModelList.add(new HorizontalCategoryProduct(R.drawable.toysandgifts, "Toys & Gifts"));

        CategoryAdapter categoryAdapter = new CategoryAdapter(horizontalCategoryModelList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        HorizontalRecycleView.setLayoutManager(linearLayoutManager);
        //set adapter
        HorizontalRecycleView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();


        //Horizontal CAtegory

//Shops List
        ShopList=view.findViewById(R.id.shopslist);
        List<Shops> shopModelList = new ArrayList<>();
        shopModelList.add(new Shops(R.drawable.grocery,"Saraswati General Store","Grocery Shop"));
        shopModelList.add(new Shops(R.drawable.stationary,"Gian Store","Stationary Shop"));
        shopModelList.add(new Shops(R.drawable.electronic_devices,"Raj Electronic Store","Electronic Shop"));
        shopModelList.add(new Shops(R.drawable.toysandgifts,"Toyz Store","Gifts&Toy Shop"));

        ShopAdapter shopAdapter = new ShopAdapter(shopModelList);
        LinearLayoutManager linearLayoutManager1 =new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(linearLayoutManager1.VERTICAL);
        ShopList.setLayoutManager(linearLayoutManager1);
        ShopList.setAdapter(shopAdapter);
        shopAdapter.notifyDataSetChanged();

        //

        return view;
    }

    private void getdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.exists()) {
                   String value = snapshot.child("address").getValue().toString();
                   addresstxt.setText(value);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }

        });

    }


    private void pagerLooper()
    {
        if(current_Page ==slider_modelList.size()-2)
        {
            current_Page = 2;
            bannerslider.setCurrentItem(current_Page,false);
        }
        if(current_Page == 1)
        {
            current_Page = slider_modelList.size()-3;
            bannerslider.setCurrentItem(current_Page,false);
        }

    }
    private void startbannerSlideShow()
    {
        Handler handler = new Handler();
        Runnable update = new Runnable() {
            @Override
            public void run() {
                if(current_Page >= slider_modelList.size())
                {
                    current_Page=1;
                }
                bannerslider.setCurrentItem(current_Page++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);
    }
    private void stopbannerSlideShow()
    {
        timer.cancel();
    }

    //bannerslider
    ///location

}

