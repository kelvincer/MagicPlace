package com.example.proyectomaster.note.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.Helper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.app.MainApplication;
import com.example.proyectomaster.dialog.LoginDialogActivity;
import com.example.proyectomaster.note.NoteActivityPresenter;
import com.example.proyectomaster.note.adapter.GaleryPhotoAdapter;
import com.example.proyectomaster.note.di.NoteActivityModule;
import com.example.proyectomaster.note.listener.OnGalleryItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteActivity extends AppCompatActivity implements NoteActivityView, OnGalleryItemClickListener {

    private static final String TAG = NoteActivity.class.getSimpleName();
    private static final int REQUEST_CAPTURE_IMAGE = 101;
    String placeId;
    @BindView(R.id.bottom_sheet)
    CoordinatorLayout bottomSheet;
    BottomSheetBehavior mBottomSheetBehavior;
    /* @BindView(R.id.gridview)
     GridView gridview;*/;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.ryv_photo_galery)
    RecyclerView ryvPhotoGalery;
    @BindView(R.id.lnl_text_photo)
    LinearLayout lnlTextPhoto;
    @BindView(R.id.igv_photo)
    ImageView igvPhoto;
    @BindView(R.id.fml_photo)
    FrameLayout fmlPhoto;
    @BindView(R.id.rtl_photo)
    RelativeLayout rtlPhoto;
    @BindView(R.id.igv_close)
    ImageView igvClose;
    @BindView(R.id.edt_comment)
    EditText edtComment;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @Inject
    NoteActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);

        setupViews();
        setupInjection();
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAPTURE_IMAGE &&
                resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                showPhoto(imageBitmap);
            }
        }
    }

    private void showPhoto(Bitmap imageBitmap) {
        hideBottomSheet();
        igvPhoto.setImageBitmap(imageBitmap);
        rtlPhoto.setVisibility(View.VISIBLE);
    }

    private void setupInjection() {

        /*DaggerNoteActivityComponent.builder()
                .noteActivityModule(new NoteActivityModule(this))
                .build()
                .inject(this);*/
        MainApplication.getAppComponent()
                .newNoteActivityComponent(new NoteActivityModule(this))
                .inject(this);
    }

    private void setupViews() {

        Bundle bundle = getIntent().getExtras();
        placeId = bundle.getString(CommonHelper.PLACE_ID);
        getSupportActionBar().setTitle(bundle.getString(CommonHelper.PLACE_NAME));
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        List<String> images = Helper.getAllShownImagesPath(this);

        ryvPhotoGalery.setLayoutManager(new GridLayoutManager(this, 3));
        ryvPhotoGalery.setAdapter(new GaleryPhotoAdapter(images, this, this));

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        edtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePhotoLayout();
            }
        });

        fmlPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCamera();
            }
        });

        igvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rtlPhoto.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.id_take_photo:
                openTakePhoto();
                break;
            case R.id.id_publish:
                publishNote();
                break;
            default:
                throw new IllegalArgumentException("Invalid menu id");
        }

        return super.onOptionsItemSelected(item);
    }

    private void publishNote() {

        if (edtComment.getText().toString().isEmpty()) {
            Snackbar.make(edtComment, "El mensaje no puede estar vacio", Snackbar.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, LoginDialogActivity.class));
        } else {

            BitmapDrawable bitmapDrawable = (BitmapDrawable) igvPhoto.getDrawable();
            if (bitmapDrawable != null) {
                Bitmap bitmap = bitmapDrawable.getBitmap();
                presenter.uploadPhotoAndNote(Helper.bitmapToByteArray(bitmap), edtComment.getText().toString(), placeId);
            } else {
                presenter.uploadOnlyNote(edtComment.getText().toString(), placeId);
            }
        }
    }

    private void openTakePhoto() {

        // Falta echarle un vistazp
        Helper.hideKeyboard(this, getCurrentFocus().getWindowToken());
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }, 100);
        //Falta echarle un vistazo

        /*Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.photos_bottom_up);
        RelativeLayout hiddenPanel = findViewById(R.id.hidden_panel);
        hiddenPanel.startAnimation(bottomUp);
        hiddenPanel.setVisibility(View.VISIBLE);*/

       /* Log.d(TAG, "height " + screenHeight);
        RelativeLayout hiddenPanel = findViewById(R.id.hidden_panel);
        hiddenPanel.setVisibility(View.VISIBLE);

        ObjectAnimator transAnimation = ObjectAnimator.ofInt(hiddenPanel, "translationY", screenHeight, screenHeight / 2);
        transAnimation.setDuration(15000);//
        transAnimation.setInterpolator(new LinearInterpolator());
        //findViewById(R.id.hidden_panel).setVisibility(View.VISIBLE);
        transAnimation.start();*/
    }

    private void closePhotoLayout() {

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        /*Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.photos_up_bottom);
        RelativeLayout hiddenPanel = findViewById(R.id.hidden_panel);
        hiddenPanel.startAnimation(bottomUp);
        hiddenPanel.setVisibility(View.VISIBLE);*/
        /*int screenHeight = getResources().getDisplayMetrics().heightPixels;

        Log.d(TAG, "height " + screenHeight);
        RelativeLayout hiddenPanel = findViewById(R.id.hidden_panel);
        hiddenPanel.setVisibility(View.VISIBLE);

        ObjectAnimator transAnimation = ObjectAnimator.ofInt(hiddenPanel, "translationY", screenHeight / 2, screenHeight);
        transAnimation.setDuration(2000);//set duration
        transAnimation.start();*/
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showProgressBar() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideKeyboard() {
        Helper.hideKeyboard(this, getCurrentFocus().getWindowToken());
    }

    @Override
    public void disableActivity() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void showCamera() {

        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pictureIntent,
                    REQUEST_CAPTURE_IMAGE);
        }
    }

    @Override
    public void onItemClickListener(String path) {
        hideBottomSheet();
        Glide.with(this).load(path).into(igvPhoto);
        rtlPhoto.setVisibility(View.VISIBLE);
    }

    private void hideBottomSheet() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
}
