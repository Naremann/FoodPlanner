package com.example.foodplanner.view.meal_by_category;


import com.example.foodplanner.model.dto.MealsItem;

import java.util.List;

public interface MealByCategoryView {

    void showMeal(List<MealsItem> mealsItems);
    void showError(String error);
}
