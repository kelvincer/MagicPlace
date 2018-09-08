package com.example.proyectomaster.start.activities.list.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.app.MainApplication;
import com.example.proyectomaster.app.di.MainApplicationComponent;
import com.example.proyectomaster.detail.activity.ui.DetailActivity;
import com.example.proyectomaster.start.activities.list.ListFavouritePresenter;
import com.example.proyectomaster.start.activities.list.adapter.ListFavouriteAdapter;
import com.example.proyectomaster.start.activities.list.di.ListFavouriteModule;
import com.example.proyectomaster.start.activities.list.entitites.FavouritePlaceModel;
import com.example.proyectomaster.start.activities.list.listeners.OnClickListener;
import com.example.proyectomaster.start.entities.CategoryModel;
import com.example.proyectomaster.start.fragments.favourites.adapter.FavouritesFragAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFavouritesActivity extends AppCompatActivity implements ListFavouriteView, OnClickListener {

    ListFavouriteAdapter adapter;
    @BindView(R.id.ryv_list_favourites)
    RecyclerView ryvListFavourites;

    @Inject
    ListFavouritePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_favorites);
        ButterKnife.bind(this);

        CategoryModel categoryModel = (CategoryModel) getIntent().getSerializableExtra(CommonHelper.CATEGORY);

        setupInjection();
        presenter.onCreate();
        presenter.getFavourites(categoryModel.getType_name());
        getSupportActionBar().setTitle("Lugares Favoritos");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        if (adapter != null)
            adapter.stopListening();
    }

    private void setupInjection() {

        MainApplication.getAppComponent()
                .newListFavouriteComponent(new ListFavouriteModule(this))
                .inject(this);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setOptions(FirestoreRecyclerOptions<FavouritePlaceModel> options) {
        adapter = new ListFavouriteAdapter(options, this);
        ryvListFavourites.setLayoutManager(new LinearLayoutManager(this));
        ryvListFavourites.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onDeleteItemClickListener(FavouritePlaceModel model) {
        Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show();
        presenter.deleteFavouritePlace(model);
    }

    @Override
    public void onItemClickListenr(FavouritePlaceModel model) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(CommonHelper.PLACE_ID, model.getPlaceId());
        startActivity(intent);
    }
}
