package com.example.proyectomaster.detail.fragments.photos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.detail.fragments.GridSpacingItemDecoration;
import com.example.proyectomaster.detail.fragments.photos.adapters.PhotosAdapter;
import com.example.proyectomaster.lib.di.DaggerLibsComponent;
import com.example.proyectomaster.photo.PhotoActivity;
import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.entities.Photo;
import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.detail.fragments.listener.PhotoClickListener;
import com.example.proyectomaster.lib.ImageLoader;
import com.example.proyectomaster.lib.di.LibsModule;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Cache;

public class PhotosFragment extends Fragment implements PhotoClickListener {

    private static final String TAG = PhotosFragment.class.getSimpleName();
    Unbinder unbinder;
    Map<String, Drawable> drawableMap;
    @BindView(R.id.rcv_photos)
    RecyclerView rcvPhotos;
    Result result;
    @Inject
    ImageLoader imageLoader;

    public static Fragment getInstance(Result result) {
        Fragment fragment = new PhotosFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonHelper.RESULT, result);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        result = (Result) bundle.getSerializable(CommonHelper.RESULT);
        setupInjection();
        drawableMap = new HashMap<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvPhotos.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rcvPhotos.setAdapter(new PhotosAdapter(result, imageLoader, this));
        int spanCount = 2;
        int spacing = 15;
        boolean includeEdge = true;
        rcvPhotos.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setupInjection() {

        DaggerLibsComponent.builder()
                .libsModule(new LibsModule(getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public void onPhotoItemClickListner(final Photo photo, View view) {

        Drawable drawable = drawableMap.get(photo.getPhotoReference());

        if (drawable != null) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            File f = new File(getContext().getFilesDir(), "myphoto");
            try {
                f.createNewFile();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] bitmapdata = bos.toByteArray();
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                Intent intent = new Intent(getActivity(), PhotoActivity.class);
                intent.putExtra(CommonHelper.BITMAP_PATH, f.getAbsolutePath());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(),
                                    view,
                                    ViewCompat.getTransitionName(view));
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Intent intent = new Intent(getActivity(), PhotoActivity.class);
            intent.putExtra(CommonHelper.PHOTO_REF, photo.getPhotoReference());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(),
                                view,
                                ViewCompat.getTransitionName(view));
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }

    @Override
    public void setPhoto(String reference, Drawable drawable) {
        drawableMap.put(reference, drawable);
    }
}
