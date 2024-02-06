package com.example.foodplanner.view.calender;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.MealRepoImp;
import com.example.foodplanner.model.repo.local.MealLocalDatasource;
import com.example.foodplanner.model.repo.remote.MealRemoteDataSource;
import com.example.foodplanner.model.repo.remote.RandomMealRemoteDataSourceImp;
import com.example.foodplanner.presenter.CalenderPresenter;
import com.example.foodplanner.presenter.MealDetailsPresenter;
import com.example.foodplanner.view.AlertMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CalenderFragment extends Fragment implements CalenderView{
    CalenderAdapter calenderAdapter;
    RecyclerView recyclerViewSat,recyclerViewSun,recyclerViewMon,recyclerViewTues,recyclerViewWed,recyclerViewThurs,recyclerViewFri;
    Button satBtn,sunBtn,monBtn,tuesBtn,wedBun,thursBtn,friBtn;
    String date;
    CalenderPresenter calenderPresenter;
    MealDetailsPresenter mealDetailsPresenter;
    RandomMealResponse.MealsItem mealsItem;


    public CalenderFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDependencies();
        initViews(view);
    }

    private void initDependencies() {

        calenderPresenter=new CalenderPresenter.CalenderPresenterImp(this,
                new MealRepoImp(new RandomMealRemoteDataSourceImp(),new MealLocalDatasource.MealLocalDataSourceImp(this.requireContext()),new MealRemoteDataSource.MealRemoteDataSourceImp(requireContext())));
        calenderPresenter.getPlannedMealsByDate("Mon");
        calenderPresenter.getPlannedMealsByDate("Sat");
        calenderPresenter.getPlannedMealsByDate("Tue");
        calenderPresenter.getPlannedMealsByDate("Sun");
        calenderPresenter.getPlannedMealsByDate("Wed");
        calenderPresenter.getPlannedMealsByDate("Thu");
        calenderPresenter.getPlannedMealsByDate("Fri");
        calenderPresenter.getWeeklyPlannedMealsFirestore("Mon");
        calenderPresenter.getWeeklyPlannedMealsFirestore("Sat");
        calenderPresenter.getWeeklyPlannedMealsFirestore("Tue");
        calenderPresenter.getWeeklyPlannedMealsFirestore("Sun");
        calenderPresenter.getWeeklyPlannedMealsFirestore("Wed");
        calenderPresenter.getWeeklyPlannedMealsFirestore("Thu");
        calenderPresenter.getWeeklyPlannedMealsFirestore("Fri");





    }

    private void initViews(View view) {
        calenderAdapter=new CalenderAdapter(new ArrayList<>());
        recyclerViewSat=view.findViewById(R.id.recycler_view);
        recyclerViewSun=view.findViewById(R.id.recycler_view_sun);
        recyclerViewMon=view.findViewById(R.id.recycler_view_mon);
        recyclerViewTues=view.findViewById(R.id.recycler_view_tue);
        recyclerViewWed=view.findViewById(R.id.recycler_view_wed);
        recyclerViewThurs=view.findViewById(R.id.recycler_view_thurs);
        recyclerViewFri=view.findViewById(R.id.recycler_view_fri);
    }

    @Override
    public void onGetAllPlannedMeals(List<RandomMealResponse.MealsItem> plannedMeals, String date) {

        List<RandomMealResponse.MealsItem> filteredMeals = new ArrayList<>();

        for (RandomMealResponse.MealsItem mealsItem : plannedMeals) {
            if (mealsItem.getDateModified() != null && mealsItem.getDateModified().equalsIgnoreCase(date)) {
                filteredMeals.add(mealsItem);
            }
        }

        switch (date) {
            case "Sat":
                recyclerViewSat.setAdapter(new CalenderAdapter(filteredMeals));
                break;
            case "Sun":
                recyclerViewSun.setAdapter(new CalenderAdapter(filteredMeals));
                break;
            case "Mon":
                recyclerViewMon.setAdapter(new CalenderAdapter(filteredMeals));
                break;
            case "Tue":
                recyclerViewTues.setAdapter(new CalenderAdapter(filteredMeals));
                break;
            case "Wed":
                recyclerViewWed.setAdapter(new CalenderAdapter(filteredMeals));
                break;
            case "Thu":
                recyclerViewThurs.setAdapter(new CalenderAdapter(filteredMeals));
                break;
            case "Fri":
                recyclerViewFri.setAdapter(new CalenderAdapter(filteredMeals));
                break;
        }
    }


    @Override
    public void onGetAllPlannedMealsError(String errorMessage) {
        showErrorMessage(errorMessage);
    }

    @Override
    public void showWeeklyPlannedMeals(List<RandomMealResponse.MealsItem> mealsItems,String date) {
        List<RandomMealResponse.MealsItem> filteredMeals = new ArrayList<>();

        for (RandomMealResponse.MealsItem mealsItem : mealsItems) {
            if (mealsItem.getDateModified() != null && mealsItem.getDateModified().equalsIgnoreCase(date)) {
                filteredMeals.add(mealsItem);
                if(mealsItem != null){
                    calenderPresenter.addMealToFavorite(mealsItem);
                }
                Log.e("TAG", "showWeeklyPlannedMeals: "+mealsItem.getIdMeal());
            }
        }
        switch (date) {
            case "Sat":
                recyclerViewSat.setAdapter(new CalenderAdapter(filteredMeals));
                break;
            case "Sun":
                recyclerViewSun.setAdapter(new CalenderAdapter(filteredMeals));
                break;
            case "Mon":
                recyclerViewMon.setAdapter(new CalenderAdapter(filteredMeals));
                break;
            case "Tue":
                recyclerViewTues.setAdapter(new CalenderAdapter(filteredMeals));
                break;
            case "Wed":
                recyclerViewWed.setAdapter(new CalenderAdapter(filteredMeals));
                break;
            case "Thu":
                recyclerViewThurs.setAdapter(new CalenderAdapter(filteredMeals));
                break;
            case "Fri":
                recyclerViewFri.setAdapter(new CalenderAdapter(filteredMeals));
                break;
        }
    }

    @Override
    public void showPlannedMealsErrorFireStore(String error) {
        showErrorMsg(error);
    }

    @Override
    public void onInsertFavSuccess() {

    }

    @Override
    public void onInsertFavError(String localizedMessage) {
        showErrorMsg(localizedMessage);

    }

    private void showErrorMessage(String error){
        showErrorMsg(error);
    }

    private void showErrorMsg(String error){
        AlertMessage.showToastMessage(error,getContext());
    }
}