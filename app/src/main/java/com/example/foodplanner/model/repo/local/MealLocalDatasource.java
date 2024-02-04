package com.example.foodplanner.model.repo.local;

import android.content.Context;

import com.example.foodplanner.db.MealDao;
import com.example.foodplanner.db.MyDatabase;
import com.example.foodplanner.model.dto.RandomMealResponse;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface MealLocalDatasource {
    Completable insertProductToFavorite(RandomMealResponse.MealsItem mealsItem);
    Completable deleteFavoriteProduct(RandomMealResponse.MealsItem mealsItem);
    Flowable<List<RandomMealResponse.MealsItem>> getFavMeals();
    Flowable<List<RandomMealResponse.MealsItem>> getPlannedMealByDate(String date);

    Completable addMealToWeekPlan(RandomMealResponse.MealsItem mealsItem);

    public class MealLocalDataSourceImp implements MealLocalDatasource {
        private MealDao mealDao;
        private Flowable<List<RandomMealResponse.MealsItem>> favProducts;
        private static MealLocalDataSourceImp productRepoImp =null;

        public MealLocalDataSourceImp(Context ctx) {
            MyDatabase database= MyDatabase.getInstance(ctx);
            mealDao =database.getproductDao();
            favProducts = mealDao.getAllFavMeals();

        }
        public static MealLocalDataSourceImp getInstance(Context context){
            if(productRepoImp ==null) {
                productRepoImp = new MealLocalDataSourceImp(context);
            }
            return productRepoImp;
        }

        public Flowable<List<RandomMealResponse.MealsItem>> getFavMeals() {
            return favProducts;
        }

        @Override
        public Flowable<List<RandomMealResponse.MealsItem>> getPlannedMealByDate(String date) {
            return mealDao.getPlannedMealsByDate(date);
        }

        @Override
        public Completable addMealToWeekPlan(RandomMealResponse.MealsItem mealsItem) {
            return mealDao.addMealToPlan(mealsItem).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public Completable deleteFavoriteProduct(RandomMealResponse.MealsItem mealsItem){
            return mealDao.deleteMealFromFavorite(mealsItem).
                    subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
           /* new Thread(() -> mealDao.deleteMealFromFavorite(product)){
            }.start();*/
        }


        @Override
        public Completable insertProductToFavorite(RandomMealResponse.MealsItem mealItem){
            return mealDao.insertMealToFavorite(mealItem)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

    }
}
