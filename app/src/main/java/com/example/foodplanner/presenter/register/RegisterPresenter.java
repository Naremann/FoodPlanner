package com.example.foodplanner.presenter.register;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

public interface RegisterPresenter {
    void addAccountToFirebase(String email, String password);
    void signUpWithGoogle(String idToken);
}
