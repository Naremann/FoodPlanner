package com.example.foodplanner.view.calender;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.GlideImage;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.MealsItem;
import java.util.List;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {


    List<MealsItem> mealsItems;
    OnCancelClickListener onCancelClickListener;

    public CalenderAdapter(List<MealsItem> mealsItems) {
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
        MealsItem mealsItem=mealsItems.get(position);
        GlideImage.downloadImageToImageView(holder.mealImg.getContext(),
                mealsItem.getStrMealThumb(),holder.mealImg);
        holder.cancelImg.setOnClickListener(v -> {
            Log.e("TAG", "onBindViewHolder: "+mealsItem );
                onCancelClickListener.onItemClick(mealsItem);
        });
    }

    @Override
    public int getItemCount() {
        return mealsItems.size();
    }

    public void changeData(List<MealsItem> mealsItems){
        this.mealsItems=mealsItems;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mealImg,cancelImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImg=itemView.findViewById(R.id.meal_img);
            cancelImg=itemView.findViewById(R.id.cancel_img);
        }
    }

    public interface OnCancelClickListener{
        void onItemClick(MealsItem mealsItem);
    }
}
