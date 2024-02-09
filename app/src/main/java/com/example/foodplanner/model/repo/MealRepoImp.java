package com.example.foodplanner.model.repo;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.dto.Country;
import com.example.foodplanner.model.dto.Ingredient;
import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.repo.remote.MealRemoteDataSource;
import com.example.foodplanner.model.repo.remote.RandomMealRemoteDataSource;
import com.example.foodplanner.model.repo.local.MealLocalDatasource;

import java.util.ArrayList;
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
    public Flowable<List<MealsItem>> getAllFavMeals() {
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
    public Flowable<List<MealsItem>> getAllPlannedMeals(String date) {
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
    public Observable<List<Ingredient>> getIngredients() {
        return mealRemoteDataSource.getIngredients();
    }

    @Override
    public Observable<List<MealsItem>> getMealsByIngredient(String ingredient) {
        return mealRemoteDataSource.getMealsByIngredient(ingredient);
    }

    @Override
    public Observable<List<MealsItem>> getMealByCategory(String category) {
        return mealRemoteDataSource.getMealByCategory(category);
    }

    @Override
    public Observable<Object> getMealById(String mealId) {
        return mealRemoteDataSource.getMealById(mealId);
    }

    @Override
    public Observable<List<Country>> getCountries() {
        return mealRemoteDataSource.getCountries();
    }

    @Override
    public Observable<List<MealsItem>> getMealsByCountry(String country) {
        return mealRemoteDataSource.getMealsByCountry(country);
    }

    @Override
    public Observable<List<MealsItem>> searchMeals(String name) {
        return mealRemoteDataSource.searchMeals(name);
    }

    @Override
    public Completable deleteFromRemoteAndLocal(MealsItem mealsItem) {
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
    public Completable deletePlannedMealRemoteAndLocal(MealsItem mealsItem) {
        Completable deleteRemote = Completable.create(emitter -> {
            mealRemoteDataSource.deletePlannedMealFireStore(mealsItem, unused -> emitter.onComplete(), e -> emitter.onError(new IllegalStateException(e.getLocalizedMessage())));
        });
        Completable deleteLocal=mealLocalDataSource.deleteFavoriteProduct(mealsItem);
        return Completable.mergeArray(deleteRemote,deleteLocal);
    }

    @Override
    public Completable insertMealToFavRemoteAndLocal(MealsItem mealsItem) {
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
    public Completable insertMealToWeeklyPlanRemoteAndLocal(MealsItem mealsItem) {
        mealsItem.setPlanned(true);
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

    private Flowable<List<MealsItem>> fetchAndSaveFavMealsFromRemote() {
        return mealRemoteDataSource.getFavMealsFromFireStore()
                .flatMapCompletable(mealsItems -> {
                    Completable insertCompletable = Completable.complete();
                    for (MealsItem mealItem : mealsItems) {
                        insertCompletable = insertCompletable
                                .andThen(mealLocalDataSource.insertProductToFavorite(mealItem));
                    }
                    return insertCompletable.andThen(Completable.fromAction(() -> {
                    }));
                })
                .andThen(mealLocalDataSource.getFavMeals());
    }
    @Override
    public Flowable<List<MealsItem>> fetchAndSavePlannedMealsFromRemote(String date) {
        return mealRemoteDataSource.getWeeklyPlannedMealsObservable(date)
                .flatMapCompletable(mealsItems -> {
                    Completable insertCompletable = Completable.complete();
                    for (MealsItem mealsItem : mealsItems) {
                        insertCompletable = insertCompletable.andThen(mealLocalDataSource.addMealToWeekPlan(mealsItem));
                    }
                    return insertCompletable.andThen(Completable.fromAction(() -> {
                    }));
                }).andThen(mealLocalDataSource.getPlannedMealByDate(date));
    }



}
