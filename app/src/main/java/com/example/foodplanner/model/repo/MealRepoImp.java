package com.example.foodplanner.model.repo;

import android.util.Log;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.remote.MealRemoteDataSource;
import com.example.foodplanner.model.repo.remote.RandomMealRemoteDataSource;
import com.example.foodplanner.model.repo.local.MealLocalDatasource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public class MealRepoImp implements MealRepo{
    RandomMealRemoteDataSource randomMealRemoteDataSource;
    MealLocalDatasource mealLocalDataSource;
    MealRemoteDataSource mealRemoteDataSource;

    public MealRepoImp(RandomMealRemoteDataSource randomMealRemoteDataSource,MealLocalDatasource mealLocalDataSource,MealRemoteDataSource mealRemoteDataSource) {
        this.randomMealRemoteDataSource = randomMealRemoteDataSource;
        this.mealLocalDataSource=mealLocalDataSource;
        this.mealRemoteDataSource=mealRemoteDataSource;
    }

    public MealRepoImp(MealLocalDatasource mealLocalDataSource, MealRemoteDataSource mealRemoteDataSource) {
        this.mealLocalDataSource = mealLocalDataSource;
        this.mealRemoteDataSource = mealRemoteDataSource;
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

    @Override
    public void addMealToWeeklyPlay(RandomMealResponse.MealsItem mealsItem, String email, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        mealRemoteDataSource.addMealToFav(mealsItem,onSuccessListener,onFailureListener);

    }

    @Override
    public Observable<List<RandomMealResponse.MealsItem>> getWeeklyPlannedMealsObservable(String date) {


        Log.d("TAG", "Fetching data from Firestore");
        return mealRemoteDataSource.getWeeklyPlannedMealsObservable(date)
                .doOnSubscribe(disposable -> Log.d("TAG", "Subscribed to Firestore Observable"))
                .doOnNext(meals -> Log.d("TAG", "Received data from Firestore: " + meals.size() + " items"))
                .doOnError(error -> Log.e("TAG", "Error fetching data from Firestore", error))
                .switchIfEmpty(Observable.defer(() -> {
                    Log.d("TAG", "Firestore Observable is empty, fetching data from Room");
                    List<RandomMealResponse.MealsItem> localMeals = mealLocalDataSource.getPlannedMealByDate(date).blockingFirst();
                    if (localMeals.isEmpty()) {
                        return Observable.empty(); // Return empty Observable if local storage is empty
                    } else {
                        return Observable.just(localMeals);
                    }
                }));
        /*Log.d("TAG", "Fetching data from Firestore");
        return mealRemoteDataSource.getWeeklyPlannedMealsObservable(date)
                .doOnSubscribe(disposable -> Log.d("TAG", "Subscribed to Firestore Observable"))
                .doOnNext(meals -> Log.d("TAG", "Received data from Firestore: " + meals.size() + " items"))
                .doOnError(error -> Log.e("TAG", "Error fetching data from Firestore", error))
                .switchIfEmpty(Observable.fromCallable(() -> {
                    Log.d("TAG", "Firestore Observable is empty, fetching data from Room");
                    return mealLocalDataSource.getPlannedMealByDate(date).blockingFirst();
                }));*/
    }



}
