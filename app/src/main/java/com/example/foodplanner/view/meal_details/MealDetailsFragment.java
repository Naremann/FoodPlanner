package com.example.foodplanner.view.meal_details;

import static com.example.foodplanner.model.dto.Ingredient.getIngredients;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foodplanner.GlideImage;
import com.example.foodplanner.R;
import com.example.foodplanner.YouTubeVideo;
import com.example.foodplanner.db.SharedPreferencesManager;
import com.example.foodplanner.model.dto.Ingredient;
import com.example.foodplanner.model.dto.RandomMealResponse;
import com.example.foodplanner.model.repo.MealRepoImp;
import com.example.foodplanner.model.repo.local.MealLocalDatasource;
import com.example.foodplanner.model.repo.remote.MealRemoteDataSource;
import com.example.foodplanner.model.repo.remote.RandomMealRemoteDataSourceImp;
import com.example.foodplanner.presenter.MealDetailsPresenter;
import com.example.foodplanner.view.AlertMessage;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsFragment extends Fragment implements MealDetailsView {
    TextView mealTitle, mealCategory, mealArea;
    ImageView mealImg, fillHeartImg, emptyHeartImg, planImg;
    RecyclerView ingredientRecyclerView;
    RandomMealResponse.MealsItem mealsItem = null;
    IngredientAdapter ingredientAdapter;
    List<Ingredient> ingredients;
    WebView webView;
    ProgressBar videoProgressBar;
    MealDetailsPresenter mealDetailsPresenter, mealDetailsPresenter2;
    boolean isFavorite;
    Button sundayButton, mondayButton, tuesdayButton, wednesdayButton, thursdayButton, fridayButton, saturdayButton;


    public MealDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDependencies();
        initViews(view);
        if (getArguments() != null) {
            mealsItem = MealDetailsFragmentArgs.fromBundle(getArguments()).getMeal();
            isFavorite = SharedPreferencesManager.loadFavoriteStatus(requireContext(), mealsItem.getIdMeal());
            updateHeartIconVisibility(mealsItem);
            Log.e("TAG", "onViewCreated: " + mealsItem.getStrArea());
            emptyHeartImg.setOnClickListener(v -> {
                mealDetailsPresenter.addMealToFavorite(mealsItem);
                mealsItem.setStrCreativeCommonsConfirmed(SharedPreferencesManager.getUserEmail(requireContext()));
                mealsItem.setFavorite(true);
                onInsertFavSuccess();
                SharedPreferencesManager.saveFavoriteStatus(getContext(), true, mealsItem.getIdMeal());

            });
            fillHeartImg.setOnClickListener(v -> {
                deleteMealFromFav(mealsItem);
                
            });
        }
        ingredients = new ArrayList<>();
        if (mealsItem != null) {
            Log.e("TAG", "onViewCreated1: " + mealsItem.getStrIngredient1());
            getIngredients(mealsItem);

            ingredientAdapter.changeData(getIngredients(mealsItem));
            Log.e("TAG1", "onViewCreated: " + mealsItem.getStrCategory());
            setMealDataInViews(mealsItem);
        }

    }

    private void initDependencies() {
        mealDetailsPresenter = new MealDetailsPresenter.
                MealDetailsPresenterImp(new MealRepoImp(new RandomMealRemoteDataSourceImp(), new
                MealLocalDatasource.MealLocalDataSourceImp(this.requireContext()), new MealRemoteDataSource.MealRemoteDataSourceImp(requireContext())), this);


    }
    public void deleteMealFromFav(RandomMealResponse.MealsItem mealsItem){
        mealDetailsPresenter.deleteFavMeals(mealsItem);
        fillHeartImg.setVisibility(View.INVISIBLE);
        emptyHeartImg.setVisibility(View.VISIBLE);
        SharedPreferencesManager.saveFavoriteStatus(getContext(), false, mealsItem.getIdMeal());
    }


    private void initViews(View view) {
        planImg = view.findViewById(R.id.calender);
        planImg.setOnClickListener(v -> showDaySelectorDialog());
        emptyHeartImg = view.findViewById(R.id.empty_heart);
        fillHeartImg = view.findViewById(R.id.filled_heart);
        mealTitle = view.findViewById(R.id.title_tv);
        mealArea = view.findViewById(R.id.area_tv);
        mealCategory = view.findViewById(R.id.category_tv);
        mealImg = view.findViewById(R.id.meal_img);
        webView = view.findViewById(R.id.web_view);
        videoProgressBar = view.findViewById(R.id.video_progress_bar);
        ingredientRecyclerView = view.findViewById(R.id.ingredient_recycler_view);
        ingredientAdapter = new IngredientAdapter(new ArrayList<>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ingredientRecyclerView.setLayoutManager(linearLayoutManager);
        ingredientRecyclerView.setAdapter(ingredientAdapter);
    }


    private void setMealDataInViews(RandomMealResponse.MealsItem mealsItem) {
        mealTitle.setText(mealsItem.getStrMeal());
        mealCategory.setText(mealsItem.getStrCategory());
        mealArea.setText(mealsItem.getStrArea());
        GlideImage.downloadImageToImageView(this.getContext(), mealsItem.getStrMealThumb(), mealImg);
        YouTubeVideo.loadVideoUrlInWebView(webView, mealsItem.getStrYoutube(), videoProgressBar);
    }


    private void showDaySelectorDialog() {
        final Dialog dialog = new Dialog(this.requireContext());
        dialog.setContentView(R.layout.dialog_day_selector);
        dialog.setTitle("Select Day");
        initDialogButtons(dialog);
        View.OnClickListener dayClickListener = v -> {
            String selectedDay = ((Button) v).getText().toString();
            mealsItem.setDateModified(selectedDay);
            String savedEmail = SharedPreferencesManager.getUserEmail(requireContext());
            mealsItem.setStrCreativeCommonsConfirmed(savedEmail);
            Log.e("TAG", "email: " + savedEmail);
            //mealDetailsPresenter.i
            mealDetailsPresenter.addMealToWeeklyPlan(mealsItem);
           // mealDetailsPresenter.addWeeklyPlayMealToFireStore(mealsItem);
            dialog.dismiss();
        };
        sundayButton.setOnClickListener(dayClickListener);
        mondayButton.setOnClickListener(dayClickListener);
        tuesdayButton.setOnClickListener(dayClickListener);
        wednesdayButton.setOnClickListener(dayClickListener);
        thursdayButton.setOnClickListener(dayClickListener);
        fridayButton.setOnClickListener(dayClickListener);
        saturdayButton.setOnClickListener(dayClickListener);
        dialog.show();
    }

    private void initDialogButtons(Dialog dialog) {
        sundayButton = dialog.findViewById(R.id.sundayButton);
        mondayButton = dialog.findViewById(R.id.mondayButton);
        tuesdayButton = dialog.findViewById(R.id.tuesdayButton);
        wednesdayButton = dialog.findViewById(R.id.wednesdayButton);
        thursdayButton = dialog.findViewById(R.id.thursdayButton);
        fridayButton = dialog.findViewById(R.id.fridayButton);
        saturdayButton = dialog.findViewById(R.id.saturdayButton);
    }

    @Override
    public void onInsertFavSuccess() {
        fillHeartImg.setVisibility(View.VISIBLE);
        emptyHeartImg.setVisibility(View.INVISIBLE);
        if (isAdded()) {
            AlertMessage.showToastMessage("Meal added to favorite", requireContext());
        }    }

    @Override
    public void onInsertFavError(String error) {
        AlertMessage.showToastMessage(error, requireContext());
    }

    @Override
    public void onPlanMealSuccess() {
        if(isAdded()){
            AlertMessage.showToastMessage("Saved to meal plan", requireContext());
        }
    }

    @Override
    public void onPlanMealFail(String error) {
        showFailMessage(error);
    }

    @Override
    public void onSuccessDeleteFromFav() {
        AlertMessage.showToastMessage("Meal deleted from favorite", this.getContext());
    }

    @Override
    public void onFailDeleteFromFav(String error) {
        showFailMessage(error);
    }
    void showFailMessage(String error) {
        AlertMessage.showToastMessage(error, this.getContext());
    }


    private void updateHeartIconVisibility(RandomMealResponse.MealsItem mealsItem) {
        if (isFavorite) {
            fillHeartImg.setVisibility(View.VISIBLE);
            emptyHeartImg.setVisibility(View.INVISIBLE);
        } else {
            if(mealsItem.isFavorite()){
                fillHeartImg.setVisibility(View.VISIBLE);
                emptyHeartImg.setVisibility(View.INVISIBLE);
            }
            else {
                fillHeartImg.setVisibility(View.INVISIBLE);
                emptyHeartImg.setVisibility(View.VISIBLE);
            }
            //fillHeartImg.setVisibility(View.INVISIBLE);
            //emptyHeartImg.setVisibility(View.VISIBLE);
        }
    }
}