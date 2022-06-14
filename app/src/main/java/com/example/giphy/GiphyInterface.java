package com.example.giphy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyInterface {
    @GET("search")
    Call<ParentObject> getGif(@Query("q") String text,
                              @Query("api_key") String key);

    @GET("trending")
    Call<ParentObject> getTrendingGif(@Query("api_key") String key);
}
