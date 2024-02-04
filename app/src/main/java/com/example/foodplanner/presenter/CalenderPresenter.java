package com.example.foodplanner.presenter;

import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.MealRepo;
import com.example.foodplanner.view.calender.CalenderView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface CalenderPresenter {
    void getPlannedMealsByDate(String date);

     class CalenderPresenterImp implements CalenderPresenter{
         CalenderView calenderView;
         MealRepo mealRepo;

         public CalenderPresenterImp(CalenderView calenderView, MealRepo mealRepo) {
             this.calenderView = calenderView;
             this.mealRepo = mealRepo;
         }
         @Override
         public void getPlannedMealsByDate(String date) {
             mealRepo.getPlannedMealsByDate(date).subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(meals->calenderView.onGetAllPlannedMeals((List<RandomMealResponse.MealsItem>) meals,date),
                             error->calenderView.onGetAllPlannedMealsError(error.toString()));
         }
     }
}
