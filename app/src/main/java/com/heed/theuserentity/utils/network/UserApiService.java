package com.heed.theuserentity.utils.network;

import com.heed.theuserentity.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApiService {

    @GET("user/all")
    Call<User> getUserInfo();

    @DELETE("user/{id}")
    Call<ResponseBody> deleteUser(@Path("id") String id);
}
