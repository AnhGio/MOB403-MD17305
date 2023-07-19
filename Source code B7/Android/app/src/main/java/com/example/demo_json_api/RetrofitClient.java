package com.example.demo_json_api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class RetrofitClient {
    private static final String BaseUrl = "http://10.82.0.24:3000/";

    public static NYManagerService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NYManagerService.class);
    }
}


interface NYManagerService {
    @GET("nyManager/list-ny")
    Call<List<NyModel>> getListNy();

    @POST("nyManager/add-ny")
    Call<ResMessage> addNewNy(@Body NyRequestModel requestModel);
}