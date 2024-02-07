package com.example.foodplanner.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.dto.RandomMealResponse.MealsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDao {
    @Query("Select * from mealItem WHERE is_favorite = 1")
    Flowable<List<RandomMealResponse.MealsItem>> getAllFavMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMealToFavorite(RandomMealResponse.MealsItem mealsItem);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addMealToPlan(MealsItem mealsItem);
    @Delete
    Completable deleteMealFromFavorite(RandomMealResponse.MealsItem mealsItem);

    @Query("select * from mealItem where dateModified = :date AND is_planned=1")
    Flowable<List<MealsItem>> getPlannedMealsByDate(String date);


}
