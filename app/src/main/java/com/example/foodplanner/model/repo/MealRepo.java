package com.example.foodplanner.model.repo;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

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

   void addMealToWeeklyPlay(RandomMealResponse.MealsItem mealsItem, String email, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener);
   Observable<List<RandomMealResponse.MealsItem>> getWeeklyPlannedMealsObservable(String date);

}
