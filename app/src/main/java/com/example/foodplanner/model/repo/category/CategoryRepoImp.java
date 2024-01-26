package com.example.foodplanner.model.repo.category;

import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.model.repo.category.remote.CategoryRemoteDataSource;

public class CategoryRepoImp implements CategoryRepo{

    public CategoryRepoImp(CategoryRemoteDataSource categoryRemoteDataSource) {
        this.categoryRemoteDataSource = categoryRemoteDataSource;
    }

    CategoryRemoteDataSource categoryRemoteDataSource;

    @Override
    public void getCategories(CategoryCallback categoryCallback) {
        categoryRemoteDataSource.getCategories(categoryCallback);
    }
}
