package com.example.foodplanner.model.dto.filter;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FilterResponse{

	@SerializedName("meals")
	private List<MealsItem> meals;

	public List<MealsItem> getMeals(){
		return meals;
	}
}