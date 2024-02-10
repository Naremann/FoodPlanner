package com.example.foodplanner.api;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class ApiManager {
    static Retrofit retrofit=null;
    final static String BASE_URL="https://themealdb.com/";
    public static Retrofit getInstance(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())

                    .build();
        }
        return retrofit;
    }
    public static WebService getApi(){
        return getInstance().create(WebService.class);
    }


}
