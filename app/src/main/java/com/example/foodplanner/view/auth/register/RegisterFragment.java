package com.example.foodplanner.view.auth.register;

import android.app.ProgressDialog;
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
import com.example.foodplanner.presenter.register.RegisterPresenter;
import com.example.foodplanner.presenter.register.RegisterPresenterImp;
import com.example.foodplanner.view.FragmentNavigator;
import com.example.foodplanner.view.auth.login.LoginFragment;

public class RegisterFragment extends Fragment implements RegisterView{

   Button signUpBtn;
   EditText email,password;
   RegisterPresenter registerPresenter;
   TextView haveAccountText;
   ProgressDialog progressDialog;
    public RegisterFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerPresenter=new RegisterPresenterImp(this);
        initViews(view);

    }

    private void initViews(View view) {
        progressDialog=new ProgressDialog(this.getContext());
        signUpBtn=view.findViewById(R.id.signup_btn);
        email=view.findViewById(R.id.email_et);
        password=view.findViewById(R.id.pass_et);
        haveAccountText=view.findViewById(R.id.have_account_tv);
        haveAccountText.setOnClickListener(view1 -> navigateToLoginFragment());
        signUpBtn.setOnClickListener(view1 -> {
            addAccountToFirebase();
            showProgressDialog();
        });
    }

    private void navigateToLoginFragment() {
        FragmentNavigator.addFragment(new LoginFragment(),this.requireActivity(),R.id.fragment_container);
    }

    private void addAccountToFirebase() {
        registerPresenter.addAccountToFirebase(email.getText().toString(), password.getText().toString());
    }

    @Override
    public void showSuccessMessage() {
        AlertMessage.showToastMessage("Success",getContext());
        hideProgressDialog();
        navigateToLoginFragment();
    }

    @Override
    public void showErrorMessage(String error) {
        AlertMessage.showToastMessage(error,getContext());
        hideProgressDialog();

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

}