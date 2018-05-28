package com.example.proyectomaster.lib;

import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ykro.
 */
public interface ImageLoader {

    void load(ImageView imageView, String URL);
    void setOnFinishedImageLoadingListener(Object object);
    void setBackground(String url, View view);
    void setToolbarColor(String uri, CollapsingToolbarLayout collapsingToolbarLayout);
}
