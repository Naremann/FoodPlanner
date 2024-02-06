package com.example.foodplanner.view.calender;

import com.example.foodplanner.model.dto.RandomMealResponse;

import java.util.List;

public interface CalenderView {
    void onGetAllPlannedMeals(List<RandomMealResponse.MealsItem> plannedMeals,String date);
    void onGetAllPlannedMealsError(String errorMessage);
    void showWeeklyPlannedMeals(List<RandomMealResponse.MealsItem> mealsItems,String date);
    void showPlannedMealsErrorFireStore(String error);

    void onInsertFavSuccess();

    void onInsertFavError(String localizedMessage);
}
