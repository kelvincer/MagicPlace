package com.example.proyectomaster.lib;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.proyectomaster.R;

public class GlideImageLoader implements ImageLoader {

    private RequestManager glideRequestManager;
    private RequestListener onFinishedImageLoadingListener;
    private Activity activity;

    public void setLoaderContext(Activity activity) {
        this.glideRequestManager = Glide.with(activity);
        this.activity = activity;
    }

    @Override
    public void load(ImageView imageView, String URL) {

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.background_blur);
                //.centerCrop();

        if (onFinishedImageLoadingListener != null) {
            glideRequestManager
                    .load(URL)
                    .apply(requestOptions)
                    .listener(onFinishedImageLoadingListener)
                    .into(imageView);
        } else {
            glideRequestManager
                    .load(URL)
                    .apply(requestOptions)
                    .into(imageView);
        }
    }

    @Override
    public void setOnFinishedImageLoadingListener(Object listener) {
        try {
            this.onFinishedImageLoadingListener = (RequestListener) listener;
        } catch (ClassCastException e) {
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }

    @Override
    public void setBackground(String url, final View view) {
        glideRequestManager.asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(activity.getApplicationContext().getResources(), resource);
                        view.setBackground(drawable);
                    }
                });
    }

    @Override
    public void setToolbarColor(String url, final CollapsingToolbarLayout collapsingToolbarLayout) {
        glideRequestManager.asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette palette) {
                                int vibrantColor = palette.getVibrantColor(0);
                                int vibrantDarkColor = palette.getDarkVibrantColor(0);
                                collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                                collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
                            }
                        });
                    }
                });
    }
}
