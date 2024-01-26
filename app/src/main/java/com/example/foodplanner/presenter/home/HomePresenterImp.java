package com.example.foodplanner.presenter.home;

import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.model.RandomMealResponse;
import com.example.foodplanner.model.repo.meal.MealRepo;
import com.example.foodplanner.view.home.HomeView;

public class HomePresenterImp implements HomePresenter, MealCallBack {
    HomeView homeView;
    MealRepo mealRepo;


    public HomePresenterImp(HomeView homeView, MealRepo mealRepo) {
        this.homeView = homeView;
        this.mealRepo = mealRepo;
    }

    @Override
    public void getRandomMeal() {
        mealRepo.getRandomMeal(this);
    }

    @Override
    public void onSuccess(RandomMealResponse.MealsItem mealsItem) {
        homeView.showSuccessMessage(mealsItem);
    }

    @Override
    public void onFailure(String errorMessage) {
        homeView.showErrorMessage(errorMessage);
    }
}
