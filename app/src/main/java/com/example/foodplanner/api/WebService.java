package com.example.foodplanner.api;

import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.RandomMealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {
    @GET("api/json/v1/1/random.php")
    Call<RandomMealResponse> getRandomMeal();

    @GET("/api/json/v1/1/categories.php")
    Call<CategoryResponse> getCategories();

}
