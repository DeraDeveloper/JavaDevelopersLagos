package com.example.seamfix.javadeveloperslagos.api;

import com.example.seamfix.javadeveloperslagos.model.ItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by SEAMFIX on 9/12/2017.
 */

public interface Service {
    @GET("/search/users?q=language:java+location:lagos")
    Call<ItemResponse> getItems();
}
