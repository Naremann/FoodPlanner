package com.example.foodplanner.presenter.login;

import com.example.foodplanner.Constants;
import com.example.foodplanner.db.FirebaseUtils;
import com.example.foodplanner.view.auth.login.LoginView;
import com.google.firebase.auth.FirebaseUser;

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
                Constants.CURRENT_USER= FirebaseUtils.getFirebaseInstance().getCurrentUser();
              //  Constants.EMAIL=email;
                loginView.showSuccessMessage();
            }
            else{
                loginView.showErrorMessage(Objects.requireNonNull(task.getException()).getLocalizedMessage());
            }
        });
    }
}
