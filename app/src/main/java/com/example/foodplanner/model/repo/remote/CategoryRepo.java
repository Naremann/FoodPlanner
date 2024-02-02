package com.example.foodplanner.model.repo.remote;

import com.example.foodplanner.api.CategoryCallback;

public interface CategoryRepo {
    void getCategories(CategoryCallback categoryCallback);

    public class CategoryRepoImp implements CategoryRepo {

        public CategoryRepoImp(CategoryRemoteDataSource categoryRemoteDataSource) {
            this.categoryRemoteDataSource = categoryRemoteDataSource;
        }

        CategoryRemoteDataSource categoryRemoteDataSource;

        @Override
        public void getCategories(CategoryCallback categoryCallback) {
            categoryRemoteDataSource.getCategories(categoryCallback);
        }
    }

}
