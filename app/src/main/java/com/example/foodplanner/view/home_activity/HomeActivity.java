package com.example.foodplanner.view.home_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodplanner.R;
import com.example.foodplanner.view.FragmentNavigator;
import com.example.foodplanner.view.home.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FragmentNavigator.addFragment(new HomeFragment(),this,R.id.home_fragment_container);
    }
}