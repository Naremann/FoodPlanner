package com.example.foodplanner.model.repo.remote;

import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.model.dto.CategoryResponse;

public interface CategoryRemoteDataSource {
    CategoryResponse.CategoriesItem getCategories(CategoryCallback categoryCallback);

}
