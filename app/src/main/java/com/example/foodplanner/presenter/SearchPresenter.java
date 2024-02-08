package com.example.foodplanner.presenter;

import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.remote.MealRemoteDataSource;
import com.example.foodplanner.view.serach_meal.SearchMealView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface SearchPresenter {
    void searchMealByName(String name);
     void getMealById(String idMeal);

     class SearchPresenterImp implements SearchPresenter{
        MealRemoteDataSource mealRemoteDataSource;
        SearchMealView searchMealView;
        CompositeDisposable compositeDisposable;

        public SearchPresenterImp(MealRemoteDataSource mealRemoteDataSource, SearchMealView searchMealView) {
            this.mealRemoteDataSource = mealRemoteDataSource;
            this.searchMealView = searchMealView;
            this.compositeDisposable = new CompositeDisposable();
        }

        @Override
        public void searchMealByName(String name) {
            compositeDisposable.add(mealRemoteDataSource.searchMeals(name)
                    .subscribeWith(new DisposableObserver<List<MealsItem>>() {
                        @Override
                        public void onNext(List<MealsItem> meals) {
                            searchMealView.showMeal(meals);
                        }

                        @Override
                        public void onError(Throwable e) {
                            searchMealView.showError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            // Handle completion if needed
                        }
                    }));
        }

        @Override
        public void getMealById(String idMeal) {
            compositeDisposable.add(
                    mealRemoteDataSource.getMealById(idMeal)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    this::handleMealByIdSuccess,
                                    this::handleError
                            )
            );
        }
        private void handleMealByIdSuccess(Object o) {
            searchMealView.showMealById((MealsItem) o);
        }
        private void handleError(Throwable throwable) {
            searchMealView.showError("Error loading meals");
        }

        public void onDestroy() {
            compositeDisposable.clear();
        }
    }
}
