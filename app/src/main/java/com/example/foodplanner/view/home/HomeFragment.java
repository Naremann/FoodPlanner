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
import com.example.foodplanner.Contract;
import com.example.foodplanner.view.AlertMessage;
import com.example.foodplanner.GlideImage;
import com.example.foodplanner.R;
import com.example.foodplanner.model.RandomMealResponse;
import com.example.foodplanner.model.repo.meal.MealRepoImp;
import com.example.foodplanner.model.repo.meal.remote.MealRemoteDataSourceImp;
import com.example.foodplanner.presenter.home.HomePresenter;
import com.example.foodplanner.presenter.home.HomePresenterImp;
import com.google.android.material.card.MaterialCardView;

public class HomeFragment extends Fragment implements HomeView{
    ImageView mealImg;
    TextView mealTitle;
    HomePresenter homePresenter;
    MaterialCardView cardView;
    public Navigator navigator;
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
        cardView=view.findViewById(R.id.meal_card_view);
        cardView.setOnClickListener(v -> navigateToMealDetailsFragment());
    }

    private void navigateToMealDetailsFragment() {
        navigator.navigateToMealDetails();
    }

    @Override
    public void showSuccessMessage(RandomMealResponse.MealsItem mealsItem) {
        sendDataToActivity(mealsItem);
        mealTitle.setText(mealsItem.getStrMeal());
        Log.e("TAG", "showSuccessMessage: "+mealsItem.getStrImageSource());
        GlideImage.downloadImageToImageView(mealImg.getContext(),mealsItem.getStrMealThumb(),mealImg);

    }

    @Override
    public void showErrorMessage(String error) {
        AlertMessage.showToastMessage(error,this.getContext());
    }
    private void sendDataToActivity(RandomMealResponse.MealsItem mealsItems) {
        if (isAdded() && (!isRemoving()|| getActivity()!=null)) {
            ((Contract.Presenter) requireActivity()).sendDataToActivity(mealsItems);


        }
    }

}