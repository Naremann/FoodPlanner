package com.example.foodplanner.presenter;

import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.MealRepo;
import com.example.foodplanner.view.meal_details.MealDetailsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface MealDetailsPresenter {
     void addMealToFavorite(RandomMealResponse.MealsItem mealsItem);
     void deleteFavMeals(RandomMealResponse.MealsItem mealItem);
     void addMealToWeeklyPlan(RandomMealResponse.MealsItem mealsItem);



    public class MealDetailsPresenterImp implements MealDetailsPresenter{
        MealRepo mealRepo;
        MealDetailsView mealDetailsView;

        public MealDetailsPresenterImp(MealRepo mealRepo, MealDetailsView mealDetailsView) {
            this.mealRepo = mealRepo;
            this.mealDetailsView = mealDetailsView;
        }

        @Override
        public void addMealToFavorite(RandomMealResponse.MealsItem mealsItem) {
            mealRepo.addMealToFavorite(mealsItem).
                    subscribe(()->mealDetailsView.onInsertFavSuccess(),
                            error->mealDetailsView.onInsertFavError(error.getLocalizedMessage()));
        }

        @Override
        public void deleteFavMeals(RandomMealResponse.MealsItem mealItem) {
            mealRepo.deleteMealFromFav(mealItem)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> mealDetailsView.onSuccessDeleteFromFav(),
                            error -> mealDetailsView.onFailDeleteFromFav(error.getLocalizedMessage())
                    );

        }

        @Override
        public void addMealToWeeklyPlan(RandomMealResponse.MealsItem mealsItem) {
            mealRepo.addMealToWeeklyPlay(mealsItem)
                    .subscribe(()->mealDetailsView.onPlanMealSuccess(),error->mealDetailsView.onPlanMealFail(error.getLocalizedMessage()));
        }
    }

}
