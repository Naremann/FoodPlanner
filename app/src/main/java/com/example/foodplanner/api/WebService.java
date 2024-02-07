package com.example.foodplanner.api;


import com.example.foodplanner.model.dto.CategoryResponse;
import com.example.foodplanner.model.dto.MealResponse;
import com.example.foodplanner.model.dto.RandomMealResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {
    @GET("api/json/v1/1/random.php")
    Call<RandomMealResponse> getRandomMeal();

    @GET("/api/json/v1/1/categories.php")
    Call<CategoryResponse> getCategories();

   @GET("/api/json/v1/1/filter.php")
   Observable<MealResponse> getAllMealsByCategory(@Query("c") String category);

   @GET("/api/json/v1/1/lookup.php")
   Call<RandomMealResponse> getMealById(@Query("i") String mealId);

    @GET("/api/json/v1/1/search.php")
    Observable<MealResponse> searchMealByName(@Query("s") String mealName);

}
