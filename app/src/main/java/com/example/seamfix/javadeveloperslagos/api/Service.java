package com.example.seamfix.javadeveloperslagos.api;

import com.example.seamfix.javadeveloperslagos.model.ItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Service {
    //Using pagination to get all required data
    @GET("/search/users?q=language:java+location:lagos")
    Call<ItemResponse> getItems(@Query("page") String page, @Query("per_page") String perPage);
}
