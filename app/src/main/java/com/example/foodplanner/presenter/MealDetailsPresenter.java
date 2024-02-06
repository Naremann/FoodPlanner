package com.example.foodplanner.presenter;

import androidx.annotation.NonNull;

import com.example.foodplanner.Constants;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.MealRepo;
import com.example.foodplanner.view.meal_details.MealDetailsView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface MealDetailsPresenter {
    void addMealToFavorite(RandomMealResponse.MealsItem mealsItem);

    void deleteFavMeals(RandomMealResponse.MealsItem mealItem);

    void addMealToWeeklyPlan(RandomMealResponse.MealsItem mealsItem);

    void addWeeklyPlayMealToFireStore(RandomMealResponse.MealsItem mealsItem);
    //void addMealToFavFireStore(RandomMealResponse.MealsItem mealsItem);


    class MealDetailsPresenterImp implements MealDetailsPresenter {
        MealRepo mealRepo;
        MealDetailsView mealDetailsView;

        public MealDetailsPresenterImp(MealRepo mealRepo, MealDetailsView mealDetailsView) {
            this.mealRepo = mealRepo;
            this.mealDetailsView = mealDetailsView;
        }

        @Override
        public void addMealToFavorite(RandomMealResponse.MealsItem mealsItem) {

            mealRepo.insertMealToFavRemoteAndLocal(mealsItem).
                    subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> mealDetailsView.onInsertFavSuccess(),
                            error -> mealDetailsView.onInsertFavError(error.getLocalizedMessage()));

            /*mealRepo.addMealToFavorite(mealsItem).
                    subscribe(()->mealDetailsView.onInsertFavSuccess(),
                            error->mealDetailsView.onInsertFavError(error.getLocalizedMessage()));*/
        }

        @Override
        public void deleteFavMeals(RandomMealResponse.MealsItem mealItem) {
            mealRepo.deleteFromRemoteAndLocal(mealItem)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> mealDetailsView.onSuccessDeleteFromFav(),
                            error -> mealDetailsView.onFailDeleteFromFav(error.getLocalizedMessage())
                    );
        }

        @Override
        public void addMealToWeeklyPlan(RandomMealResponse.MealsItem mealsItem) {
            mealRepo.insertMealToWeeklyPlanRemoteAndLocal(mealsItem).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> mealDetailsView.onPlanMealSuccess(),
                            error -> mealDetailsView.onPlanMealFail(error.getLocalizedMessage()));

            /*mealRepo.addMealToWeeklyPlay(mealsItem)
                    .subscribe(() -> mealDetailsView.onPlanMealSuccess(), error -> mealDetailsView.onPlanMealFail(error.getLocalizedMessage()));*/
        }

        @Override
        public void addWeeklyPlayMealToFireStore(RandomMealResponse.MealsItem mealsItem) {
            mealRepo.addMealToWeeklyPlay(mealsItem, Constants.EMAIL,
                    unused -> mealDetailsView.onPlanMealSuccess(),
                    e -> mealDetailsView.onPlanMealFail(e.getLocalizedMessage()));
        }

         /*@Override
         public void addMealToFavFireStore(RandomMealResponse.MealsItem mealsItem) {
             mealRepo.addMealToFav(mealsItem, unused -> {
                 mealDetailsView.onAddToFavSuccessFB();
                 mealRepo.addMealToFavorite(mealsItem);
             }, e -> mealDetailsView.onAddToFavFailFB(e.getLocalizedMessage()));
         }
     }*/

    }
}
