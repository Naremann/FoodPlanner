package com.example.foodplanner.view.home;

import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.RandomMealResponse;

import java.util.List;

public interface HomeView {
    void showSuccessMessage(RandomMealResponse.MealsItem mealsItem);
    void showErrorMessage(String error);
    void showCategorySuccessMessage(List<CategoryResponse.CategoriesItem> categoriesItems);
    void showCategoryErrorMessage(String error);
}
