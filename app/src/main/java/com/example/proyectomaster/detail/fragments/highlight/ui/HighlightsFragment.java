package com.example.proyectomaster.detail.fragments.highlight.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.app.MainApplication;
import com.example.proyectomaster.detail.activity.di.DetailApiModule;
import com.example.proyectomaster.detail.activity.di.DetailModule;
import com.example.proyectomaster.detail.activity.ui.DetailActivity;
import com.example.proyectomaster.detail.fragments.highlight.adapters.FavoritesFirestoreAdapter;
import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.detail.entities.StoragePhoto;
import com.example.proyectomaster.detail.fragments.highlight.HighlightPresenter;
import com.example.proyectomaster.detail.fragments.highlight.di.HighlightFragmentModule;
import com.example.proyectomaster.lib.ImageLoader;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.willy.ratingbar.ScaleRatingBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HighlightsFragment extends Fragment implements HighlightView {

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
        Toast.makeText(getContext(), "DESTROY", Toast.LENGTH_SHORT).show();
       // result = null;
        Log.d(TAG, "destroy destroy");
    }

    private void setupViews() {
        if (result.getRating() != null) {
            simpleRatingBar.setRating(result.getRating().floatValue());
            txvLabelCount.setText(String.format("Calificado %s de 5", result.getRating()));
        }
    }

    @OnClick(R.id.txv_take_photos)
    public void onViewClicked() {
        if (getActivity() != null)
            ((DetailActivity) getActivity()).launchDialog();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setOptions(FirestoreRecyclerOptions<StoragePhoto> options) {

        adapter = new FavoritesFirestoreAdapter(options, imageLoader);
        // ryvFavoritos.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ryvFavoritos.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        ryvFavoritos.setAdapter(adapter);
        //ryvFavoritos.addItemDecoration(new ItemOffsetDecoration(2));
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
}
