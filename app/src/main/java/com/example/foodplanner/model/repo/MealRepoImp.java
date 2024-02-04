package com.example.foodplanner.model.repo;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.remote.RandomMealRemoteDataSource;
import com.example.foodplanner.model.repo.local.MealLocalDatasource;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealRepoImp implements MealRepo{
    RandomMealRemoteDataSource randomMealRemoteDataSource;
    MealLocalDatasource mealLocalDataSource;

    public MealRepoImp(RandomMealRemoteDataSource randomMealRemoteDataSource,MealLocalDatasource mealLocalDataSource) {
        this.randomMealRemoteDataSource = randomMealRemoteDataSource;
        this.mealLocalDataSource=mealLocalDataSource;
    }

    @Override
    public void getRandomMeal(MealCallBack mealCallBack) {
        randomMealRemoteDataSource.getMeal(mealCallBack);
    }

    @Override
    public Completable addMealToFavorite(RandomMealResponse.MealsItem mealsItem) {
        return mealLocalDataSource.insertProductToFavorite(mealsItem);
    }

    @Override
    public Flowable getFavMeals() {
        return mealLocalDataSource.getFavMeals();
    }

    @Override
    public Flowable getPlannedMealsByDate(String date) {
        return mealLocalDataSource.getPlannedMealByDate(date);
    }

    @Override
    public Completable deleteMealFromFav(RandomMealResponse.MealsItem mealItem) {
        return mealLocalDataSource.deleteFavoriteProduct(mealItem);
    }

    @Override
    public Completable addMealToWeeklyPlay(RandomMealResponse.MealsItem mealsItem) {
        return mealLocalDataSource.addMealToWeekPlan(mealsItem);
    }


}
