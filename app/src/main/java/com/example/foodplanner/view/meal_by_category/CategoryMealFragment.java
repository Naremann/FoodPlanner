package com.example.foodplanner.view.meal_by_category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.CategoryResponse;
import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.repo.meal.MealRemoteDataSource;
import com.example.foodplanner.presenter.meal_by_category.MealPresenter;
import com.example.foodplanner.view.AlertMessage;

import java.util.ArrayList;
import java.util.List;

public class CategoryMealFragment extends Fragment implements MealByCategoryView {

    private CategoryResponse.CategoriesItem categoryItem;
    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;
    private MealPresenter mealPresenter;

    public CategoryMealFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeDependencies();
        retrieveCategoryFromArguments();
        initViews(view);
    }

    private void initializeDependencies() {
        mealPresenter = new MealPresenter.MealPresenterImp(
                new MealRemoteDataSource.MealRemoteDataSourceImp(),
                this
        );
    }

    private void retrieveCategoryFromArguments() {
        if (getArguments() != null) {
            categoryItem = CategoryMealFragmentArgs.fromBundle(getArguments()).getCategory();
            if (categoryItem != null) {
                Log.e("TAG", "onViewCreated: " + categoryItem.getStrCategory());
                mealPresenter.getMealByCategory(categoryItem.getStrCategory());
            }
        }
    }

    private void initViews(View view) {
        mealAdapter = new MealAdapter(new ArrayList<>());
        recyclerView = view.findViewById(R.id.meal_recycler_view);
        recyclerView.setAdapter(mealAdapter);
    }

    @Override
    public void showMeal(List<MealsItem> mealsItems) {
        mealAdapter.changeData(mealsItems);
    }

    @Override
    public void showError(String error) {
        AlertMessage.showToastMessage(error, requireContext());
    }
}
