package com.example.foodplanner.db;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUtils {
    public static FirebaseAuth firebaseAuth=getFirebaseInstance();
    public static FirebaseAuth getFirebaseInstance(){
        if(firebaseAuth==null){
            firebaseAuth=FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static void createAccount(String email,String password,OnCompleteListener<AuthResult> onCompleteListener){
        FirebaseAuth auth=FirebaseUtils.getFirebaseInstance();
        FirebaseUser firebaseUser=auth.getCurrentUser();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(onCompleteListener);
    }
    public static void signIn(String email, String password, OnCompleteListener<AuthResult> onCompleteListener){
        getFirebaseInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener);

    }

}
