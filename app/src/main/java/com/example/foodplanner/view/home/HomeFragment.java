package com.example.foodplanner.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foodplanner.model.dto.CategoryResponse;
import com.example.foodplanner.model.dto.Ingredient;
import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.local.MealLocalDatasource;
import com.example.foodplanner.model.repo.remote.CategoryRemoteDataSourceImp;
import com.example.foodplanner.model.repo.remote.CategoryRepo;
import com.example.foodplanner.model.repo.remote.MealRemoteDataSource;
import com.example.foodplanner.view.AlertMessage;
import com.example.foodplanner.GlideImage;
import com.example.foodplanner.R;
import com.example.foodplanner.model.repo.MealRepoImp;
import com.example.foodplanner.model.repo.remote.RandomMealRemoteDataSourceImp;
import com.example.foodplanner.presenter.HomePresenter;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeView {
    ImageView mealImg;
    TextView mealTitle;
    HomePresenter homePresenter;
    MaterialCardView cardView;
    CategoryAdapter categoryAdapter;
    RecyclerView categoryRecyclerView, ingredRecyclerView;
    ProgressBar progressBar;
    MealsItem mealItem;
    com.example.foodplanner.view.home.IngredientAdapter ingredientAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homePresenter = new HomePresenter.HomePresenterImp(this,
                new MealRepoImp(new RandomMealRemoteDataSourceImp(), new MealLocalDatasource.MealLocalDataSourceImp(this.getContext()), new MealRemoteDataSource.MealRemoteDataSourceImp(requireContext())),
                new CategoryRepo.CategoryRepoImp(new CategoryRemoteDataSourceImp()));
        homePresenter.getRandomMeal();
        homePresenter.getCategories();
        homePresenter.getIngredients();
        intiViews(view);


    }

    private void intiViews(View view) {
        ingredRecyclerView = view.findViewById(R.id.ingredients_recycler_view);
        ingredientAdapter = new IngredientAdapter(new ArrayList<>());
        ingredRecyclerView.setAdapter(ingredientAdapter);
        ingredientAdapter.onItemClickListener = ingredient -> {
            homePresenter.getMealsByIngredient(ingredient.getIngredientName());
        };
        mealImg = view.findViewById(R.id.meal_img);
        mealTitle = view.findViewById(R.id.meal_title_tv);
        progressBar = view.findViewById(R.id.recycler_progress_bar);
        cardView = view.findViewById(R.id.meal_card_view);
        cardView.setOnClickListener(v -> navigateToMealDetailsFragment());
        categoryAdapter = new CategoryAdapter(new ArrayList<>());
        categoryAdapter.onItemClickListener = categoriesItem -> {
            homePresenter.getMealByCategory(categoriesItem.getStrCategory());
        };
        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void navigateToCategoryMeal(MealsItem[] mealsItems) {
        HomeFragmentDirections.ActionHomeFragmentToCategoryMealFragment action = HomeFragmentDirections
                .actionHomeFragmentToCategoryMealFragment(mealsItems);
        Navigation.findNavController(requireView()).navigate(action);

    }

    private void navigateToMealDetailsFragment() {
        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealItem);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showSuccessMessage(MealsItem mealsItem) {
        this.mealItem = mealsItem;
        mealTitle.setText(mealsItem.getStrMeal());
        GlideImage.downloadImageToImageView(mealImg.getContext(), mealsItem.getStrMealThumb(), mealImg);

    }

    @Override
    public void showErrorMessage(String error) {
        AlertMessage.showToastMessage(error, this.getContext());
        hideProgressBar(progressBar);
    }

    @Override
    public void showCategorySuccessMessage(List<CategoryResponse.CategoriesItem> categoriesItems) {
        categoryAdapter.changeData(categoriesItems);
        hideProgressBar(progressBar);
    }

    @Override
    public void showCategoryErrorMessage(String error) {
        showMessage(error);
    }

    @Override
    public void showIngredientSuccessMessage(List<Ingredient> ingredients) {
        ingredientAdapter.changeData(ingredients);
    }

    @Override
    public void showIngredientsErrorMessage(String localizedMessage) {
        showMessage(localizedMessage);
    }

    @Override
    public void showMealsByIngredientSuccess(List<MealsItem> mealsItems) {
        ingredientAdapter.onItemClickListener = ingredient -> {
            homePresenter.getMealsByIngredient(ingredient.getIngredientName());
            // Navigate to another fragment and send parameter
            Log.e("TAG", "showMealsByIngredientSuccess: "+mealsItems.get(0).getStrIngredient2());
            MealsItem[] mealsItem = mealsItems.toArray(new MealsItem[mealsItems.size()]);
            navigateToCategoryMeal(mealsItem);
        };
    }

    @Override
    public void showMealsByIngredientError(String localizedMessage) {

    }

    @Override
    public void onMealByCategorySuccess(List<MealsItem> mealsItems) {

        categoryAdapter.onItemClickListener = categoriesItem -> {
            MealsItem[] mealsItem = mealsItems.toArray(new MealsItem[mealsItems.size()]);
            Log.e("TAG", "onItemClick: "+mealsItems.get(0).getIdMeal());
            navigateToCategoryMeal(mealsItem);

        };
    }

    @Override
    public void onMealByCategoryFail(String localizedMessage) {

    }

    void hideProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showMessage(String message) {
        AlertMessage.showToastMessage(message, this.getContext());
    }

}