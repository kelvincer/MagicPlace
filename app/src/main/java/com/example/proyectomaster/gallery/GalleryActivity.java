package com.example.proyectomaster.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.ConstantsHelper;
import com.example.proyectomaster.Helper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.note.adapter.GalleryPhotoAdapter;
import com.example.proyectomaster.note.listener.OnGalleryItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity implements OnGalleryItemClickListener {

    @BindView(R.id.ryv_gallery)
    RecyclerView ryvGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Galería");

        List<String> images = Helper.getAllShownImagesPath(this);

        ryvGallery.setLayoutManager(new GridLayoutManager(this, 3));
        ryvGallery.setAdapter(new GalleryPhotoAdapter(images, this, this));
    }

    @Override
    public void onItemClickListener(String path) {
        Intent intent = new Intent();
        intent.putExtra(CommonHelper.PATH, path);
        setResult(RESULT_OK, intent);
        finish();
    }
}
