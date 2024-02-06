package com.example.foodplanner.model.repo.remote;

import android.content.Context;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.foodplanner.api.ApiManager;
import com.example.foodplanner.api.WebService;
import com.example.foodplanner.db.FirebaseUtils;
import com.example.foodplanner.db.SharedPreferencesManager;
import com.example.foodplanner.model.dto.MealResponse;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public interface MealRemoteDataSource {
    @NonNull Observable<Object> getMealByCategory(String category);

    Observable<Object> getMealById(String mealId);

    Observable<List<RandomMealResponse.MealsItem>> getFavMealsFromFirestore();

    void addMealToPlan(RandomMealResponse.MealsItem mealsItem,
                       OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener);

    void addMealToFav(RandomMealResponse.MealsItem mealsItem, OnSuccessListener<Void> onSuccessListener,
                      OnFailureListener onFailureListener);

    Observable<List<RandomMealResponse.MealsItem>> getWeeklyPlannedMealsObservable(String date);
    void deleteFavoriteMealFromFireStore(RandomMealResponse.MealsItem mealsItem,
                                         OnSuccessListener<Void> onSuccessListener,
                                         OnFailureListener onFailureListener);


    class MealRemoteDataSourceImp implements MealRemoteDataSource {
        WebService webService;
        Context context;
        String savedEmail;

        public MealRemoteDataSourceImp(Context context) {
            this.webService = ApiManager.getApi();
            this.context = context;
            this.savedEmail=SharedPreferencesManager.getUserEmail(context);
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

        @Override
        public void addMealToPlan(RandomMealResponse.MealsItem mealsItem,
                                  OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
            FirebaseUtils.addMealToPlan(mealsItem, onSuccessListener, onFailureListener);
        }

        @Override
        public void addMealToFav(RandomMealResponse.MealsItem mealsItem, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
            FirebaseUtils.addMealToFav(mealsItem, onSuccessListener, onFailureListener);
        }

        public void getWeeklyPlannedMeals(String date, OnCompleteListener<QuerySnapshot> onCompleteListener, OnFailureListener onFailureListener) {
            CollectionReference plannedMealCollection = FirebaseFirestore.getInstance().collection(RandomMealResponse.MealsItem.COLLECTION_NAME);
            plannedMealCollection
                    .whereEqualTo("strCreativeCommonsConfirmed", savedEmail)
                    .whereEqualTo("dateModified", date)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            onCompleteListener.onComplete(task);
                        } else {
                            onFailureListener.onFailure(task.getException());
                        }
                    })
                    .addOnFailureListener(onFailureListener);
        }


        public Observable<List<RandomMealResponse.MealsItem>> getWeeklyPlannedMealsObservable(String date) {
            return Observable.<List<RandomMealResponse.MealsItem>>create(emitter -> {
                getWeeklyPlannedMeals(
                        date,
                        task -> {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (querySnapshot != null) {
                                    List<RandomMealResponse.MealsItem> meals = convertQueryDocumentSnapshotToList(querySnapshot);
                                    emitter.onNext(meals);
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(new IllegalStateException("QuerySnapshot is null"));
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        },
                        emitter::onError
                );
            }).subscribeOn(Schedulers.io());
        }

        @Override
        public void deleteFavoriteMealFromFireStore(RandomMealResponse.MealsItem mealsItem, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
            FirebaseUtils.deleteFavMeal(context,mealsItem.getIdMeal(),onSuccessListener,onFailureListener);
        }


        private static List<RandomMealResponse.MealsItem> convertQueryDocumentSnapshotToList(QuerySnapshot queryDocumentSnapshots) {
            List<RandomMealResponse.MealsItem> mealsList = new ArrayList<>();

            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                RandomMealResponse.MealsItem mealsItem = documentSnapshot.toObject(RandomMealResponse.MealsItem.class);
                mealsList.add(mealsItem);
            }

            return mealsList;
        }


        @Override
        public Observable<List<RandomMealResponse.MealsItem>> getFavMealsFromFirestore() {
            return Observable.<List<RandomMealResponse.MealsItem>>create(emitter -> {
                FirebaseUtils.getFavMeals(context, task -> {
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
                });
            }).subscribeOn(Schedulers.io());
        }
    }

}


