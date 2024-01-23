package com.example.foodplanner.presenter.register;

import com.example.foodplanner.db.FirebaseUtils;
import com.example.foodplanner.view.register.RegisterView;

import java.util.Objects;

public class RegisterPresenterImp implements RegisterPresenter{
    RegisterView registerView;

    public RegisterPresenterImp(RegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void addAccountToFirebase(String email, String password) {
        FirebaseUtils.createAccount(email, password, task -> {
            if(task.isSuccessful()){
                registerView.showSuccessMessage();
            }
            else{
                registerView.showErrorMessage(Objects.requireNonNull(task.getException()).getLocalizedMessage());
            }
        });
    }
}
