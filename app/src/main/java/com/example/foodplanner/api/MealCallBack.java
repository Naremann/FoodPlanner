package com.example.foodplanner.api;

import com.example.foodplanner.model.RandomMealResponse;

public interface MealCallBack {
    void onSuccess(RandomMealResponse.MealsItem mealsItem);
    void onFailure(String errorMessage);
}
