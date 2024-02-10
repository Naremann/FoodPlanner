package com.example.foodplanner.view.calender;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.GlideImage;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.MealsItem;

import org.w3c.dom.Text;

import java.util.List;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {


    List<MealsItem> mealsItems;
    OnCancelClickListener onCancelClickListener;
    OnItemClickListener onItemClickListener;

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
        holder.cancelImg.setOnClickListener(v -> onCancelClickListener.onItemClick(mealsItem));
        holder.mealName.setText(mealsItem.getStrMeal());
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(mealsItem));
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
        TextView mealName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImg=itemView.findViewById(R.id.meal_img);
            cancelImg=itemView.findViewById(R.id.cancel_img);
            mealName=itemView.findViewById(R.id.meal_name);
        }
    }

    public interface OnCancelClickListener{
        void onItemClick(MealsItem mealsItem);
    }
    public interface OnItemClickListener{
        void onItemClick(MealsItem mealsItem);
    }
}
