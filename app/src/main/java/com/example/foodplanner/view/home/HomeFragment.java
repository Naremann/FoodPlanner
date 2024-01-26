package com.example.foodplanner.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.foodplanner.Contract;
import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.repo.category.CategoryRepoImp;
import com.example.foodplanner.model.repo.category.remote.CategoryRemoteDataSourceImp;
import com.example.foodplanner.view.AlertMessage;
import com.example.foodplanner.GlideImage;
import com.example.foodplanner.R;
import com.example.foodplanner.model.RandomMealResponse;
import com.example.foodplanner.model.repo.meal.MealRepoImp;
import com.example.foodplanner.model.repo.meal.remote.MealRemoteDataSourceImp;
import com.example.foodplanner.presenter.home.HomePresenter;
import com.example.foodplanner.presenter.home.HomePresenterImp;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements HomeView{
    ImageView mealImg;
    TextView mealTitle;
    HomePresenter homePresenter;
    MaterialCardView cardView;
    public Navigator navigator;
    CategoryAdapter categoryAdapter;
    RecyclerView categoryRecyclerView;
    ProgressBar progressBar;
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
                new MealRepoImp(new MealRemoteDataSourceImp()),
                new CategoryRepoImp(new CategoryRemoteDataSourceImp()));
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
       // GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2,RecyclerView.HORIZONTAL,false);
        categoryRecyclerView=view.findViewById(R.id.category_recycler_view);
        //categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void navigateToMealDetailsFragment() {
        navigator.navigateToMealDetails();
    }

    @Override
    public void showSuccessMessage(RandomMealResponse.MealsItem mealsItem) {
        sendDataToActivity(mealsItem);
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

    private void sendDataToActivity(RandomMealResponse.MealsItem mealsItems) {
        if (isAdded() && (!isRemoving()|| getActivity()!=null)) {
            ((Contract.Presenter) requireActivity()).sendDataToActivity(mealsItems);


        }
    }
    void hideProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.INVISIBLE);
    }

}