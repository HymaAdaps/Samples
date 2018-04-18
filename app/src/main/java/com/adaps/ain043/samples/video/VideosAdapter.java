package com.adaps.ain043.samples.video;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adaps.ain043.samples.R;
import com.adaps.ain043.samples.databinding.ItemVideoBinding;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by AIN043 on 1/24/2018 at 8:17 AM
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MyViewHolder> {

    private List<File> videosList;
    private Context context;
    private IItemClick iItemClick;

    public interface IItemClick {
        void onPlayClick(File category);

        void onDelelteClick(File category);
    }

    public void addItem(File model) {
        this.videosList.add(0, model);
        notifyDataSetChanged();
    }

    public void removeItem(File model) {
        this.videosList.remove(model);
        notifyDataSetChanged();
    }

    public VideosAdapter(Context context, IItemClick iItemClick) {
        this.videosList = new ArrayList<>();
        this.context = context;
        this.iItemClick = iItemClick;
    }

    public void addAll(List<File> videosList) {
        this.videosList.clear();
        this.videosList.addAll(videosList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemVideoBinding binding;

        public MyViewHolder(View convertView) {
            super(convertView);
            binding = DataBindingUtil.bind(itemView);
            binding.ivPlay.setOnClickListener(this);
            binding.ivClose.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            switch (v.getId()) {
                case R.id.ivPlay:
                    if (iItemClick != null) {
                        iItemClick.onPlayClick(videosList.get(position));
                    }
                    break;
                case R.id.ivClose:
                    if (iItemClick != null) {
                        iItemClick.onDelelteClick(videosList.get(position));
                    }
                    break;
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        File category = videosList.get(position);
        Glide.with(context).load(category.getAbsolutePath()).centerCrop().into(holder.binding.ivThumb);
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }
}
