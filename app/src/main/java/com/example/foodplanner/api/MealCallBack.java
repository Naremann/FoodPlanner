package com.example.foodplanner.api;

import com.example.foodplanner.model.dto.RandomMealResponse;

public interface MealCallBack {
    void onSuccess(RandomMealResponse.MealsItem mealsItem);
    void onFailure(String errorMessage);
}
