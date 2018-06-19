package com.example.proyectomaster.lib;

import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.proyectomaster.detail.fragments.listener.PhotoClickListener;

/**
 * Created by ykro.
 */
public interface ImageLoader {

    void load(ImageView imageView, String Url);

    void load(ImageView imageView, String url, PhotoClickListener listener);

    void setOnFinishedImageLoadingListener(Object object);

    void load(ImageView imageView, CollapsingToolbarLayout collapsingToolbarLayout, String url);
}
