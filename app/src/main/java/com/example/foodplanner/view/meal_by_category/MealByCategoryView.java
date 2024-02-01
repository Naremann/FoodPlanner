package com.example.foodplanner.view.meal_by_category;


import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.dto.RandomMealResponse;

import java.util.List;

public interface MealByCategoryView {

    void showMeal(List<MealsItem> mealsItems);
    void showError(String error);


    void showMealById(RandomMealResponse.MealsItem mealsItem);
    void showErrorOfMealById(String error);
}
