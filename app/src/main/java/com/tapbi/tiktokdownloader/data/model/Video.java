package com.tapbi.tiktokdownloader.data.model;

import android.net.Uri;

public class Video {

    String videoThumblia;
    String videoTitle;
    String videoDuration;
    Uri videoUri;

    public String getVideoThumblia() {
        return videoThumblia;
    }

    public void setVideoThumblia(String videoThumblia) {
        this.videoThumblia = videoThumblia;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public Uri getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(Uri videoUri) {
        this.videoUri = videoUri;
    }

}