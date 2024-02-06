package com.example.foodplanner.presenter;

import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.MealRepo;
import com.example.foodplanner.view.calender.CalenderView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface CalenderPresenter {
    void getPlannedMealsByDate(String date);
    //public void getWeeklyPlannedMealsFirestore(String date);
    void addMealToFavorite(RandomMealResponse.MealsItem mealsItem);



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
            /*mealRepo.getPlannedMealsByDate(date).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(meals -> calenderView.onGetAllPlannedMeals((List<RandomMealResponse.MealsItem>) meals, date),
                            error -> calenderView.onGetAllPlannedMealsError(error.toString()));*/
        }

       /* @Override
        public void getWeeklyPlannedMealsFirestore(String date) {
            Disposable disposable = mealRepo.getWeeklyPlannedMealsObservable(date)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            meals -> calenderView.showWeeklyPlannedMeals(meals,date),
                            throwable -> calenderView.showPlannedMealsErrorFireStore(throwable.getMessage())
                    );
        }*/

        @Override
        public void addMealToFavorite(RandomMealResponse.MealsItem mealsItem) {
            mealRepo.insertMealToFavRemoteAndLocal(mealsItem)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(()->calenderView.onInsertFavSuccess(),error->calenderView.onInsertFavError(error.getLocalizedMessage()));
          /*  mealRepo.addMealToFavorite(mealsItem).
                    subscribe(()->calenderView.onInsertFavSuccess(),
                            error->calenderView.onInsertFavError(error.getLocalizedMessage()));*/
        }

    }
}