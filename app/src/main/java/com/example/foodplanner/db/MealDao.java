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
    @Query("Select * from mealItem")
    Flowable<List<RandomMealResponse.MealsItem>> getAllFavMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMealToFavorite(RandomMealResponse.MealsItem product);
    @Delete
    Completable deleteMealFromFavorite(RandomMealResponse.MealsItem product);


}
