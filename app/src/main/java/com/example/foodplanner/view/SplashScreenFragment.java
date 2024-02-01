package com.example.foodplanner.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;

import com.example.foodplanner.R;
import com.example.foodplanner.view.auth.login.LoginFragment;
import com.google.android.material.card.MaterialCardView;

public class SplashScreenFragment extends Fragment {


    public SplashScreenFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_splash_screen,container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialCardView cardView = view.findViewById(R.id.cardview);
        cardView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
              animateOnCardView(cardView,this);

                return true;
            }
        });
        new Handler(Looper.getMainLooper()).postDelayed(this::navigateLoginFragment,2000);

    }

    private void animateOnCardView(MaterialCardView cardView,ViewTreeObserver.OnPreDrawListener drawListener) {
        cardView.getViewTreeObserver().removeOnPreDrawListener(drawListener);
        cardView.setScaleX(0.1f);
        cardView.setScaleY(0.1f);

        cardView.setTranslationY(cardView.getHeight());

        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(cardView, "translationY", 0);
        translationAnimator.setDuration(2000);

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(cardView, "scaleX", 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(cardView, "scaleY", 1f);
        AnimatorSet scaleAnimatorSet = new AnimatorSet();
        scaleAnimatorSet.playTogether(scaleXAnimator, scaleYAnimator);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationAnimator, scaleAnimatorSet);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();
    }

    private void navigateLoginFragment() {
        FragmentNavigator.addFragment(new LoginFragment(),this.requireActivity(),R.id.fragment_container);
    }
}