package com.tapbi.tiktokdownloader.ui.video;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.Toolbar;

import com.tapbi.tiktokdownloader.R;
import com.tapbi.tiktokdownloader.common.Constants;
import com.tapbi.tiktokdownloader.data.model.Video;
import com.tapbi.tiktokdownloader.databinding.FragmentVideoBinding;
import com.tapbi.tiktokdownloader.ui.adapter.RecyclerViewAdapter;
import com.tapbi.tiktokdownloader.ui.adapter.VideoAdapter;
import com.tapbi.tiktokdownloader.ui.base.BaseFragment;
import com.tapbi.tiktokdownloader.ui.base.BaseViewModel;
import com.tapbi.tiktokdownloader.ui.download.DownloadFragment;
import com.tapbi.tiktokdownloader.ui.play.VideoPlayActivity;

import java.io.File;
import java.util.ArrayList;

import static androidx.databinding.DataBindingUtil.inflate;


public class VideoFragment extends BaseFragment<FragmentVideoBinding, VideoViewModel> {


    private RecyclerViewAdapter recyclerViewAdapter;
    public static ArrayList<Video> videoArrayList;
    public static final int PERMISSION_READ = 0;
    private Uri fileUri;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }


    @Override
    protected Class<VideoViewModel> getViewModelClass() {
        return VideoViewModel.class;

    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_video;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (checkPermission()) {
            videoList();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    fileUri = data.getData();
                    getActivity().getContentResolver().takePersistableUriPermission(fileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
        }
    }

    public void videoList() {

        binding.listVideo.setHasFixedSize(true);
        binding.listVideo.setItemViewCacheSize(20);
        binding.listVideo.setDrawingCacheEnabled(true);
        binding.listVideo.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        videoArrayList = new ArrayList<>();
        getVideos();
    }

    //get video files from storage
    public void getVideos() {
        String path = Environment.getExternalStorageDirectory().toString() + "/Download/VideoTiktok";
        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {

            Video video = new Video();
            String data = File.separator + files[i];
            video.setVideoThumblia(data);
            videoArrayList.add(video);
           // Uri uri = MediaStore.getMediaUri(getActivity(), Uri.parse(data));
            ContentResolver contentResolver = getActivity().getContentResolver();
           // Cursor cursor = contentResolver.query(uri, null, null, null, null);
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(data);
            long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            video.setVideoDuration(timeConversion(duration));
            video.setVideoUri(Uri.parse(data));
            retriever.release();


        }

        if (!videoArrayList.isEmpty()) {
            binding.txtVideo.setVisibility(View.GONE);
            binding.txtVideoSelect.setVisibility(View.VISIBLE);

        }
       /* ContentResolver contentResolver = getActivity().getContentResolver();

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = directory.getPath().query(uri, null, null, null, null);

        //looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {

                // String album = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ALBUM));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                //Uri uriThumblia = Uri.fromFile(new File(data));

                Video video = new Video();
                if (data.contains("VideoTiktok")) {
                    video.setVideoTitle(data);
                    video.setVideoUri(Uri.parse(data));
                    video.setVideoDuration(timeConversion(Long.parseLong(duration)));
                    videoArrayList.add(video);
                }

                if (!videoArrayList.isEmpty()) {
                    binding.txtVideo.setVisibility(View.GONE);

                }

            } while (cursor.moveToNext());
        }
*/

        //looping through all rows and adding to list

        VideoAdapter adapter = new VideoAdapter(getActivity(), videoArrayList);
        binding.listVideo.setAdapter(adapter);

        adapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                intent.putExtra("pos", pos);
                startActivity(intent);
            }
        });

    }

    public String timeConversion(long value) {
        String videoTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            videoTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            videoTime = String.format("%02d:%02d", mns, scs);
        }
        return videoTime;
    }

    private void initView() {


        /*binding.listVideo.setHasFixedSize(true);
        binding.listVideo.setItemViewCacheSize(20);
        binding.listVideo.setDrawingCacheEnabled(true);
        binding.listVideo.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity());

        binding.listVideo.setAdapter(recyclerViewAdapter);*/
        //setTe();
    }

    private void setTe() {
        String path = Environment.getExternalStorageDirectory().toString() + "/Download/VideoTiktok";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: " + files.length);
        for (int i = 0; i < files.length; i++) {
            binding.videoHome.setVideoPath(File.separator + files[1]);
            Toast.makeText(getContext(), File.separator + files[0], Toast.LENGTH_LONG).show();
            binding.videoHome.setMediaController(new MediaController(getActivity()));
            binding.videoHome.requestFocus();
            binding.videoHome.start();


        }
    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getActivity(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {
                        videoList();
                    }
                }
            }
        }
    }


}