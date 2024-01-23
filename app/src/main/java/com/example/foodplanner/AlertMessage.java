package com.example.foodplanner;

import android.content.Context;
import android.widget.Toast;

public class AlertMessage {
    public static void showToastMessage(String message, Context context){
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }

}
