package com.example.foodplanner.presenter;

import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.MealRepo;
import com.example.foodplanner.view.fav_meal.FavoriteView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface FavMealPresenter {
    public void getMeals();
    public void deleteFavMeals(RandomMealResponse.MealsItem mealItem);

    public class FavMealPresenterImp implements FavMealPresenter{
        MealRepo mealRepo;
        FavoriteView favoriteView;

        public FavMealPresenterImp(MealRepo mealRepo, FavoriteView favoriteView) {
            this.mealRepo = mealRepo;
            this.favoriteView = favoriteView;
        }

        @Override
        public void getMeals() {
            /*mealRepo.getFavMeals().subscribe(
                    favMeals->favoriteView.onGetAllFavoriteMeals((List<RandomMealResponse.MealsItem>) favMeals),
                    error -> favoriteView.onGetAllFavoriteMealsError((String) error));*/
            mealRepo.getFavMeals()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            favMeals -> favoriteView.onGetAllFavoriteMeals((List<RandomMealResponse.MealsItem>) favMeals),
                            error -> favoriteView.onGetAllFavoriteMealsError(error.toString())
                    );
        }

        @Override
        public void deleteFavMeals(RandomMealResponse.MealsItem mealItem) {
           // mealRepo.deleteMealFromFav(mealItem).subscribe(()->favoriteView.onSuccessDeleteFromFav(),error->favoriteView.onFailDeleteFromFav(error.getLocalizedMessage()));

            mealRepo.deleteMealFromFav(mealItem)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> favoriteView.onSuccessDeleteFromFav(),
                            error -> favoriteView.onFailDeleteFromFav(error.getLocalizedMessage())
                    );

        }
    }
}
