package com.example.foodplanner.presenter.login;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.core.Single;

public interface LoginPresenter {
    void signInWithFirebaseAuth(String email,String password);
   //  void signInWithGoogle(Activity activity);
    void handleGoogleSignInResult(Intent data);
     void signInWithGoogle(GoogleSignInAccount account);
}
