package edu.upc.dsa.dsa_error404_android;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("game/users/register")
    Call<User> registerUser(@Body Credentials credentials);

    @POST("game/users/login")
    Call<User> loginUser(@Body Credentials credentials);

    @POST("game/users/objects/buy")
    Call<User> comprarItem(@Body Credentials credentials);
}