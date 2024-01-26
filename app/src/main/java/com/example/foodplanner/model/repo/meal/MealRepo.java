package com.example.foodplanner.model.repo.meal;

import com.example.foodplanner.api.MealCallBack;

public interface MealRepo {
   void getRandomMeal(MealCallBack mealCallBack);

}
