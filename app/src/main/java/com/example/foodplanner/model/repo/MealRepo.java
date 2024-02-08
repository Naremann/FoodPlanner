package com.example.foodplanner.model.repo;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.dto.Ingredient;
import com.example.foodplanner.model.dto.IngredientResponse;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public interface MealRepo {
   void getRandomMeal(MealCallBack mealCallBack);
   Completable addMealToFavorite(RandomMealResponse.MealsItem mealsItem);
   Flowable getFavMeals();
   Flowable getPlannedMealsByDate(String date);
   Completable deleteMealFromFav(RandomMealResponse.MealsItem mealItem);
   Completable addMealToWeeklyPlay(RandomMealResponse.MealsItem mealsItem);

   void addMealToFav(RandomMealResponse.MealsItem mealsItem, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener);

   void addMealToWeeklyPlay(RandomMealResponse.MealsItem mealsItem, String email, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener);
   Observable<List<RandomMealResponse.MealsItem>> getWeeklyPlannedMealsObservable(String date);


    Flowable<List<RandomMealResponse.MealsItem>> getAllFavMeals();
    Completable deleteFromRemoteAndLocal(RandomMealResponse.MealsItem mealsItem);
    Completable deletePlannedMealRemoteAndLocal(RandomMealResponse.MealsItem mealsItem);
    Completable insertMealToFavRemoteAndLocal(RandomMealResponse.MealsItem mealsItem);
    Completable insertMealToWeeklyPlanRemoteAndLocal(RandomMealResponse.MealsItem mealsItem);
    Flowable<List<RandomMealResponse.MealsItem>> fetchAndSavePlannedMealsFromRemote(String date);
    Flowable<List<RandomMealResponse.MealsItem>> getAllPlannedMeals(String date);
    Observable<List<Ingredient>> getIngredients();
}
