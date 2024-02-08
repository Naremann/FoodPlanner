package com.example.foodplanner.view.serach_meal;

import androidx.annotation.NonNull;

import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.dto.RandomMealResponse;

import java.util.List;

public interface SearchMealView {

    void showMeal(List<MealsItem> meals);

    void showError(String message);

    void showMealById(MealsItem o);
}
