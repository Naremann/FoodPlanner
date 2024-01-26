package com.example.foodplanner.view.meal_details;

import static com.example.foodplanner.model.Ingredient.getIngredients;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foodplanner.GlideImage;
import com.example.foodplanner.R;
import com.example.foodplanner.YouTubeVideo;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.RandomMealResponse;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsFragment extends Fragment {
    TextView mealTitle,mealCategory, mealArea;
    ImageView mealImg;
    RecyclerView ingredientRecyclerView;
    RandomMealResponse.MealsItem mealsItem=null;
    IngredientAdapter ingredientAdapter;
    Ingredient ingredient;
    List<Ingredient> ingredients;
    WebView webView;
    ProgressBar videoProgressBar;



    public MealDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        ingredients=new ArrayList<>();
        if(mealsItem!=null){
            Log.e("TAG", "onViewCreated1: "+    mealsItem.getStrIngredient1());
            getIngredients(mealsItem);
            ingredientAdapter.changeData(getIngredients(mealsItem));
            Log.e("TAG1", "onViewCreated: "+mealsItem.getStrCategory());
            setMealDataInViews(mealsItem);


        }

    }



    private void initViews(View view) {
        mealTitle=view.findViewById(R.id.title_tv);
        mealArea =view.findViewById(R.id.area_tv);
        mealCategory=view.findViewById(R.id.category_tv);
        mealImg=view.findViewById(R.id.meal_img);
        webView=view.findViewById(R.id.web_view);
        videoProgressBar=view.findViewById(R.id.video_progress_bar);
        ingredientRecyclerView=view.findViewById(R.id.ingredient_recycler_view);
        ingredientAdapter=new IngredientAdapter(new ArrayList<>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ingredientRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientRecyclerView.setAdapter(ingredientAdapter);
    }

    public void displayData(RandomMealResponse.MealsItem mealsItem) {
        Log.e("TAG1", "displayData: " + mealsItem.getStrArea());
        this.mealsItem=mealsItem;

    }

    private void setMealDataInViews(RandomMealResponse.MealsItem mealsItem)  {
        mealTitle.setText(mealsItem.getStrMeal());
        mealCategory.setText(mealsItem.getStrCategory());
        mealArea.setText(mealsItem.getStrArea());
        GlideImage.downloadImageToImageView(this.getContext(), mealsItem.getStrMealThumb(), mealImg);
         YouTubeVideo.loadVideoUrlInWebView(webView, mealsItem.getStrYoutube(),videoProgressBar);
    }
}