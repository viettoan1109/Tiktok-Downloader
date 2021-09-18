package com.tapbi.tiktokdownloader.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tapbi.tiktokdownloader.R;
import com.tapbi.tiktokdownloader.ui.main.MainActivity;
import com.tapbi.tiktokdownloader.utils.Method;
import com.tapbi.tiktokdownloader.utils.StorageUtil;

import java.io.File;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000;
    private File storage;
    private String[] storagePaths;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       // loaddata();
        init();

    }

  /*  private void loaddata() {
        storagePaths = StorageUtil.getStorageDirectories(this);

        for (String path : storagePaths) {
            storage = new File(path);
            Method.load_Directory_Files(storage);
        }
    }*/

    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}