package com.example.foodplanner.view.calender;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplanner.view.bottom_sheet.BottomSheetDismissListener;
import com.example.foodplanner.view.bottom_sheet.BottomSheetFragment;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dto.MealsItem;
import com.example.foodplanner.model.repo.MealRepoImp;
import com.example.foodplanner.model.repo.local.MealLocalDatasource;
import com.example.foodplanner.model.repo.remote.MealRemoteDataSource;
import com.example.foodplanner.model.repo.remote.RandomMealRemoteDataSourceImp;
import com.example.foodplanner.presenter.CalenderPresenter;
import com.example.foodplanner.view.AlertMessage;

import java.util.ArrayList;
import java.util.List;


public class CalenderFragment extends Fragment implements CalenderView {
    CalenderAdapter calenderAdapter;
    RecyclerView recyclerViewSat, recyclerViewSun, recyclerViewMon, recyclerViewTues, recyclerViewWed, recyclerViewThurs, recyclerViewFri;
    Button satBtn, sunBtn, monBtn, tuesBtn, wedBun, thursBtn, friBtn;
    String date;
    CalenderPresenter calenderPresenter;
    CalenderAdapter adapterSat;
    CalenderAdapter adapterSun;
    CalenderAdapter adapterMon;
    CalenderAdapter adapterTue;
    CalenderAdapter adapterWed;
    CalenderAdapter adapterThu;
    CalenderAdapter adapterFri;


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

