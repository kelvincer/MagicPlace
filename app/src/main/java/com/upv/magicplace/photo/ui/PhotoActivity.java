package com.upv.magicplace.photo.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.upv.magicplace.CommonHelper;
import com.upv.magicplace.Helper;
import com.upv.magicplace.R;
import com.upv.magicplace.app.MainApplication;
import com.upv.magicplace.detail.entities.FavoritePhotoModel;
import com.upv.magicplace.lib.ImageLoader;
import com.upv.magicplace.photo.PhotoPresenter;
import com.upv.magicplace.photo.di.PhotoActivityModule;
import com.jsibbold.zoomage.ZoomageView;
import com.upv.magicplace.detail.entities.FavoritePhotoModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity implements PhotoView {

    private static final String TAG = PhotoActivity.class.getSimpleName();
    int fragment;
    String placeId;
    FavoritePhotoModel model;
    File imgFile;
    @BindView(R.id.myZoomageView)
    ZoomageView myZoomageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    ImageLoader imageLoader;
    @Inject
    PhotoPresenter photoPresenter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_two);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setupInjection();
        photoPresenter.onCreate();
        fragment = getIntent().getIntExtra(CommonHelper.FROM_FRAGMENT, 0);
        placeId = getIntent().getStringExtra(CommonHelper.PLACE_ID);
        String imagePath = getIntent().getStringExtra(CommonHelper.BITMAP_PATH);
        model = (FavoritePhotoModel) getIntent().getExtras().getSerializable(CommonHelper.FIRE_PHOTO_MODEL);
        if (imagePath != null) {
            imgFile = new File(imagePath);
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                myZoomageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, "NO EXISTE IMAGEN", Toast.LENGTH_SHORT).show();
            }
        } else {
            switch (fragment) {
                case CommonHelper.FROM_HIGHLIGHTS:
                    imageLoader.load(myZoomageView, model.getUrl());
                    break;
                case CommonHelper.FROM_PHOTOS:
                    String reference = getIntent().getStringExtra(CommonHelper.PHOTO_REF);
                    imageLoader.load(myZoomageView, Helper.generateUrl(reference));
                    break;
                default:
                    throw new IllegalArgumentException("Illegal fragment id");
            }
        }

        setupViews();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupViews() {

        myZoomageView.setOnTouchListener(new View.OnTouchListener() {
            float x1, x2, y1, y2;
            long t1, t2;
            int CLICK_DURATION = 1000;
            boolean show = true;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RectF rect = getImageBounds(myZoomageView);
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        t1 = System.currentTimeMillis();
                        if (rect.contains((int) event.getX(), (int) event.getY())) {
                            myZoomageView.setZoomable(true);
                        } else {
                            myZoomageView.setZoomable(false);
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        t2 = System.currentTimeMillis();

                        if (x1 == x2 && y1 == y2 && (t2 - t1) < CLICK_DURATION) {
                            if (show) {
                                toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                                hideSystemUI();
                                show = false;
                            } else {
                                toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                                showSystemUI();
                                show = true;
                            }
                        }
                        return false;
                }
                return false;
            }
        });
    }

    private void setupInjection() {

        MainApplication.getAppComponent()
                .newPhotoComponent(new PhotoActivityModule(this))
                .inject(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG, "configuration changed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        photoPresenter.onDestroy();
        if (imgFile != null) {
            boolean b = imgFile.delete();
            if (b) {
                Toast.makeText(this, "FILE DELETED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "FILE NOT DELETED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        switch (fragment) {
            case CommonHelper.FROM_HIGHLIGHTS:
                getMenuInflater().inflate(R.menu.menu_photo_delete, menu);
                break;
            case CommonHelper.FROM_PHOTOS:
                getMenuInflater().inflate(R.menu.menu_photo, menu);
                break;
            default:
                throw new IllegalStateException("Illegal fragment id");
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.fire_share_photo:
                shareImage();
                Toast.makeText(getApplicationContext(), "SHARE FIRE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fire_delete_photo:
                deletePhoto();
                break;
            case R.id.share:
                shareImage();
                Toast.makeText(this, "SHARE", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareImage() {
        BitmapDrawable drawable = (BitmapDrawable) myZoomageView.getDrawable();
        if (drawable == null)
            return;
        Bitmap bimap = drawable.getBitmap();

        if (bimap == null)
            return;

        Uri uri = saveImage(bimap);
        if (uri != null) {
            shareImageUri(uri);
        } else {
            Toast.makeText(this, "Uri Error", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri saveImage(Bitmap image) {
        //TODO - Should be processed in another thread
        Uri uri = null;
        try {

            File file = Helper.createImageFile(this);
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(this, "com.upv.magicplace.fileprovider", file);

        } catch (IOException e) {
            Log.d(TAG, "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }

    private void shareImageUri(Uri uri) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }

    private void deletePhoto() {
        if (fragment == CommonHelper.FROM_HIGHLIGHTS)
            photoPresenter.deletePhoto(model, placeId);
    }

    public static RectF getImageBounds(ImageView imageView) {
        RectF bounds = new RectF();
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            imageView.getImageMatrix().mapRect(bounds, new RectF(drawable.getBounds()));
        }
        return bounds;
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }
}
