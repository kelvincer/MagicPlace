package com.upv.magicplace.detail.fragments.listener;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.upv.magicplace.detail.entities.Photo;

public interface PhotoClickListener {

    void onPhotoItemClickListner(Photo photo, View view);

    void setPhoto(String reference, Drawable drawable);
}
