package com.example.foodplanner.model.repo;

import android.util.Log;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.remote.MealRemoteDataSource;
import com.example.foodplanner.model.repo.remote.RandomMealRemoteDataSource;
import com.example.foodplanner.model.repo.local.MealLocalDatasource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

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
    public void addMealToFav(RandomMealResponse.MealsItem mealsItem, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        mealRemoteDataSource.addMealToPlan(mealsItem,onSuccessListener,onFailureListener);
    }

    @Override
    public void addMealToWeeklyPlay(RandomMealResponse.MealsItem mealsItem, String email, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        mealRemoteDataSource.addMealToPlan(mealsItem,onSuccessListener,onFailureListener);
    }

    @Override
    public Observable<List<RandomMealResponse.MealsItem>> getWeeklyPlannedMealsObservable(String date) {
        return mealRemoteDataSource.getWeeklyPlannedMealsObservable(date)
                .doOnSubscribe(disposable -> Log.d("TAG", "Subscribed to Firestore Observable"))
                .doOnNext(meals -> Log.d("TAG", "Received data from Firestore: " + meals.size() + " items"))
                .doOnError(error -> Log.e("TAG", "Error fetching data from Firestore", error))
                .switchIfEmpty(Observable.defer(() -> {
                    List<RandomMealResponse.MealsItem> localMeals = mealLocalDataSource.getPlannedMealByDate(date).blockingFirst();
                    if (localMeals.isEmpty()) {
                        return Observable.empty();
                    } else {
                        return Observable.fromArray(localMeals);
                    }
                }));
    }

    @Override
    public Flowable<List<RandomMealResponse.MealsItem>> getAllFavMeals() {
        return mealLocalDataSource.getFavMeals()
                .flatMap(favMeals -> {
                    if (favMeals.isEmpty()) {
                        return fetchAndSaveFavMealsFromRemote();
                    } else {
                        return Flowable.just(favMeals);
                    }
                });
    }

    @Override
    public Flowable<List<RandomMealResponse.MealsItem>> getAllPlannedMeals(String date) {
        return mealLocalDataSource.getPlannedMealByDate(date)
                .flatMap(mealsItems -> {
                    if(mealsItems.isEmpty()){
                       return fetchAndSavePlannedMealsFromRemote(date);
                    }
                    else {
                        return Flowable.just(mealsItems);
                    }
                })
                ;
    }



    @Override
    public Completable deleteFromRemoteAndLocal(RandomMealResponse.MealsItem mealsItem) {
        Completable deleteFromRemote=Completable.create(emitter -> {
            mealRemoteDataSource.deleteFavoriteMealFromFireStore(mealsItem, unused -> {
                emitter.onComplete();
            }, e -> {
                emitter.onError(new IllegalStateException(e.getLocalizedMessage()));
            });
        });
        Completable deleteFromLocal=mealLocalDataSource.deleteFavoriteProduct(mealsItem);
        return Completable.mergeArray(deleteFromRemote,deleteFromLocal);
    }

    @Override
    public Completable insertMealToFavRemoteAndLocal(RandomMealResponse.MealsItem mealsItem) {
        Completable insertRemote=Completable.create(emitter -> {
            mealRemoteDataSource.addMealToFav(mealsItem, unused -> {
                emitter.onComplete();
            }, e -> {
                emitter.onError(new IllegalStateException(e.getLocalizedMessage()));
            });
        });
        Completable insertLocal=mealLocalDataSource.insertProductToFavorite(mealsItem);
        return Completable.mergeArray(insertRemote,insertLocal);
    }

    @Override
    public Completable insertMealToWeeklyPlanRemoteAndLocal(RandomMealResponse.MealsItem mealsItem) {
       Completable insertRemote=Completable.create(emitter -> {
           mealRemoteDataSource.addMealToPlan(mealsItem, unused -> {
               emitter.onComplete();
           }, e -> {
               emitter.onError(new IllegalStateException(e.getLocalizedMessage()));
           });
       });
       Completable insertLocal=mealLocalDataSource.addMealToWeekPlan(mealsItem);
       return Completable.mergeArray(insertRemote,insertLocal);
    }

    private Flowable<List<RandomMealResponse.MealsItem>> fetchAndSaveFavMealsFromRemote() {
        return mealRemoteDataSource.getFavMealsFromFirestore()
                .flatMapCompletable(mealsItems -> {
                    Completable insertCompletable = Completable.complete();
                    for (RandomMealResponse.MealsItem mealItem : mealsItems) {
                        insertCompletable = insertCompletable
                                .andThen(mealLocalDataSource.insertProductToFavorite(mealItem));
                    }
                    return insertCompletable.andThen(Completable.fromAction(() -> {
                    }));
                })
                .andThen(mealLocalDataSource.getFavMeals());
    }
    @Override
    public Flowable<List<RandomMealResponse.MealsItem>> fetchAndSavePlannedMealsFromRemote(String date) {
        return mealRemoteDataSource.getWeeklyPlannedMealsObservable(date)
                .flatMapCompletable(mealsItems -> {
                    Completable insertCompletable = Completable.complete();
                    for (RandomMealResponse.MealsItem mealsItem : mealsItems) {
                        insertCompletable = insertCompletable.andThen(mealLocalDataSource.addMealToWeekPlan(mealsItem));
                    }
                    return insertCompletable.andThen(Completable.fromAction(() -> {
                    }));
                }).andThen(mealLocalDataSource.getPlannedMealByDate(date));
    }



}
