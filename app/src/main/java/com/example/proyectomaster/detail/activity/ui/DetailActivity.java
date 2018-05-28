package com.example.proyectomaster.detail.activity.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.activity.DetailActivityPresenter;
import com.example.proyectomaster.detail.activity.adapters.PagerAdapter;
import com.example.proyectomaster.detail.activity.di.DaggerDetailComponent;
import com.example.proyectomaster.detail.activity.di.DetailModule;
import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.lib.ImageLoader;
import com.example.proyectomaster.lib.di.LibsModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailActivityView, TabLayout.OnTabSelectedListener {

    private final String[] pageTitle = {"DESTACADOS", "FOTOS"};
    /*  @BindView(R.id.expandedImage)
      ImageView expandedImage;*/
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
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    DetailActivityPresenter presenter;
    @Inject
    ImageLoader imageLoader;
    @BindView(R.id.header)
    ImageView header;
   /* @Inject
    PagerAdapter adapter;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_three);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("MAS");

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

        String photoReference = result.getPhotos().get(0).getPhotoReference();
        final String url = String.format("https://maps.googleapis.com/maps/api/place/photo?photoreference=%s&key=AIzaSyCwmYvGIV7owfcc7muneajVaIz6cXKA8Wg" +
                "&maxheight=800", photoReference);

        imageLoader.load(header, url);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager(), pageTitle.length, result));
        //imageLoader.setBackground(url, appBar);
        imageLoader.setToolbarColor(url, collapsingToolbarLayout);
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
