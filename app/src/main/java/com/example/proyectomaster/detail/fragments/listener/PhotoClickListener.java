package com.example.proyectomaster.detail.fragments.listener;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.proyectomaster.detail.entities.Photo;

public interface PhotoClickListener {

    void onPhotoItemClickListner(Photo photo, View view);

    void setPhoto(String reference, Drawable drawable);
}
