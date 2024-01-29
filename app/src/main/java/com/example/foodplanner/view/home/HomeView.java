package com.example.foodplanner.view.home;

import com.example.foodplanner.model.dto.CategoryResponse;
import com.example.foodplanner.model.dto.RandomMealResponse;

import java.util.List;

public interface HomeView {
    void showSuccessMessage(RandomMealResponse.MealsItem mealsItem);
    void showErrorMessage(String error);
    void showCategorySuccessMessage(List<CategoryResponse.CategoriesItem> categoriesItems);
    void showCategoryErrorMessage(String error);
}
