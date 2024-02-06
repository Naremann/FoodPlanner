package com.example.foodplanner.model.repo.remote;

import com.example.foodplanner.api.ApiManager;
import com.example.foodplanner.api.MealCallBack;
import com.example.foodplanner.api.WebService;
import com.example.foodplanner.model.dto.RandomMealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomMealRemoteDataSourceImp implements RandomMealRemoteDataSource {
    WebService webService;

    public RandomMealRemoteDataSourceImp() {
        webService= ApiManager.getApi();
    }

    @Override
    public RandomMealResponse.MealsItem getMeal(MealCallBack mealCallBack) {
        webService.getRandomMeal().enqueue(new Callback<RandomMealResponse>() {
            @Override
            public void onResponse(Call<RandomMealResponse> call, Response<RandomMealResponse> response) {
                mealCallBack.onSuccess(response.body().getMeals().get(0));
            }

            @Override
            public void onFailure(Call<RandomMealResponse> call, Throwable t) {
                mealCallBack.onFailure(t.getLocalizedMessage());
            }
        });
        return null;
    }
}
