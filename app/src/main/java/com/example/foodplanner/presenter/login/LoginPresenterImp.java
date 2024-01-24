package com.example.foodplanner.presenter.login;

import androidx.annotation.NonNull;

import com.example.foodplanner.db.FirebaseUtils;
import com.example.foodplanner.view.login.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;

public class LoginPresenterImp implements LoginPresenter{
    LoginView loginView;

    public LoginPresenterImp(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void signInWithFirebaseAuth(String email, String password) {
        FirebaseUtils.signIn(email, password, task -> {
            if(task.isSuccessful()){
                loginView.showSuccessMessage();
            }
            else{
                loginView.showErrorMessage(Objects.requireNonNull(task.getException()).getLocalizedMessage());
            }
        });
    }
}
