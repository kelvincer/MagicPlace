package com.upv.magicplace.detail.activity.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.upv.magicplace.CommonHelper;
import com.upv.magicplace.Helper;
import com.upv.magicplace.R;
import com.upv.magicplace.app.MainApplication;
import com.upv.magicplace.detail.activity.DetailActivityPresenter;
import com.upv.magicplace.detail.activity.adapters.ViewPagerAdapter;
import com.upv.magicplace.detail.activity.di.DetailApiModule;
import com.upv.magicplace.detail.activity.di.DetailModule;
import com.upv.magicplace.detail.entities.Result;
import com.upv.magicplace.detail.fragments.highlight.ui.HighlightsFragment;
import com.upv.magicplace.detail.fragments.notes.ui.NotesFragment;
import com.upv.magicplace.dialog.LoginDialogActivity;
import com.upv.magicplace.gallery.GalleryActivity;
import com.upv.magicplace.lib.ImageLoader;
import com.upv.magicplace.note.ui.NoteActivity;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailActivityView, TabLayout.OnTabSelectedListener
        , View.OnClickListener {

    private static final int CAPTURE_REQUEST_CODE = 100;
    private static final int LOGIN_REQUEST_CODE = 101;
    private static final int GALLERY_REQUEST_CODE = 102;
    private static final int NOTE_ACT_REQUEST_CODE = 103;
    private final String[] pageTitle = {"DESTACADOS", "FOTOS", "NOTAS", "RUTA"};
    Dialog dialog;
    Result result;
    File photoFile;
    ViewPagerAdapter adapter;
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
    @BindView(R.id.speedDial)
    SpeedDialView speedDial;
    @BindView(R.id.pgb_detail)
    ProgressBar pgbDetail;
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
        String place_id = intent.getStringExtra(CommonHelper.PLACE_ID);
        Toast.makeText(this, place_id, Toast.LENGTH_SHORT).show();

        setupTabLayout();
        setupInjection();
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);
        presenter.onCreate();

        if (!Helper.isNetworkAvailable(this)) {
            Toast.makeText(this, "No tienes conexión a la red", Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.getPlaceDetail(place_id);
    }

    private void setupTabLayout() {
        for (String aPageTitle : pageTitle) {
            tabLayout.addTab(tabLayout.newTab().setText(aPageTitle));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAPTURE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(photoFile));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (bitmap != null) {
                        uploadToFirebaseStorage(bitmap);
                    }
                }
                break;
            case LOGIN_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    loadFavoritesPhotos();
                }
                break;
            case GALLERY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    String path = data.getStringExtra(CommonHelper.PATH);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    uploadToFirebaseStorage(bitmap);
                }
                break;
            case NOTE_ACT_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    loadNotes();
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid request code");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void loadNotes() {
        Fragment notesFragment = adapter.getmPageReferenceMap().get(2);
        if (notesFragment != null) {
            ((NotesFragment) notesFragment).loadNotes();
        }
    }

    @Override
    public void loadFavoritesPhotos() {
        Fragment highlightFragment = adapter.getmPageReferenceMap().get(0);
        if (highlightFragment != null) {
            ((HighlightsFragment) highlightFragment).loadFavoritePhotos();
        }
    }

    private void uploadToFirebaseStorage(Bitmap bitmap) {
        presenter.uploadPhoto(Helper.bitmapToByteArray(bitmap), result.getPlaceId());
    }

    private void setupSpeedDial(boolean isFavourite) {
        speedDial.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_photo_label, R.drawable
                .ic_add_a_photo_white_24dp)
                .setLabel("Agregar una foto")
                .setLabelColor(Color.BLACK)
                .create());
        speedDial.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_note_label, R.drawable
                .ic_note_add_white_24dp)
                .setLabel("Dejar una nota")
                .setLabelColor(Color.BLACK)
                .create());

        if (!isFavourite) {
            speedDial.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_favorite_label, R.drawable
                    .ic_favorite_border_white_24dp)
                    .setLabel("Agregar a favorito")
                    .setLabelColor(Color.BLACK)
                    .create());
        }

        speedDial.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.fab_photo_label:
                        launchDialog();
                        return false; // true to keep the Speed Dial open
                    case R.id.fab_note_label:
                        goToNoteActivity();
                        return false;
                    case R.id.fab_favorite_label:
                        saveFavorite();
                        return false;
                    default:
                        throw new IllegalArgumentException("Illegal speed dial id");
                }
            }
        });
        speedDial.show();
    }

    private void saveFavorite() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null)
            startActivityForResult(new Intent(this, LoginDialogActivity.class), LOGIN_REQUEST_CODE);
        else {
            presenter.saveFavourite(result);
        }
    }

    public void launchDialog() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null)
            startActivityForResult(new Intent(this, LoginDialogActivity.class), LOGIN_REQUEST_CODE);
        else {
            launchChooserDialog();
        }
    }

    private void launchChooserDialog() {

        dialog = new Dialog(this, R.style.AppDialogTheme);
        dialog.setContentView(R.layout.dialog_chooser);
        dialog.findViewById(R.id.btn_camera).setOnClickListener(this);
        dialog.findViewById(R.id.btn_gallery).setOnClickListener(this);
        dialog.show();
    }

    public DetailActivityView getView() {
        return this;
    }

    private void goToNoteActivity() {
        Intent intent = new Intent(DetailActivity.this, NoteActivity.class);
        intent.putExtra(CommonHelper.PLACE_NAME, result.getName());
        intent.putExtra(CommonHelper.PLACE_ID, result.getPlaceId());
        startActivityForResult(intent, NOTE_ACT_REQUEST_CODE);
    }

    private void setupInjection() {
        MainApplication.getAppComponent()
                .newDetailComponent(new DetailApiModule(), new DetailModule(this))
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
    public void removeFavouriteOption() {
        speedDial.removeActionItemById(R.id.fab_favorite_label);
    }

    @Override
    public void setResult(Result result) {

        this.result = result;
        setupActionBar(result);
        if (result.getPhotos() != null) {
            String photoReference = result.getPhotos().get(0).getPhotoReference();
            imageLoader.load(header, collapsingToolbarLayout, Helper.generateUrl(photoReference));
        }
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), pageTitle.length, result, imageLoader);
        pager.setAdapter(adapter);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            presenter.checkIfFavourite(result);
        } else {
            setupSpeedDial(false);
        }
    }

    @Override
    public void seFavourite(boolean isFavourite) {
        setupSpeedDial(isFavourite);
        pgbDetail.setVisibility(View.GONE);
        pager.setVisibility(View.VISIBLE);
    }

    private void setupActionBar(Result result) {
        getSupportActionBar().setTitle(result.getName());
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());
        if (tab.getPosition() == 3) {
            appBar.setExpanded(false);
            speedDial.hide();
        } else {
            speedDial.show();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_camera:
                dialog.dismiss();
                dispatchTakePictureIntent();
                break;
            case R.id.btn_gallery:
                dialog.dismiss();
                gotoGalleryActivity();
                break;
            default:
                throw new IllegalArgumentException("Illegal view id");
        }
    }

    private void gotoGalleryActivity() {
        startActivityForResult(new Intent(this, GalleryActivity.class), GALLERY_REQUEST_CODE);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = Helper.createImageFile(this);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.upv.magicplace.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAPTURE_REQUEST_CODE);
            }
        }
    }
}
