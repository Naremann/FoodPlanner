package com.example.foodplanner.view.auth.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodplanner.view.AlertMessage;
import com.example.foodplanner.R;
import com.example.foodplanner.presenter.login.LoginPresenter;
import com.example.foodplanner.presenter.login.LoginPresenterImp;
import com.example.foodplanner.view.FragmentNavigator;
import com.example.foodplanner.view.activity.HomeActivity;
import com.example.foodplanner.view.auth.register.RegisterFragment;

public class LoginFragment extends Fragment implements LoginView {

    Button signInBtn;
    EditText email, password;
    TextView haveAccountText;
    LoginPresenter loginPresenter;
    ProgressDialog progressDialog;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginPresenter = new LoginPresenterImp(this);
        initViews(view);

    }

    private void initViews(View view) {
        progressDialog = new ProgressDialog(this.getContext());
        signInBtn = view.findViewById(R.id.sign_in_btn);
        email = view.findViewById(R.id.email_et);
        password = view.findViewById(R.id.pass_et);
        haveAccountText = view.findViewById(R.id.have_account_tv);
        haveAccountText.setOnClickListener(view1 -> navigateToRegisterFragment());
        signInBtn.setOnClickListener(view1 ->
        {
            loginWithFirebaseAuth();
            showProgressDialog();
        });
    }

    void showProgressDialog() {
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    private void navigateToRegisterFragment() {
        FragmentNavigator.addFragment(new RegisterFragment(), this.requireActivity(), R.id.fragment_container);
    }

    private void loginWithFirebaseAuth() {
        loginPresenter.signInWithFirebaseAuth(email.getText().toString(), password.getText().toString());
    }

    @Override
    public void showSuccessMessage() {
        AlertMessage.showToastMessage("Sign in Successfully", this.getContext());
        hideProgressDialog();
        startHomeActivity();
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this.getContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void showErrorMessage(String error) {
        AlertMessage.showToastMessage(error, this.getContext());
        hideProgressDialog();
    }
}