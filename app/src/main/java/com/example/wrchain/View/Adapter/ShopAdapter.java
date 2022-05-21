package com.example.wrchain.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wrchain.R;
import com.example.wrchain.View.ModalClass.HorizontalCategoryProduct;
import com.example.wrchain.View.ModalClass.Shops;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder>{

    private List<Shops> ShopModelList;


    public ShopAdapter(List<Shops> shopModelList) {
        ShopModelList = shopModelList;
    }


    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearshop_list,parent,false);
        return new ShopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {
     int resource=ShopModelList.get(position).getShopimg();
     String sname=ShopModelList.get(position).getShopname();
     String cname=ShopModelList.get(position).getCategoryname();
     holder.setShopimg(resource);
     holder.setShopname(sname);
     holder.setCategoryname(cname);
    }

    @Override
    public int getItemCount() {
        return ShopModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView shopimg;
        TextView shopname, categoryname;
        RatingBar rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shopimg=itemView.findViewById(R.id.shopimg);
            shopname=itemView.findViewById(R.id.shopname);
            categoryname=itemView.findViewById(R.id.categoryname);
            //rating=itemView.findViewById(R.id.rating);

        }

        public void setShopimg(int resource) {
            shopimg.setImageResource(resource);
        }

        public void setShopname(String sname) {
            shopname.setText(sname);
        }

        public void setCategoryname(String cname) {
            categoryname.setText(cname);
        }
    }
}
