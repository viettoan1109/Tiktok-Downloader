package com.tapbi.tiktokdownloader.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tapbi.tiktokdownloader.R;

import com.tapbi.tiktokdownloader.databinding.ActivityMainBinding;
import com.tapbi.tiktokdownloader.ui.base.BaseActivity;
import com.tapbi.tiktokdownloader.ui.download.DownloadFragment;
import com.tapbi.tiktokdownloader.ui.home.HomeFragment;
import com.tapbi.tiktokdownloader.ui.setting.SettingFragment;
import com.tapbi.tiktokdownloader.ui.video.VideoFragment;

import java.io.File;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private HomeFragment homeFragment = new HomeFragment();
    private VideoFragment videoFragment = new VideoFragment();
    private SettingFragment settingFragment = new SettingFragment();
    public static final int PERMISSION_READ = 0;


    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void init() {
        loadFragment(homeFragment, "1");
        binding.bottomMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //if (getSupportFragmentManager().findFragmentByTag("1") == null){
                        loadFragment(homeFragment, "1");
                    return true;
                case R.id.navigation_download:
                    loadFragment(videoFragment, "2");
                    return true;
                case R.id.navigation_setting:
                    loadFragment(settingFragment, "3");
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment, tag);
       // transaction.addToBackStack(null);
        transaction.commit();
    }

    

}