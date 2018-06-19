package com.example.proyectomaster.photo;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.Helper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.lib.ImageLoader;
import com.example.proyectomaster.lib.di.DaggerLibsComponent;
import com.example.proyectomaster.lib.di.LibsModule;
import com.jsibbold.zoomage.ZoomageView;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity {

    private static final String TAG = PhotoActivity.class.getSimpleName();
    @Inject
    ImageLoader imageLoader;
    @BindView(R.id.myZoomageView)
    ZoomageView myZoomageView;
    File imgFile;
    /*@BindView(R.id.view_main)
    View viewMain;*/
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().show();

        String imagePath = getIntent().getStringExtra(CommonHelper.BITMAP_PATH);
        if (imagePath != null) {
            imgFile = new File(imagePath);
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                myZoomageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(this, "NO EXISTE IMAGEN", Toast.LENGTH_SHORT).show();
            }
        } else {
            String reference = getIntent().getStringExtra(CommonHelper.PHOTO_REF);
            DaggerLibsComponent.builder()
                    .libsModule(new LibsModule(this))
                    .build()
                    .inject(this);

            imageLoader.load(myZoomageView, Helper.generateUrl(reference));
        }

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
        Log.d(TAG, "on destroy");
        if (imgFile != null) {
            boolean b = imgFile.delete();
            if (b) {
                Toast.makeText(this, "FILE DELETED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "FILE NOT DELETED", Toast.LENGTH_SHORT).show();
            }
        }
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
}
