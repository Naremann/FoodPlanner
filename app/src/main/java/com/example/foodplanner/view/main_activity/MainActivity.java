package com.example.foodplanner.view.main_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.foodplanner.R;
import com.example.foodplanner.view.FragmentNavigator;
import com.example.foodplanner.view.SplashScreenFragment;

public class MainActivity extends AppCompatActivity {
    Fragment splashFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splashFragment=new SplashScreenFragment();
        FragmentNavigator.addFragment(splashFragment,this);
    }
}