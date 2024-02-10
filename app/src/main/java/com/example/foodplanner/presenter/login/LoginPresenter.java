package com.example.foodplanner.presenter.login;

import android.app.Activity;
import android.content.Intent;

public interface LoginPresenter {
    void signInWithFirebaseAuth(String email,String password);
     void signInWithGoogle(Activity activity);
    void handleGoogleSignInResult(Intent data);
}
