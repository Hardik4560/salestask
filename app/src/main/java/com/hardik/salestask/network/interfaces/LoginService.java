package com.hardik.salestask.network.interfaces;

import com.hardik.salestask.models.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Hardik Shah on 9/7/2016.
 */
public interface LoginService {

    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(@Field("email") String email, @Field("password") String password);
}
