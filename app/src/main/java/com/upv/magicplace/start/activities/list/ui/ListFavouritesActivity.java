package com.upv.magicplace.start.activities.list.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.upv.magicplace.CommonHelper;
import com.upv.magicplace.R;
import com.upv.magicplace.app.MainApplication;
import com.upv.magicplace.detail.activity.ui.DetailActivity;
import com.upv.magicplace.start.activities.list.ListFavouritePresenter;
import com.upv.magicplace.start.activities.list.adapter.ListFavouriteAdapter;
import com.upv.magicplace.start.activities.list.di.ListFavouriteModule;
import com.upv.magicplace.start.activities.list.entitites.FavouritePlaceModel;
import com.upv.magicplace.start.activities.list.listeners.OnClickListener;
import com.upv.magicplace.start.entities.CategoryModel;
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
        presenter.deleteFavouritePlace(model);
    }

    @Override
    public void onItemClickListenr(FavouritePlaceModel model) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(CommonHelper.PLACE_ID, model.getPlaceId());
        startActivity(intent);
    }
}
