package com.example.foodplanner.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodplanner.ContactPresenterImp;
import com.example.foodplanner.Contract;
import com.example.foodplanner.R;
import com.example.foodplanner.model.RandomMealResponse;
import com.example.foodplanner.view.FragmentNavigator;
import com.example.foodplanner.view.home.HomeFragment;
import com.example.foodplanner.view.home.Navigator;
import com.example.foodplanner.view.meal_details.MealDetailsFragment;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements Contract.Presenter,Contract.View, Navigator {
    Contract.Presenter presenter;
    MealDetailsFragment mealDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter=new ContactPresenterImp(this);
        mealDetailsFragment=new MealDetailsFragment();
        HomeFragment homeFragment = new HomeFragment();
        FragmentNavigator.addFragment(homeFragment,this,R.id.home_fragment_container);
        homeFragment.navigator=this;

    }


    @Override
    public void sendDataToActivity(RandomMealResponse.MealsItem mealsItems) {
        presenter.sendDataToActivity(mealsItems);
        showDataInDetailsFragment(mealsItems);
    }

    @Override
    public void showDataInDetailsFragment(RandomMealResponse.MealsItem mealsItems) {
        //mealDetailsFragment=new MealDetailsFragment();
        mealDetailsFragment.displayData(mealsItems);
    }

    @Override
    public void navigateToMealDetails() {
        FragmentNavigator.addFragment(mealDetailsFragment,this,R.id.home_fragment_container);
    }
}