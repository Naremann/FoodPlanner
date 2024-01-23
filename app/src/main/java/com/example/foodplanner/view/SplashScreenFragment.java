package com.example.foodplanner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.foodplanner.R;
import com.example.foodplanner.view.register.RegisterFragment;

public class SplashScreenFragment extends Fragment {


    public SplashScreenFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler(Looper.getMainLooper()).postDelayed(this::navigateRegisterFragment,2000);

    }

    private void navigateRegisterFragment() {
        RegisterFragment registerFragment=new RegisterFragment();
        FragmentNavigator.addFragment(registerFragment,this.requireActivity());
    }
}