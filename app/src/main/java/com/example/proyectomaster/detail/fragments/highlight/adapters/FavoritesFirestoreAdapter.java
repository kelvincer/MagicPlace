package com.example.proyectomaster.detail.fragments.highlight.adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.entities.FavoritePhotoModel;
import com.example.proyectomaster.detail.fragments.listener.FavoritePhotoClickListener;
import com.example.proyectomaster.lib.ImageLoader;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesFirestoreAdapter extends FirestoreRecyclerAdapter<FavoritePhotoModel, FavoritesFirestoreAdapter.ViewHolder> {

    ImageLoader imageLoader;
    FavoritePhotoClickListener favoritePhotoClickListener;

    public FavoritesFirestoreAdapter(@NonNull FirestoreRecyclerOptions<FavoritePhotoModel> options, ImageLoader imageLoader,
                                     FavoritePhotoClickListener listener) {
        super(options);
        this.imageLoader = imageLoader;
        favoritePhotoClickListener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull FavoritePhotoModel model) {
        holder.bindItem(model);
        Log.d("TAG", "name " + model.getName());
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
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }

        public void bindItem(final FavoritePhotoModel photo) {
            imageLoader.loadWithoutOverride(favorPhoto, photo.getUrl());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BitmapDrawable bitmapDrawable = ((BitmapDrawable) favorPhoto.getDrawable());
                    if (bitmapDrawable != null) {
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        favoritePhotoClickListener.onItemClickListener(photo, bitmap, favorPhoto);
                    } else {
                        favoritePhotoClickListener.onItemClickListener(photo, null, favorPhoto);
                    }
                }
            });
        }
    }
}
