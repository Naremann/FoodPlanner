package com.example.foodplanner.presenter;

import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.dto.CategoryResponse;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.MealRepo;
import com.example.foodplanner.model.repo.remote.CategoryRepo;
import com.example.foodplanner.view.home.HomeView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface HomePresenter {
    void getRandomMeal();
    void getCategories();
    public void getIngredients();


    public class HomePresenterImp implements HomePresenter, MealCallBack, CategoryCallback {
        HomeView homeView;
        MealRepo mealRepo;
        CategoryRepo categoryRepo;
        private CompositeDisposable compositeDisposable;


        public HomePresenterImp(HomeView homeView, MealRepo mealRepo,CategoryRepo categoryRepo) {
            this.homeView = homeView;
            this.mealRepo = mealRepo;
            this.categoryRepo=categoryRepo;
            compositeDisposable=new CompositeDisposable();
        }

        @Override
        public void getRandomMeal() {
            mealRepo.getRandomMeal(this);
        }

        @Override
        public void getCategories() {
            categoryRepo.getCategories(this);
        }

        @Override
        public void getIngredients() {
            compositeDisposable.add(mealRepo.getIngredients().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(ingredients->homeView.showIngredientSuccessMessage(ingredients),error->homeView.showIngredientsErrorMessage(error.getLocalizedMessage())));
        }
        @Override
        public void onSuccess(RandomMealResponse.MealsItem mealsItem) {
            homeView.showSuccessMessage(mealsItem);
        }

        @Override
        public void onSuccess(List<CategoryResponse.CategoriesItem> categoriesItems) {
            homeView.showCategorySuccessMessage(categoriesItems);
        }

        @Override
        public void onCategoryFailure(String error) {
            homeView.showCategoryErrorMessage(error);
        }

        @Override
        public void onFailure(String errorMessage) {
            homeView.showErrorMessage(errorMessage);
        }
    }

}
