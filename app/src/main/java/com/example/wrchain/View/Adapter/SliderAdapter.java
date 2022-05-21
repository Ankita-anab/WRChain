package com.example.wrchain.View.Adapter;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.wrchain.R;
import com.example.wrchain.View.ModalClass.slider_model;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private List<slider_model> slider_modelList;



    public SliderAdapter(List<slider_model> slider_modelList) {
        this.slider_modelList = slider_modelList;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       View view= LayoutInflater.from(container.getContext()).inflate(R.layout.slider,container,false);

        ImageView banner= view.findViewById(R.id.img_slider);
        banner.setImageResource(slider_modelList.get(position).getBanner());
        container.addView(view,0);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView((View)object);
    }
    @Override
    public int getCount() {
        return slider_modelList.size();
    }
}
