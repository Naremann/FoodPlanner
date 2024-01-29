package com.example.foodplanner.presenter.meal_by_category;

import android.util.Log;
import com.example.foodplanner.api.CategoryMealCallBack;
import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.repo.meal.MealRemoteDataSource;
import com.example.foodplanner.view.meal_by_category.MealByCategoryView;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface MealPresenter {
    void getMealByCategory(String category);

    public class MealPresenterImp implements MealPresenter, CategoryMealCallBack {
        MealRemoteDataSource mealRemoteDataSource;
        MealByCategoryView meal;
        CompositeDisposable compositeDisposable;

        public MealPresenterImp(MealRemoteDataSource mealRemoteDataSource, MealByCategoryView meal) {
            this.mealRemoteDataSource = mealRemoteDataSource;
            this.meal = meal;
            this.compositeDisposable = new CompositeDisposable();
        }

        @Override
        public void getMealByCategory(String category) {
            compositeDisposable.add(
                    mealRemoteDataSource.getMealByCategory(category)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    this::handleSuccess,
                                    this::handleError
                            )
            );
        }

        private void handleSuccess(Object o) {
            meal.showMeal((List<MealsItem>)o);
        }

        private void handleSuccess(List<MealsItem> mealsItems) {
            meal.showMeal(mealsItems);
        }

        private void handleError(Throwable throwable) {
            Log.e("MealPresenter", "Error: " + throwable.getLocalizedMessage());
            meal.showError("Error loading meals");
        }

        @Override
        public void onSuccessCategoryMeal(List<MealsItem> mealsItems) {
            handleSuccess(mealsItems);
        }

        @Override
        public void onFailureCategoryMeal(String errorMessage) {
            handleError(new Throwable(errorMessage));
        }
        public void onDestroy() {
            compositeDisposable.clear();
        }
    }
}

