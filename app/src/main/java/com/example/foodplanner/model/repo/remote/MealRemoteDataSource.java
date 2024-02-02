package com.example.foodplanner.model.repo.remote;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.foodplanner.api.ApiManager;
import com.example.foodplanner.api.WebService;
import com.example.foodplanner.model.dto.MealResponse;
import com.example.foodplanner.model.dto.RandomMealResponse;

public interface MealRemoteDataSource {
    @NonNull Observable<Object> getMealByCategory(String category);
    Observable<Object> getMealById(String mealId);

    class MealRemoteDataSourceImp implements MealRemoteDataSource {
        WebService webService;

        public MealRemoteDataSourceImp() {
            this.webService = ApiManager.getApi();
        }

        @Override
        public @NonNull Observable<Object> getMealByCategory(String category) {
            return Observable.create(emitter -> {
                webService.getAllMealsByCategory(category).enqueue(new Callback<MealResponse>() {
                    @Override
                    public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            emitter.onNext(response.body().getMeals());
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Throwable("Failed to get meals by category"));
                        }
                    }

                    @Override
                    public void onFailure(Call<MealResponse> call, Throwable t) {
                        emitter.onError(t);
                    }
                });
            }).subscribeOn(Schedulers.io());
        }

        @Override
        public @NonNull Observable<Object> getMealById(String mealId) {
            return Observable.create(emitter -> {
                webService.getMealById(mealId).enqueue(new Callback<RandomMealResponse>() {
                    @Override
                    public void onResponse(Call<RandomMealResponse> call, Response<RandomMealResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            emitter.onNext(response.body().getMeals().get(0));
                            emitter.onComplete();
                        } else {
                            emitter.onError(new Throwable("Failed to get meals"));
                        }
                    }

                    @Override
                    public void onFailure(Call<RandomMealResponse> call, Throwable t) {
                        emitter.onError(t);
                    }
                });
            }).subscribeOn(Schedulers.io());
        }

    }

}
