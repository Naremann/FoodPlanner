package com.example.foodplanner.model.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class IngredientResponse{

	@SerializedName("meals")
	private List<Ingredient> ingredients;

	public List<Ingredient> getMeals(){
		return ingredients;
	}
}