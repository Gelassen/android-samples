package com.example.interview.network;

import com.example.interview.model.api.PageWithVideos;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by John on 10/3/2016.
 */
public interface ApiEndpointInterface {

    @GET("gildor/02526b8f6495898bd8933d49a41c5991/raw/64c689cb2cfb9da3531d1d93eebfb11dffd0380b/videos.json")
    Call<PageWithVideos> getInitialPageWithVideos();

}
