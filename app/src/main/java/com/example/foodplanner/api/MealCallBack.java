package com.example.foodplanner.api;

import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.dto.RandomMealResponse;

public interface MealCallBack {
    void onSuccess(MealsItem mealsItem);
    void onFailure(String errorMessage);
}
