package com.example.proyectomaster.photo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.Helper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.lib.ImageLoader;
import com.example.proyectomaster.lib.di.DaggerLibsComponent;
import com.example.proyectomaster.lib.di.LibsModule;
import com.jsibbold.zoomage.ZoomageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity {

    @Inject
    ImageLoader imageLoader;
    @BindView(R.id.myZoomageView)
    ZoomageView myZoomageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        String reference = getIntent().getStringExtra(CommonHelper.PHOTO_REF);
        DaggerLibsComponent.builder()
                .libsModule(new LibsModule(this))
                .build()
                .inject(this);

        imageLoader.load(myZoomageView, Helper.generateUrl(reference));

        /*final double viewWidthToBitmapWidthRatio = (double)myZoomageView.getWidth() / (double)myZoomageView.getWidth();
        myZoomageView.getLayoutParams().height = (int) (myZoomageView.getHeight() * viewWidthToBitmapWidthRatio);*/
    }
}
