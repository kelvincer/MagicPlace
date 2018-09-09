package com.upv.magicplace.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.upv.magicplace.CommonHelper;
import com.upv.magicplace.ConstantsHelper;
import com.upv.magicplace.Helper;
import com.upv.magicplace.R;
import com.upv.magicplace.detail.entities.Result;
import com.upv.magicplace.note.adapter.GalleryPhotoAdapter;
import com.upv.magicplace.note.listener.OnGalleryItemClickListener;

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
