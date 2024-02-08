package com.example.foodplanner.model.repo.remote;

import android.content.Context;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.foodplanner.api.ApiManager;
import com.example.foodplanner.api.WebService;
import com.example.foodplanner.db.FirebaseUtils;
import com.example.foodplanner.db.SharedPreferencesManager;
import com.example.foodplanner.model.dto.Ingredient;
import com.example.foodplanner.model.dto.IngredientResponse;
import com.example.foodplanner.model.dto.MealResponse;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public interface MealRemoteDataSource {
    @NonNull Observable<List<RandomMealResponse.MealsItem>> getMealByCategory(String category);

    Observable<Object> getMealById(String mealId);

    Observable<List<RandomMealResponse.MealsItem>> getFavMealsFromFireStore();

    void addMealToPlan(RandomMealResponse.MealsItem mealsItem,
                       OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener);

    void addMealToFav(RandomMealResponse.MealsItem mealsItem, OnSuccessListener<Void> onSuccessListener,
                      OnFailureListener onFailureListener);

    Observable<List<RandomMealResponse.MealsItem>> getWeeklyPlannedMealsObservable(String date);
    void deleteFavoriteMealFromFireStore(RandomMealResponse.MealsItem mealsItem,
                                         OnSuccessListener<Void> onSuccessListener,
                                         OnFailureListener onFailureListener);

     void deletePlannedMealFireStore(RandomMealResponse.MealsItem mealsItem, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener);
     Observable<List<RandomMealResponse.MealsItem>> searchMeals(String name);

     Observable<List<Ingredient>> getIngredients();



    class MealRemoteDataSourceImp implements MealRemoteDataSource {
        WebService webService;
        Context context;
       // String savedEmail;

        public MealRemoteDataSourceImp(Context context) {
            this.webService = ApiManager.getApi();
            this.context = context;
           // this.savedEmail=SharedPreferencesManager.getUserEmail(context);
        }

        @Override
        public @NonNull Observable<List<RandomMealResponse.MealsItem>> getMealByCategory(String category) {
            return webService.getAllMealsByCategory(category)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(mealResponse -> {
                        List<RandomMealResponse.MealsItem> meals = new ArrayList<>();
                        if (mealResponse != null && mealResponse.getMeals() != null) {
                            meals.addAll(mealResponse.getMeals());
                        }
                        return meals;
                    });
            /*return webService.getAllMealsByCategory(category)
                    .map(MealResponse::getMeals)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());*/
            /*return Observable.create(emitter -> webService.getAllMealsByCategory(category).enqueue(new Callback<MealResponse>() {
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
            })).subscribeOn(Schedulers.io());*/
        }

        @Override
        public @NonNull Observable<Object> getMealById(String mealId) {
            return Observable.create(emitter -> webService.getMealById(mealId).enqueue(new Callback<RandomMealResponse>() {
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
            })).subscribeOn(Schedulers.io());
        }

        @Override
        public void addMealToPlan(RandomMealResponse.MealsItem mealsItem,
                                  OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
            FirebaseUtils.addMealToPlan(mealsItem, onSuccessListener, onFailureListener);
        }

        @Override
        public void addMealToFav(RandomMealResponse.MealsItem mealsItem, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
            FirebaseUtils.addMealToFav(mealsItem, onSuccessListener, onFailureListener);
        }

        @Override
        public Observable<List<RandomMealResponse.MealsItem>> getFavMealsFromFireStore() {
            return Observable.<List<RandomMealResponse.MealsItem>>create(emitter -> FirebaseUtils.getFavMeals(context, task -> {
                if(task.isSuccessful()){
                    QuerySnapshot  querySnapshot= (QuerySnapshot) task.getResult();
                    if(querySnapshot!=null){
                        List<RandomMealResponse.MealsItem> mealsItems=new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot:querySnapshot.getDocuments()){
                            RandomMealResponse.MealsItem mealsItem=documentSnapshot.toObject(RandomMealResponse.MealsItem.class);
                            if(mealsItem!=null){
                                mealsItems.add(mealsItem);
                            }
                        }
                        emitter.onNext(mealsItems);
                        emitter.onComplete();
                    }else {
                        emitter.onError(new Exception("QuerySnapshot is null"));
                    }
                }
                else {
                    emitter.onError(task.getException());
                }
            })).subscribeOn(Schedulers.io());
        }
        @Override
        public Observable<List<RandomMealResponse.MealsItem>> getWeeklyPlannedMealsObservable(String date) {
            return Observable.create((ObservableOnSubscribe<List<RandomMealResponse.MealsItem>>) emitter -> FirebaseUtils.getWeeklyPlannedMeals(context, date, task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        List<RandomMealResponse.MealsItem> mealsItems = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                            RandomMealResponse.MealsItem mealsItem = documentSnapshot.toObject(RandomMealResponse.MealsItem.class);
                            if (mealsItem != null) {
                                mealsItems.add(mealsItem);
                            }
                        }
                        emitter.onNext(mealsItems);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new IllegalStateException("No Data"));
                    }
                } else {
                    emitter.onError(task.getException());
                }
            }, e -> emitter.onError(e.fillInStackTrace()))).subscribeOn(Schedulers.io());
        }
        @Override
        public void deleteFavoriteMealFromFireStore(RandomMealResponse.MealsItem mealsItem, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
            FirebaseUtils.deleteFavMeal(context, mealsItem.getIdMeal(), onSuccessListener, onFailureListener);
        }

        @Override
        public void deletePlannedMealFireStore(RandomMealResponse.MealsItem mealsItem, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
            FirebaseUtils.deletePlannedMeal(context,mealsItem.getIdMeal(),onSuccessListener,onFailureListener);
        }

        @Override
        public Observable<List<RandomMealResponse.MealsItem>> searchMeals(String name) {
            return webService.searchMealByName(name)
                    .map(MealResponse::getMeals)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public Observable<List<Ingredient>> getIngredients() {
            return webService.getIngredients().map(IngredientResponse::getMeals);
        }

    }

}


