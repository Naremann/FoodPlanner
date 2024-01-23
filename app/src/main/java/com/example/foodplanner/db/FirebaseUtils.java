package com.example.foodplanner.db;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;

public class FirebaseUtils {
    public static FirebaseAuth firebaseAuth=null;
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

}
