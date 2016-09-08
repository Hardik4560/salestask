package com.hardik.salestask.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hardik Shah on 9/7/2016.
 */
public class NetworkControllerRetro {

    private static final String BASE_URL = "http://depasserinfotech.in/reporting-manager/api/";

    private final Retrofit retrofit;
    private Context mContext;
    private static NetworkControllerRetro _INSTANCE;

    private NetworkControllerRetro(Context context) {
        this.mContext = context;

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static NetworkControllerRetro initialize(Context context) {
        if (_INSTANCE == null) {
            _INSTANCE = new NetworkControllerRetro(context);
        }
        return _INSTANCE;
    }

    public static NetworkControllerRetro getInstance() {
        if (_INSTANCE == null) {
            throw new RuntimeException(NetworkController.class + " not initialized");
        }
        return _INSTANCE;
    }
}
