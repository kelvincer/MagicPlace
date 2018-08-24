package com.example.proyectomaster.detail.fragments.highlight.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.entities.StoragePhoto;
import com.example.proyectomaster.lib.ImageLoader;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesFirestoreAdapter extends FirestoreRecyclerAdapter<StoragePhoto, FavoritesFirestoreAdapter.ViewHolder> {

    ImageLoader imageLoader;

    public FavoritesFirestoreAdapter(@NonNull FirestoreRecyclerOptions<StoragePhoto> options, ImageLoader imageLoader) {
        super(options);
        this.imageLoader = imageLoader;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull StoragePhoto model) {
        holder.bindItem(model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorito_photo_item, parent, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.favor_photo)
        ImageView favorPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(StoragePhoto photo) {
            imageLoader.loadWithoutOverride(favorPhoto, photo.getUrl());
        }
    }
}
