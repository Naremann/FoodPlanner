package com.example.foodplanner.model.dto;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    String ingredientName;
    String ingredientMeasure;

    public Ingredient(String ingredientName, String ingredientMeasure) {
        this.ingredientName = ingredientName;
        this.ingredientMeasure = ingredientMeasure;
    }

    public static List<Ingredient> getIngredients(RandomMealResponse.MealsItem mealsItem) {
        List<Ingredient> ingredients=new ArrayList<>();
        if(validateIngredient(mealsItem.getStrIngredient1())) {
            ingredients.add(new Ingredient(mealsItem.getStrIngredient1(), mealsItem.getStrMeasure1()));
        }
        if(validateIngredient(mealsItem.getStrIngredient2())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient2(),mealsItem.getStrMeasure2()));
        }
        if(validateIngredient(mealsItem.getStrIngredient3())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient3(),mealsItem.getStrMeasure3()));
        }

        if(validateIngredient(mealsItem.getStrIngredient4())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient4(),mealsItem.getStrMeasure4()));
        }
        if(validateIngredient(mealsItem.getStrIngredient5())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient5(),mealsItem.getStrMeasure5()));
        }
        if(validateIngredient(mealsItem.getStrIngredient6())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient6(),mealsItem.getStrMeasure6()));
        }
        if(validateIngredient(mealsItem.getStrIngredient7())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient7(),mealsItem.getStrMeasure7()));
        }
        if(validateIngredient(mealsItem.getStrIngredient8())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient8(),mealsItem.getStrMeasure8()));
        }
        if(validateIngredient(mealsItem.getStrIngredient9())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient9(),mealsItem.getStrMeasure9()));
        }
        if(validateIngredient(mealsItem.getStrIngredient10())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient10(),mealsItem.getStrMeasure10()));
        }
        if(validateIngredient(mealsItem.getStrIngredient11())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient11(),mealsItem.getStrMeasure11()));
        }
        if(validateIngredient(mealsItem.getStrIngredient12())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient12(),mealsItem.getStrMeasure12()));
        }
        if(validateIngredient(mealsItem.getStrIngredient13())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient13(),mealsItem.getStrMeasure13()));
        }

        if(validateIngredient(mealsItem.getStrIngredient14())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient14(),mealsItem.getStrMeasure14()));
        }
        if(validateIngredient(mealsItem.getStrIngredient15())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient15(),mealsItem.getStrMeasure15()));
        }
        if(validateIngredient(mealsItem.getStrIngredient16())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient16(),mealsItem.getStrMeasure16()));
        }
        if(validateIngredient(mealsItem.getStrIngredient17())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient17(),mealsItem.getStrMeasure17()));
        }
        if(validateIngredient(mealsItem.getStrIngredient18())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient18(),mealsItem.getStrMeasure18()));
        }
        if(validateIngredient(mealsItem.getStrIngredient19())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient19(),mealsItem.getStrMeasure19()));
        }
        if(validateIngredient(mealsItem.getStrIngredient20())){
            ingredients.add(new Ingredient(mealsItem.getStrIngredient20(),mealsItem.getStrMeasure20()));
            Log.e("TAG", "getIngredients: "+ mealsItem.getStrIngredient20());
        }
        return ingredients;
    }

    private static boolean validateIngredient(String ingredientName) {
        boolean isValidate=true;
        for (int i=0;i<20;i++){
            if (ingredientName.equals("")){
                isValidate = false;
                break;
            }
        }
        return isValidate;
    }

    public String getIngredientName() {
        return ingredientName;
    }


    public String getIngredientMeasure() {
        return ingredientMeasure;
    }

}
