package com.example.foodplanner.model.repo.random_meal;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.repo.random_meal.local.MealLocalDataSource;
import com.example.foodplanner.model.repo.random_meal.remote.RandomMealRemoteDataSource;

public class MealRepoImp implements MealRepo{
    RandomMealRemoteDataSource randomMealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;

    public MealRepoImp(RandomMealRemoteDataSource randomMealRemoteDataSource) {
        this.randomMealRemoteDataSource = randomMealRemoteDataSource;
    }

    @Override
    public void getRandomMeal(MealCallBack mealCallBack) {
        randomMealRemoteDataSource.getMeal(mealCallBack);
    }
}
