package com.example.foodplanner.model.repo.meal.remote;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.RandomMealResponse;

public interface MealRemoteDataSource {
    RandomMealResponse.MealsItem getMeal(MealCallBack mealCallBack);
}
