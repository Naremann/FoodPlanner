package com.example.foodplanner.view.serach_meal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.CategoryResponse;
import com.example.foodplanner.model.dto.Ingredient;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.MealRepoImp;
import com.example.foodplanner.model.repo.local.MealLocalDatasource;
import com.example.foodplanner.model.repo.remote.CategoryRemoteDataSourceImp;
import com.example.foodplanner.model.repo.remote.CategoryRepo;
import com.example.foodplanner.model.repo.remote.MealRemoteDataSource;
import com.example.foodplanner.model.repo.remote.RandomMealRemoteDataSourceImp;
import com.example.foodplanner.presenter.SearchPresenter;
import com.example.foodplanner.presenter.HomePresenter;
import com.example.foodplanner.view.AlertMessage;
import com.example.foodplanner.view.home.CategoryAdapter;
import com.example.foodplanner.view.home.HomeView;
import com.example.foodplanner.view.meal_by_category.MealAdapter;
import java.util.ArrayList;
import java.util.List;

public class SearchMealFragment extends Fragment implements HomeView , SearchMealView {

    HomePresenter homePresenter;
    CategoryAdapter categoryAdapter;
    RecyclerView categoryRecyclerView,mealRecyclerView;
    SearchView searchBar;
    SearchPresenter searchPresenter;
    MealAdapter mealAdapter;
    ProgressBar categoryProgressBar;

    public SearchMealFragment() {
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
        initDependencies();
        intiViews(view);

    }



    private void initDependencies() {
        homePresenter=new HomePresenter.HomePresenterImp(this,
                new MealRepoImp(
                        new RandomMealRemoteDataSourceImp(),
                        new MealLocalDatasource.MealLocalDataSourceImp(this.getContext()),new MealRemoteDataSource.MealRemoteDataSourceImp(requireContext())),
                new CategoryRepo.CategoryRepoImp(new CategoryRemoteDataSourceImp()));
        homePresenter.getCategories();
        searchPresenter=new SearchPresenter.
                SearchPresenterImp(new MealRemoteDataSource.MealRemoteDataSourceImp(requireContext()),
                this);
    }


    private void intiViews(View view) {
        categoryProgressBar=view.findViewById(R.id.category_progress_bar);
        searchBar=view.findViewById(R.id.search_view);
        mealAdapter=new MealAdapter(new ArrayList<>());
        mealRecyclerView=view.findViewById(R.id.recycler_view_meal);
        mealAdapter.onItemClickListener= mealsItem -> searchPresenter.getMealById(mealsItem.getIdMeal());
        mealRecyclerView.setAdapter(mealAdapter);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    searchPresenter.searchMealByName(newText);
                } else {
                    mealAdapter.clearData();
                }
                return true;
            }
        });



    categoryAdapter=new CategoryAdapter(new ArrayList<>());
        categoryAdapter.onItemClickListener= categoriesItem -> navigateToCategoryMeal(categoriesItem.getStrCategory());
        categoryRecyclerView=view.findViewById(R.id.recyclerview_meals);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }



    @Override
    public void showSuccessMessage(RandomMealResponse.MealsItem mealsItem) {


    }

    private void navigateToCategoryMeal(String categoriesItem) {
        SearchMealFragmentDirections.ActionSearchFragment2ToCategoryMealFragment action=SearchMealFragmentDirections
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
        hideProgressBar(categoryProgressBar);
    }

    @Override
    public void showCategoryErrorMessage(String error) {
        AlertMessage.showToastMessage(error,this.getContext());
        hideProgressBar(categoryProgressBar);
    }

    @Override
    public void showIngredientSuccessMessage(List<Ingredient> ingredients) {

    }

    @Override
    public void showIngredientsErrorMessage(String localizedMessage) {

    }

    void hideProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void showMeal(List<RandomMealResponse.MealsItem> meals) {
        mealAdapter.changeData(meals);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showMealById(RandomMealResponse.MealsItem mealsItem) {
        navigateToMealDetailsFragment(mealsItem);
    }
    private void navigateToMealDetailsFragment(RandomMealResponse.MealsItem mealsItems) {
        SearchMealFragmentDirections.ActionSearchFragment2ToMealDetailsFragment action=SearchMealFragmentDirections
                .actionSearchFragment2ToMealDetailsFragment(mealsItems) ;
        Navigation.findNavController(requireView()).navigate(action);

    }
}