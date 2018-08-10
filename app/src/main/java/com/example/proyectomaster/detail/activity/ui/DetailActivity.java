package com.example.proyectomaster.detail.activity.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.Helper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.activity.DetailActivityPresenter;
import com.example.proyectomaster.detail.activity.adapters.PagerAdapter;
import com.example.proyectomaster.detail.activity.di.DaggerDetailComponent;
import com.example.proyectomaster.detail.activity.di.DetailModule;
import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.lib.ImageLoader;
import com.example.proyectomaster.lib.di.LibsModule;
import com.example.proyectomaster.note.NoteActivity;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailActivityView, TabLayout.OnTabSelectedListener {

    private final String[] pageTitle = {"DESTACADOS", "FOTOS", "NOTAS"};
    Result result;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.header)
    ImageView header;
    @Inject
    DetailActivityPresenter presenter;
    @Inject
    ImageLoader imageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_three);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        String id = intent.getStringExtra(CommonHelper.PLACE_ID);
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        setupTabLayout();
        setupInjection();
        //pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);
        presenter.onCreate();
        presenter.getPlaceDetail(id);
    }

    private void setupTabLayout() {
        for (int i = 0; i < pageTitle.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }

        setupSpeedDial();
    }

    private void setupSpeedDial() {

        SpeedDialView speedDialView = findViewById(R.id.speedDial);
        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_photo_label, R.drawable
                .ic_add_a_photo_white_24dp)
                .setLabel("Agregar una foto")
                .setLabelColor(Color.BLACK)
                .create());
        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_note_label, R.drawable
                .ic_note_add_white_24dp)
                .setLabel("Dejar una nota")
                .setLabelColor(Color.BLACK)
                .create());

        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.fab_photo_label:
                        Toast.makeText(DetailActivity.this, "QO", Toast.LENGTH_SHORT).show();
                        return false; // true to keep the Speed Dial open
                    case R.id.fab_note_label:
                        Toast.makeText(DetailActivity.this, "TO", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailActivity.this, NoteActivity.class);
                        intent.putExtra(CommonHelper.PLACE_NAME, result.getName());
                        startActivity(intent);
                        return false;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void setupInjection() {

        DaggerDetailComponent.builder()
                .detailModule(new DetailModule(this, this, pageTitle.length))
                .libsModule(new LibsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showProgressaBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setResult(Result result) {

        this.result = result;
        setupActionBar(result);
        String photoReference = result.getPhotos().get(0).getPhotoReference();
        imageLoader.load(header, collapsingToolbarLayout, Helper.generateUrl(photoReference));
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager(), pageTitle.length, result, imageLoader));
    }

    private void setupActionBar(Result result) {
        getSupportActionBar().setTitle(result.getName());
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
