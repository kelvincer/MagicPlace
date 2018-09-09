package com.upv.magicplace.detail.fragments.photos.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.upv.magicplace.R;
import com.upv.magicplace.detail.entities.Photo;
import com.upv.magicplace.detail.entities.Result;
import com.upv.magicplace.detail.fragments.listener.PhotoClickListener;
import com.upv.magicplace.lib.ImageLoader;

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

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindItem(Photo photo) {
            imageLoader.load(igvPhoto, photo.getPhotoReference(), photoClickListener);
        }

        void setOnItemClick(final Photo photo) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photoClickListener.onPhotoItemClickListner(photo, igvPhoto);
                }
            });
        }
    }
}
