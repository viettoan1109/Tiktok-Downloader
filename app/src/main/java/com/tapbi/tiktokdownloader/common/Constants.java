package com.tapbi.tiktokdownloader.common;

import java.io.File;
import java.util.ArrayList;

public class Constants {
    public static String[] videoExtensions = {".mp4",".ts",".mkv",".mov",
            ".3gp",".mv2",".m4v",".webm",".mpeg1",".mpeg2",".mts",".ogm",
            ".bup", ".dv",".flv",".m1v",".m2ts",".mpeg4",".vlc",".3g2",
            ".avi",".mpeg",".mpg",".wmv",".asf"};



    public static final String OEMBED_URL = "https://www.tiktok.com";
    public static final String KEY = "d68b8fea74msh764d7257314086ap171dcajsned434d160151";
    public static final String HOST = "tiktok.p.rapidapi.com";
    public static final String HEADER_KEY = "x-rapidapi-key";
    public static final String HEADER_HOST = "rapidapi-host";

    //all loaded files will be here
    public static ArrayList<File> allMediaList = new ArrayList<>();
}
