package com.example.foodplanner.model.repo;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.dto.RandomMealResponse;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface MealRepo {
   void getRandomMeal(MealCallBack mealCallBack);
   Completable addMealToFavorite(RandomMealResponse.MealsItem mealsItem);
   Flowable getFavMeals();
   Completable deleteMealFromFav(RandomMealResponse.MealsItem mealItem);

}
