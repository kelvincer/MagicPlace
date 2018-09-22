package com.upv.magicplace.start.fragments.favourites.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.upv.magicplace.CommonHelper;
import com.upv.magicplace.R;
import com.upv.magicplace.app.MainApplication;
import com.upv.magicplace.start.activities.InitActivity;
import com.upv.magicplace.start.activities.list.ui.ListFavouritesActivity;
import com.upv.magicplace.start.entities.CategoryModel;
import com.upv.magicplace.start.fragments.favourites.FavouritesFragmentPresenter;
import com.upv.magicplace.start.fragments.favourites.adapter.FavouritesFragAdapter;
import com.upv.magicplace.start.fragments.favourites.di.FavouritesFragModule;
import com.upv.magicplace.start.fragments.favourites.listener.OnCategoryClickListener;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavouritesFragment extends Fragment implements FavouritesFragmentView, OnCategoryClickListener {

    Unbinder unbinder;
    FavouritesFragAdapter adapter;

    @Inject
    FavouritesFragmentPresenter presenter;
    @BindView(R.id.txv_favourites)
    TextView txvFavourites;
    @BindView(R.id.ryv_favourites)
    RecyclerView ryvFavourites;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjection();
        presenter.onCreate();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            presenter.getCategories();
        ((InitActivity) getActivity()).setSupportActionBar(toolbar);
        ((InitActivity) getActivity()).getSupportActionBar().setTitle("Favoritos");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void setupInjection() {
        MainApplication.getAppComponent()
                .newFavouritesFragComponent(new FavouritesFragModule(this))
                .inject(this);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setOptions(FirestoreRecyclerOptions<CategoryModel> options) {

        adapter = new FavouritesFragAdapter(options, this);
        ryvFavourites.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ryvFavourites.setAdapter(adapter);
        adapter.startListening();
        ryvFavourites.setVisibility(View.VISIBLE);
        txvFavourites.setVisibility(View.GONE);
    }

    @Override
    public void onItemClickListener(CategoryModel model) {
        startActivity(new Intent(getContext(), ListFavouritesActivity.class)
                .putExtra(CommonHelper.CATEGORY, model));
    }
}
