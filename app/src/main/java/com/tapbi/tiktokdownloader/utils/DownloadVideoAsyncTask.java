package com.tapbi.tiktokdownloader.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadVideoAsyncTask extends AsyncTask<String, Integer, String> {

    private Context mContext;
    private String mVideoId;

    public DownloadVideoAsyncTask(Context context, String videoId) {
        mContext = context;
        mVideoId = videoId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File myDir = new File(root + "/VideoTiktok");
            if (!myDir.exists()) {
                if (myDir.mkdirs()) {
                    Log.i("Folder ", "created");
                }
            }

            File mediaFile = new File(myDir.getAbsolutePath() + "/tiktok_" + mVideoId + ".mp4");

/*
            String root = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + File.separator +"TiktokDownloads";
            File myDir = new File(root);
            if (!myDir.exists()) {
                if (myDir.mkdirs()) {
                    Log.i("Folder ", "created");
                }
            }

            File mediaFile = new File(myDir.getAbsolutePath() + "/tiktok_" + mVideoId + ".mp4");*/

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();

            //output = new FileOutputStream("/data/data/com.tapbi.tiktokdownloader/video");
            output = new FileOutputStream(mediaFile);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.d("ptg", "onProgressUpdate: " + values[0]);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
