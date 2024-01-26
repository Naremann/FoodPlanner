package com.example.foodplanner.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodplanner.view.AlertMessage;
import com.example.foodplanner.GlideImage;
import com.example.foodplanner.R;
import com.example.foodplanner.model.RandomMealResponse;
import com.example.foodplanner.model.repo.meal.MealRepoImp;
import com.example.foodplanner.model.repo.meal.remote.MealRemoteDataSourceImp;
import com.example.foodplanner.presenter.home.HomePresenter;
import com.example.foodplanner.presenter.home.HomePresenterImp;

public class HomeFragment extends Fragment implements HomeView{
    ImageView mealImg;
    TextView mealTitle;
    HomePresenter homePresenter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homePresenter=new HomePresenterImp(this,new MealRepoImp(new MealRemoteDataSourceImp()));
        homePresenter.getRandomMeal();
       intiViews(view);


    }

    private void intiViews(View view) {
        mealImg=view.findViewById(R.id.meal_img);
        mealTitle=view.findViewById(R.id.meal_title_tv);
    }

    @Override
    public void showSuccessMessage(RandomMealResponse.MealsItem mealsItem) {
        mealTitle.setText(mealsItem.getStrMeal());
        Log.e("TAG", "showSuccessMessage: "+mealsItem.getStrImageSource());
        GlideImage.downloadImageToImageView(this.getContext(),mealsItem.getStrMealThumb(),mealImg);

    }

    @Override
    public void showErrorMessage(String error) {
        AlertMessage.showToastMessage(error,this.getContext());
    }

}