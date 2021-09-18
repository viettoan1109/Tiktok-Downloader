package com.tapbi.tiktokdownloader.ui.home;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.tapbi.tiktokdownloader.common.Constants;
import com.tapbi.tiktokdownloader.common.RetrofitClient;
import com.tapbi.tiktokdownloader.data.Oembed;
import com.tapbi.tiktokdownloader.interfaces.OembedApi;
import com.tapbi.tiktokdownloader.ui.base.BaseViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeViewModel extends BaseViewModel {

    public MutableLiveData<List<Oembed>> mutableLiveDataTextSaveInstagram = new MutableLiveData<>();
   // public FontRepository fontRepository;
    /*public void initTextSaveInsta() {
        fontRepository = FontRepository.getInstance();
        fontRepository.getAllTextSave().subscribe(new SingleObserver<List<TextSaveInsta>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }
            @Override
            public void onSuccess(@NonNull List<TextSaveInsta> list) {
                mutableLiveDataTextSaveInstagram.postValue(list);
            }
            @Override
            public void onError(@NonNull Throwable e) {
            }
        });
    }*/

    private void oembedApiCall(String urlConvert) {
        Retrofit retrofit = RetrofitClient.getClient(Constants.OEMBED_URL);
        OembedApi oembedApi = retrofit.create(OembedApi.class);
        Call<Oembed> call = oembedApi.getOembed(urlConvert);
        call.enqueue(new Callback<Oembed>() {
            @Override
            public void onResponse(Call<Oembed> call, Response<Oembed> response) {
                if (response.isSuccessful() && response.body() != null) {
                   /* Oembed oembed = response.body();
                    binding.imgHomeThumbnail.setVisibility(View.VISIBLE);
                    binding.videoHome.setVisibility(View.INVISIBLE);

                    Glide.with(getActivity()).load(oembed.getThumbnailUrl())
                            .centerCrop()
                            .into(binding.imgHomeThumbnail);*/
                }
            }

            @Override
            public void onFailure(Call<Oembed> call, Throwable t) {

            }
        });
    }

}
