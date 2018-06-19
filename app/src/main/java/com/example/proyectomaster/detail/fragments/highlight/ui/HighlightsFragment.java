package com.example.proyectomaster.detail.fragments.highlight.ui;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.dialog.DialogActivity;
import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.detail.entities.StoragePhoto;
import com.example.proyectomaster.detail.fragments.highlight.HighlightPresenter;
import com.example.proyectomaster.detail.fragments.highlight.adapters.FavoritosFirestoreAdapter;
import com.example.proyectomaster.detail.fragments.highlight.di.DaggerHighlightComponent;
import com.example.proyectomaster.detail.fragments.highlight.di.HighlightModule;
import com.example.proyectomaster.lib.ImageLoader;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.willy.ratingbar.ScaleRatingBar;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class HighlightsFragment extends Fragment implements View.OnClickListener, HighlightView {

    private static final String TAG = HighlightsFragment.class.getSimpleName();
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    Unbinder unbinder;
    Result result;

    Dialog dialog;
    ImageLoader imageLoader;
    FavoritosFirestoreAdapter adapter;
    @BindView(R.id.txv_label_count)
    TextView txvLabelCount;
    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar simpleRatingBar;
    @BindView(R.id.igv_take_photo)
    ImageView igvTakePhoto;
    @BindView(R.id.ryv_favoritos)
    RecyclerView ryvFavoritos;
    @BindView(R.id.lnl_take_photos)
    LinearLayout lnlTakePhotos;

    @Inject
    HighlightPresenter presenter;


    public static Fragment getInstance(Result result, ImageLoader imageLoader) {
        HighlightsFragment fragment = new HighlightsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonHelper.RESULT, result);
        fragment.setArguments(bundle);
        fragment.imageLoader = imageLoader;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        result = (Result) bundle.getSerializable(CommonHelper.RESULT);


        setupInjection();
        presenter.onCreate();
    }

    private void setupInjection() {

        DaggerHighlightComponent.builder()
                .highlightModule(new HighlightModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_important, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (result != null && result.getId() != null)
            setupViews();

        loadFavoritePhotos();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter != null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDestroy();
        result = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAPTURE_IMAGE &&
                resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                uploadToFirebaseStorage(imageBitmap);
            }
        }
    }

    private void uploadToFirebaseStorage(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        presenter.uploadPhoto(data, result.getPlaceId());
    }

    private void setupViews() {
        if (result.getRating() != null) {

            Log.d(TAG, "float: " + result.getRating().floatValue());
            simpleRatingBar.setRating(result.getRating().floatValue());
            txvLabelCount.setText(String.format("Calificado %s de %s", result.getRating(), "5"));
        }
    }

    @OnClick(R.id.igv_take_photo)
    public void onViewClicked() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null)
            startActivity(new Intent(getActivity(), DialogActivity.class));
        else
            launchChooserDialog();
    }

    private void launchChooserDialog() {

        dialog = new Dialog(getContext(), R.style.AppDialogTheme);
        dialog.setContentView(R.layout.dialog_chooser);
        dialog.findViewById(R.id.txv_camera).setOnClickListener(this);
        dialog.findViewById(R.id.txv_gallery).setOnClickListener(this);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txv_camera:
                showCamera();
                break;
            case R.id.txv_gallery:
                break;
        }
    }

    private void showCamera() {

        dialog.dismiss();
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(pictureIntent,
                    REQUEST_CAPTURE_IMAGE);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setOptions(FirestoreRecyclerOptions<StoragePhoto> options) {

        adapter = new FavoritosFirestoreAdapter(options, imageLoader);
       // ryvFavoritos.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ryvFavoritos.setLayoutManager(new StaggeredGridLayoutManager(3, 1));
        ryvFavoritos.setAdapter(adapter);
        //ryvFavoritos.addItemDecoration(new ItemOffsetDecoration(2));
        adapter.startListening();
        ryvFavoritos.setVisibility(View.VISIBLE);
        lnlTakePhotos.setVisibility(View.GONE);
    }

    @Override
    public void loadFavoritePhotos() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            presenter.getFavoritePhotos(result.getPlaceId());
        }
    }
}
