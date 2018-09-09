package com.upv.magicplace.detail.fragments.listener;

import android.graphics.Bitmap;
import android.view.View;

import com.upv.magicplace.detail.entities.FavoritePhotoModel;

public interface FavoritePhotoClickListener {

    void onItemClickListener(FavoritePhotoModel model, Bitmap bitmap, View view);
}
