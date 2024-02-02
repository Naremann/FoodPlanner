package com.example.foodplanner.model.repo.remote;

import com.example.foodplanner.api.ApiManager;
import com.example.foodplanner.api.CategoryCallback;
import com.example.foodplanner.api.WebService;
import com.example.foodplanner.model.dto.CategoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRemoteDataSourceImp implements CategoryRemoteDataSource{
    WebService webService;

    public CategoryRemoteDataSourceImp() {
        this.webService = ApiManager.getApi();
    }

    @Override
    public CategoryResponse.CategoriesItem getCategories(CategoryCallback categoryCallback) {
        webService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    categoryCallback.onSuccess(response.body().getCategories());
                }
                else{
                    categoryCallback.onCategoryFailure("Something went error");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                categoryCallback.onCategoryFailure(t.getLocalizedMessage());
            }
        });
        return null;
    }
}
