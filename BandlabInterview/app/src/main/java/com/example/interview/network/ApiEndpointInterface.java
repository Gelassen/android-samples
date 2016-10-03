package com.example.interview.network;

import com.example.interview.model.PageWithVideos;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by John on 10/3/2016.
 */
public interface ApiEndpointInterface {
    //https://gist.githubusercontent.com/gildor/fd94ee68a383ec269958288590c7683d/raw/dbe1c5c9146e0355f17457645ebb7aad7c30533c/videos.json
    @GET("gildor/9c826c7bb14946ef9e6bb8305651cf15/raw/f5f5061e93c963a1e4e405d861fc941365f5e455/videos.json")
    Call<PageWithVideos> getInitialPageWithVideos();

}
