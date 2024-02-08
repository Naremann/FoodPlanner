package com.example.foodplanner.view.calender;

import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.dto.RandomMealResponse;

import java.util.List;

public interface CalenderView {
    void onGetAllPlannedMeals(List<MealsItem> plannedMeals, String date);
    void onGetAllPlannedMealsError(String errorMessage);
    void onInsertFavSuccess();
    void onInsertFavError(String localizedMessage);
    void onDeletePlannedMealSuccess();

    void onDeletePlannedMealError(String localizedMessage);
}
