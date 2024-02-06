package com.example.foodplanner.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foodplanner.model.dto.CategoryResponse;
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
import com.example.foodplanner.presenter.home.HomePresenter;
import com.example.foodplanner.presenter.home.HomePresenterImp;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView{
    ImageView mealImg;
    TextView mealTitle;
    HomePresenter homePresenter;
    MaterialCardView cardView;
    public Navigator navigator;
    CategoryAdapter categoryAdapter;
    RecyclerView categoryRecyclerView;
    ProgressBar progressBar;
    NavController navController;
    RandomMealResponse.MealsItem mealItem;
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
        homePresenter=new HomePresenterImp(this,
                new MealRepoImp(new RandomMealRemoteDataSourceImp(),new MealLocalDatasource.MealLocalDataSourceImp(this.getContext()),new MealRemoteDataSource.MealRemoteDataSourceImp()),
                new CategoryRepo.CategoryRepoImp(new CategoryRemoteDataSourceImp()));
        homePresenter.getRandomMeal();
        homePresenter.getCategories();
        intiViews(view);


    }

    private void intiViews(View view) {
        mealImg=view.findViewById(R.id.meal_img);
        mealTitle=view.findViewById(R.id.meal_title_tv);
        progressBar=view.findViewById(R.id.recycler_progress_bar);
        cardView=view.findViewById(R.id.meal_card_view);
        cardView.setOnClickListener(v -> navigateToMealDetailsFragment());
        categoryAdapter=new CategoryAdapter(new ArrayList<>());
        categoryAdapter.onItemClickListener= categoriesItem -> {
            navigateToCategoryMeal(categoriesItem);
        };
        categoryRecyclerView=view.findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void navigateToCategoryMeal(CategoryResponse.CategoriesItem categoriesItem) {
        HomeFragmentDirections.ActionHomeFragmentToCategoryMealFragment action=HomeFragmentDirections
                .actionHomeFragmentToCategoryMealFragment(categoriesItem.getStrCategory()) ;
        Navigation.findNavController(requireView()).navigate(action);

    }

    private void navigateToMealDetailsFragment() {
           HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                   HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealItem);
           Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void showSuccessMessage(RandomMealResponse.MealsItem mealsItem) {
        this.mealItem=mealsItem;
        mealTitle.setText(mealsItem.getStrMeal());
        Log.e("TAG", "showSuccessMessage: "+mealsItem.getStrImageSource());
        GlideImage.downloadImageToImageView(mealImg.getContext(),mealsItem.getStrMealThumb(),mealImg);

    }

    @Override
    public void showErrorMessage(String error) {
        AlertMessage.showToastMessage(error,this.getContext());
        hideProgressBar(progressBar);
    }

    @Override
    public void showCategorySuccessMessage(List<CategoryResponse.CategoriesItem> categoriesItems) {
        categoryAdapter.changeData(categoriesItems);
        hideProgressBar(progressBar);
    }

    @Override
    public void showCategoryErrorMessage(String error) {
        AlertMessage.showToastMessage(error,this.getContext());
    }
    void hideProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.INVISIBLE);
    }

}