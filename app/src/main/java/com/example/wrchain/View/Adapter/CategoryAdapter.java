package com.example.wrchain.View.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wrchain.R;
import com.example.wrchain.View.ModalClass.HorizontalCategoryProduct;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
//vars

    private List<HorizontalCategoryProduct> horizontalCategoryModelList;

    //constructor
    public CategoryAdapter(List<HorizontalCategoryProduct> horizontalCategoryModelList) {
        this.horizontalCategoryModelList = horizontalCategoryModelList;
    }

    public CategoryAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_categoryitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder viewHolder, int position) {
        int resource =horizontalCategoryModelList.get(position).getCategoryimg();
        String cname= horizontalCategoryModelList.get(position).getCategoryname();
      viewHolder.setCategoryImage(resource);
      viewHolder.setCategoryName(cname);

    }

    @Override
    public int getItemCount()
    {
        return horizontalCategoryModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
       CircleImageView  image;
       TextView categoryname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            categoryname=itemView.findViewById(R.id.categoryname);
        }
        private  void setCategoryImage(int resource)
        {
            image.setImageResource(resource);
        }
        private  void setCategoryName(String cname)
        {
            categoryname.setText(cname);
        }
    }

}
