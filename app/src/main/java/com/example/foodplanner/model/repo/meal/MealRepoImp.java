package com.example.foodplanner.model.repo.meal;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.repo.meal.local.MealLocalDataSource;
import com.example.foodplanner.model.repo.meal.remote.MealRemoteDataSource;

public class MealRepoImp implements MealRepo{
    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;

    public MealRepoImp(MealRemoteDataSource mealRemoteDataSource) {
        this.mealRemoteDataSource = mealRemoteDataSource;
    }

    @Override
    public void getRandomMeal(MealCallBack mealCallBack) {
        mealRemoteDataSource.getMeal(mealCallBack);
    }
}
