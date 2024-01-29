package com.example.foodplanner.view.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodplanner.GlideImage;
import com.example.foodplanner.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplanner.model.dto.CategoryResponse.CategoriesItem;
import com.example.foodplanner.model.dto.CategoryResponse;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<CategoryResponse.CategoriesItem> categoriesItems;
    OnItemClickListener onItemClickListener;

    public CategoryAdapter(List<CategoryResponse.CategoriesItem> categoriesItems) {
        this.categoriesItems = categoriesItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriesItem categoriesItem=categoriesItems.get(position);
        holder.categoryName.setText(categoriesItem.getStrCategory());
        GlideImage.downloadImageToImageView(holder.categoryImg.getContext(),categoriesItem.getStrCategoryThumb(),holder.categoryImg);
        holder.itemView.setOnClickListener(view->{
            onItemClickListener.onItemClick(categoriesItem);
        });
    }

    @Override
    public int getItemCount() {
        return categoriesItems.size();
    }
    void changeData(List<CategoryResponse.CategoriesItem> categoriesItems){
        this.categoriesItems=categoriesItems;
        notifyDataSetChanged();

    }

    interface OnItemClickListener{
        void onItemClick(CategoriesItem categoriesItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        ImageView categoryImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName=itemView.findViewById(R.id.category_name);
            categoryImg=itemView.findViewById(R.id.category_img);

        }

    }
}
