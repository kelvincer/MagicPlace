package com.upv.magicplace.detail.fragments.highlight.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.upv.magicplace.CommonHelper;
import com.upv.magicplace.R;
import com.upv.magicplace.app.MainApplication;
import com.upv.magicplace.detail.activity.di.DetailApiModule;
import com.upv.magicplace.detail.activity.di.DetailModule;
import com.upv.magicplace.detail.activity.ui.DetailActivity;
import com.upv.magicplace.detail.entities.FavoritePhotoModel;
import com.upv.magicplace.detail.entities.Result;
import com.upv.magicplace.detail.fragments.highlight.HighlightPresenter;
import com.upv.magicplace.detail.fragments.highlight.adapters.FavoritesFirestoreAdapter;
import com.upv.magicplace.detail.fragments.highlight.di.HighlightFragmentModule;
import com.upv.magicplace.detail.fragments.listener.FavoritePhotoClickListener;
import com.upv.magicplace.lib.ImageLoader;
import com.upv.magicplace.photo.ui.PhotoActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.willy.ratingbar.ScaleRatingBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HighlightsFragment extends Fragment implements HighlightView, FavoritePhotoClickListener {

    private static final String TAG = HighlightsFragment.class.getSimpleName();
    Unbinder unbinder;
    Result result;

    FavoritesFirestoreAdapter adapter;
    @BindView(R.id.txv_label_count)
    TextView txvLabelCount;
    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar simpleRatingBar;
    @BindView(R.id.ryv_favoritos)
    RecyclerView ryvFavoritos;
    @BindView(R.id.txv_take_photos)
    TextView txvTakePhotos;
    @BindView(R.id.txv_address_info)
    TextView txvAddressInfo;
    @BindView(R.id.separator_one)
    View separatorOne;
    @BindView(R.id.txv_label_important)
    TextView txvLabelImportant;
    @Inject
    HighlightPresenter presenter;
    @Inject
    ImageLoader imageLoader;


    public static Fragment getInstance(Result result) {
        HighlightsFragment fragment = new HighlightsFragment();
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
        presenter.onCreate();
        Toast.makeText(getContext(), "RESULT FRAGMENT", Toast.LENGTH_SHORT).show();
    }

    private void setupInjection() {

        /*DaggerHighlightFragmentComponent.builder()
                .highlightModule(new HighlightFragmentModule(this))
                .build()
                .inject(this);*/
        MainApplication.getAppComponent()
                .newDetailComponent(new DetailApiModule(), new DetailModule(((DetailActivity) getActivity()).getView()))
                .newHighlightComponent(new HighlightFragmentModule(this))
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
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDestroy();
        Log.d(TAG, "destroy destroy");
        if (adapter != null)
            adapter.stopListening();
    }

    private void setupViews() {
        if (result.getRating() != null) {
            simpleRatingBar.setRating(result.getRating().floatValue());
            txvLabelCount.setText(String.format("Calificado %s de 5", result.getRating()));
        } else {
            simpleRatingBar.setVisibility(View.GONE);
            txvLabelCount.setVisibility(View.GONE);
            separatorOne.setVisibility(View.GONE);
            txvLabelImportant.setVisibility(View.GONE);
        }

        if (result.getFormattedAddress() != null)
            txvAddressInfo.setText(result.getFormattedAddress());
    }

    @OnClick(R.id.txv_take_photos)
    public void onViewClicked() {
        if (getActivity() != null)
            ((DetailActivity) getActivity()).launchDialog();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        if (adapter != null && adapter.getItemCount() == 0) {
            ryvFavoritos.setVisibility(View.GONE);
            txvTakePhotos.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setOptions(FirestoreRecyclerOptions<FavoritePhotoModel> options) {

        adapter = new FavoritesFirestoreAdapter(options, imageLoader, this);
        ryvFavoritos.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        ryvFavoritos.setAdapter(adapter);
        adapter.startListening();
        ryvFavoritos.setVisibility(View.VISIBLE);
        txvTakePhotos.setVisibility(View.GONE);
    }

    @Override
    public void loadFavoritePhotos() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            presenter.getFavoritePhotos(result.getPlaceId());
        }
    }

    @Override
    public void onItemClickListener(FavoritePhotoModel model, Bitmap bitmap, View view) {

        if (bitmap != null) {
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
                intent.putExtra(CommonHelper.FROM_FRAGMENT, CommonHelper.FROM_HIGHLIGHTS);
                intent.putExtra(CommonHelper.FIRE_PHOTO_MODEL, model);
                intent.putExtra(CommonHelper.BITMAP_PATH, f.getAbsolutePath());
                intent.putExtra(CommonHelper.PLACE_ID, result.getPlaceId());
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
            intent.putExtra(CommonHelper.FIRE_PHOTO_MODEL, model);
            intent.putExtra(CommonHelper.FROM_FRAGMENT, CommonHelper.FROM_HIGHLIGHTS);
            intent.putExtra(CommonHelper.PLACE_ID, result.getPlaceId());
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
}
