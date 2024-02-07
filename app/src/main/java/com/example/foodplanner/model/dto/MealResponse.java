package com.example.foodplanner.model.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MealResponse{

	@SerializedName("meals")
	private List<RandomMealResponse.MealsItem> meals;

	public List<RandomMealResponse.MealsItem> getMeals(){
		return meals;
	}
}