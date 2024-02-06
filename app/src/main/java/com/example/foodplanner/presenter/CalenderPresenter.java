package com.example.foodplanner.presenter;

import androidx.annotation.NonNull;

import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.MealRepo;
import com.example.foodplanner.view.calender.CalenderView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface CalenderPresenter {
    void getPlannedMealsByDate(String date);
    public void getWeeklyPlannedMealsFirestore(String date);
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
            mealRepo.getPlannedMealsByDate(date).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(meals -> calenderView.onGetAllPlannedMeals((List<RandomMealResponse.MealsItem>) meals, date),
                            error -> calenderView.onGetAllPlannedMealsError(error.toString()));
        }

        @Override
        public void getWeeklyPlannedMealsFirestore(String date) {
            Disposable disposable = mealRepo.getWeeklyPlannedMealsObservable(date)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            meals -> calenderView.showWeeklyPlannedMeals(meals,date),
                            throwable -> calenderView.showPlannedMealsErrorFireStore(throwable.getMessage())
                    );
        }

        @Override
        public void addMealToFavorite(RandomMealResponse.MealsItem mealsItem) {
            mealRepo.addMealToFavorite(mealsItem).
                    subscribe(()->calenderView.onInsertFavSuccess(),
                            error->calenderView.onInsertFavError(error.getLocalizedMessage()));
        }

    }
}