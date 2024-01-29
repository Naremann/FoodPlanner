package com.example.foodplanner.model.repo.random_meal.remote;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.dto.RandomMealResponse;

public interface RandomMealRemoteDataSource {
    RandomMealResponse.MealsItem getMeal(MealCallBack mealCallBack);
}
