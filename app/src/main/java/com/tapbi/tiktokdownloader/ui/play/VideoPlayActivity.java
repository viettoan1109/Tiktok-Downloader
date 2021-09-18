package com.tapbi.tiktokdownloader.ui.play;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.tapbi.tiktokdownloader.R;
import com.tapbi.tiktokdownloader.databinding.ActivityVideoPlayBinding;
import com.tapbi.tiktokdownloader.ui.base.BaseActivity;

import java.io.File;
import java.io.IOException;

import static com.tapbi.tiktokdownloader.ui.video.VideoFragment.videoArrayList;

public class VideoPlayActivity extends BaseActivity<ActivityVideoPlayBinding, PlayViewModel> {

    int video_index = 0;
    double current_pos, total_duration;

    Handler mHandler, handler;
    boolean isVisible = true;
    public static final int PERMISSION_READ = 0;

    @Override
    protected Class getViewModelClass() {
        return PlayViewModel.class;
    }

    @Override
    protected void init() {
        if (checkPermission()) {
            setVideo();

        }
        setOnBackpress();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_play;
    }


    private void setOnBackpress() {
        binding.imgPlayBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

    public void setVideo() {
        video_index = getIntent().getIntExtra("pos", 0);
        mHandler = new Handler();
        handler = new Handler();

        binding.videoPlay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                video_index++;
                if (video_index < (videoArrayList.size())) {
                    playVideo(video_index);
                } else {
                    video_index = 0;
                    playVideo(video_index);
                    videoArrayList.clear();
                }

            }
        });

        binding.videoPlay.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setVideoProgress();
            }
        });

        playVideo(video_index);
        setPause();
        hideLayout();
        deleteVideo(video_index);
    }


    public void playVideo(int pos) {
        try {
            binding.videoPlay.setVideoURI(videoArrayList.get(pos).getVideoUri());
            binding.videoPlay.start();
            binding.imgPause.setImageResource(R.drawable.ic_pause);
            video_index = pos;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteVideo(int pos) {

        binding.imgPlayDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri root = videoArrayList.get(pos).getVideoUri();
                Toast.makeText(VideoPlayActivity.this ,String.valueOf(root),Toast.LENGTH_SHORT).show();

                File file = new File(String.valueOf(root));
                if (file.exists()) {
                    file.delete();
                    videoArrayList.remove(pos);
                    onBackPressed();
                }
            }
        });
    }


    public void setVideoProgress() {

        current_pos = binding.videoPlay.getCurrentPosition();
        total_duration = binding.videoPlay.getDuration();


        binding.txtPlayTotal.setText(timeConversion((long) total_duration));
        binding.txtPlayCurrent.setText(timeConversion((long) current_pos));
        binding.seekbar.setMax((int) total_duration);
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_pos = binding.videoPlay.getCurrentPosition();
                    binding.txtPlayCurrent.setText(timeConversion((long) current_pos));
                    binding.seekbar.setProgress((int) current_pos);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);


        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_pos = seekBar.getProgress();
                binding.videoPlay.seekTo((int) current_pos);
            }
        });
    }

    public void setPause() {
        binding.imgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.videoPlay.isPlaying()) {
                    binding.videoPlay.pause();
                    binding.imgPause.setImageResource(R.drawable.ic_play);
                } else {
                    binding.videoPlay.start();
                    binding.imgPause.setImageResource(R.drawable.ic_pause);
                }
            }
        });
    }

    public String timeConversion(long value) {
        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;
    }

    // hide progress when the video is playing
    public void hideLayout() {

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                binding.rlPlayToolbar.setVisibility(View.GONE);
                binding.rlPlayProgress.setVisibility(View.GONE);
                isVisible = false;
            }
        };
        handler.postDelayed(runnable, 5000);

        binding.rlPlayVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(runnable);
                if (isVisible) {
                    binding.rlPlayToolbar.setVisibility(View.GONE);
                    binding.rlPlayProgress.setVisibility(View.GONE);
                    isVisible = false;
                } else {
                    binding.rlPlayToolbar.setVisibility(View.VISIBLE);
                    binding.rlPlayProgress.setVisibility(View.VISIBLE);
                    mHandler.postDelayed(runnable, 5000);
                    isVisible = true;
                }
            }
        });

    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getApplicationContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {
                        setVideo();
                    }
                }
            }
        }
    }


}