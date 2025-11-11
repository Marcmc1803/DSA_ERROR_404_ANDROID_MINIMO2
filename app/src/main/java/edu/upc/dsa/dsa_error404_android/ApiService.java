package edu.upc.dsa.dsa_error404_android;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/register")
    Call<User> registerUser(@Body User user);
    @POST("auth/login")
    Call<User> loginUser(@Body Credentials credentials);
}