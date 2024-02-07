package com.example.foodplanner.api;

import com.example.foodplanner.model.dto.RandomMealResponse;

import java.util.List;

public interface CategoryMealCallBack {
    void onSuccessCategoryMeal(List<RandomMealResponse.MealsItem> mealsItems);
    void onFailureCategoryMeal(String errorMessage);

    void onSuccessByMealId(RandomMealResponse.MealsItem mealsItem);
    void onFailureByMealId(String errorMessage);
}
