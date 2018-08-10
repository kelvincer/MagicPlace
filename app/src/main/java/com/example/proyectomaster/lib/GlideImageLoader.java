package com.example.proyectomaster.lib;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.proyectomaster.Helper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.fragments.listener.PhotoClickListener;

public class GlideImageLoader implements ImageLoader {

    private static final String TAG = GlideImageLoader.class.getSimpleName();
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
                .timeout(10000)
                .skipMemoryCache(true)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.background_blur);

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
    public void load(final ImageView imageView, final String photoRef, final PhotoClickListener listener) {

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .timeout(10000)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.background_blur);

        String url = Helper.generateUrl(photoRef);

        Log.d("IMAGE", url);

        glideRequestManager
                .load(url)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        e.printStackTrace();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        listener.setPhoto(photoRef, resource);
                        return false;
                    }
                })
                .into(imageView);
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
    public void load(final ImageView imageView, final CollapsingToolbarLayout collapsingToolbarLayout, String url) {

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .timeout(10000);

        glideRequestManager.asBitmap()
                .apply(requestOptions)
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette palette) {
                                int vibrantColor = palette.getVibrantColor(Color.parseColor("#FF0000"));
                                Log.d(TAG, "vibrantColor " + vibrantColor);
                                int darkVibrantColor = palette.getDarkVibrantColor(Color.parseColor("#000000"));
                                Log.d(TAG, "darkVibrantColor " + darkVibrantColor);
                                collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                                collapsingToolbarLayout.setStatusBarScrimColor(darkVibrantColor);
                                imageView.setImageBitmap(resource);
                            }
                        });
                    }
                });
    }
}
