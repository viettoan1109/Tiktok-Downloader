package com.tapbi.tiktokdownloader.ui.adapter;

import android.content.Context;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tapbi.tiktokdownloader.R;
import com.tapbi.tiktokdownloader.common.Constants;
import com.tapbi.tiktokdownloader.data.model.Video;

import java.io.File;
import java.util.ArrayList;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.viewHolder> {

    Context context;
    ArrayList<Video> videoArrayList;
    public OnItemClickListener onItemClickListener;

    public VideoAdapter(Context context, ArrayList<Video> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @Override
    public VideoAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int i) {
        Uri uri = Uri.fromFile(new File(videoArrayList.get(i).getVideoThumblia()));

        Glide.with(context)
                .load(uri).thumbnail(0.1f).into(holder.imgThumblia);

       // Glide.with(context).load(videoArrayList.get(i).getVideoTitle()).into(holder.imgThumblia);
    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title, duration;
        ImageView imgThumblia;

        public viewHolder(View itemView) {
            super(itemView);
            imgThumblia = (ImageView) itemView.findViewById(R.id.img_item_thumblia);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });
        }

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }
}