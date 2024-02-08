package com.example.foodplanner.model.repo;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.dto.Ingredient;
import com.example.foodplanner.model.dto.IngredientResponse;
import com.example.foodplanner.model.dto.MealResponse;
import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public interface MealRepo {
   void getRandomMeal(MealCallBack mealCallBack);
   Completable addMealToFavorite(MealsItem mealsItem);
   Flowable getFavMeals();
   Flowable getPlannedMealsByDate(String date);
   Completable deleteMealFromFav(MealsItem mealItem);
   Completable addMealToWeeklyPlay(MealsItem mealsItem);

   void addMealToFav(MealsItem mealsItem, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener);

   void addMealToWeeklyPlay(MealsItem mealsItem, String email, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener);
   Observable<List<MealsItem>> getWeeklyPlannedMealsObservable(String date);


    Flowable<List<MealsItem>> getAllFavMeals();
    Completable deleteFromRemoteAndLocal(MealsItem mealsItem);
    Completable deletePlannedMealRemoteAndLocal(MealsItem mealsItem);
    Completable insertMealToFavRemoteAndLocal(MealsItem mealsItem);
    Completable insertMealToWeeklyPlanRemoteAndLocal(MealsItem mealsItem);
    Flowable<List<MealsItem>> fetchAndSavePlannedMealsFromRemote(String date);
    Flowable<List<MealsItem>> getAllPlannedMeals(String date);
    Observable<List<Ingredient>> getIngredients();
    Observable<List<MealsItem>> getMealsByIngredient(String ingredient);
    @NonNull Observable<List<MealsItem>> getMealByCategory(String category);
    Observable<Object> getMealById(String mealId);
}
