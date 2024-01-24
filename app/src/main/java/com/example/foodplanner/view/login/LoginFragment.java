package com.example.foodplanner.view.login;

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

import com.example.foodplanner.AlertMessage;
import com.example.foodplanner.R;
import com.example.foodplanner.presenter.login.LoginPresenter;
import com.example.foodplanner.presenter.login.LoginPresenterImp;
import com.example.foodplanner.view.FragmentNavigator;
import com.example.foodplanner.view.register.RegisterFragment;

public class LoginFragment extends Fragment implements LoginView{

    Button signInBtn;
    EditText email,password;
    TextView haveAccountText;
    LoginPresenter loginPresenter;

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
        loginPresenter= new LoginPresenterImp(this);
        initViews(view);

    }

    private void initViews(View view) {
        signInBtn=view.findViewById(R.id.sign_in_btn);
        email=view.findViewById(R.id.email_et);
        password=view.findViewById(R.id.pass_et);
        haveAccountText=view.findViewById(R.id.have_account_tv);
        haveAccountText.setOnClickListener(view1 -> navigateToRegisterFragment());
        signInBtn.setOnClickListener(view1 -> loginWithFirebaseAuth());
    }

    private void navigateToRegisterFragment() {
        FragmentNavigator.addFragment(new RegisterFragment(),this.requireActivity());
    }

    private void loginWithFirebaseAuth() {
        loginPresenter.signInWithFirebaseAuth(email.getText().toString(),password.getText().toString());
    }

    @Override
    public void showSuccessMessage() {
        AlertMessage.showToastMessage("Sign in Successfully",this.getContext());
    }

    @Override
    public void showErrorMessage(String error) {
        AlertMessage.showToastMessage(error,this.getContext());
    }
}