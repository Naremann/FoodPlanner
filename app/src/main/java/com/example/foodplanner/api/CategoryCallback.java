package com.example.foodplanner.api;

import com.example.foodplanner.model.CategoryResponse;

import java.util.List;

public interface CategoryCallback {
    void onSuccess(List<CategoryResponse.CategoriesItem> categoriesItems);
    void onCategoryFailure(String error);
}
