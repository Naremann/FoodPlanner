package com.example.foodplanner.view.meal_details;

public interface MealDetailsView {
    void onInsertFavSuccess();
    void onInsertFavError(String error);
    void onPlanMealSuccess();
    void onPlanMealFail(String error);
     void onSuccessDeleteFromFav();
     void onFailDeleteFromFav(String error);


    void onAddToFavSuccessFB();
    void onAddToFavFailFB(String error);
}
