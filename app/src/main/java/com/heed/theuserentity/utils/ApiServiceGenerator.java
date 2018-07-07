package com.heed.theuserentity.utils;

import com.heed.theuserentity.models.User;
import com.heed.theuserentity.utils.network.UserApiService;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceGenerator {

    private final static String BASE_URL = "http://private-anon-43f5685c43-test16231.apiary-mock.com/";

    private static Retrofit getRetrofitClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static UserApiService generateUserApiService() {
        return getRetrofitClient().create(UserApiService.class);
    }

    public static void getUserInfo(Callback<User> userInfoCallback) {
        Call<User> call = generateUserApiService().getUserInfo();
        call.enqueue(userInfoCallback);
    }

    public static void deleteUser(String id, Callback<ResponseBody> responseBodyCallback) {
        Call<ResponseBody> call = generateUserApiService().deleteUser(id);
        call.enqueue(responseBodyCallback);
    }
}
