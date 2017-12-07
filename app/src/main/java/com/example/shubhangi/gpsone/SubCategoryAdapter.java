package com.example.shubhangi.gpsone;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shubhangi on 06-12-2017.
 */

 class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {
    private List<SubCategory> categoryList;
    SubCategoryAdapter(List<SubCategory> subCategories){
        categoryList=subCategories;
    }

    @Override
    public SubCategoryAdapter.SubCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem,parent,false);
        return new SubCategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SubCategoryAdapter.SubCategoryViewHolder holder, int position) {
        SubCategory sc= categoryList.get(position);
        holder.tv.setText(sc.getName());
        holder.img.setImageResource(sc.getImage());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class SubCategoryViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView tv;
        ImageView img;
        SubCategoryViewHolder(View itemView) {
            super(itemView);
            cv=itemView.findViewById(R.id.cv);
            tv=itemView.findViewById(R.id.cat_name);
            img=itemView.findViewById(R.id.cate_photo);
        }
    }
}
