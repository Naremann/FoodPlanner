package com.example.foodplanner.presenter.login;

import com.example.foodplanner.db.FirebaseUtils;
import com.example.foodplanner.view.auth.login.LoginView;

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
