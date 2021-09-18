package com.tapbi.tiktokdownloader.data;

import android.view.View;

import com.bumptech.glide.Glide;
import com.tapbi.tiktokdownloader.common.Constants;
import com.tapbi.tiktokdownloader.common.RetrofitClient;
import com.tapbi.tiktokdownloader.interfaces.OembedApi;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VideoRepository {

    private static VideoRepository videoRepository;
    public static VideoRepository getInstance() {
        if (videoRepository == null) {
            videoRepository = new VideoRepository();
        }
        return videoRepository;
    }


    private OembedApi oembedApi;

    public VideoRepository() {
        Retrofit retrofit = RetrofitClient.getClient(Constants.OEMBED_URL);
         oembedApi = retrofit.create(OembedApi.class);
    }


    /*private void oembedApiCall() {

        Call<Oembed> call = oembedApi.getOembed(urlConvert);
        call.enqueue(new Callback<Oembed>() {
            @Override
            public void onResponse(Call<Oembed> call, Response<Oembed> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Oembed oembed = response.body();
                    binding.imgHomeThumbnail.setVisibility(View.VISIBLE);
                    binding.videoHome.setVisibility(View.INVISIBLE);

                    Glide.with(getActivity()).load(oembed.getThumbnailUrl())
                            .centerCrop()
                            .into(binding.imgHomeThumbnail);
                }
            }

            @Override
            public void onFailure(Call<Oembed> call, Throwable t) {

            }
        });
    }*/
}
