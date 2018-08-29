package com.example.proyectomaster.note.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.proyectomaster.R;
import com.example.proyectomaster.note.listener.OnGalleryItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryPhotoAdapter extends RecyclerView.Adapter<GalleryPhotoAdapter.ViewHolder> {

    private List<String> images;
    Context context;
    OnGalleryItemClickListener listener;

    public GalleryPhotoAdapter(List<String> images, Context context, OnGalleryItemClickListener listener) {
        this.images = images;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_photo)
        ImageView imagePhoto;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }

        public void bindItem(final String path) {
            Glide.with(context).load(path).into(imagePhoto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(path);
                }
            });
        }
    }
}
