package com.example.foodplanner.view.calender;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.GlideImage;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.view.fav_meal.FavMealAdapter;

import java.util.List;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {


    List<RandomMealResponse.MealsItem> mealsItems;

    public CalenderAdapter(List<RandomMealResponse.MealsItem> mealsItems) {
        this.mealsItems = mealsItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.meal_plan_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RandomMealResponse.MealsItem mealsItem=mealsItems.get(position);
        GlideImage.downloadImageToImageView(holder.mealImg.getContext(),
                mealsItem.getStrMealThumb(),holder.mealImg);
    }

    @Override
    public int getItemCount() {
        return mealsItems.size();
    }

    public void changeData(List<RandomMealResponse.MealsItem> mealsItems){
        this.mealsItems=mealsItems;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mealImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImg=itemView.findViewById(R.id.meal_img);
        }
    }
}
