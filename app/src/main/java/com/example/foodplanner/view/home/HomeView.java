package com.example.foodplanner.view.home;

import com.example.foodplanner.model.RandomMealResponse;

public interface HomeView {
    void showSuccessMessage(RandomMealResponse.MealsItem mealsItem);
    void showErrorMessage(String error);
}
