package com.example.foodplanner.presenter.home;

import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.dto.CategoryResponse;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.category.CategoryRepo;
import com.example.foodplanner.model.repo.random_meal.MealRepo;
import com.example.foodplanner.view.home.HomeView;

import java.util.List;

public class HomePresenterImp implements HomePresenter, MealCallBack, CategoryCallback {
    HomeView homeView;
    MealRepo mealRepo;
    CategoryRepo categoryRepo;



    public HomePresenterImp(HomeView homeView, MealRepo mealRepo,CategoryRepo categoryRepo) {
        this.homeView = homeView;
        this.mealRepo = mealRepo;
        this.categoryRepo=categoryRepo;
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
