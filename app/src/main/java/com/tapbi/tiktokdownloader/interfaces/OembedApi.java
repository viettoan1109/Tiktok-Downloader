package com.tapbi.tiktokdownloader.interfaces;

import com.tapbi.tiktokdownloader.data.Oembed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OembedApi {

    @GET("/oembed")
    Call<Oembed> getOembed (@Query("url") String url);

}
