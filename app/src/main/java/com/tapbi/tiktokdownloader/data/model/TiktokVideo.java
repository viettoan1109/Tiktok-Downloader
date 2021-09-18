package com.tapbi.tiktokdownloader.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TiktokVideo {
    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("main_url")
    @Expose
    private String mainUrl;
    @SerializedName("tmp_no_watermakr_url")
    @Expose
    private String tmpNoWatermakrUrl;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getTmpNoWatermakrUrl() {
        return tmpNoWatermakrUrl;
    }

    public void setTmpNoWatermakrUrl(String tmpNoWatermakrUrl) {
        this.tmpNoWatermakrUrl = tmpNoWatermakrUrl;
    }

}

