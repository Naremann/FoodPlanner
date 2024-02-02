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

        /*
           Observable.create((ObservableOnSubscribe<RandomMealResponse>) emitter -> {
            try {
                Response<RandomMealResponse> response = webService.getRandomMeal().execute();
                if (response.isSuccessful() && response.body() != null) {
                    emitter.onNext(response.body());
                    emitter.onComplete();
                } else {
                    emitter.onError(new Exception("API call failed"));
                }
            } catch (IOException e) {
                emitter.onError(e);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RandomMealResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // Not used in this example, but you can handle subscription here if needed.
                    }

                    @Override
                    public void onNext(RandomMealResponse response) {
                        if (response != null && response.getMeals() != null && response.getMeals().size() > 0) {
                            mealCallBack.onSuccess(response.getMeals().get(0));
                        } else {
                            mealCallBack.onFailure("No meals found");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mealCallBack.onFailure(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        // Not used in this example, but you can handle completion here if needed.
                    }
                });
        */
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
