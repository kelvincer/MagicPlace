package com.example.proyectomaster.detail.fragments.listener;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.example.proyectomaster.detail.entities.FavoritePhotoModel;

public interface FavoritePhotoClickListener {

    void onItemClickListener(FavoritePhotoModel model, Bitmap bitmap, View view);
}