        calenderPresenter = new CalenderPresenter.CalenderPresenterImp(this,
                new MealRepoImp(new RandomMealRemoteDataSourceImp(), new MealLocalDatasource.MealLocalDataSourceImp(this.requireContext()), new MealRemoteDataSource.MealRemoteDataSourceImp(requireContext())));
        calenderPresenter.getPlannedMealsByDate("Mon");
        calenderPresenter.getPlannedMealsByDate("Sat");
        calenderPresenter.getPlannedMealsByDate("Tue");
        calenderPresenter.getPlannedMealsByDate("Sun");
        calenderPresenter.getPlannedMealsByDate("Wed");
        calenderPresenter.getPlannedMealsByDate("Thu");
        calenderPresenter.getPlannedMealsByDate("Fri");

    }

    private void initViews(View view) {
        initDayButtons(view);
        calenderAdapter = new CalenderAdapter(new ArrayList<>());
        recyclerViewSat = view.findViewById(R.id.recycler_view);
        recyclerViewSun = view.findViewById(R.id.recycler_view_sun);
        recyclerViewMon = view.findViewById(R.id.recycler_view_mon);
        recyclerViewTues = view.findViewById(R.id.recycler_view_tue);
        recyclerViewWed = view.findViewById(R.id.recycler_view_wed);
        recyclerViewThurs = view.findViewById(R.id.recycler_view_thurs);
        recyclerViewFri = view.findViewById(R.id.recycler_view_fri);

        adapterSat = new CalenderAdapter(new ArrayList<>());
        adapterSun = new CalenderAdapter(new ArrayList<>());
        adapterMon = new CalenderAdapter(new ArrayList<>());
        adapterTue = new CalenderAdapter(new ArrayList<>());
        adapterWed = new CalenderAdapter(new ArrayList<>());
        adapterThu = new CalenderAdapter(new ArrayList<>());
        adapterFri = new CalenderAdapter(new ArrayList<>());

        adapterSat.onCancelClickListener = createOnCancelClickListener();
        adapterSun.onCancelClickListener = createOnCancelClickListener();
        adapterMon.onCancelClickListener = createOnCancelClickListener();
        adapterTue.onCancelClickListener = createOnCancelClickListener();
        adapterWed.onCancelClickListener = createOnCancelClickListener();
        adapterThu.onCancelClickListener = createOnCancelClickListener();
        adapterFri.onCancelClickListener = createOnCancelClickListener();

        recyclerViewSat.setAdapter(adapterSat);
        recyclerViewSun.setAdapter(adapterSun);
        recyclerViewMon.setAdapter(adapterMon);
        recyclerViewTues.setAdapter(adapterTue);
        recyclerViewWed.setAdapter(adapterWed);
        recyclerViewThurs.setAdapter(adapterThu);
        recyclerViewFri.setAdapter(adapterFri);

    }

    private void initDayButtons(View view) {
        satBtn=view.findViewById(R.id.sat_btn);
        satBtn.setOnClickListener(v -> navigateToBottomSheetFragment(view,"Sat"));
        sunBtn=view.findViewById(R.id.sun_btn);
        sunBtn.setOnClickListener(v -> navigateToBottomSheetFragment(view,"Sun"));

        monBtn=view.findViewById(R.id.mon_btn);
        monBtn.setOnClickListener(v -> navigateToBottomSheetFragment(view,"Mon"));

        tuesBtn=view.findViewById(R.id.tue_btn);
        tuesBtn.setOnClickListener(v -> navigateToBottomSheetFragment(view,"Tue"));

        wedBun=view.findViewById(R.id.wed_btn);
        wedBun.setOnClickListener(v -> navigateToBottomSheetFragment(view,"Wen"));

        thursBtn=view.findViewById(R.id.thurs_btn);
        thursBtn.setOnClickListener(v -> navigateToBottomSheetFragment(view,"Thu"));
        thursBtn.setOnClickListener(v -> navigateToBottomSheetFragment(view,"Thu"));

        friBtn=view.findViewById(R.id.fri_btn);
        friBtn.setOnClickListener(v -> navigateToBottomSheetFragment(view,"Fri"));
    }
    private void navigateToBottomSheetFragment(View view,String day){
        NavController navController = Navigation.findNavController(view);
        Bundle args = new Bundle();
        args.putString("day", day);
        navController.navigate(R.id.action_calenderFragment_to_bottomSheetFragment, args);



       /* Bundle args = new Bundle();
        args.putString("day", day);
        NavOptions navOptions = new NavOptions.Builder().setLaunchSingleTop(true).build();
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.bottomSheetFragment, args, navOptions);*/

        //BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        //bottomSheetFragment.show(requireActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
       /* NavController navController=Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        navController.navigate(R.id.bottomSheetFragment);*/
    };

    private CalenderAdapter.OnCancelClickListener createOnCancelClickListener() {
        return mealsItem -> {
            AlertMessage.showCustomAlertDialog(requireContext(),
                    "Are you sure you want to delete the meal from weekly plan?"
                    , "Yes", (dialog, which) -> {
                        calenderPresenter.deleteMealFromWeeklyPlan(mealsItem);
                    });
        };
    }

    @Override
    public void onGetAllPlannedMeals(List<MealsItem> plannedMeals, String date) {

        List<MealsItem> filteredMeals = new ArrayList<>();

        for (MealsItem mealsItem : plannedMeals) {
            if (mealsItem.getDateModified() != null && mealsItem.getDateModified().equalsIgnoreCase(date)) {
                filteredMeals.add(mealsItem);
            }
        }

        switch (date) {
            case "Sat":
                adapterSat.changeData(filteredMeals);
                break;
            case "Sun":
                adapterSun.changeData(filteredMeals);
                break;
            case "Mon":
                adapterMon.changeData(filteredMeals);
                break;
            case "Tue":
                adapterTue.changeData(filteredMeals);
                break;
            case "Wed":
                adapterWed.changeData(filteredMeals);
                break;
            case "Thu":
                adapterThu.changeData(filteredMeals);
                break;
            case "Fri":
                adapterFri.changeData(filteredMeals);
                break;
    }

    }


    @Override
    public void onGetAllPlannedMealsError(String errorMessage) {
        showErrorMessage(errorMessage);
    }

    @Override
    public void onInsertFavSuccess() {
        showMsg("Meal added to favorite");
    }

    @Override
    public void onInsertFavError(String localizedMessage) {
        showMsg(localizedMessage);

    }


    @Override
    public void onDeletePlannedMealSuccess() {
        showMsg("Meal deleted from weekly plan");
    }

    @Override
    public void onDeletePlannedMealError(String localizedMessage) {
        showMsg(localizedMessage);
    }

    private void showErrorMessage(String error){
        showMsg(error);
    }

    private void showMsg(String message){
        AlertMessage.showToastMessage(message,getContext());
    }



}