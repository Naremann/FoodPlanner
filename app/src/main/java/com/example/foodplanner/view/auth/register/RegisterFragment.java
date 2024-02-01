package com.example.foodplanner.view.auth.register;

import static androidx.core.content.ContextCompat.getColor;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class RegisterFragment extends Fragment implements RegisterView{

   Button signUpBtn;
   EditText email,password,confirmPass;
   RegisterPresenter registerPresenter;
   TextView haveAccountText;
   ProgressDialog progressDialog;
    TextInputLayout inputLayoutEmail,inputLayoutPass,inputLayoutConfirmPass;
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
        inputLayoutEmail =view.findViewById(R.id.email_input_layout);
        inputLayoutPass=view.findViewById(R.id.pass_input_layout);
        inputLayoutConfirmPass=view.findViewById(R.id.confirm_pass_input_layout);
        signUpBtn=view.findViewById(R.id.signup_btn);
        email=view.findViewById(R.id.email_et);
        password=view.findViewById(R.id.pass_et);
        confirmPass=view.findViewById(R.id.confirm_pass_et);
        haveAccountText=view.findViewById(R.id.have_account_tv);
        haveAccountText.setOnClickListener(view1 -> navigateToLoginFragment());
        signUpBtn.setOnClickListener(view1 -> {
            if(validateFields()){
                addAccountToFirebase();
                showProgressDialog();
            }
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
    boolean validateFields(){
        boolean isValid=true;
        if(email.getText().toString().isEmpty()){
            showError("Please Enter The Email",inputLayoutEmail);
        }
        else {
            showError(null,inputLayoutEmail);
        }
        if(password.getText().toString().isEmpty()){
            showError("Please Enter The Password",inputLayoutPass);
            isValid=false;
        }
        else {
            showError(null,inputLayoutPass);
        }
        if(confirmPass.getText().toString().isEmpty()){
            showError("Please Enter The Password",inputLayoutConfirmPass);
            isValid=false;
        }
        else {
            if(!confirmPass.getText().toString().equals(password.getText().toString())){
                showError("The Password didn't match",inputLayoutConfirmPass);
                isValid=false;
            }
            else {
                showError(null,inputLayoutConfirmPass);
            }
        }

        return isValid;
    }

    private void showError(String errorMessage, TextInputLayout inputLayout) {
        inputLayout.setError(errorMessage);
        inputLayout.setBoxStrokeErrorColor(ColorStateList.valueOf(getColor(this.requireContext(), R.color.red)));
    }

}