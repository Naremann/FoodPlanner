package com.example.foodplanner;

import com.example.foodplanner.model.RandomMealResponse;

import java.util.List;

public interface Contract {
    interface View{
        void showDataInDetailsFragment(RandomMealResponse.MealsItem mealsItems);

    }
    interface Presenter{
        void sendDataToActivity(RandomMealResponse.MealsItem mealsItems);
       // void getDataFromHomeFragment(RandomMealResponse.MealsItem mealsItems);
    }
}
