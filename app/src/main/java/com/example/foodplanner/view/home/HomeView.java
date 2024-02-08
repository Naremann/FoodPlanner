package com.example.foodplanner.view.home;

import com.example.foodplanner.model.dto.CategoryResponse;
import com.example.foodplanner.model.dto.Ingredient;
import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.dto.RandomMealResponse;

import java.util.List;

public interface HomeView {
    void showSuccessMessage(MealsItem mealsItem);
    void showErrorMessage(String error);
    void showCategorySuccessMessage(List<CategoryResponse.CategoriesItem> categoriesItems);
    void showCategoryErrorMessage(String error);

    void showIngredientSuccessMessage(List<Ingredient> ingredients);

    void showIngredientsErrorMessage(String localizedMessage);

    void showMealsByIngredientSuccess(List<MealsItem> mealsItems);

    void showMealsByIngredientError(String localizedMessage);

    void onMealByCategorySuccess(List<MealsItem> mealsItems);

    void onMealByCategoryFail(String localizedMessage);
}
