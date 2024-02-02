package com.example.foodplanner.view.meal_details;

public interface MealDetailsView {
    void onInsertFavSuccess();
    void onInsertFavError(String error);
    public void onSuccessDeleteFromFav();
    public void onFailDeleteFromFav(String error);
}
