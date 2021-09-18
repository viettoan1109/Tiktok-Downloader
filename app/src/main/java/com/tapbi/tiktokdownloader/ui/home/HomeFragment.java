package com.tapbi.tiktokdownloader.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tapbi.tiktokdownloader.BuildConfig;
import com.tapbi.tiktokdownloader.R;
import com.tapbi.tiktokdownloader.common.Constants;
import com.tapbi.tiktokdownloader.common.RetrofitClient;
import com.tapbi.tiktokdownloader.data.Oembed;
import com.tapbi.tiktokdownloader.data.model.TiktokVideo;
import com.tapbi.tiktokdownloader.databinding.FragmentHomeBinding;
import com.tapbi.tiktokdownloader.interfaces.OembedApi;
import com.tapbi.tiktokdownloader.ui.base.BaseFragment;
import com.tapbi.tiktokdownloader.utils.DownloadVideoAsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements View.OnClickListener {


    public static final int PERMISSION_WRITE = 0;
    private ClipboardManager mClipboardManager;
    private View view;
    private String url;
    private String urlConvert;
    private String urlPlay;
    private String channel;
    private String videoId;

    @Override
    protected Class getViewModelClass() {
        return HomeViewModel.class;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mClipboardManager = (ClipboardManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CLIPBOARD_SERVICE);
        initView();
    }

    private void initView() {
        binding.imgHomePaste.setOnClickListener(this);
        binding.imgHomeClose.setOnClickListener(this);
        binding.btnHomeOpen.setOnClickListener(this);
        binding.imgHomePlay.setOnClickListener(this);
        binding.btnHomeDownload.setOnClickListener(this);

        binding.edtHomeLink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setupURL();
              //  binding.imgHomeClose.setVisibility(View.VISIBLE);
                //binding.imgHomePaste.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void download() {

        DownloadVideoAsyncTask task = new DownloadVideoAsyncTask(getActivity(), videoId);
        task.execute(urlPlay);
        binding.progressHomePlay.setVisibility(View.INVISIBLE);

    }

    private void callApi(String channel, String videoId) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://tiktok.p.rapidapi.com/live/post/direct?video=https%3A%2F%2Fwww.tiktok.com%2F%40" + channel + "%2Fvideo%2F" + videoId)
                .get()
                .addHeader(Constants.HEADER_KEY, Constants.KEY)
                .addHeader(Constants.HEADER_HOST, Constants.HOST)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String myResponse = response.body().string();
                    Gson gson = new Gson();
                    TiktokVideo tiktokVideo = gson.fromJson(myResponse, TiktokVideo.class);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            urlPlay = tiktokVideo.getTmpNoWatermakrUrl();
                            binding.imgHomePlay.setVisibility(View.VISIBLE);
                            binding.btnHomeDownload.setVisibility(View.VISIBLE);
                            binding.progressHomePlay.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });
    }

    private void pasteText() {
        String clipboardText;
        clipboardText = mClipboardManager.getPrimaryClip().getItemAt(0)
                .coerceToText(getContext()).toString();
//        hideKeyBoard();
        if (!clipboardText.isEmpty()){
            binding.edtHomeLink.setText(clipboardText);
            binding.imgHomePaste.setVisibility(View.INVISIBLE);
            binding.imgHomeClose.setVisibility(View.VISIBLE);
            binding.btnHomeOpen.setVisibility(View.INVISIBLE);

        }


    }

    private void clearText() {
        binding.imgHomeClose.setVisibility(View.INVISIBLE);
        binding.imgHomePaste.setVisibility(View.VISIBLE);
        binding.videoHome.setVisibility(View.INVISIBLE);
        binding.btnHomeOpen.setVisibility(View.VISIBLE);
        binding.imgHomeThumbnail.setVisibility(View.INVISIBLE);
        binding.btnHomeOpen.setVisibility(View.INVISIBLE);
        binding.btnHomeDownload.setVisibility(View.INVISIBLE);
        binding.edtHomeLink.setText("");
    }

    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void openTiktokApp() {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.ss.android.ugc.trill");
        if (intent != null) {
            startActivity(intent);
        } else {
            String url = "https://play.google.com/store/apps/details?id=com.ss.android.ugc.trill";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }


    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupURL() {
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                convertURL();
                oembedApiCall();
                callApi(channel, videoId);
            }
        });
        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        binding.webView.getSettings().setUserAgentString(newUA);
        url = String.valueOf(binding.edtHomeLink.getText());
        if (url.contains("http") && url.contains("tiktok")) {
            binding.webView.loadUrl(url);
        } else {
            Toast.makeText(getActivity(), "it not TikTok url", Toast.LENGTH_LONG).show();

        }

    }

    private void convertURL() {
        url = binding.webView.getUrl();
        if (url.contains("?")) {
            urlConvert = url.substring(0, url.indexOf("?"));
            channel = urlConvert.substring(urlConvert.indexOf("@") + 1, urlConvert.indexOf("/video"));
            videoId = urlConvert.substring(urlConvert.lastIndexOf("/") + 1, urlConvert.length());

            if (urlConvert.contains("tiktok")) {
                binding.btnHomeOpen.setVisibility(View.GONE);
                binding.videoHome.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getActivity(), "it not TikTok url", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getActivity(), "it not TikTok url", Toast.LENGTH_LONG).show();
        }

    }

    private void playVideo(){
        binding.videoHome.setVisibility(View.VISIBLE);
        binding.imgHomePlay.setVisibility(View.INVISIBLE);
        binding.imgHomeThumbnail.setVisibility(View.INVISIBLE);
        binding.videoHome.setVisibility(View.VISIBLE);
        binding.videoHome.setVideoPath(urlPlay);
        binding.videoHome.start();
    }

    private void oembedApiCall() {
        Retrofit retrofit = RetrofitClient.getClient(Constants.OEMBED_URL);
        OembedApi oembedApi = retrofit.create(OembedApi.class);
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
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_home_paste:
                binding.progressHomePlay.setVisibility(View.VISIBLE);
                pasteText();
                setupURL();
                break;

            case R.id.img_home_close:
                clearText();
                break;

            case R.id.btn_home_open:
                openTiktokApp();
                break;

            case R.id.img_home_play:
                playVideo();
                break;

            case R.id.btn_home_download:
                binding.videoHome.pause();
                binding.progressHomePlay.setVisibility(View.VISIBLE);
                if (checkfPermission()){
                    download();
                }
                break;
        }
    }



    public boolean checkfPermission() {
        int WRITE_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if ((WRITE_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_WRITE: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getActivity(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {
                        download();
                    }
                }
            }
        }
    }


}