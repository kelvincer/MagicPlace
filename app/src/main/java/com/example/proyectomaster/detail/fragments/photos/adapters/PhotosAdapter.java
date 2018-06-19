package com.example.proyectomaster.detail.fragments.photos.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.proyectomaster.Helper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.entities.Photo;
import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.detail.fragments.listener.PhotoClickListener;
import com.example.proyectomaster.lib.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    Result result;
    ImageLoader imageLoader;
    PhotoClickListener photoClickListener;

    public PhotosAdapter(Result result, ImageLoader imageLoader, PhotoClickListener listener) {
        this.result = result;
        this.imageLoader = imageLoader;
        this.photoClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = result.getPhotos().get(position);
        holder.bindItem(photo);
        holder.setOnItemClick(photo);
    }

    @Override
    public int getItemCount() {
        return result.getPhotos().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.igv_photo)
        ImageView igvPhoto;
        View view;

        ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

        void bindItem(Photo photo) {
            imageLoader.load(igvPhoto, photo.getPhotoReference(), photoClickListener);
        }

        void setOnItemClick(final Photo photo) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photoClickListener.onPhotoItemClickListner(photo);
                }
            });
        }
    }
}
