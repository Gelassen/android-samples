package com.example.interview.network;

import com.example.interview.model.api.PageWithVideos;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by John on 10/3/2016.
 */
public interface ApiEndpointInterface {

    @GET("gildor/fd94ee68a383ec269958288590c7683d/raw/dbe1c5c9146e0355f17457645ebb7aad7c30533c/videos.json")
    Call<PageWithVideos> getInitialPageWithVideos();

}
