package com.example.foodplanner.db;

import com.example.foodplanner.model.dto.RandomMealResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseUtils {
    public static FirebaseAuth firebaseAuth=getFirebaseInstance();
   static FirebaseUser currentUser = getFirebaseInstance().getCurrentUser();
    public static FirebaseAuth getFirebaseInstance(){
        if(firebaseAuth==null){
            firebaseAuth=FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    private static CollectionReference getCollectionReference(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return db.collection(RandomMealResponse.MealsItem.COLLECTION_NAME);
    }

    public static void addMealToFav(RandomMealResponse.MealsItem mealsItem,
                      OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener){

        if (currentUser != null) {
            getCollectionReference()
                    .document(String.valueOf(currentUser))
                    .set(mealsItem)
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }
    public static void getWeeklyPlannedMeals(String date, OnCompleteListener<QuerySnapshot>  onCompleteListener, OnFailureListener onFailureListener){
        String userEmail = String.valueOf(currentUser);
        CollectionReference favoritesCollection = getCollectionReference().document(userEmail).collection(RandomMealResponse.MealsItem.COLLECTION_NAME);

        favoritesCollection
                .whereEqualTo("dateModified", date)
                .get()
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
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
