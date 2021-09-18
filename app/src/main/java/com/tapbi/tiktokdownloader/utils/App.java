package com.tapbi.tiktokdownloader.utils;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }

}
