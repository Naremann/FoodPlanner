package com.example.foodplanner;

import com.example.foodplanner.model.RandomMealResponse;

import java.util.List;

public class ContactPresenterImp implements Contract.Presenter{
    Contract.View view;

    public ContactPresenterImp(Contract.View view) {
        this.view = view;
    }

    @Override
    public void sendDataToActivity(RandomMealResponse.MealsItem mealsItem) {
        view.showDataInDetailsFragment(mealsItem);
    }

}
