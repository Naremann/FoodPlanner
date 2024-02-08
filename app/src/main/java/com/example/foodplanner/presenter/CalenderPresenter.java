package com.example.foodplanner.presenter;

import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.MealRepo;
import com.example.foodplanner.view.calender.CalenderView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface CalenderPresenter {
    void getPlannedMealsByDate(String date);
    void addMealToFavorite(MealsItem mealsItem);
    void deleteMealFromWeeklyPlan(MealsItem mealsItem);




    class CalenderPresenterImp implements CalenderPresenter {
        CalenderView calenderView;
        MealRepo mealRepo;

        public CalenderPresenterImp(CalenderView calenderView, MealRepo mealRepo) {
            this.calenderView = calenderView;
            this.mealRepo = mealRepo;
        }

        @Override
        public void getPlannedMealsByDate(String date) {
            mealRepo.getAllPlannedMeals(date)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mealsItems->calenderView.onGetAllPlannedMeals(mealsItems,date),
                            error->calenderView.onGetAllPlannedMealsError(error.getLocalizedMessage()));
        }
        @Override
        public void addMealToFavorite(MealsItem mealsItem) {
            mealRepo.insertMealToFavRemoteAndLocal(mealsItem)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(()->calenderView.onInsertFavSuccess(),error->calenderView.onInsertFavError(error.getLocalizedMessage()));
        }

        @Override
        public void deleteMealFromWeeklyPlan(MealsItem mealsItem) {
            mealRepo.deletePlannedMealRemoteAndLocal(mealsItem)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(()->calenderView.onDeletePlannedMealSuccess(),error->calenderView.onDeletePlannedMealError(error.getLocalizedMessage()));
        }

    }
}