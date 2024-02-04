package com.example.foodplanner.view.serach_meal;

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
import android.widget.SearchView;
import android.widget.TextView;

import com.example.foodplanner.GlideImage;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.CategoryResponse;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.MealRepoImp;
import com.example.foodplanner.model.repo.local.MealLocalDatasource;
import com.example.foodplanner.model.repo.remote.CategoryRemoteDataSourceImp;
import com.example.foodplanner.model.repo.remote.CategoryRepo;
import com.example.foodplanner.model.repo.remote.RandomMealRemoteDataSourceImp;
import com.example.foodplanner.presenter.home.HomePresenter;
import com.example.foodplanner.presenter.home.HomePresenterImp;
import com.example.foodplanner.view.AlertMessage;
import com.example.foodplanner.view.home.CategoryAdapter;
import com.example.foodplanner.view.home.HomeFragmentDirections;
import com.example.foodplanner.view.home.HomeView;
import com.example.foodplanner.view.home.Navigator;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements HomeView {

    ImageView mealImg;
    TextView mealTitle;
    HomePresenter homePresenter;
    MaterialCardView cardView;
    public Navigator navigator;
    CategoryAdapter categoryAdapter;
    RecyclerView categoryRecyclerView;
    SearchView searchBar;
    RandomMealResponse.MealsItem mealItem;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homePresenter=new HomePresenterImp(this,
                new MealRepoImp(new RandomMealRemoteDataSourceImp(),new MealLocalDatasource.MealLocalDataSourceImp(this.getContext())),
                new CategoryRepo.CategoryRepoImp(new CategoryRemoteDataSourceImp()));
        homePresenter.getCategories();
        intiViews(view);

    }


    private void intiViews(View view) {
        //progressBar=view.findViewById(R.id.recycler_progress_bar);
        searchBar=view.findViewById(R.id.search_view);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the search query when the user submits it
                navigateToCategoryMeal(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle changes in the search query text
                // This method is called every time the user types a character
                return true;
            }
        });



    categoryAdapter=new CategoryAdapter(new ArrayList<>());
        categoryAdapter.onItemClickListener= categoriesItem -> {
            navigateToCategoryMeal(categoriesItem.getStrCategory());
        };
        categoryRecyclerView=view.findViewById(R.id.recyclerview_meals);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void showSuccessMessage(RandomMealResponse.MealsItem mealsItem) {


    }

    private void navigateToCategoryMeal(String categoriesItem) {
        SearchFragmentDirections.ActionSearchFragment2ToCategoryMealFragment action=SearchFragmentDirections
                .actionSearchFragment2ToCategoryMealFragment(categoriesItem) ;
        Navigation.findNavController(requireView()).navigate(action);

    }
    @Override
    public void showErrorMessage(String error) {
        AlertMessage.showToastMessage(error,this.getContext());
        //hideProgressBar(progressBar);
    }

    @Override
    public void showCategorySuccessMessage(List<CategoryResponse.CategoriesItem> categoriesItems) {
        categoryAdapter.changeData(categoriesItems);
      //  hideProgressBar(progressBar);
    }

    @Override
    public void showCategoryErrorMessage(String error) {
        AlertMessage.showToastMessage(error,this.getContext());
    }
    void hideProgressBar(ProgressBar progressBar){
       // progressBar.setVisibility(View.INVISIBLE);
    }

}